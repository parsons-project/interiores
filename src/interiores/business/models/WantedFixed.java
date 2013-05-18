/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models;

import interiores.business.models.constraints.unary.PositionConstraint;
import interiores.core.business.BusinessException;
import interiores.utils.Dimension;
import java.awt.Point;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author alvaro
 */
public class WantedFixed extends WantedElement {
    
    Dimension size;
    SpaceAround space;
    String color;
    String material;

    public WantedFixed(String name, String typeName, Point position, Dimension size,
                String color, String material, SpaceAround space) {
        super(name, typeName);
        super.addUnaryConstraint(new PositionConstraint(position));
        this.size = size;
        this.color = color;
        this.material = material;
        this.space = space;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<FurnitureModel> getModels() throws BusinessException {
        return Arrays.asList(new FurnitureModel(name, size, 0, color, material, space));
    }
    
}
