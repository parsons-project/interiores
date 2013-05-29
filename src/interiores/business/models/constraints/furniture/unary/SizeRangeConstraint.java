package interiores.business.models.constraints.furniture.unary;

import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.constraints.furniture.UnaryConstraint;
import interiores.business.models.room.FurnitureModel;
import interiores.utils.Dimension;
import interiores.utils.Range;
import java.util.HashSet;
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
    
    public SizeRangeConstraint()
    { }
    
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
    public void preliminarTrim(FurnitureVariable variable) {
        HashSet<FurnitureModel> validModels = new HashSet(variable.getDomain().getModels(0));
        Iterator<FurnitureModel> it = validModels.iterator();
        while (it.hasNext()) {
            if (! it.next().getSize().isBetween(component, range))
                it.remove();
        }        
        variable.eliminateExceptM(validModels);
    }       
    
    protected String toString(String name) {
        return name + range;
    }
    
    @Override
    public String toString() {
        return toString("Range: ");
        //return getClass().getName() + " Range[" + range + "]";
    }
}
