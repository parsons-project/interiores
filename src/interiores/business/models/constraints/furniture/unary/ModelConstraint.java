package interiores.business.models.constraints.furniture.unary;

import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.constraints.furniture.UnaryConstraint;
import interiores.business.models.room.FurnitureModel;
import java.util.HashSet;
import java.util.Iterator;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * ModelConstraint represents a constraint imposed over the model of a piece of
 * furniture.
 * Since there are no repeted models, this restriction leaves only one model
 * as valid.
 */
@XmlRootElement
public class ModelConstraint
    extends UnaryConstraint {
    
    
    /** 
     * 'modelName' represents the exact name of the wanted model.
     */
    @XmlAttribute
    private String modelName;
    
    public ModelConstraint() {
        
    }
    
    /**
     * Creates a model constraint such that only the model with that name
     * can pass it.
     * @param modelName The model name that will define the constraint
     */
    public ModelConstraint(String modelName) {
        this.modelName = modelName;
    }
    
    /**
     * Eliminates models which do not satisfy the constraint.
     * @param variable The variable whose values have to be checked.
     */
    @Override
    public void preliminarTrim(FurnitureVariable variable) {
        HashSet<FurnitureModel> validModels = new HashSet(variable.getDomain().getModels(0));
        Iterator<FurnitureModel> it = validModels.iterator();
        while (it.hasNext()) {
            if (! it.next().getName().equals(modelName))
                it.remove();
        }
        
        variable.eliminateExceptM(validModels);
    }
    
    /**
     * Modifies the model name defined for the constraint.
     * @param newModelName The new name that will override the previous one
     */
    public void changeColor(String newModelName) {
        modelName = newModelName;
    }
    
    
    @Override
    public String toString() {
        return "Model: " + modelName;
    }
}