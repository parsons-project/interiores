package interiores.business.controllers;

import interiores.business.controllers.abstracted.InterioresController;
import interiores.business.exceptions.NoRoomCreatedException;
import interiores.business.models.Room;
import interiores.business.models.WishList;
import interiores.business.models.backtracking.FurnitureVariableSet;
import interiores.business.models.backtracking.FurnitureVariableSetDebugger;
import interiores.core.Observer;
import interiores.core.data.JAXBDataController;
import interiores.shared.backtracking.NoSolutionException;


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
        
        computeSolution(furVarSet);
    }
    
    public void debug()
            throws NoRoomCreatedException
    {     
        WishList wishList = getWishList();
        Room room = getRoom();
        
        FurnitureVariableSetDebugger debugFurVarSet = new FurnitureVariableSetDebugger(room, wishList);
        debugFurVarSet.addListener(this);
        
        notify("debugDesignStarted");
        computeSolution(debugFurVarSet);
    }
    
    private void computeSolution(FurnitureVariableSet furVarSet)
    {
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
