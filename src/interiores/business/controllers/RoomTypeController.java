package interiores.business.controllers;

import interiores.business.exceptions.DefaultCatalogOverwriteException;
import interiores.business.exceptions.ElementNotFoundBusinessException;
import interiores.business.models.FurnitureType;
import interiores.business.models.RoomType;
import interiores.business.models.catalogs.NamedCatalog;
import interiores.core.data.JAXBDataController;

/**
 *
 * @author hector
 */
public class RoomTypeController
    extends CatalogElementController<RoomType>
{
    public RoomTypeController(JAXBDataController data) {
        super(data, RoomTypesCatalogController.getCatalogTypeName());
    }
    
    public void add(String typeName)
            throws DefaultCatalogOverwriteException
    {
        RoomType type = new RoomType(typeName);
        super.add(type);
    }
    
    public void addToMandatory(String roomTypeName, String furnitureTypeName)
            throws DefaultCatalogOverwriteException, ElementNotFoundBusinessException
    {
        RoomType roomType = getRoomTypeForWrite(roomTypeName);
        FurnitureType furnitureType = getFurnitureType(furnitureTypeName);
        
        roomType.addToMandatory(furnitureType);
    }
    
    public void removeFromMandatory(String roomTypeName, String furnitureTypeName)
            throws DefaultCatalogOverwriteException, ElementNotFoundBusinessException
    {
        RoomType roomType = getRoomTypeForWrite(roomTypeName);
        
        roomType.removeFromMandatory(furnitureTypeName);
    }
    
    public void addToForbidden(String roomTypeName, String furnitureTypeName)
            throws DefaultCatalogOverwriteException, ElementNotFoundBusinessException
    {
        RoomType roomType = getRoomTypeForWrite(roomTypeName);
        FurnitureType furnitureType = getFurnitureType(furnitureTypeName);
        
        roomType.addToForbidden(furnitureType);
    }
    
    public void removeFromForbidden(String roomTypeName, String furnitureTypeName)
            throws DefaultCatalogOverwriteException, ElementNotFoundBusinessException
    {
        RoomType roomType = getRoomTypeForWrite(roomTypeName);
        
        roomType.removeFromForbidden(furnitureTypeName);
    }
        
    private RoomType getRoomTypeForWrite(String roomTypeName)
            throws ElementNotFoundBusinessException, DefaultCatalogOverwriteException
    {
        NamedCatalog<RoomType> activeCatalog = getActiveCatalog();
        return activeCatalog.getForWrite(roomTypeName);
    }
    
    private FurnitureType getFurnitureType(String furnitureTypeName)
            throws ElementNotFoundBusinessException
    {
        String ftCatalogTypeName = FurnitureTypesCatalogController.getCatalogTypeName();
        NamedCatalog<FurnitureType> ftCatalog = (NamedCatalog) data.get(ftCatalogTypeName);
        
        return ftCatalog.get(furnitureTypeName);
    }
}
