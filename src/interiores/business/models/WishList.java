package interiores.business.models;

import interiores.business.exceptions.ForbiddenFurnitureException;
import interiores.business.exceptions.MandatoryFurnitureException;
import interiores.business.exceptions.RoomFunctionalitiesNotSatisfiedException;
import interiores.business.exceptions.WantedElementNotFoundException;
import interiores.business.exceptions.WantedFurnitureNotFoundException;
import interiores.business.models.backtracking.InterioresVariable;
import interiores.business.models.backtracking.VariableConfig;
import interiores.business.models.catalogs.NamedCatalog;
import interiores.business.models.constraints.furniture.BinaryConstraintEnd;
import interiores.business.models.constraints.furniture.UnaryConstraint;
import interiores.business.models.constraints.room.GlobalConstraint;
import interiores.business.models.room.FurnitureType;
import interiores.business.models.room.elements.WantedElementSet;
import interiores.business.models.room.elements.WantedFixed;
import interiores.business.models.room.elements.WantedFurniture;
import interiores.utils.Functionality;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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
    private Map<String, GlobalConstraint> globalConstraints;
    
    public WishList()
    { }
    
    public WishList(Room room)
    {
        this.room = room;
        
        furniture = new WantedElementSet("furniture");
        fixed = new WantedElementSet("fixed element");
        
        globalConstraints = new HashMap<String,GlobalConstraint>();
        
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
    }

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
    
    
    public void addGlobalConstraint(GlobalConstraint gc) {
        globalConstraints.put(gc.getClass().getName(), gc);
    } // remove i fer el controlador i les comandes.
    // Afegir les comandes a la documentació
    // afegir a l'interficie gráfica
    // tests a saco
    //Començar la guia de l'usuari
    
    
    /**
     * Adds a new binary constraint.
     * @param binaryConstraintClass The type of the constraint
     * @param bc The specific binary constraint to add
     * @param f1 First WantedFurniture affected by this constraint
     */
    public void addBinaryConstraint(BinaryConstraintEnd bc, String furnitureId, String elementId) {
        if(! furniture.containsElement(furnitureId))
            throw new WantedFurnitureNotFoundException(furnitureId);
        
        InterioresVariable element = getElement(elementId);
        bc.setOtherVariable(element);
        
        getWantedFurniture(furnitureId).bound(bc);
    }
    
    /**
     * Removes a specific binary constraint. Between 2 given WantedFurnitures,
     * there can only be one furniture of the same type.
     * @param ctype The type of the binary constraint
     * @param f1 First WantedFurniture affected by this constraint
     * @param f2 Second WantedFurniture affected by this constraint
     */
    public void removeBinaryConstraint(Class<? extends BinaryConstraintEnd> binaryConstraintClass,
            String furnitureId, String elementId)
    {
        getWantedFurniture(furnitureId).unbound(binaryConstraintClass, elementId);
    }
    
    
    public void removeGlobalConstraint(String name) {
        globalConstraints.remove(GlobalConstraint.getConstraintClass(name).getName());
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
    
    public Collection<BinaryConstraintEnd> getBinaryConstraints(String elementId)
    {
        if(!furniture.containsElement(elementId))
            throw new WantedFurnitureNotFoundException(elementId);
        
        return furniture.get(elementId).getBinaryConstraints();
    }
    
    public Collection<GlobalConstraint> getGlobalConstraints() {
        return globalConstraints.values();
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
    
    public Set<String> getElementTypes() {
        Set<String> elementTypes = new TreeSet();
        
        elementTypes.addAll(furniture.getTypeNames());
        elementTypes.addAll(fixed.getTypeNames());
        
        return elementTypes;
    }
    
    public InterioresVariable getElement(String id) {
        if(furniture.containsElement(id))
            return furniture.get(id);
        
        if(fixed.containsElement(id))
            return fixed.get(id);
        
        throw new WantedElementNotFoundException("element", id);
    }
    
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
    
    public Set<Functionality> getUnsatisfiedFunctionalities(NamedCatalog<FurnitureType> typesCatalog) {
        Set<Functionality> notSatisfied = room.getNeededFunctions();
        
        for(String elementType : furniture.getTypeNames()) {
            FurnitureType type = typesCatalog.get(elementType);
            
            for(Functionality function : type.getFunctionalities())
                notSatisfied.remove(function);
        }
        
        return notSatisfied;
    }
    
    public VariableConfig getVariableConfig(NamedCatalog<FurnitureType> typesCatalog) {
        Set<Functionality> functionsNotSatisfied = getUnsatisfiedFunctionalities(typesCatalog);
        
        if(! functionsNotSatisfied.isEmpty())
            throw new RoomFunctionalitiesNotSatisfiedException(functionsNotSatisfied);
        
        VariableConfig variableSet = new VariableConfig(room.getDimension());
        
        for(WantedFixed wfixed : fixed.getAll())
            variableSet.addConstant(wfixed);
        
        for(WantedFurniture wfurniture : furniture.getAll())
            variableSet.addVariable(wfurniture, typesCatalog.get(wfurniture.getTypeName()));
        
        return variableSet;
    }
}
