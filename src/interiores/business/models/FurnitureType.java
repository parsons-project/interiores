package interiores.business.models;

import interiores.business.models.constraints.BinaryConstraint;
import interiores.business.models.constraints.UnaryConstraint;
import interiores.business.models.catalogs.PersistentIdObject;
import interiores.utils.Dimension;
import interiores.utils.Range;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represents the type of a furniture model
 * @author alvaro
 */
@XmlRootElement
public class FurnitureType
    extends PersistentIdObject
{
    /**
     * Width range
     */
    @XmlElement
    private Range widthRange;
    
    /**
     * Depth range
     */
    @XmlElement
    private Range depthRange;
    
    @XmlElementWrapper
    private ArrayList<UnaryConstraint> unaryConstraints;
    
    @XmlElementWrapper
    private HashMap<FurnitureType, ArrayList<BinaryConstraint>> binaryConstraints;
    
    public FurnitureType() {
        super();
    }
    
    /**
     * Full constructor that specifies the properties of the furniture type 
     * @param name Name of the furniture type
     * @param min Minimum dimensions a model of this type should have
     * @param max Maximum dimensions a model of this type should have
     */
    public FurnitureType(String name, Range widthRange, Range depthRange) {
        super(name);
        
        this.widthRange = widthRange;
        this.depthRange = depthRange;
        
        unaryConstraints = new ArrayList();
        binaryConstraints = new HashMap();
    }
    
    public List<UnaryConstraint> getUnaryConstraints() {
        return unaryConstraints;
    }
    
    public Map<FurnitureType, ArrayList<BinaryConstraint>> getBinaryConstraints() {
        return binaryConstraints;
    }
    
    public void addUnaryConstraint(UnaryConstraint unaryConstraint) {
        unaryConstraints.add(unaryConstraint);
    }
    
    public void addBinaryConstraint(FurnitureType other, BinaryConstraint binaryConstraint) {
        if(! binaryConstraints.containsKey(other))
            binaryConstraints.put(other, new ArrayList());
            
        binaryConstraints.get(other).add(binaryConstraint);
    }
    
    /**
     * Gets the name of the type
     * @return The name of the type of furniture
     */
    public String getName() {
        return identifier;
    }
    
    /**
     * Gets the width range a furniture model of this type should have
     * @return The minimum dimensions a furniture model of this type should have
     */
    public Range getWidthRange() {
        return widthRange;
    }
    
    /**
     * Gets the depth range a furniture model of this type should have
     * @return The maximum dimensions a furniture model of this type should have
     */
    public Range getDepthRange() {
        return depthRange;
    }
    
    
    /**
     * Checks if dimension is between the minimum and the maximum dimensions of the type
     * @param dimension The dimension to be checked
     * @return True if the dimension is between the min and the max dimensions of the type, false otherwise
     */
    public Boolean checkDimension(Dimension dimension) {
        return dimension.isWidthBetween(widthRange) && dimension.isDepthBetween(depthRange);
    }
    
    @Override
    public String toString() {
        return getName() + " " + widthRange.toString() + " " + depthRange.toString();
    }
    
}
