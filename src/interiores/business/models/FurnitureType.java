package interiores.business.models;

import horarios.shared.IdObject;
import interiores.business.models.constraints.BinaryConstraint;
import interiores.business.models.constraints.UnaryConstraint;
import interiores.utils.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represents the type of a furniture model
 * @author alvaro
 */
@XmlRootElement
public class FurnitureType
    extends IdObject
{
    /**
     * Minimum width and depth
     */
    @XmlElement
    private Dimension minimum;
    
    /**
     * maximum width and depth
     */
    @XmlElement
    private Dimension maximum;
    
    @XmlElementWrapper
    private List<UnaryConstraint> unaryConstraints;
    
    @XmlElementWrapper
    private List<BinaryConstraint> binaryConstraints;
    
    public FurnitureType() {
        super();
    }
    
    /**
     * Full constructor that specifies the properties of the furniture type 
     * @param name Name of the furniture type
     * @param min Minimum dimensions a model of this type should have
     * @param max Maximum dimensions a model of this type should have
     */
    public FurnitureType(String name, Dimension min, Dimension max) {
        super(name);
        
        minimum = min;
        maximum = max;
        
        unaryConstraints = new ArrayList();
        binaryConstraints = new ArrayList();
    }
    
    public List<UnaryConstraint> getUnaryConstraints() {
        return unaryConstraints;
    }
    
    public List<BinaryConstraint> getBinaryConstraints() {
        return binaryConstraints;
    }
    
    public void addUnaryConstraint(UnaryConstraint unaryConstraint) {
        unaryConstraints.add(unaryConstraint);
    }
    
    public void addBinaryConstraint(BinaryConstraint binaryConstraint) {
        binaryConstraints.add(binaryConstraint);
    }
    
    /**
     * Gets the name of the type
     * @return The name of the type of furniture
     */
    public String getName() {
        return identifier;
    }
    
    /**
     * Gets the minimum dimensions a furniture model of this type should have
     * @return The minimum dimensions a furniture model of this type should have
     */
    public Dimension getMinDimension() {
        return minimum;
    }
    
    /**
     * Gets the maximum dimensions a furniture model of this type should have
     * @return The maximum dimensions a furniture model of this type should have
     */
    public Dimension getMaxDimension() {
        return maximum;
    }
    
    
    /**
     * Checks if dimension is between the minimum and the maximum dimensions of the type
     * @param dimension The dimension to be checked
     * @return True if the dimension is between the min and the max dimensions of the type, false otherwise
     */
    public Boolean checkDimension(Dimension dimension) {
        return (minimum.width <= dimension.width && dimension.width <= maximum.width) &&
               (minimum.depth <= dimension.depth && dimension.depth <= maximum.depth);
    }
    
    @Override
    public String toString() {
        return getName() + " " + minimum.toString() + " " + maximum.toString();
    }
    
}
