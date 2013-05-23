package interiores.business.models.constraints.unary;

import interiores.utils.Dimension;
import interiores.utils.Range;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hector
 */
@XmlRootElement
public class WidthConstraint
    extends SizeRangeConstraint
{
    public WidthConstraint()
    { }
    
    public WidthConstraint(int minWidth, int maxWidth) {
        super(Dimension.Component.WIDTH, new Range(minWidth, maxWidth));
    }
}
