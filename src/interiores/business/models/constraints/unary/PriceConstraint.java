package interiores.business.models.constraints.unary;

import interiores.business.models.room.FurnitureModel;
import interiores.business.models.backtracking.Domain;
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
    public void eliminateInvalidValues(Domain domain) {
        Iterator it = domain.getModels(0).iterator();
        while (it.hasNext()) {
            FurnitureModel model = (FurnitureModel) it.next();
            if (model.getPrice() > maxPrice)
                it.remove();
        }
    }
    
    @Override
    public String toString() {
        return "Max price: " + maxPrice;
        //return this.getClass().getName() + " Max price[" + maxPrice + "]";
    }
}
