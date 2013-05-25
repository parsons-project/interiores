package interiores.business.controllers;

import interiores.business.controllers.abstracted.InterioresController;
import interiores.business.exceptions.NoRoomCreatedException;
import interiores.business.exceptions.WantedElementNotFoundException;
import interiores.business.models.constraints.furniture.BinaryConstraint;
import interiores.business.models.constraints.furniture.binary.MaxDistanceConstraint;
import interiores.business.models.constraints.furniture.binary.MinDistanceConstraint;
import interiores.business.models.constraints.furniture.binary.PartialFacingConstraint;
import interiores.business.models.constraints.furniture.binary.StraightFacingConstraint;
import interiores.core.business.BusinessException;
import interiores.core.data.JAXBDataController;
import interiores.utils.BinaryConstraintAssociation;
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
    
    private void enableConstraint(String name, String type, Class<? extends BinaryConstraint> bClass) {
        BinaryConstraint.addConstraintClass(name + SEPARATOR_CONSTRAINT_TYPE + type, bClass);
    }
    
    public String getConstraintTypeSeparator() {
        return SEPARATOR_CONSTRAINT_TYPE;
    }
    
    public void addMaxDistanceConstraint(String furnitureId1, String furnitureId2, int distance)
            throws NoRoomCreatedException, WantedElementNotFoundException
    {
        getWishList().addBinaryConstraint(new MaxDistanceConstraint(distance), furnitureId1, furnitureId2);
    }
    
    public void addMinDistanceConstraint(String furnitureId1, String furnitureId2, int distance)
            throws NoRoomCreatedException, WantedElementNotFoundException
    {
        getWishList().addBinaryConstraint(new MinDistanceConstraint(distance), furnitureId1, furnitureId2);
    }
    
    public void addStraightFacingConstraint(String furnitureId1, String furnitureId2)
            throws NoRoomCreatedException, WantedElementNotFoundException
    {
        getWishList().addBinaryConstraint(new StraightFacingConstraint(), furnitureId1, furnitureId2);
    }
    
    public void addPartialFacingConstraint(String furnitureId1, String furnitureId2)
            throws NoRoomCreatedException, WantedElementNotFoundException
    {
        getWishList().addBinaryConstraint(new PartialFacingConstraint(), furnitureId1, furnitureId2);
    }
    
    public void removeConstraint(String furnitureId1, String furnitureId2, String constraintAlias)
            throws NoRoomCreatedException, BusinessException
    {
        Class binaryConstraintClass = BinaryConstraint.getConstraintClass(constraintAlias);
        
        removeConstraint(furnitureId1, furnitureId2, binaryConstraintClass);
    }
    
    public void removeConstraint(String furnitureId1, String furnitureId2,
            Class<? extends BinaryConstraint> binaryConstraintClass)
            throws NoRoomCreatedException
    {
        getWishList().removeBinaryConstraint(binaryConstraintClass, furnitureId1, furnitureId2);
    }

    public Collection<BinaryConstraintAssociation> getConstraints(String furn)
            throws NoRoomCreatedException, WantedElementNotFoundException
    {
        return getWishList().getBinaryConstraints(furn);
    }
    
    public Collection<String> getAvailableConstraints() {
        return BinaryConstraint.getConstraintClasses();
    }
    
}
