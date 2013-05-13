package interiores.business.models.constraints.unary;

import interiores.business.models.constraints.unary.SizeRangeConstraint;
import interiores.utils.Dimension;
import interiores.utils.Range;

/**
 *
 * @author hector
 */
public class WidthConstraint
    extends SizeRangeConstraint
{
    public WidthConstraint(int minWidth, int maxWidth) {
        super(Dimension.Component.WIDTH, new Range(minWidth, maxWidth));
    }
}
