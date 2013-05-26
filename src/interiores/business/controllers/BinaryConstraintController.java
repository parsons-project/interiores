package interiores.business.controllers;

import interiores.business.controllers.abstracted.InterioresController;
import interiores.business.events.constraints.BinaryConstraintAddedEvent;
import interiores.business.events.constraints.BinaryConstraintRemovedEvent;
import interiores.business.models.constraints.furniture.BinaryConstraintEnd;
import interiores.business.models.constraints.furniture.binary.MaxDistanceConstraint;
import interiores.business.models.constraints.furniture.binary.MinDistanceConstraint;
import interiores.business.models.constraints.furniture.binary.PartialFacingConstraint;
import interiores.business.models.constraints.furniture.binary.StraightFacingConstraint;
import interiores.core.data.JAXBDataController;
import java.util.Collection;

/**
 *
 * @author larribas
 */
public class BinaryConstraintController
    extends InterioresController
{
    private static String SEPARATOR_CONSTRAINT_TYPE = "-";
    /**
     * Creates a particular instance of the binary constraint controller
     * @param data The data controller that will give access to the objects this controller will use
     */
    public BinaryConstraintController(JAXBDataController data)
    {
        super(data);
        
        // Define aliases for available constraints
        enableConstraint("distance", "max", MaxDistanceConstraint.class);
        enableConstraint("distance", "min", MinDistanceConstraint.class);
        enableConstraint("facing", "straight", StraightFacingConstraint.class);
        enableConstraint("facing", "partial", PartialFacingConstraint.class);
    }
    
    private void enableConstraint(String name, String type, Class<? extends BinaryConstraintEnd> bClass) {
        BinaryConstraintEnd.addConstraintClass(name + SEPARATOR_CONSTRAINT_TYPE + type, bClass);
    }
    
    public String getConstraintTypeSeparator() {
        return SEPARATOR_CONSTRAINT_TYPE;
    }
    
    public void addMaxDistanceConstraint(String furnitureId1, String furnitureId2, int distance)
    {
        getWishList().addBinaryConstraint(new MaxDistanceConstraint(distance), furnitureId1, furnitureId2);
        notify(new BinaryConstraintAddedEvent());
    }
    
    public void addMinDistanceConstraint(String furnitureId1, String furnitureId2, int distance)
    {
        getWishList().addBinaryConstraint(new MinDistanceConstraint(distance), furnitureId1, furnitureId2);
        notify(new BinaryConstraintAddedEvent());
    }
    
    public void addStraightFacingConstraint(String furnitureId1, String furnitureId2, int distance)
    {
        getWishList().addBinaryConstraint(new StraightFacingConstraint(distance), furnitureId1, furnitureId2);
        notify(new BinaryConstraintAddedEvent());
    }
    
    public void addPartialFacingConstraint(String furnitureId1, String furnitureId2, int distance)
    {
        getWishList().addBinaryConstraint(new PartialFacingConstraint(distance), furnitureId1, furnitureId2);
        notify(new BinaryConstraintAddedEvent());
    }
    
    public void removeConstraint(String furnitureId, String constraintAlias)
    {
        Class binaryConstraintClass = BinaryConstraintEnd.getConstraintClass(constraintAlias);
        
        removeConstraint(furnitureId, binaryConstraintClass);
    }
    
    public void removeConstraint(String furnitureId,
            Class<? extends BinaryConstraintEnd> binaryConstraintClass)
    {
        getWishList().removeBinaryConstraint(binaryConstraintClass, furnitureId);
        notify(new BinaryConstraintRemovedEvent());
    }

    public Collection<BinaryConstraintEnd> getConstraints(String furn)
    {
        return getWishList().getBinaryConstraints(furn);
    }
    
    public Collection<String> getAvailableConstraints() {
        return BinaryConstraintEnd.getConstraintClasses();
    }
}
