package interiores.business.controllers;

import interiores.business.controllers.abstracted.CatalogElementController;
import interiores.business.events.catalogs.FTModifiedEvent;
import interiores.business.events.catalogs.FTSetModifiedEvent;
import interiores.business.events.furniture.ElementSelectedEvent;
import interiores.business.events.furniture.ElementUnselectedEvent;
import interiores.business.exceptions.InvalidValueException;
import interiores.business.models.SpaceAround;
import interiores.business.models.catalogs.AvailableCatalog;
import interiores.business.models.catalogs.NamedCatalog;
import interiores.business.models.room.FurnitureType;
import interiores.business.models.room.RoomType;
import interiores.core.business.BusinessException;
import interiores.core.data.JAXBDataController;
import interiores.utils.Functionality;
import interiores.utils.Range;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

/**
 * Business controller covering the operations performed over a type of furniture
 * @author hector
 */
public class FurnitureTypeController
    extends CatalogElementController<FurnitureType>
{
    
    /**
     * Creates a particular instance of the furniture type controller
     * @param data The data controller that will give access to the objects this controller will use
     */
    public FurnitureTypeController(JAXBDataController data) {
        super(data, AvailableCatalog.FURNITURE_TYPES);
    }
    
    /**
     * Adds a furniture type to the current catalog
     * @param name The name of the type
     * @param minWidth The minimum width of the type
     * @param maxWidth The maximum width of the type
     * @param minDepth The minimum depth of the type
     * @param maxDepth The maximum depth of the type
     */    
    public void add(String name, int minWidth, int maxWidth, int minDepth, int maxDepth, boolean clingToWalls)
    {
        Range widthRange = new Range(minWidth, maxWidth);
        Range depthRange = new Range(minDepth, maxDepth);
        
        FurnitureType toAdd = new FurnitureType(name, widthRange, depthRange);
        toAdd.setToWalls(clingToWalls);
        
        super.add(toAdd);
        notify(new FTSetModifiedEvent(toAdd.getFullName(),name, true));
    }
    
    /**
     * Removes a type of furniture from the currently active catalog
     * @param typeName The type we want to remove
     */
    @Override
    public void rm(String typeName) {
        String fullName = get(typeName).getFullName();
        super.rm(typeName);
        notify(new FTSetModifiedEvent(fullName,typeName,false));
    }
    
    /**
     * Selects a type and includes it in the wish list (the list of wanted furniture)
     * @param name The name of the type
     */
    public void select(String name)
    {        
        String id = getWishList().addWantedFurniture(name);
        
        notify(new ElementSelectedEvent(id));
    }
    
    /**
     * Removes an element from the list of wanted furniture
     * @param name The identifier of the element to remove
     */
    public void unselect(String name)
            throws BusinessException
    {
        getWishList().removeWantedFurniture(name);
        
        notify(new ElementUnselectedEvent(name));
    }
    
    /**
     * Gets all the furniture in the wish list, that is, all the wanted furniture for the room being designed
     * @return A collection containing all the pieces of furniture in the wish list
     */
    public Collection<String> getRoomFurniture()
    {
        return getWishList().getFurnitureNames();
    }
    
    /**
     * Obtains a dictionary mapping the full name of a type of furniture to its short name.
     * The former is visually clearer, while the latter is more suitable to be used internally
     * @return A Map associating full names (as keys) to short names (as values)
     */
    public Map<String, String> getFullNamesMap() {
        Map<String, String> fullNames = new TreeMap();
        
        for(FurnitureType ft : getCatalogObjects())
            fullNames.put(ft.getFullName(), ft.getName());
        
        return fullNames;
    }
    
    /**
     * Gets the range the width of a model of the specified type should fall within
     * @param ftname The name of the furniture type
     * @return A Range representing the allowed widths
     */
    public Range getWidthRange(String ftname) {
        return get(ftname).getWidthRange();
    }
    
    /**
     * Gets the range the depth of a model of the specified type should fall within
     * @param ftname The name of the furniture type
     * @return A Range representing the allowed depths
     */
    public Range getDepthRange(String ftname) {
        return get(ftname).getDepthRange();
    }
    
    public void setWidthRange(String ftname, int wmin, int wmax) {
        FurnitureType ft = getForWrite(ftname);
        ft.setWidthRange(new Range(wmin, wmax));
        notify(new FTModifiedEvent(ft.getFullName(),ftname));
    }
    
    public void setDepthRange(String ftname, int dmin, int dmax) {
        FurnitureType ft = getForWrite(ftname);
        ft.setWidthRange(new Range(dmin, dmax));
        notify(new FTModifiedEvent(ft.getFullName(),ftname));
    }
    
    public int[] getPassiveSpace(String ftname) {
        return get(ftname).getPassiveSpace().getOffsets();
    }
    
    public void setPassiveSpace(String ftname, int[] ps) {
        if (ps.length != 4)
            throw new InvalidValueException("Passive space needs four values");
        
        SpaceAround sa = new SpaceAround(ps[0],ps[1],ps[2],ps[3]);
        getForWrite(ftname).setPassiveSpace(sa);
        notify(new FTModifiedEvent(get(ftname).getFullName(),ftname));
    }

    /**
     * Gets all the furniture types that can be placed within the current room.
     * That is, the set of all furniture types in the catalog save for those marked
     * as forbidden for the current room type
     * @return A collection of strings containing the names of the mentioned furniture types
     */
    public Collection getSelectableFurniture() {
        Collection<String> selectable = new ArrayList();
        for (FurnitureType ft : getCatalogObjects()) selectable.add(ft.getName());
        
        Collection<String> forbidden = getRoom().getType().getForbidden();
        selectable.removeAll(forbidden);
        return selectable;
    }
    
    
    /**
     * Obtains all the furniture types that remain uncategorized (out of the mandatory or forbidden lists)
     * determined room type
     * @param rtype The type of room you want the uncategorized furniture types from
     * @return A Collection of strings containing all the uncategorized furniture of a room
     */
    public Collection<String> getUncategorizedFurniture(String rtype) {
        Collection<String> u = new ArrayList();
        for (FurnitureType ft : getCatalogObjects()) u.add(ft.getName());
        
        Collection<String> forbidden = getRoomType(rtype).getForbidden();
        u.removeAll(forbidden);
        Collection<String> mandatory = getRoomType(rtype).getMandatory();
        u.removeAll(mandatory);
        
        return u;
    }
    
    /**
     * Adds a placement constraint to a type of furniture respect to the other.
     * The other type isn't affected by the placement constraint symmetrically.
     * There can only be one placement constraint between two types
     * @param pctype The kind of placement constraint
     * @param type1 The type over which we define it
     * @param type2 The second type affected by it
     */
    public void addPlacementConstraint(String pctype, String type1, String type2) {
        get(type1).addPlacementConstraint(type2, pctype);
        notify(new FTModifiedEvent(get(type1).getFullName(),type1));
    }
    
    /**
     * Removes a placement constraint affecting two furniture types
     * @param type1 The type over which the placement constraint is imposed
     * @param type2 The type indirectly affected by the constraint
     */
    public void removePlacementConstraint(String type1, String type2) {
        get(type1).removePlacementConstraint(type2);
        notify(new FTModifiedEvent(get(type1).getFullName(),type1));
    }
    
    /**
     * Obtains all the placement constraints defined for a type
     * @param name The name of the type
     * @return A HashMap mapping type names to placement constraint names
     */
    public HashMap<String,String> getPlacementConstraints(String name) {
        return get(name).getPlacementConstraints();
    }

    /**
     * Finds out whether the specified furniture type must be clung to a wall
     * @param name The name of the furniture type
     * @return 'true' if 'name' must be clung to a wall. 'false' otherwise
     */
    public boolean getWallClinging(String name) {
        return get(name).shouldBeClungToWalls();
    }
    
    /**
     * Specifies whether the specified furniture type must be clung to a wall
     * @param name The name of the furniture type
     * @param clinging A boolean saying the clinging status, where 'true' means "yes, it must be clung to a wall"
     */
    public void setWallClinging(String name, boolean clinging) {
        get(name).setToWalls(clinging);
        notify(new FTModifiedEvent(get(name).getFullName(),name));
    }

    /**
     * Gives a properly formatted description of all the placement constraints defined over a type
     * @param typeName The name of the furniture type
     * @return A Collection of Strings containing all the placement constraint descriptions of 'typeName'
     */
    public Collection<String> getPlacementDescriptions(String typeName) {
        ArrayList<String> description = new ArrayList<String>();
        HashMap<String,String> map = getPlacementConstraints(typeName);
        for (String type : map.keySet()) {
            description.add(type + " with " + map.get(type));
        }
        return description;
    }
    
    /**
     * Obtains all the functionalities (represented by their name) a furniture type covers
     * @param name The name of the furniture type
     * @return A Collection of Strings containing all the functionalities 'name' covers
     */
    public Collection<String> getFunctionalities(String name) {
        Collection<String> functs = new ArrayList();
        for (Functionality f : get(name).getFunctionalities() ) functs.add(f.toString());
        return functs;
    }
    
    /**
     * Sets a list of functionalities to a certain specified furniture type
     * @param name The name of the furniture type
     * @param functs An array of functions, represented by their name
     */
    public void setFunctionalities(String name, String[] functs) {

        HashSet<Functionality> hs = new HashSet();
        for (String f : functs) {
            hs.add(Functionality.getEnum(f));
        }
        get(name).setFunctionalities(hs);
    }
    
    
    private RoomType getRoomType(String name) {
        NamedCatalog<RoomType> rtCatalog = (NamedCatalog) getCatalog(AvailableCatalog.ROOM_TYPES);
        return rtCatalog.get(name);
    }
}
