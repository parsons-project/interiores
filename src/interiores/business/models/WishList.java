package interiores.business.models;

import interiores.business.exceptions.ForbiddenFurnitureException;
import interiores.business.exceptions.MandatoryFurnitureException;
import interiores.business.exceptions.WantedElementNotFoundException;
import interiores.business.models.constraints.BinaryConstraint;
import interiores.business.models.constraints.BinaryConstraintSet;
import interiores.business.models.constraints.UnaryConstraint;
import interiores.utils.BinaryConstraintAssociation;
import java.util.Collection;
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
    @XmlElement
    private Room room;
    
    @XmlElementWrapper
    private TreeMap<String, WantedFurniture> furniture;
    
    @XmlElementWrapper
    private TreeMap<String, WantedFixed> fixed;
    
    @XmlElementWrapper
    private TreeMap<String, Integer> typesCount;
    
    @XmlElementWrapper
    private BinaryConstraintSet binaryConstraints;
    
    /**
     * Default constructor.
     */
    public WishList(Room room)
    {
        this.room = room;
        
        furniture = new TreeMap();
        fixed = new TreeMap();
        typesCount = new TreeMap();
        binaryConstraints = new BinaryConstraintSet();
        
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
    
    private void addWithoutChecking(String typeName) {
        if(typesCount.containsKey(typeName))
            typesCount.put(typeName, typesCount.get(typeName) + 1);
        else
            typesCount.put(typeName, 1);
        
        String wantedFurnitureId = nextIdFor(typeName, furniture);
        furniture.put(wantedFurnitureId, new WantedFurniture(wantedFurnitureId, typeName));
    }
    
    public void addWantedFixed(WantedFixed wfixed) {
        
        String typeName = wfixed.getTypeName();
        
        if(typesCount.containsKey(typeName))
            typesCount.put(typeName, typesCount.get(typeName) + 1);
        else
            typesCount.put(typeName, 1);
        
        String wantedFixedId = nextIdFor(typeName, fixed);
        wfixed.setName(wantedFixedId);
        fixed.put(wantedFixedId, wfixed);   
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
    
    private boolean removeWantedElement(String elementId, TreeMap map) 
            throws MandatoryFurnitureException {
        String typeName = furniture.get(elementId).getTypeName();
        int typeCount = typesCount.get(typeName);
        
        if(typeCount == 1) {
            if (room.isMandatory(typeName))
                throw new MandatoryFurnitureException(typeName, room.getTypeName());
            
            typesCount.remove(typeName);
        }
        else
            typesCount.put(typeName, typeCount - 1);
        
        if (! map.containsKey(elementId)) 
            return false;
        map.remove(elementId);
        return true;
    }
    
    public boolean removeWantedFixed(String wantedFixedId)
            throws MandatoryFurnitureException {
        return removeWantedElement(wantedFixedId, fixed);
    }
    
    public boolean removeWantedFurniture(String wantedFurnitureId)
            throws MandatoryFurnitureException {
        return removeWantedElement(wantedFurnitureId, furniture);
    }
    
    public int getSize() {
        return furniture.size() + fixed.size();
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
        
        binaryConstraints.add(bc, f1, f2);
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
        binaryConstraints.remove(binaryConstraintClass, f1, f2);
    }
    
    /**
     * Returns all the constraints (both unary and binary) of a given WantedFurniture.
     * @param furnitureID the identifier of the WantedFurniture
     * @return the set of constraints
     */
    public Collection<UnaryConstraint> getUnaryConstraints(String elementId)
            throws WantedElementNotFoundException
    {
        return getWantedElement(elementId).getUnaryConstraints();
    }
    
    public Collection<BinaryConstraintAssociation> getBinaryConstraints(String elementId)
            throws WantedElementNotFoundException
    {
        if(!containsElement(elementId))
            throw new WantedElementNotFoundException(elementId);
        
        return binaryConstraints.getConstraints(elementId);
    }
    
    /**
     * Returns all the binary constraints.
     * @return List containing all the binary constraints.
     */
    public Collection<BinaryConstraintAssociation> getBinaryConstraints() {
        return binaryConstraints.getConstraints();
    }
    
    public int getPriority(String furnitureId)
            throws WantedElementNotFoundException
    {
        if(!containsElement(furnitureId))
            throw new WantedElementNotFoundException(furnitureId);
        
        return binaryConstraints.getPriority(furnitureId);
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
    
    public Collection<String> getElementTypes() {
        return typesCount.keySet();
    }
    
    private WantedElement getWantedElement(String id) 
            throws WantedElementNotFoundException {
        if(!furniture.containsKey(id)) {
            if (!fixed.containsKey(id))
                throw new WantedElementNotFoundException(id);
            return (WantedElement) fixed.get(id);
        }
                   
        return (WantedElement) furniture.get(id);
    }
    
    private WantedElement getWantedElement(String id, TreeMap map) 
            throws WantedElementNotFoundException {
        if(!map.containsKey(id))
            throw new WantedElementNotFoundException(id);
                
        return (WantedElement) map.get(id);
    }
    
    /**
     * Returns a particular WantedFurniture.
     * @param id the identifier 
     * @return the WantedFurniture with the identifier id
     */    
    public WantedFurniture getWantedFurniture(String id)
            throws WantedElementNotFoundException {
        return (WantedFurniture) getWantedElement(id, furniture);
    }
    
    public WantedFixed getWantedFixed(String id) 
            throws WantedElementNotFoundException {
        return (WantedFixed) getWantedElement(id, fixed);
    }
    
    public void addUnaryConstraint(String elementId, UnaryConstraint unaryConstraint)
            throws WantedElementNotFoundException
    {
        getWantedElement(elementId).addUnaryConstraint(unaryConstraint);
    }
    
    public void removeUnaryConstraint(String elementId, Class<? extends UnaryConstraint> unaryConstriantClass)
            throws WantedElementNotFoundException
    {
        getWantedElement(elementId).removeUnaryConstraint(unaryConstriantClass);
    }
    
    /**
     * Returns the set of WantedFurnitures
     * @return the collection of WantedFurnitures
     */
    public Collection<WantedFurniture> getWantedFurniture() {
        return furniture.values();
    }

    public Collection<WantedFixed> getWantedFixed() {
        return fixed.values();
    }
    
}
