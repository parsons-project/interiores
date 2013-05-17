/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models;

import interiores.business.models.constraints.UnaryConstraint;
import interiores.business.models.constraints.unary.PositionConstraint;
import interiores.utils.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 *
 * @author alvaro
 */
public class WantedFixed {
    String typeName;
    PositionConstraint positionConstraint;
    Dimension size;

    WantedFixed(String typeName, Point position, Dimension size) {
        this.typeName = typeName;
        this.positionConstraint = new PositionConstraint(position);
        this.size = size;
    }

    public String getName() {
        return typeName;
    }
    
    public Collection<UnaryConstraint> getUnaryConstraints() {
        return new ArrayList(Arrays.asList(positionConstraint));
    }
    
}
