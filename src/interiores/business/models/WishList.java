package interiores.business.models;

import interiores.business.exceptions.ForbiddenFurnitureException;
import interiores.business.exceptions.MandatoryFurnitureException;
import interiores.business.exceptions.WantedElementNotFoundException;
import interiores.business.models.constraints.BinaryConstraint;
import interiores.business.models.constraints.UnaryConstraint;
import interiores.utils.BinaryConstraintAssociation;
import interiores.utils.Dimension;
import java.awt.Point;
import java.util.Collection;
import java.util.LinkedList;
import java.util.TreeMap;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * This class represents the bag of furniture types that the user wants to
 * include in the room and the constraints required upon them.
 * @author larribas
 */
public class WishList
{
    private static final String KEY_BINARY_CONSTRAINT = "%s-%s-%s";
    
    @XmlElement
    private Room room;
    
    @XmlElementWrapper
    private TreeMap<String, WantedFurniture> furniture;
    
    @XmlElementWrapper
    private TreeMap<String, WantedFixed> fixed;
    
    @XmlElementWrapper
    private TreeMap<String, Integer> typesCount;
    
    @XmlElementWrapper
    private TreeMap<String, BinaryConstraintAssociation> binaryConstraints;
    
    /**
     * Default constructor.
     */
    public WishList(Room room)
    {
        this.room = room;
        
        furniture = new TreeMap();
        fixed = new TreeMap();
        typesCount = new TreeMap();
        binaryConstraints = new TreeMap();
        
        for(String mandatoryType : room.getMandatoryFurniture())
            addWithoutChecking(mandatoryType);
    }
    
    public Room getRoom() {
        return room;
    }
    
    public boolean containsElement(String elementId) {
        return furniture.containsKey(elementId) || fixed.containsKey(elementId);
    }
    
    /**
     * Add a new WantedFurniture 
     * @param f the WantedFurniture to add
     */
    public void addWantedFurniture(String typeName)
            throws ForbiddenFurnitureException
    {
        if(room.isForbidden(typeName))
            throw new ForbiddenFurnitureException(typeName, room.getTypeName());
        
        addWithoutChecking(typeName);
    }
    
    public void addWantedFixed(String typeName, Point position, Dimension size) {
        
        String wantedFixedId = nextIdFor(typeName, fixed);
        fixed.put(wantedFixedId, new WantedFixed(typeName, position, size));
    
    }
    
    private void addWithoutChecking(String typeName) {
        if(typesCount.containsKey(typeName))
            typesCount.put(typeName, typesCount.get(typeName + 1));
        else
            typesCount.put(typeName, 1);
        
        String wantedFurnitureId = nextIdFor(typeName, furniture);
        furniture.put(wantedFurnitureId, new WantedFurniture(wantedFurnitureId, typeName));
    }
    
    private String nextIdFor(String typeName, TreeMap map) {
        String nextId;
        int i = 1;
        
        do {
            nextId = typeName + String.valueOf(i);
            i++;
        } while(map.containsKey(nextId));
        
        return nextId;
    }
    
    public void removeWantedFixed(String wantedFurnitureId) {
        if (! fixed.containsKey(wantedFurnitureId)) 
            return;
        fixed.remove(wantedFurnitureId);
    }
    
    public void removeWantedFurniture(String wantedFurnitureId) throws MandatoryFurnitureException {
        if(! furniture.containsKey(wantedFurnitureId))
            return;
        
        String typeName = furniture.get(wantedFurnitureId).getTypeName();
        int typeCount = typesCount.get(typeName);
        
        if(typeCount <= 1) {
            if(room.isMandatory(typeName))
                throw new MandatoryFurnitureException(typeName, room.getTypeName());
            
            typesCount.remove(typeName);
        }
        else
            typesCount.put(typeName, typeCount - 1);
        
        furniture.remove(wantedFurnitureId);
    }
    
    public int getSize() {
        return furniture.size();
    }
    
    /**
     * Adds a new binary constraint.
     * @param binaryConstraintClass The type of the constraint
     * @param bc The specific binary constraint to add
     * @param f1 First WantedFurniture affected by this constraint
     * @param f2 Second WantedFurniture affected by this constraint
     */
    public void addBinaryConstraint(BinaryConstraint bc, String f1, String f2)
            throws WantedElementNotFoundException {
        if(!containsElement(f1))
            throw new WantedElementNotFoundException(f1);
        
        if(!containsElement(f2))
            throw new WantedElementNotFoundException(f2);
        
        String key = getBinaryConstraintID(bc.getClass(), f1, f2);
        BinaryConstraintAssociation bca = new BinaryConstraintAssociation(bc, f1, f2);
        
        binaryConstraints.put(key, bca);   
    }
    
