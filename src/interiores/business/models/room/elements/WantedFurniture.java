package interiores.business.models.room.elements;

import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.constraints.ConstraintIndex;
import interiores.business.models.constraints.furniture.BinaryConstraintEnd;
import interiores.business.models.constraints.furniture.UnaryConstraint;
import java.util.Collection;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * This class is the abstraction of a wanted element for the room
 * @author alvaro
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class WantedFurniture
    extends FurnitureVariable
{
    @XmlElementWrapper
    private ConstraintIndex<UnaryConstraint> unaryConstraints;
    
    @XmlElementWrapper
    private ConstraintIndex<BinaryConstraintEnd> binaryConstraints;
    
    @XmlTransient
    private boolean isBounding = false;
    
    public WantedFurniture()
    { }
    
    /**
     * Simple creator. Creates a wanted element given its type
     * @param ft The funiture type of this wanted element
     */
    public WantedFurniture(String typeName) {
        super(typeName);
        
        unaryConstraints = new ConstraintIndex<UnaryConstraint>();
        binaryConstraints = new ConstraintIndex<BinaryConstraintEnd>();
    }
    
    public void addUnaryConstraint(UnaryConstraint unaryConstraint) {
        unaryConstraints.add(unaryConstraint, furnitureConstraints);
    }
    
    public void removeUnaryConstraint(Class<? extends UnaryConstraint> unaryConstraintClass) {
        unaryConstraints.remove(unaryConstraintClass, furnitureConstraints);
    }
    
    public UnaryConstraint getUnaryConstraint(Class<? extends UnaryConstraint> unaryConstraintClass) {
        return unaryConstraints.get(unaryConstraintClass, furnitureConstraints);
    }
    
    public Collection<UnaryConstraint> getUnaryConstraints() {
        return unaryConstraints.getAll(furnitureConstraints);
    }
    
    public boolean isBounding() {
        return isBounding;
    }
    
    public void bound(BinaryConstraintEnd binaryConstraint)
        throws CloneNotSupportedException
    {
        isBounding = true; // This avoids infinite bounding :/
        binaryConstraint.bound(this);
        isBounding = false;
        
        addBinaryConstraint(binaryConstraint);
    }
    
    public void unbound(Class<? extends BinaryConstraintEnd> binaryConstraintClass) {
        BinaryConstraintEnd binaryConstraint = (BinaryConstraintEnd) furnitureConstraints.get(
                binaryConstraintClass);
        
        binaryConstraint.unbound();
        
        removeBinaryConstraint(binaryConstraintClass);
    }
    
    public void addBinaryConstraint(BinaryConstraintEnd binaryConstraint) {
        binaryConstraints.add(binaryConstraint, furnitureConstraints);
    }
    
    public void removeBinaryConstraint(Class<? extends BinaryConstraintEnd> binaryConstraintClass) {
        binaryConstraints.remove(binaryConstraintClass, furnitureConstraints);
    }
    
    public BinaryConstraintEnd getBinaryConstraint(Class<? extends BinaryConstraintEnd> binaryConstraintClass)
    {
        return binaryConstraints.get(binaryConstraintClass, furnitureConstraints);
    }
    
    public Collection<BinaryConstraintEnd> getBinaryConstraints() {
        return binaryConstraints.getAll(furnitureConstraints);
    }
}
