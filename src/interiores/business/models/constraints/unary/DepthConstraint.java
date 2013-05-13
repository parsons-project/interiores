package interiores.business.models.constraints.unary;

import interiores.business.models.constraints.unary.SizeRangeConstraint;
import interiores.utils.Dimension;
import interiores.utils.Range;

/**
 *
 * @author hector
 */
public class DepthConstraint
    extends SizeRangeConstraint
{
    public DepthConstraint(int minDepth, int maxDepth) {
        super(Dimension.Component.DEPTH, new Range(minDepth, maxDepth));
    }
}
