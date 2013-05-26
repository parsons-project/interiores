package interiores.business.controllers;

import interiores.business.controllers.abstracted.CatalogAccessController;
import interiores.business.events.backtracking.DebugSolveDesignStartedEvent;
import interiores.business.events.backtracking.SolveDesignFinishedEvent;
import interiores.business.events.backtracking.SolveDesignStartedEvent;
import interiores.business.events.room.RoomDesignChangedEvent;
import interiores.business.exceptions.SolverNotFinishedException;
import interiores.business.models.RoomDesign;
import interiores.business.models.WishList;
import interiores.business.models.backtracking.FurnitureVariableSet;
import interiores.business.models.backtracking.FurnitureVariableSetDebugger;
import interiores.business.models.catalogs.AvailableCatalog;
import interiores.business.models.constraints.room.global.SpaceRespectingConstraint;
import interiores.business.models.constraints.room.global.UnfitModelsPseudoConstraint;
import interiores.business.models.room.FurnitureType;
import interiores.core.Observer;
import interiores.core.data.JAXBDataController;
import interiores.shared.backtracking.NoSolutionException;
import java.util.Set;


/**
 * Business Controller covering the operations related to the design of a room, whether it is automatic or manual
 * @author alvaro
 */
public class DesignController
    extends CatalogAccessController<FurnitureType>
    implements Observer
{
    private boolean solutionFound = false;
    private long time = -1;
    private Thread solver;
    
    /**
     * Creates a particular instance of the design controller
     * @param data The data controller that will give access to the objects this controller will use
     */
    public DesignController(JAXBDataController data) {
        super(data, AvailableCatalog.FURNITURE_TYPES);
    }
    
    /**
     * Transforms the furniture and constraints defined in the wish list into proper types to execute
     * the automatic design generation algorithm. Then, it tries to solve this algorithm and it such
     * case, returns the solution.
     */
    public void solve(boolean timeIt) {
        if(isSolving())
            throw new SolverNotFinishedException();
                
        WishList wishList = getWishList();
        FurnitureVariableSet furVarSet = new FurnitureVariableSet(wishList, getActiveCatalog());
        
        computeSolution(furVarSet, timeIt);
    }
    
    public void debug(boolean timeIt) {
        if(isSolving())
            throw new SolverNotFinishedException();
        
        WishList wishList = getWishList();
        FurnitureVariableSetDebugger furVarSetDebug = new FurnitureVariableSetDebugger(wishList,
                getActiveCatalog());
        furVarSetDebug.addListener(this);
        
        notify(new DebugSolveDesignStartedEvent());
        computeSolution(furVarSetDebug, timeIt);
    }
    
    private void computeSolution(final FurnitureVariableSet furVarSet, final boolean timeIt)
    {
        final DesignController me = this;
        
        solver = new Thread(){
            @Override
            public void run() {
                // @TODO Refactorize. Create a FurnitureVariableSetFactory
                furVarSet.addPreliminarTrimmer(new SpaceRespectingConstraint());
                furVarSet.addPreliminarTrimmer(new UnaryConstraintsPreliminarTrimmer());
                furVarSet.addPreliminarTrimmer(new UnfitModelsPseudoConstraint());

                final SolveDesignFinishedEvent solveFinished = new SolveDesignFinishedEvent();

                if (timeIt) time = System.nanoTime();
                // And try to solve it
                try {
                    me.notify(new SolveDesignStartedEvent());
                    furVarSet.solve();
                    solutionFound = true;
                    solveFinished.solutionFound();

                    getRoom().setDesign(new RoomDesign(furVarSet.getVariableValues()));
                    
                    me.notify(new RoomDesignChangedEvent());
                }
                catch (NoSolutionException nse) {
                    solutionFound = false;
                }

                if (timeIt) solveFinished.setTime(System.nanoTime() - time);
                me.notify(solveFinished);
            }
        };
        
        solver.start();
    }
    
    /**
     * Indicates if there is a design that contains all the furniture and satisfies all the constraints
     * @return boolean indicating if a solution has been found for the current wish list
     */
    public boolean hasSolution() {
        return solutionFound;
    }
    
    /**
     * Gets a text representation of the generated design
     * @return A String containing a text representation of the design
     */
    public RoomDesign getDesign() {
        return getRoom().getDesign();
    }
    
    public Set getDesignFurniture() {
        return getDesign().getEntries();
    }
    
    public void resumeSolver()
    {
        synchronized (solver) {
            solver.notify();
        }
    }
    
    public void stop()
    {
        resumeSolver();
        
        solver.interrupt();
    }
    
    public boolean isSolverPaused() {
        return (solver.getState() == Thread.State.WAITING);
    }
    
    public boolean isSolving()
    {
        return (solver != null && solver.isAlive());
    }
}
