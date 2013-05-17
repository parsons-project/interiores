package interiores.business.models.constraints.unary;

import interiores.business.models.FurnitureModel;
import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.constraints.UnaryConstraint;
import java.util.Iterator;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * PriceConstraint represents a constraint imposed over the highest price a
 * piece of furniture can cost
 * @author larribas
 */
@XmlRootElement
public class PriceConstraint
    extends UnaryConstraint {
    
    /**
     * 'maxPrice' represents the topmost price a furniture model should cost
     * inorder to satisfy the constraint
     */
    @XmlAttribute
    float maxPrice;
    
    public PriceConstraint() {
        
    }
    
    /**
     * Creates a price constraint such that only those pieces of furniture which cost
     * 'maxPrice' or less will satisfy it
     * @param maxPrice The topmost price the constraint will set
     */
    public PriceConstraint(float maxPrice) {
        this.maxPrice = maxPrice;
    }
   
    
    /**
     * Eliminates models which do not satisfy the constraint.
     * @param variable The variable whose values have to be checked.
     */
    @Override
    public void eliminateInvalidValues(FurnitureVariable variable) {
        Iterator it = variable.domainModels.iterator();
        while (it.hasNext()) {
            FurnitureModel model = (FurnitureModel) it.next();
            if (model.getPrice() > maxPrice)
                it.remove();
        }
    }
    
    
    /**
     * Modifies the maximum price defined for the constraint
     * @param newMaxPrice 
     */
    public void changePrice(float newMaxPrice) {
        maxPrice = newMaxPrice;
    }
    
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");

        result.append(this.getClass().getName() + NEW_LINE);

        result.append("Price: " + maxPrice + NEW_LINE);
        return result.toString();
    }
        
}
