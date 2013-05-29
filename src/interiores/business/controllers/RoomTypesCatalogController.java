package interiores.business.controllers;

import interiores.business.controllers.abstracted.CatalogController;
import interiores.business.events.catalogs.RTCatalogSetModifiedEvent;
import interiores.business.events.catalogs.RTCatalogCheckoutEvent;
import interiores.business.models.room.RoomType;
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
        
        // Now, we create a "session" catalog that can be manipulated, and set it active
        NamedCatalog sessionCatalog = new NamedCatalog("session", defaultCatalog);
        loadedCatalogs.put("session", sessionCatalog);
        
        setActiveCatalog(sessionCatalog);
    }
    
    /**
     * Creates a new room type catalog with a specified name
     * @param catalogName The name the new room type catalog will have
     */
    @Override
    public void create(String catalogName) {
        super.create(catalogName);
        notify(new RTCatalogSetModifiedEvent(catalogName,true));
    }
    
    /**
     * Removes the specified room type catalog from the list of available catalogs
     * @param catalogName The name of the room type catalog to remove
     */
    @Override
    public void remove(String catalogName) {
        super.remove(catalogName);
        notify(new RTCatalogSetModifiedEvent(catalogName, false));
    }
    
    /**
     * Merges a named furniture type catalog with the currently active one.
     * If replacement is enabled, it replaces all the furniture types named
     * identically, with the other catalog's ones
     * @param catalogName The name of the other room type catalog
     * @param replace Turns on or off the replacement option for the merge
     */
    @Override
    public void merge(String catalogName, boolean replace) {
        super.merge(catalogName,replace);
        // We notify a checkout event, as this operation performs a great change
        // upon the currently selected catalog, and thus we are somehow changing
        // into a completely different catalog
        notify(new RTCatalogCheckoutEvent(getNameActiveCatalog()));
    }
    
    /**
     * Switches the active catalog to the one specified
     * @param catalogName The name of the room type catalog to activate
     */
    @Override
    public void checkout(String catalogName) {
        super.checkout(catalogName);
        notify(new RTCatalogCheckoutEvent(catalogName));
    }
    
    /**
     * Loads a room type catalog (previously saved) from disk
     * @param path The absolute or relative directory path
     * @throws JAXBException 
     */
    @Override
    public void load(String path) throws JAXBException {
        super.load(path);
        notify(new RTCatalogSetModifiedEvent(lastLoaded, true));
    }
}