    /**
     * Removes a specific binary constraint. Between 2 given WantedFurnitures,
     * there can only be one furniture of the same type.
     * @param ctype The type of the binary constraint
     * @param f1 First WantedFurniture affected by this constraint
     * @param f2 Second WantedFurniture affected by this constraint
     */
    public void removeBinaryConstraint(Class<? extends BinaryConstraint> binaryConstraintClass, String f1,
            String f2)
    {
        String key = getBinaryConstraintID(binaryConstraintClass, f1, f2);
        
        if(binaryConstraints.containsKey(key))
            binaryConstraints.remove(key);
    }
    
    /**
     * Returns all the constraints (both unary and binary) of a given WantedFurniture.
     * @param furnitureID the identifier of the WantedFurniture
     * @return the set of constraints
     */
    public Collection<UnaryConstraint> getUnaryConstraints(String furnitureID)
            throws WantedElementNotFoundException
    {
        return getWantedFurniture(furnitureID).getUnaryConstraints();
    }
    
    public Collection<BinaryConstraintAssociation> getBinaryConstraints(String furnitureId)
            throws WantedElementNotFoundException
    {
        if(!containsElement(furnitureId))
            throw new WantedElementNotFoundException(furnitureId);
                
        Collection<BinaryConstraintAssociation> result = new LinkedList();
        
        Object[] keys = binaryConstraints.keySet().toArray();
        for (int i = 0; i < keys.length; i++) {
            String k = (String) keys[i];
            if (k.contains(furnitureId)) result.add(binaryConstraints.get(k));
        }
        return result;
    }
    
    /**
     * Returns all the binary constraints.
     * @return List containing all the binary constraints.
     */
    public Collection<BinaryConstraintAssociation> getBinaryConstraints() {
        return binaryConstraints.values();
    }
    
    /**
     * Returns the identifiers of the WantedFurniture
     * @return the string identifying the WantedFurnitures
     */
    public Collection<String> getFurnitureNames() {
        return furniture.keySet();
    }
    
    public Collection<String> getFixedNames() {
        return fixed.keySet();
    }
    
    public Collection<String> getFurnitureTypes() {
        return typesCount.keySet();
    }
    
    /**
     * Returns a particular WantedFurniture.
     * @param id the identifier 
     * @return the WantedFurniture with the identifier id
     */
    public WantedFurniture getWantedFurniture(String id)
            throws WantedElementNotFoundException
    {
        if(!containsElement(id))
            throw new WantedElementNotFoundException(id);
                
        return furniture.get(id);
    }
    
    public WantedFixed getWantedFixed(String id) 
            throws WantedElementNotFoundException {
        if(!containsElement(id))
            throw new WantedElementNotFoundException(id);
                
        return fixed.get(id);
    }
    
    public void addUnaryConstraint(String elementId, UnaryConstraint unaryConstraint)
            throws WantedElementNotFoundException
    {
        getWantedFurniture(elementId).addUnaryConstraint(unaryConstraint);
    }
    
    public void removeUnaryConstraint(String elementId, Class<? extends UnaryConstraint> unaryConstriantClass)
            throws WantedElementNotFoundException
    {
        getWantedFurniture(elementId).removeUnaryConstraint(unaryConstriantClass);
    }
    
    /**
     * Returns the set of WantedFurnitures
     * @return the collection of WantedFurnitures
     */
    public Collection<WantedFurniture> getWantedFurniture() {
        return furniture.values();
    }
    
    /**
     * Gets the identifier of the binary constraint of type ctype defined
     * over the two specified pieces of furniture
     * @param ctype The type of the constraint
     * @param f1 The first piece of furniture
     * @param f2 The second piece of furniture
     * @return A String containing the identifier of such a constraint, or
     * null if it doesn't exist
     */
    private String getBinaryConstraintID(Class binaryConstraintClass, String f1, String f2)
    {
        String className = binaryConstraintClass.getSimpleName();
        String key = String.format(KEY_BINARY_CONSTRAINT, className, f1, f2);
        
        if(binaryConstraints.containsKey(key))
            return key;
        
        return String.format(KEY_BINARY_CONSTRAINT, className, f2, f1);
    }

    public Collection<WantedFixed> getWantedFixed() {
        return fixed.values();
    }
}
