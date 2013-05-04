package interiores.business.controllers;

import interiores.business.controllers.abstracted.InterioresController;
import interiores.business.exceptions.NoRoomCreatedException;
import interiores.business.models.FurnitureModel;
import interiores.business.models.Room;
import interiores.business.models.WishList;
import interiores.business.models.backtracking.FurnitureVariableSet;
import interiores.business.models.constraints.UnaryConstraint;
import interiores.core.Observer;
import interiores.core.data.JAXBDataController;
import interiores.shared.backtracking.NoSolutionException;
import interiores.utils.BinaryConstraintAssociation;
import java.util.ArrayList;
import java.util.List;


/**
 * Business Controller covering the operations related to the design of a room, whether it is automatic or manual
 * @author alvaro
 */
public class DesignController
    extends InterioresController
    implements Observer
{
    
    private boolean solutionFound = false;
    private String lastSolution;
    
    /**
     * Creates a particular instance of the design controller
     * @param data The data controller that will give access to the objects this controller will use
     */
    public DesignController(JAXBDataController data) {
        super(data);
    }
    
    /**
     * Transforms the furniture and constraints defined in the wish list into proper types to execute
     * the automatic design generation algorithm. Then, it tries to solve this algorithm and it such
     * case, returns the solution.
     */
    public void solve()
            throws NoRoomCreatedException
    {
        
        WishList wishList = getWishList();
        Room room = getRoom();
        
        FurnitureVariableSet furVarSet = new FurnitureVariableSet(room, wishList);
        
        // And try to solve it
        try {
            notify("designStarted");
            furVarSet.solve();
            solutionFound = true;
            lastSolution = furVarSet.toString();
            
            
            notify("roomDesigned", "design", furVarSet.getValues());
        }
        catch (NoSolutionException nse) {
            solutionFound = false;
        }
        notify("designFinished", "isFound", solutionFound);
        
    }
    
    public void debug() {
        
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
}
