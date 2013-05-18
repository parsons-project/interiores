/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.constraints.unary;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author alvaro
 */
public class PositionConstraint extends AreaConstraint {
    
    public PositionConstraint(Point point) {
        super(new ArrayList(Arrays.asList(point)));
    }
    
}
