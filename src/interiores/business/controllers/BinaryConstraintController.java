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
 * Business controller that manages the operations related to creating, consulting
 * removing or modifying binary constraints
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
    
    /**
     * @return The default constraint separator character 
     */
    public String getConstraintTypeSeparator() {
        return SEPARATOR_CONSTRAINT_TYPE;
    }
    
    /**
     * Adds a constraint of maximum distance between two existent wanted furniture
     * @param furnitureId1 The first piece of furniture the constraint will be imposed over
     * @param furnitureId2 The second piece of furniture the constraint will be imposed over
     * @param distance The distance they should hold in between
     */
    public void addMaxDistanceConstraint(String furnitureId1, String furnitureId2, int distance)
    {
        getWishList().addBinaryConstraint(new MaxDistanceConstraint(distance), furnitureId1, furnitureId2);
        notify(new BinaryConstraintAddedEvent());
    }
    
    /**
     * Adds a constraint of minimum distance between two existent wanted furniture
     * @param furnitureId1 The first piece of furniture the constraint will be imposed over
     * @param furnitureId2 The second piece of furniture the constraint will be imposed over
     * @param distance The distance one should be within the other's range
     */
    public void addMinDistanceConstraint(String furnitureId1, String furnitureId2, int distance)
    {
        getWishList().addBinaryConstraint(new MinDistanceConstraint(distance), furnitureId1, furnitureId2);
        notify(new BinaryConstraintAddedEvent());
    }
    
    /**
     * Adds a constraint of straight facing between two existent wanted furniture
     * @param furnitureId1 The first piece of furniture the constraint will be imposed over
     * @param furnitureId2 The second piece of furniture the constraint will be imposed over
     */
    public void addStraightFacingConstraint(String furnitureId1, String furnitureId2)
    {
        getWishList().addBinaryConstraint(new StraightFacingConstraint(), furnitureId1, furnitureId2);
        notify(new BinaryConstraintAddedEvent());
    }
    
    /**
     * Adds a constraint of partial facing between two existent wanted furniture
     * @param furnitureId1 The first piece of furniture the constraint will be imposed over
     * @param furnitureId2 The second piece of furniture the constraint will be imposed over
     */
    public void addPartialFacingConstraint(String furnitureId1, String furnitureId2)
    {
        getWishList().addBinaryConstraint(new PartialFacingConstraint(), furnitureId1, furnitureId2);
        notify(new BinaryConstraintAddedEvent());
    }
    
    /**
     * Removes a constraint of a determined type imposed over a certain piece of furniture
     * @param furnitureId The piece of furniture that holds the constraint
     * @param constraintAlias The constraint's alias
     */
    public void removeConstraint(String furnitureId, String constraintAlias)
    {
        Class binaryConstraintClass = BinaryConstraintEnd.getConstraintClass(constraintAlias);
        
        removeConstraint(furnitureId, binaryConstraintClass);
    }
    
    /**
     * Removes a determined constraint imposed over a certain piece of furniture
     * @param furnitureId The piece of furniture that holds the constraint
     * @param binaryConstraintClass The very constraint to remove
     */
    public void removeConstraint(String furnitureId,
            Class<? extends BinaryConstraintEnd> binaryConstraintClass)
    {
        getWishList().removeBinaryConstraint(binaryConstraintClass, furnitureId);
        notify(new BinaryConstraintRemovedEvent());
    }

    /**
     * Obtains all the binary constraints that affect a certain piece of furniture
     * @param furn The piece of furniture whose constraints we want to know
     * @return A collection of BinaryConstraintEnds (i.e. the constraints themselves)
     */
    public Collection<BinaryConstraintEnd> getConstraints(String furn)
    {
        return getWishList().getBinaryConstraints(furn);
    }
    
    /**
     * Obtains the names of all the available types of binary constraints
     * @return A collection of string containing the names of all the binary constraints
     */
    public Collection<String> getAvailableConstraints() {
        return BinaryConstraintEnd.getConstraintClasses();
    }
}
