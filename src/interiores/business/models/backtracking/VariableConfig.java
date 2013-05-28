package interiores.business.models.backtracking;

import interiores.business.models.Orientation;
import interiores.business.models.OrientedRectangle;
import interiores.business.models.room.FurnitureType;
import interiores.utils.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hector
 */
public class VariableConfig
{
    private Dimension areaSize;
    private List<FurnitureConstant> constants;
    private List<FurnitureVariable> assignedVariables;
    private List<FurnitureVariable> unassignedVariables;
    private Map<FurnitureVariable, FurnitureType> furnitureTypes;
    
    public VariableConfig(Dimension areaSize) {
        this.areaSize = areaSize;
        
        constants = new ArrayList();
        assignedVariables = new ArrayList();
        unassignedVariables = new ArrayList();
        furnitureTypes = new HashMap();
    }
    
    public void addVariable(FurnitureVariable variable, FurnitureType ftype) {
        if(variable.isAssigned())
            assignedVariables.add(variable);
        
        else {
            variable.undoAssignValue();
            unassignedVariables.add(variable);
        }
        
        furnitureTypes.put(variable, ftype);
    }
    
    public void addConstant(FurnitureConstant constant) {
        constants.add(constant);
    }
    
    public boolean isConsistent() {
        for(FurnitureVariable assignedVariable : assignedVariables)
            if(!assignedVariable.hasDomain())
                return false;
        
        return unassignedVariables.isEmpty();
    }
    
    public void resetDomains() {
        unassignedVariables.addAll(assignedVariables);
        assignedVariables.clear();
        
        for(FurnitureVariable unassignedVariable : unassignedVariables) {
            FurnitureType ftype = furnitureTypes.get(unassignedVariable);
            unassignedVariable.createDomain(ftype.getFurnitureModels(), areaSize, unassignedVariables.size());
        }
    }
    
    public List<FurnitureVariable> getAssignedVariables() {
        return assignedVariables;
    }
    
    public List<FurnitureVariable> getUnassignedVariables() {
        return unassignedVariables;
    }
    
    public List<FurnitureConstant> getConstants() {
        return constants;
    }
    
    public OrientedRectangle getTotalArea() {
        return new OrientedRectangle(new Point(0, 0), areaSize, Orientation.S);
    }
}
