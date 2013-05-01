package interiores.business.controllers;

import interiores.business.models.FurnitureModel;
import interiores.business.models.Room;
import interiores.business.models.WishList;
import interiores.business.models.backtracking.FurnitureVariableSet;
import interiores.business.models.constraints.UnaryConstraint;
import interiores.core.business.BusinessController;
import interiores.core.data.JAXBDataController;
import interiores.shared.backtracking.NoSolutionException;
import interiores.utils.BinaryConstraintAssociation;
import java.util.ArrayList;
import java.util.List;


/**
 * Business Controller covering the operations related to the design of a room, whether it is automatic or manual
 * @author alvaro
 */
public class DesignController extends BusinessController
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
    public void solve() {
        
        WishList wishList = (WishList) data.get("wishList");
        Room room = (Room) data.get("room");
        
        // First, we initialize the data structures VariableSet will use
        List<String> furniture = new ArrayList(wishList.getFurnitureNames());
        List<List<FurnitureModel>> variableModels = new ArrayList();
        List<List<UnaryConstraint>> variableConstraints = new ArrayList();
        
        // We add a list of models and unary constraints for each furniture in the list
        for (String wf : furniture) {
            variableModels.add(wishList.getWantedFurniture(wf).getType().getFurnitureModels());
            variableConstraints.add(new ArrayList<UnaryConstraint>(wishList.getWantedFurniture(wf).getConstraints()));
        }
        
        // Then, we obtain all the binary constraints
        List<BinaryConstraintAssociation> bcs = wishList.getBinaryConstraints();
        
        // Build a FurnitureVariableSet from scrath
        FurnitureVariableSet furVarSet = new FurnitureVariableSet(room, furniture, variableModels,
                                                                  variableConstraints, bcs);
        // And try to solve it
        try {
            furVarSet.solve();
            solutionFound = true;
            lastSolution = furVarSet.toString();
        }
        catch (NoSolutionException nse) {
            solutionFound = false;
        }
        
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
