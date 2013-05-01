package interiores.business.controllers;

import interiores.business.controllers.abstracted.CatalogController;
import interiores.business.models.RoomType;
import interiores.business.models.catalogs.AvailableCatalog;
import interiores.business.models.catalogs.NamedCatalog;
import interiores.business.models.catalogs.factories.DefaultRoomTypesCatalogFactory;
import interiores.core.data.JAXBDataController;

/**
 *
 * @author hector
 */
public class RoomTypesCatalogController
    extends CatalogController<RoomType>
{
    public RoomTypesCatalogController(JAXBDataController data) {
        super(data, AvailableCatalog.ROOM_TYPES);
        
        // Temporary default catalog overwrite
        NamedCatalog defaultCatalog;
        
        defaultCatalog = DefaultRoomTypesCatalogFactory.getCatalog();
        
        loadedCatalogs.put(defaultCatalog.getName(), defaultCatalog);
        setActiveCatalog(defaultCatalog);
    }
}
