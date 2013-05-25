package interiores.business.models.constraints.furniture.unary;

import interiores.business.models.FurnitureModel;
import interiores.business.models.backtracking.Domain;
import interiores.business.models.constraints.furniture.UnaryConstraint;
import interiores.utils.Dimension;
import interiores.utils.Range;
import java.util.Iterator;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * SizeConstraint represents a constraint imposed over the size of a piece of furniture
 * @author larribas
 */
@XmlRootElement
@XmlSeeAlso({WidthConstraint.class, DepthConstraint.class})
abstract public class SizeRangeConstraint
    extends UnaryConstraint {
    
    @XmlElement
    private Dimension.Component component;
    
    @XmlElement
    private Range range;
    
    public SizeRangeConstraint(Dimension.Component component, Range range) {
        this.component = component;
        this.range = range;
    }
    
    public SizeRangeConstraint(Dimension.Component component, int minRange, int maxRange) {
        this(component, new Range(minRange, maxRange));
    }
    
    /**
     * Eliminates models which do not satisfy the constraint.
     * @param variable The variable whose values have to be checked.
     */
    @Override
    public void eliminateInvalidValues(Domain domain) {
        Iterator it = domain.getModels(0).iterator();
        
        while(it.hasNext()) {
            FurnitureModel model = (FurnitureModel) it.next();
            
            if(! model.getSize().isBetween(component, range))
                it.remove();
        }
    }
    
    @Override
    public String toString() {
        return getClass().getName() + " Range[" + range + "]";
    }
}
