package interiores.business.models.constraints.furniture.unary;

import interiores.utils.Dimension;
import interiores.utils.Range;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hector
 */
@XmlRootElement
public class DepthConstraint
    extends SizeRangeConstraint
{
    public DepthConstraint()
    { }
    
    public DepthConstraint(int minDepth, int maxDepth) {
        super(Dimension.Component.DEPTH, new Range(minDepth, maxDepth));
    }
}
