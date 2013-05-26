package interiores.business.models.room;

import interiores.business.models.SpaceAround;
import interiores.business.models.catalogs.PersistentIdObject;
import interiores.business.models.constraints.furniture.BinaryConstraintEnd;
import interiores.business.models.constraints.furniture.UnaryConstraint;
import interiores.core.Utils;
import interiores.core.business.BusinessException;
import interiores.utils.Dimension;
import interiores.utils.Range;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represents a type of furniture.
 * It contains a list of particular models of that type, and default unary and binary constraints
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
    private HashMap<String, ArrayList<BinaryConstraintEnd>> binaryConstraints;
    
    @XmlElementWrapper
    private TreeMap<String, FurnitureModel> models;
    
    @XmlElement
    private SpaceAround defaultPassiveSpace;
    
    public FurnitureType() {
        super();
    }
    
    public FurnitureType(String name, Range widthRange, Range depthRange) {
        this(name, widthRange, depthRange, new SpaceAround(0, 0, 0, 0));
    }
    
    /**
     * Creates a new furniture type
     * @param name The name of the type of furniture
     * @param widthRange A range of valid widths
     * @param depthRange A range of valid depths
     */
    public FurnitureType(String name, Range widthRange, Range depthRange, SpaceAround defaultPassiveSpace) {
        super(name);
        
        this.widthRange = widthRange;
        this.depthRange = depthRange;
        this.defaultPassiveSpace = defaultPassiveSpace;
        
        unaryConstraints = new ArrayList();
        binaryConstraints = new HashMap();
        
        models = new TreeMap();
        
    }
    
    /**
     * Returns the unary constraints defined for this type
     * @return A list of unary constraints set by default
     */
    public List<UnaryConstraint> getUnaryConstraints() {
        return unaryConstraints;
    }
    
    /**
     * Returns the binary constraints defined for this type
     * @return A map of binary constraints defined between this type and another
     */
    public Map<String, ArrayList<BinaryConstraintEnd>> getBinaryConstraints() {
        return binaryConstraints;
    }
    
    /**
     * Gets all the particular models of this type of furniture
     * @return A list containing all the furniture models of this type
     */
    public List<FurnitureModel> getFurnitureModels() {
        return new ArrayList(models.values());
    }
    
    /**
     * Adds a default unary constraint to the type
     * @param unaryConstraint The unary constraint to add
     */
    public void addUnaryConstraint(UnaryConstraint unaryConstraint) {
        unaryConstraints.add(unaryConstraint);
    }
    
    /**
     * Adds a default binary constraint to the type
     * @param other The other type of furniture
     * @param binaryConstraint The binary constraint to add 
     */
    public void addBinaryConstraint(FurnitureType other, BinaryConstraintEnd binaryConstraint) {
        if(! binaryConstraints.containsKey(other.getId()))
            binaryConstraints.put(other.getId(), new ArrayList());
            
        binaryConstraints.get(other.getId()).add(binaryConstraint);
    }
    
    /**
     * Adds a new model of this type. This 
     * @param furnitureModel
     * @throws BusinessException 
     */
    public void addFurnitureModel(FurnitureModel furnitureModel)
            throws BusinessException
    {
        Dimension modelDimension = furnitureModel.getSize();
        
        if (! modelDimension.isBetween(widthRange, depthRange))
            throw new BusinessException("The furniture model " + furnitureModel.getName() + " is not inside "
                    + "the range of the " + getName() + " type dimensions. Width range: " + widthRange + " "
                    + "Depth range: " + depthRange);
        
        furnitureModel.setType(identifier);
        
        if(! furnitureModel.hasPassiveSpace())
            furnitureModel.setPassiveSpace(defaultPassiveSpace);
        
        models.put(furnitureModel.getName(), furnitureModel);
    }
    
    public void removeFurnitureModel(String modelName)
            throws BusinessException
    {
        if(! models.containsKey(modelName))
            throw new BusinessException("There is no furniture model named " + modelName + " in the "
                    + "furniture type: " + identifier);
        
        models.remove(modelName);
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
    
    @Override
    public String toString() {
        return Utils.padRight(getName(), 20) + "Width[" + widthRange.toString() + "], Depth["
                + depthRange.toString() + "]";
    }
}
