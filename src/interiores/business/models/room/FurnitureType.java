package interiores.business.models.room;

import interiores.business.models.SpaceAround;
import interiores.business.models.catalogs.PersistentIdObject;
import interiores.core.Utils;
import interiores.core.business.BusinessException;
import interiores.utils.Dimension;
import interiores.utils.Functionality;
import interiores.utils.Range;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
    private boolean clingToWalls;
    
    @XmlElementWrapper
    private HashMap<String, String> placementConstraints;
    
    @XmlElementWrapper
    private TreeMap<String, FurnitureModel> models;
    
    @XmlElement
    private SpaceAround defaultPassiveSpace;
    
    @XmlElementWrapper
    private ArrayList<Functionality> functionalities;
    
    public FurnitureType() {
        super();
    }
    
    public FurnitureType(String name, Range widthRange, Range depthRange) {
        this(name, widthRange, depthRange, new SpaceAround(0, 0, 0, 0));
    }
    
    public FurnitureType(String name, Range widthRange, Range depthRange, SpaceAround defaultPassiveSpace) {
        this(name, widthRange, depthRange, defaultPassiveSpace, new Functionality[0],false);
    }
    
    public FurnitureType(String name, Range widthRange, Range depthRange, Functionality[] functionalities) {
        this(name, widthRange, depthRange, new SpaceAround(0, 0, 0, 0), functionalities,false);
    }
    
    /**
     * 
     * @param name
     * @param widthRange
     * @param depthRange
     * @param defaultPassiveSpace
     * @param functionalities
     * @param walls 
     */
    public FurnitureType(String name, Range widthRange, Range depthRange, SpaceAround defaultPassiveSpace,
            Functionality[] functionalities, boolean walls)
    {
        super(name);
        
        this.widthRange = widthRange;
        this.depthRange = depthRange;
        this.defaultPassiveSpace = defaultPassiveSpace;
        this.functionalities = new ArrayList(Arrays.asList(functionalities));
        
        clingToWalls = walls;
        placementConstraints = new HashMap();
        
        models = new TreeMap();
    }
    
    
    
    
    /**
     * Gets all the particular models of this type of furniture
     * @return A list containing all the furniture models of this type
     */
    public List<FurnitureModel> getFurnitureModels() {
        return new ArrayList(models.values());
    }
    
    public boolean shouldBeClungToWalls() {
        return clingToWalls;
    }
    
    public void setToWalls(boolean clingToWalls) {
        this.clingToWalls = clingToWalls;
    }
    
    
    public void addPlacementConstraint(String otherFT, String placement) {
        placementConstraints.put(otherFT, placement);
    }
    
    public void removePlacementConstraint(String otherFT) {
        if (placementConstraints.containsKey(otherFT))
            placementConstraints.remove(otherFT);
    }
    
    /**
     * Returns the binary constraints defined for this type
     * @return A map of placement constraints defined between this type and another
     */
    public HashMap<String, String> getPlacementConstraints() {
        return placementConstraints;
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
    
    public String getFullName() {
        String[] words = getName().split("(?=[A-Z])");
        
        String fullName = Utils.capitalize(words[0]);
        
        for(int i = 1; i < words.length; ++i)
            fullName += " " + Utils.decapitalize(words[i]);
        
        return fullName;
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
     * Sets the width range a furniture model of this type should have
     * @return The minimum dimensions a furniture model of this type should have
     */
    public void setWidthRange(Range w) {
        widthRange = w;
    }
    
    /**
     * Sets the depth range a furniture model of this type should have
     * @return The minimum dimensions a furniture model of this type should have
     */
    public void setDepthRange(Range d) {
        depthRange = d;
    }
    
    public SpaceAround getPassiveSpace() {
        return defaultPassiveSpace;
    }
    
    public void setPassiveSpace(SpaceAround sa) {
        defaultPassiveSpace = sa;
    }
    
    @Override
    public String toString() {
        return Utils.padRight(getName(), 20) + "Width[" + widthRange.toString() + "], Depth["
                + depthRange.toString() + "]";
    }

    

}
