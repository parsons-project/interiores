package interiores.business.controllers;

import interiores.business.models.FurnitureModel;
import interiores.business.models.Room;
import interiores.business.models.WantedFurniture;
import interiores.business.models.WishList;
import interiores.business.models.backtracking.FurnitureVariableSet;
import interiores.business.models.constraints.BinaryConstraintSet;
import interiores.business.models.constraints.UnaryConstraint;
import interiores.core.business.BusinessController;
import interiores.core.data.JAXBDataController;
import interiores.shared.backtracking.NoSolutionException;
import interiores.utils.BinaryConstraintAssociation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 *
 * @author alvaro
 */
public class DesignController extends BusinessController
{
    
    private boolean solutionFound = false;
    private String lastSolution;
    
    public DesignController(JAXBDataController data) {
        super(data);
    }
    
    
    public void solve() {
        
        WishList wishList = (WishList) data.get("wishList");
        Room room = (Room) data.get("room");
        
        List<String> furniture = new ArrayList(wishList.getFurnitureNames());
        List<List<FurnitureModel>> variableModels = new ArrayList();
        List<List<UnaryConstraint>> variableConstraints = new ArrayList();
        
        for (String wf : furniture) {
            variableModels.add(wishList.getWantedFurniture(wf).getType().getFurnitureModels());
            variableConstraints.add(new ArrayList<UnaryConstraint>(wishList.getWantedFurniture(wf).getConstraints()));
        }
        
        List<BinaryConstraintAssociation> bcs = wishList.getBinaryConstraints();
        
        FurnitureVariableSet furVarSet = new FurnitureVariableSet(room, furniture, variableModels,
                                                                  variableConstraints, bcs);
        try {
            furVarSet.solve();
            solutionFound = true;
            lastSolution = furVarSet.toString();
        }
        catch (NoSolutionException nse) {
            solutionFound = false;
        }
        
    }
    
    public boolean hasSolution() {
        return solutionFound;
    }
    
    public String getDesign() {
        return lastSolution;
    }
    
    
}
