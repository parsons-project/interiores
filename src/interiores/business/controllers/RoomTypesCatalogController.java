package interiores.business.controllers;

import interiores.business.controllers.abstracted.CatalogController;
import interiores.business.events.catalogs.CatalogChangedEvent;
import interiores.business.events.catalogs.RTCatalogChangedEvent;
import interiores.business.events.catalogs.RTCatalogCheckoutEvent;
import interiores.business.models.RoomType;
import interiores.business.models.catalogs.AvailableCatalog;
import interiores.business.models.catalogs.NamedCatalog;
import interiores.business.models.catalogs.factories.DefaultRoomTypesCatalogFactory;
import interiores.core.data.JAXBDataController;
import javax.xml.bind.JAXBException;

/**
 * Business controller covering the operations performed over a catalog of room types
 * @author hector
 */
public class RoomTypesCatalogController
    extends CatalogController<RoomType>
{
    /**
     * Creates a particular instance of the room type catalog controller, and initializes it
     * @param data The data controller that will give access to the objects this controller will use
     */
    public RoomTypesCatalogController(JAXBDataController data) {
        super(data, AvailableCatalog.ROOM_TYPES);
        
        // Temporary default catalog overwrite
        NamedCatalog defaultCatalog;
        
        defaultCatalog = DefaultRoomTypesCatalogFactory.getCatalog();
        
        loadedCatalogs.put(defaultCatalog.getName(), defaultCatalog);
        setActiveCatalog(defaultCatalog);
    }
    
    @Override
    public void create(String catalogName) {
        super.create(catalogName);
        notify(new RTCatalogChangedEvent(catalogName,true));
    }
    
    @Override
    public void remove(String catalogName) {
        super.remove(catalogName);
        notify(new RTCatalogChangedEvent(catalogName, false));
    }
    
    @Override
    public void checkout(String catalogName) {
        super.checkout(catalogName);
        notify(new RTCatalogCheckoutEvent());
    }
    
    @Override
    public void load(String path) throws JAXBException {
        super.load(path);
        notify(new RTCatalogChangedEvent(lastLoaded, true));
    }
}
