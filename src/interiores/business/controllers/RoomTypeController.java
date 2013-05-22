package interiores.business.controllers;

import interiores.business.controllers.abstracted.CatalogElementController;
import interiores.business.exceptions.DefaultCatalogOverwriteException;
import interiores.business.exceptions.ElementNotFoundBusinessException;
import interiores.business.models.room.FurnitureType;
import interiores.business.models.RoomType;
import interiores.business.models.catalogs.AvailableCatalog;
import interiores.business.models.catalogs.NamedCatalog;
import interiores.core.data.JAXBDataController;
import interiores.utils.Dimension;
import interiores.utils.Range;
import java.util.Collection;
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
     * @throws DefaultCatalogOverwriteException 
     */
    public void add(String typeName)
            throws DefaultCatalogOverwriteException
    {
        RoomType type = new RoomType(typeName);
        super.add(type);
    }
    
    /**
     * Marks a furniture type as mandatory for a determined type of room
     * @param roomTypeName The type of room within which we want to create this rule
     * @param furnitureTypeName The type of furniture we want to mark as mandatory
     * @throws DefaultCatalogOverwriteException
     * @throws ElementNotFoundBusinessException 
     */
    public void addToMandatory(String roomTypeName, String furnitureTypeName)
            throws DefaultCatalogOverwriteException, ElementNotFoundBusinessException
    {
        RoomType roomType = getForWrite(roomTypeName);
        FurnitureType furnitureType = getFurnitureType(furnitureTypeName);
        
        roomType.addToMandatory(furnitureType);
    }
    
    /**
     * Removes a type of furniture from the list of mandatory types within a determined room
     * @param roomTypeName The type of the room we want to perform the removal on
     * @param furnitureTypeName The type of furniture we want to remove
     * @throws DefaultCatalogOverwriteException
     * @throws ElementNotFoundBusinessException 
     */
    public void removeFromMandatory(String roomTypeName, String furnitureTypeName)
            throws DefaultCatalogOverwriteException, ElementNotFoundBusinessException
    {
        RoomType roomType = getForWrite(roomTypeName);
        
        roomType.removeFromMandatory(furnitureTypeName);
    }
    
    /**
     * Marks a furniture type as forbidden for a determined type of room
     * @param roomTypeName The type of room within which we want to create this rule
     * @param furnitureTypeName The type of furniture we want to mark as forbidden
     * @throws DefaultCatalogOverwriteException
     * @throws ElementNotFoundBusinessException 
     */
    public void addToForbidden(String roomTypeName, String furnitureTypeName)
            throws DefaultCatalogOverwriteException, ElementNotFoundBusinessException
    {
        RoomType roomType = getForWrite(roomTypeName);
        FurnitureType furnitureType = getFurnitureType(furnitureTypeName);
        
        roomType.addToForbidden(furnitureType);
    }
    
    /**
     * Removes a type of furniture from the list of forbidden types within a determined room
     * @param roomTypeName The type of the room we want to perform the removal on
     * @param furnitureTypeName The type of furniture we want to remove
     * @throws DefaultCatalogOverwriteException
     * @throws ElementNotFoundBusinessException 
     */
    public void removeFromForbidden(String roomTypeName, String furnitureTypeName)
            throws DefaultCatalogOverwriteException, ElementNotFoundBusinessException
    {
        RoomType roomType = getForWrite(roomTypeName);
        
        roomType.removeFromForbidden(furnitureTypeName);
    }
    
    /**
     * Obtains the mandatory types of furniture defined for a determined type of room
     * @param roomTypeName The type of room we want to get the list from
     * @return A collection of strings containing all the types in the mandatory list
     * @throws ElementNotFoundBusinessException 
     */
    public Collection<String> getMandatory(String roomTypeName)
            throws ElementNotFoundBusinessException
    {
        RoomType roomType = get(roomTypeName);
        
        return roomType.getMandatory();
    }
    
    /**
     * Obtains the forbidden types of furniture defined for a determined type of room
     * @param roomTypeName The type of room we want to get the list from
     * @return A collection of strings containing all the types in the forbidden list
     * @throws ElementNotFoundBusinessException 
     */    
    public Collection<String> getForbidden(String roomTypeName)
            throws ElementNotFoundBusinessException
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
    
    /**
     * Obtains a type of furniture by its name
     * @param furnitureTypeName The name of the type
     * @return A furniture type by the name passed as a parameter
     * @throws ElementNotFoundBusinessException 
     */
    private FurnitureType getFurnitureType(String furnitureTypeName)
            throws ElementNotFoundBusinessException
    {
        NamedCatalog<FurnitureType> ftCatalog = (NamedCatalog) getCatalog(AvailableCatalog.FURNITURE_TYPES);
        
        return ftCatalog.get(furnitureTypeName);
    }
}
