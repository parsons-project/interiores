package interiores.business.models.room.elements;

import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.constraints.Constraint;
import interiores.business.models.constraints.ConstraintIndex;
import interiores.business.models.constraints.furniture.BinaryConstraintEnd;
import interiores.business.models.constraints.furniture.UnaryConstraint;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
    private Map<Class, Constraint> constraints;
    
    @XmlElement
    private ConstraintIndex<UnaryConstraint> unaryConstraints;
    
    @XmlElement
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
        
        constraints = new HashMap();
        unaryConstraints = new ConstraintIndex<UnaryConstraint>();
        
        
        binaryConstraints = new ConstraintIndex<BinaryConstraintEnd>();
    }
    
    @Override
    public Collection<Constraint> getConstraints() {
        return constraints.values();
    }
    
    public void addUnaryConstraint(UnaryConstraint unaryConstraint) {
        unaryConstraints.add(unaryConstraint, constraints);
    }
    
    public void removeUnaryConstraint(Class<? extends UnaryConstraint> unaryConstraintClass) {
        unaryConstraints.remove(unaryConstraintClass, constraints);
    }
    
    public UnaryConstraint getUnaryConstraint(Class<? extends UnaryConstraint> unaryConstraintClass) {
        return unaryConstraints.get(unaryConstraintClass, constraints);
    }
    
    public Collection<UnaryConstraint> getUnaryConstraints() {
        return unaryConstraints.getAll(constraints);
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
        BinaryConstraintEnd binaryConstraint = (BinaryConstraintEnd) constraints.get(
                binaryConstraintClass);
        
        binaryConstraint.unbound();
        
        removeBinaryConstraint(binaryConstraintClass);
    }
    
    public void addBinaryConstraint(BinaryConstraintEnd binaryConstraint) {
        binaryConstraints.add(binaryConstraint, constraints);
    }
    
    public void removeBinaryConstraint(Class<? extends BinaryConstraintEnd> binaryConstraintClass) {
        binaryConstraints.remove(binaryConstraintClass, constraints);
    }
    
    public BinaryConstraintEnd getBinaryConstraint(Class<? extends BinaryConstraintEnd> binaryConstraintClass)
    {
        return binaryConstraints.get(binaryConstraintClass, constraints);
    }
    
    public Collection<BinaryConstraintEnd> getBinaryConstraints() {
        return binaryConstraints.getAll(constraints);
    }
}
