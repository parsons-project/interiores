package interiores.business.controllers;

import interiores.business.controllers.abstracted.CatalogAccessController;
import interiores.business.events.room.DebugRoomDesignStartedEvent;
import interiores.business.events.room.RoomDesignFinishedEvent;
import interiores.business.events.room.RoomDesignStartedEvent;
import interiores.business.models.FurnitureType;
import interiores.business.models.WishList;
import interiores.business.models.backtracking.FurnitureVariableSet;
import interiores.business.models.backtracking.FurnitureVariableSetDebugger;
import interiores.business.models.backtracking.trimmers.preliminar.ConstantPreliminarTrimmer;
import interiores.business.models.backtracking.trimmers.preliminar.UnaryConstraintsPreliminarTrimmer;
import interiores.business.models.backtracking.trimmers.preliminar.UnfitModelsPreliminarTrimmer;
import interiores.business.models.catalogs.AvailableCatalog;
import interiores.core.Observer;
import interiores.core.business.BusinessException;
import interiores.core.data.JAXBDataController;
import interiores.shared.backtracking.NoSolutionException;


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
    private String lastSolution;
    private FurnitureVariableSetDebugger furVarSetDebug;
    
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
    public void solve(boolean timeIt)
            throws BusinessException
    {
        WishList wishList = getWishList();
        FurnitureVariableSet furVarSet = new FurnitureVariableSet(wishList, getActiveCatalog());
        
        computeSolution(furVarSet, timeIt);
    }
    
    public void debug(boolean timeIt)
            throws BusinessException
    {     
        WishList wishList = getWishList();
        furVarSetDebug = new FurnitureVariableSetDebugger(wishList, getActiveCatalog());
        furVarSetDebug.addListener(this);
        
        notify(new DebugRoomDesignStartedEvent());
        computeSolution(furVarSetDebug, timeIt);
    }
    
    private void computeSolution(FurnitureVariableSet furVarSet, boolean timeIt)
    {
        // @TODO Refactorize. Create a FurnitureVariableSetFactory
        furVarSet.addPreliminarTrimmer(new ConstantPreliminarTrimmer());
        furVarSet.addPreliminarTrimmer(new UnaryConstraintsPreliminarTrimmer());
        furVarSet.addPreliminarTrimmer(new UnfitModelsPreliminarTrimmer());
        
        RoomDesignFinishedEvent roomDesigned = new RoomDesignFinishedEvent();

        if (timeIt) time = System.nanoTime();
        // And try to solve it
        try {
            notify(new RoomDesignStartedEvent());
            furVarSet.solve();
            solutionFound = true;
            lastSolution = furVarSet.toString();
            
            
            roomDesigned.setDesign(furVarSet.getValues());
        }
        catch (NoSolutionException nse) {
            solutionFound = false;
        }
        
        if (timeIt) roomDesigned.setTime(System.nanoTime() - time);
        notify(roomDesigned);
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
    public String getDesign() {
        return lastSolution;
    }
    
    public void resumeSolver()
    {
        synchronized (furVarSetDebug) {
            furVarSetDebug.notify();
        }
    }
    
    public void stop()
    {
        furVarSetDebug.stop();
    }
}
