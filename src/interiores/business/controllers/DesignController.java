package interiores.business.controllers;

import interiores.business.controllers.abstracted.CatalogAccessController;
import interiores.business.models.backtracking.Solver;
import interiores.business.models.catalogs.AvailableCatalog;
import interiores.business.models.room.FurnitureType;
import interiores.business.models.room.elements.WantedFurniture;
import interiores.core.Observer;
import interiores.core.data.JAXBDataController;
import java.util.Collection;


/**
 * Business Controller covering the operations related to the design of a room, whether it is automatic or manual
 * @author alvaro
 */
public class DesignController
    extends CatalogAccessController<FurnitureType>
    implements Observer
{
    private Solver solver;
    
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
    public void solve(boolean debugMode, boolean timeIt) {
        if(solver == null) {
            solver = new Solver();
            solver.addListener(this);
        }
        
        solver.setDebug(debugMode);
        solver.setTimer(timeIt);
        
        solver.solve(getWishList(), getActiveCatalog());
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
        solver.continueSolving();
    }
    
    public void stop()
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
}
