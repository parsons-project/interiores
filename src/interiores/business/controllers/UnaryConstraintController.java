package interiores.business.controllers;

import interiores.business.controllers.abstracted.InterioresController;
import interiores.business.events.constraints.UnaryConstraintAddedEvent;
import interiores.business.events.constraints.UnaryConstraintRemovedEvent;
import interiores.business.models.Orientation;
import interiores.business.models.Room;
import interiores.business.models.constraints.UnaryConstraint;
import interiores.business.models.constraints.unary.AreaConstraint;
import interiores.business.models.constraints.unary.ColorConstraint;
import interiores.business.models.constraints.unary.DepthConstraint;
import interiores.business.models.constraints.unary.MaterialConstraint;
import interiores.business.models.constraints.unary.ModelConstraint;
import interiores.business.models.constraints.unary.OrientationConstraint;
import interiores.business.models.constraints.unary.PositionConstraint;
import interiores.business.models.constraints.unary.PriceConstraint;
import interiores.business.models.constraints.unary.WallConstraint;
import interiores.business.models.constraints.unary.WidthConstraint;
import interiores.core.data.JAXBDataController;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Business Controller covering the use cases related to constraints
 * @author larribas
 */
public class UnaryConstraintController
    extends InterioresController
{
    /**
     * Creates a particular instance of the unary constraint controller
     * @param data The data controller that will give access to the objects this controller will use
     */
    public UnaryConstraintController(JAXBDataController data) {
        super(data);
        
        // Define aliases for unary constraints
        enableConstraint("position", AreaConstraint.class);
        enableConstraint("color", ColorConstraint.class);
        enableConstraint("depth", DepthConstraint.class);
        enableConstraint("material", MaterialConstraint.class);
        enableConstraint("model", ModelConstraint.class);
        enableConstraint("orientation", OrientationConstraint.class);
        enableConstraint("price", PriceConstraint.class);
        enableConstraint("wall", WallConstraint.class);
        enableConstraint("width", WidthConstraint.class);
    }
    
    private void enableConstraint(String name, Class<? extends UnaryConstraint> uClass) {
        UnaryConstraint.addConstraintClass(name, uClass);
    }
    
    public Collection<String> getAvailableConstraints() {
        return UnaryConstraint.getConstraintNames();
    }
    
     /**
     * Gets all all the unary and binary constraints related to the specified wanted furniture
     * @param id Identifier of the furniture
     * @return A collection of both unary and binary constraints
     */
    public Collection<UnaryConstraint> getConstraints(String id) {
        return getWishList().getUnaryConstraints(id);
    }
    
    public void addWidthConstraint(String furnitureId, int minWidth, int maxWidth) {
        addConstraint(furnitureId, new WidthConstraint(minWidth, maxWidth));
    }
    
    public void addDepthConstraint(String furnitureId, int minWidth, int maxWidth) {
        addConstraint(furnitureId, new DepthConstraint(minWidth, maxWidth));
    }
    
    public void addColorConstraint(String furnitureId, String color) {
        addConstraint(furnitureId, new ColorConstraint(color));
    }
    
    public void addMaterialConstraint(String furnitureId, String material) {
        addConstraint(furnitureId, new MaterialConstraint(material));
    }
    
    public void addModelConstraint(String furnitureId, String modelName) {
        addConstraint(furnitureId, new ModelConstraint(modelName));
    }
    
    public void addOrientationConstraint(String furnitureId, String orientation) {
        addConstraint(furnitureId, new OrientationConstraint(orientation));
    }
    
    public void addPriceConstraint(String furnitureId, float maxPrice) {
        addConstraint(furnitureId, new PriceConstraint(maxPrice));
    }
    
    public void addPositionConstraint(String furnitureId, List<Point> validPositions) {
        addConstraint(furnitureId, new AreaConstraint(validPositions));
    }
    
    public void addPositionAtConstraint(String furnitureId, int x, int y) {
        addConstraint(furnitureId, new PositionConstraint(new Point(x, y)));
    }
    
    public void addPositionRangeConstraint(String furnitureId, int x1, int y1, int x2, int y2) {
        List<Point> validPositions = new ArrayList();
        
        for(int i = x1; i <= x2; ++i)
            for(int j = y1; j <= y2; ++j)
                validPositions.add(new Point(i, j));
        
        addPositionConstraint(furnitureId, validPositions);
    }
    
    public void addWallConstraint(String furnitureId, Orientation[] whichWalls) {
        Room room = getRoom();
        
        addConstraint(furnitureId, new WallConstraint(room.getWidth(), room.getDepth(), whichWalls));
    }
    
    private void addConstraint(String furnitureId, UnaryConstraint unaryConstraint) {
        getWishList().addUnaryConstraint(furnitureId, unaryConstraint);
        notify(new UnaryConstraintAddedEvent());
    }
    
    public void remove(String furnitureId, String unaryConstraintAlias) {
        Class<? extends UnaryConstraint> unaryConstraintClass;
        unaryConstraintClass = UnaryConstraint.getConstraintClass(unaryConstraintAlias);
        
        remove(furnitureId, unaryConstraintClass);
    }
    
    public void remove(String furnitureId, Class<? extends UnaryConstraint> unaryConstraintClass) {
        getWishList().removeUnaryConstraint(furnitureId, unaryConstraintClass);
        notify(new UnaryConstraintRemovedEvent());
    }
}
