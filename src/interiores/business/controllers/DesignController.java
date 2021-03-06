package interiores.business.controllers;

import interiores.business.controllers.abstracted.CatalogAccessController;
import interiores.business.exceptions.SolverNotFinishedException;
import interiores.business.models.backtracking.ThreadSolver;
import interiores.business.models.backtracking.ThreadSolverDebugger;
import interiores.business.models.backtracking.VariableConfig;
import interiores.business.models.catalogs.AvailableCatalog;
import interiores.business.models.constraints.room.GlobalConstraint;
import interiores.business.models.room.FurnitureType;
import interiores.business.models.room.elements.WantedFurniture;
import interiores.core.Observer;
import interiores.core.data.JAXBDataController;
import java.awt.Point;
import java.util.Collection;


/**
 * Business Controller covering the operations related to the design of a room, whether it is automatic or manual
 * @author alvaro
 */
public class DesignController
    extends CatalogAccessController<FurnitureType>
    implements Observer
{
    private ThreadSolver solver;
    
    /**
     * Creates a particular instance of the design controller
     * @param data The data controller that will give access to the objects this controller will use
     */
    public DesignController(JAXBDataController data) {
        super(data, AvailableCatalog.FURNITURE_TYPES);
        
        solver = new ThreadSolver(); // Set an empty solver
    }
    
    /**
     * Transforms the furniture and constraints defined in the wish list into proper types to execute
     * the automatic design generation algorithm. Then, it tries to solve this algorithm and it such
     * case, returns the solution.
     */
    public void solve(boolean debugMode, boolean timeIt) {
        if(solver.isSolving())
            throw new SolverNotFinishedException();
        
        VariableConfig variableConfig = getWishList().getVariableConfig(getActiveCatalog());
        
        if(debugMode || solver.shouldRenew(variableConfig)) {
            if(debugMode) {
                solver = new ThreadSolverDebugger(variableConfig);
            }
            else {
                solver = new ThreadSolver(variableConfig);
            }
            
            solver.addListener(this);
        }
        
        solver.setTimerEnabled(timeIt);
        solver.solve();
    }
    
    /**
     * Indicates if there is a design that contains all the furniture and satisfies all the constraints
     * @return boolean indicating if a solution has been found for the current wish list
     */
    public boolean isSolutionFound() {
        return solver.isSolutionFound();
    }
    
    public Collection<WantedFurniture> getDesignFurniture() {
        return getWishList().getWantedFurniture();
    }
    
    public void resumeSolver()
    {
        solver.resumeSolving();
    }

    public void stopSolver()
    {
        solver.stopSolving();
    }
    
    public boolean isSolverPaused() {
        return solver.isPaused();
    }
    
    public boolean isSolving()
    {
        return solver.isSolving();
    }
    
    public void setPosition(String furnitureName, Point position) {
        WantedFurniture wf = getWishList().getWantedFurniture(furnitureName);
        wf.setPosition(position);
    }
}
