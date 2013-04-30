package interiores.business.controllers;

import interiores.business.models.FurnitureModel;
import interiores.business.models.Room;
import interiores.business.models.WantedFurniture;
import interiores.business.models.backtracking.FurnitureVariableSet;
import interiores.business.models.constraints.BinaryConstraintSet;
import interiores.business.models.constraints.UnaryConstraint;
import interiores.core.business.BusinessController;
import interiores.core.data.JAXBDataController;
import interiores.shared.backtracking.NoSolutionException;
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
        
        Room room = (Room) data.get("room");
        
        List<WantedFurniture> wishList = room.getWishList();
        
        List<List<FurnitureModel>> variableModels = new ArrayList();
        List<List<UnaryConstraint>> variableConstraints = new ArrayList();
        
        for (WantedFurniture wf : wishList) {
            variableModels.add(wf.getType().getFurnitureModels());
            variableConstraints.add((List) wf.getConstraints());
        }
        
        FurnitureVariableSet furVarSet = new FurnitureVariableSet(room, variableModels,
                                                                  variableConstraints, new BinaryConstraintSet());
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
