package interiores.business.models.room.elements;

import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.constraints.Constraint;
import interiores.business.models.constraints.furniture.BinaryConstraintEnd;
import interiores.business.models.constraints.furniture.BinaryConstraintSet;
import interiores.business.models.constraints.furniture.UnaryConstraint;
import interiores.business.models.constraints.furniture.UnaryConstraintSet;
import java.awt.Point;
import java.util.Collection;
import java.util.HashSet;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class is the abstraction of a wanted element for the room
 * @author alvaro
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class WantedFurniture
    extends FurnitureVariable
{
    @XmlElement
    private UnaryConstraintSet unaryConstraints;
    
    @XmlElement
    private BinaryConstraintSet binaryConstraints;
    
    public WantedFurniture()
    { }
    
    /**
     * Simple creator. Creates a wanted element given its type
     * @param ft The funiture type of this wanted element
     */
    public WantedFurniture(String typeName) {
        super(typeName);
        
        unaryConstraints = new UnaryConstraintSet();
        binaryConstraints = new BinaryConstraintSet();
    }
    
    public void setPosition(Point p) {
        if(!isAssigned())
            return;
        
        getAssignedValue().setPosition(p);
    }
    
    @Override
    public Collection<Constraint> getConstraints() {
        Collection<Constraint> constraints = new HashSet();
        
        constraints.addAll(unaryConstraints.getAll());
        constraints.addAll(binaryConstraints.getAll());
        
        return constraints;
    }
    
    public void addUnaryConstraint(UnaryConstraint unaryConstraint) {
        unaryConstraints.add(unaryConstraint);
        isDirty = true;
    }
    
    public void removeUnaryConstraint(Class<? extends UnaryConstraint> unaryConstraintClass) {
        unaryConstraints.remove(unaryConstraintClass);
        isDirty = true;
    }
    
    public UnaryConstraint getUnaryConstraint(Class<? extends UnaryConstraint> unaryConstraintClass) {
        return unaryConstraints.get(unaryConstraintClass);
    }
    
    public Collection<UnaryConstraint> getUnaryConstraints() {
        return unaryConstraints.getAll();
    }
    
    public void bound(BinaryConstraintEnd binaryConstraint)
    {
        binaryConstraints.bound(this, binaryConstraint);
        isDirty = true;
    }
    
    public void unbound(Class<? extends BinaryConstraintEnd> binaryConstraintClass, String otherName)
    {
        binaryConstraints.unbound(name, binaryConstraintClass, otherName);
        isDirty = true;
    }
    
    public void addBinaryConstraint(BinaryConstraintEnd binaryConstraint, String otherName)
    {
        binaryConstraints.add(binaryConstraint, otherName);
        isDirty = true;
    }
    
    public void removeBinaryConstraint(Class<? extends BinaryConstraintEnd> binaryConstraintClass,
            String otherName)
    {
        binaryConstraints.remove(binaryConstraintClass, otherName);
        isDirty = true;
    }
    
    public Collection<BinaryConstraintEnd> getBinaryConstraints() {
        return binaryConstraints.getAll();
    }
    
    public void removeBinaryConstraints()
    {
        binaryConstraints.clear(name);
    }
}
