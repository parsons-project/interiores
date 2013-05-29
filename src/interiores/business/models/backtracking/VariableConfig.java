package interiores.business.models.backtracking;

import interiores.business.models.Orientation;
import interiores.business.models.OrientedRectangle;
import interiores.business.models.room.FurnitureType;
import interiores.core.Debug;
import interiores.utils.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
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
    private List<FurnitureVariable> variables;
    private Map<FurnitureVariable, FurnitureType> furnitureTypes;
    
    public VariableConfig(Dimension areaSize) {
        this.areaSize = areaSize;
        
        constants = new ArrayList();
        variables = new ArrayList();
        furnitureTypes = new HashMap();
    }
    
    public void addVariable(FurnitureVariable variable, FurnitureType ftype) {
        variables.add(variable);
        furnitureTypes.put(variable, ftype);
    }
    
    public void addConstant(FurnitureConstant constant) {
        constants.add(constant);
    }
    
    public boolean isConsistent() {
        for(FurnitureVariable assignedVariable : variables)
            if(!assignedVariable.hasDomain() || !assignedVariable.isAssigned())
                return false;
        
        return true;
    }
    
    public void resetDomains() {
        for(FurnitureVariable variable : variables) {
            FurnitureType ftype = furnitureTypes.get(variable);
            variable.createDomain(ftype.getFurnitureModels(), areaSize, variables.size());
        }
    }
    
    public List<FurnitureVariable> getVariables() {
        return new ArrayList(variables);
    }
    
    public List<FurnitureConstant> getConstants() {
        return new ArrayList(constants);
    }
    
    public OrientedRectangle getTotalArea() {
        return new OrientedRectangle(new Point(0, 0), areaSize, Orientation.S);
    }
    
    public boolean equals(VariableConfig other) {
        if(! areaSize.equals(other.areaSize))
            return false;
        
        if(! constants.equals(other.constants))
            return false;
        
        if(! variables.equals(other.variables))
            return false;
        
        return true;
    }
}
