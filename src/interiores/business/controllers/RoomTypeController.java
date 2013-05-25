package interiores.business.controllers;

import interiores.business.controllers.abstracted.CatalogElementController;
import interiores.business.events.catalogs.RTChangedEvent;
import interiores.business.models.RoomType;
import interiores.business.models.catalogs.AvailableCatalog;
import interiores.business.models.catalogs.NamedCatalog;
import interiores.business.models.room.FurnitureType;
import interiores.core.data.JAXBDataController;
import interiores.utils.Range;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Business controller covering the operations performed over a type of room
 * @author hector
 */
public class RoomTypeController
    extends CatalogElementController<RoomType>
{
    /**
     * Creates a particular instance of the room type controller
     * @param data The data controller that will give access to the objects this controller will use
     */
    public RoomTypeController(JAXBDataController data) {
        super(data, AvailableCatalog.ROOM_TYPES);
    }
    
    /**
     * Adds a new type of room to the current catalog
     * @param typeName The name of the new type
     */
    public void add(String typeName, int width, int depth)
    {
        RoomType type = new RoomType(typeName,width,depth);
        super.add(type);
        
        notify(new RTChangedEvent(type.getFullName(), typeName,true));
    }
    
    @Override
    public void rm(String typeName) {
        String fullName = get(typeName).getFullName();
        super.rm(typeName);
        notify(new RTChangedEvent(fullName,typeName,false));
    }
    
    /**
     * Marks a furniture type as mandatory for a determined type of room
     * @param roomTypeName The type of room within which we want to create this rule
     * @param furnitureTypeName The type of furniture we want to mark as mandatory
     */
    public void addToMandatory(String roomTypeName, String furnitureTypeName)
    {
        RoomType roomType = getForWrite(roomTypeName);
        FurnitureType furnitureType = getFurnitureType(furnitureTypeName);
        
        roomType.addToMandatory(furnitureType);
    }
    
    /**
     * Removes a type of furniture from the list of mandatory types within a determined room
     * @param roomTypeName The type of the room we want to perform the removal on
     * @param furnitureTypeName The type of furniture we want to remove
     */
    public void removeFromMandatory(String roomTypeName, String furnitureTypeName)
    {
        RoomType roomType = getForWrite(roomTypeName);
        
        roomType.removeFromMandatory(furnitureTypeName);
    }
    
    public void setMandatory(String roomTypeName, String[] mandatory) {
        RoomType roomType = getForWrite(roomTypeName);
        Collection<FurnitureType> cft = new LinkedList();
        for (String s : mandatory) cft.add(getFurnitureType(s));
        // Now, if everything went alright
        roomType.clearMandatory();
        for (FurnitureType ft : cft) roomType.addToMandatory(ft);
    }
    
    public void setForbidden(String roomTypeName, String[] forbidden) {
        RoomType roomType = getForWrite(roomTypeName);
        Collection<FurnitureType> cft = new LinkedList();
        for (String s : forbidden) cft.add(getFurnitureType(s));
        // Now, if everything went alright
        roomType.clearForbidden();
        for (FurnitureType ft : cft) roomType.addToForbidden(ft);
    }
    
    /**
     * Marks a furniture type as forbidden for a determined type of room
     * @param roomTypeName The type of room within which we want to create this rule
     * @param furnitureTypeName The type of furniture we want to mark as forbidden
     */
    public void addToForbidden(String roomTypeName, String furnitureTypeName)
    {
        RoomType roomType = getForWrite(roomTypeName);
        FurnitureType furnitureType = getFurnitureType(furnitureTypeName);
        
        roomType.addToForbidden(furnitureType);
    }
    
    /**
     * Removes a type of furniture from the list of forbidden types within a determined room
     * @param roomTypeName The type of the room we want to perform the removal on
     * @param furnitureTypeName The type of furniture we want to remove
     */
    public void removeFromForbidden(String roomTypeName, String furnitureTypeName)
    {
        RoomType roomType = getForWrite(roomTypeName);
        
        roomType.removeFromForbidden(furnitureTypeName);
    }
    
    /**
     * Obtains the mandatory types of furniture defined for a determined type of room
     * @param roomTypeName The type of room we want to get the list from
     * @return A collection of strings containing all the types in the mandatory list
     */
    public Collection<String> getMandatory(String roomTypeName)
    {
        RoomType roomType = get(roomTypeName);
        
        return roomType.getMandatory();
    }
    
    /**
     * Obtains the forbidden types of furniture defined for a determined type of room
     * @param roomTypeName The type of room we want to get the list from
     * @return A collection of strings containing all the types in the forbidden list
     */    
    public Collection<String> getForbidden(String roomTypeName)
    {
        RoomType roomType = get(roomTypeName);
        
        return roomType.getForbidden();
    }
    
    public Map<String, String> getFullNamesMap() {
        Map<String, String> fullNames = new TreeMap();
        
        for(RoomType rt : getCatalogObjects())
            fullNames.put(rt.getFullName(), rt.getName());
        
        return fullNames;
    }
    
    public Range getWidthRange(String roomTypeName) {
        return get(roomTypeName).getWidthRange();
    }
    
    public Range getDepthRange(String roomTypeName) {
        return get(roomTypeName).getDepthRange();
    }
    
    public void setMinWidth(String roomTypeName, int w) {
        
        getForWrite(roomTypeName).setMinWidth(w);
    }
    
    public void setMinDepth(String roomTypeName, int d) {
        getForWrite(roomTypeName).setMinDepth(d);
    }
    
    /**
     * Obtains a type of furniture by its name
     * @param furnitureTypeName The name of the type
     * @return A furniture type by the name passed as a parameter
     */
    private FurnitureType getFurnitureType(String furnitureTypeName)
    {
        NamedCatalog<FurnitureType> ftCatalog = (NamedCatalog) getCatalog(AvailableCatalog.FURNITURE_TYPES);
        
        return ftCatalog.get(furnitureTypeName);
    }
}
