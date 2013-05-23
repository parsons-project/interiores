package interiores.business.models;

import interiores.business.exceptions.ForbiddenFurnitureException;
import interiores.business.exceptions.MandatoryFurnitureException;
import interiores.business.exceptions.WantedElementNotFoundException;
import interiores.business.models.constraints.BinaryConstraint;
import interiores.business.models.constraints.BinaryConstraintSet;
import interiores.business.models.constraints.UnaryConstraint;
import interiores.business.models.room.elements.WantedElementSet;
import interiores.business.models.room.elements.WantedFixed;
import interiores.business.models.room.elements.WantedFurniture;
import interiores.utils.BinaryConstraintAssociation;
import java.util.Collection;
import java.util.TreeSet;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represents the bag of furniture types and fixed elements
 * that the user wants to include in the room and the constraints required upon them.
 * @author larribas
 */
@XmlRootElement
public class WishList
{   
    @XmlElement
    private Room room;
    
    @XmlElement
    private WantedElementSet<WantedFurniture> furniture;
    
    @XmlElement
    private WantedElementSet<WantedFixed> fixed;
    
    @XmlElement
    private BinaryConstraintSet binaryConstraints;
    
    public WishList()
    { }
    
    public WishList(Room room)
    {
        this.room = room;
        
        furniture = new WantedElementSet();
        fixed = new WantedElementSet();
        binaryConstraints = new BinaryConstraintSet();
        
        for(String mandatoryType : room.getMandatoryFurniture())
            addWithoutChecking(mandatoryType);
    }
    
    public Room getRoom() {
        return room;
    }
    
    public boolean containsElement(String elementId) {
        return furniture.containsElement(elementId) || fixed.containsElement(elementId);
    }
    
    public String addWantedFurniture(String typeName)
    {
        if(room.isForbidden(typeName))
            throw new ForbiddenFurnitureException(typeName, room.getTypeName());
        
        return addWithoutChecking(typeName);
    }
    
    private String addWithoutChecking(String typeName)
    {
        return furniture.add(new WantedFurniture(typeName));
    }
      
    public void removeWantedFurniture(String elementId) 
    {
        String typeName = furniture.getElementTypeName(elementId);
        
        // if type is mandatory and only one is remaining...
        if(room.isMandatory(typeName) && furniture.getTypeCount(typeName) == 1)
            throw new MandatoryFurnitureException(typeName, room.getTypeName());
        
        furniture.remove(elementId);
        binaryConstraints.removeConstraints(elementId);
    }
    
    /**
     * Adds the given wanted fixed element to the set. The name of the
     * wanted fixed is updated to fix the identifier
     * @param wfixed The wanted fixed element to be added. It's name will be overriden.
     */
    public String addWantedFixed(WantedFixed wfixed)
    {
        return fixed.add(wfixed);   
    }
    
    /**
     * Removes the wanted element with wantedFixedId from the wishlist
     * @param wantedFixedId The id of the element to be removed
     * @return A boolean representing if the operation went ok.
     * @throws MandatoryFurnitureException 
     */
    public void removeWantedFixed(String wantedFixedId)
    {
        fixed.remove(wantedFixedId);
    }

    public int getUnfixedSize() {
        return furniture.size();
    }
    
    public int getFixedSize() {
        return fixed.size();
    }
    
    /**
     * Adds a new binary constraint.
     * @param binaryConstraintClass The type of the constraint
     * @param bc The specific binary constraint to add
     * @param f1 First WantedFurniture affected by this constraint
     * @param f2 Second WantedFurniture affected by this constraint
     */
    public void addBinaryConstraint(BinaryConstraint bc, String f1, String f2)
    {
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
    {
        return furniture.get(elementId).getUnaryConstraints();
    }
    
    public Collection<BinaryConstraintAssociation> getBinaryConstraints(String elementId)
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
        return furniture.getElementNames();
    }
    
    /**
     * Returns the identifiers of the WantedFixed elements
     * @return the string identifying the WantedFixed
     */
    public Collection<String> getFixedNames() {
        return fixed.getElementNames();
    }
    
    public Collection<String> getElementTypes() {
        Collection<String> elementTypes = new TreeSet();
        
        elementTypes.addAll(furniture.getTypeNames());
        elementTypes.addAll(fixed.getTypeNames());
        
        return elementTypes;
    }
    
    /**
     * Returns a particular WantedFurniture.
     * @param id the identifier 
     * @return the WantedFurniture with the identifier id
     */    
    public WantedFurniture getWantedFurniture(String id)
    {
        return furniture.get(id);
    }
    
    public WantedFixed getWantedFixed(String id) 
    {
        return fixed.get(id);
    }
    
    public void addUnaryConstraint(String elementId, UnaryConstraint unaryConstraint)
    {
        furniture.get(elementId).addUnaryConstraint(unaryConstraint);
    }
    
    public void removeUnaryConstraint(String elementId, Class<? extends UnaryConstraint> unaryConstraintClass)
    {
        furniture.get(elementId).removeUnaryConstraint(unaryConstraintClass);
    }
    
    /**
     * Returns the set of WantedFurnitures
     * @return the collection of WantedFurnitures
     */
    public Collection<WantedFurniture> getWantedFurniture() {
        return furniture.getAll();
    }

    /**
     * Returns the set of WantedFixed
     * @return the collection of WantedFixed elements
     */
    public Collection<WantedFixed> getWantedFixed() {
        return fixed.getAll();
    }
    
}
