package interiores.business.models.constraints.furniture.unary;

import interiores.business.models.constraints.furniture.unary.SizeRangeConstraint;
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
