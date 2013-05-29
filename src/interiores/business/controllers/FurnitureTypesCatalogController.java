package interiores.business.controllers;

import interiores.business.controllers.abstracted.CatalogController;
import interiores.business.events.catalogs.FTCatalogCheckoutEvent;
import interiores.business.events.catalogs.FTCatalogSetModifiedEvent;
import interiores.business.models.catalogs.AvailableCatalog;
import interiores.business.models.catalogs.NamedCatalog;
import interiores.business.models.catalogs.factories.DefaultFurnitureTypesCatalogFactory;
import interiores.business.models.room.FurnitureType;
import interiores.core.data.JAXBDataController;
import java.util.Collection;
import javax.xml.bind.JAXBException;

/**
 * Business controller covering the operations performed over the catalog of furniture types
 * @author hector
 */
public class FurnitureTypesCatalogController
    extends CatalogController<FurnitureType>
{
    private static final String CATALOG_TYPE_NAME = "typesCatalog";
    
    /**
     * Creates a particular instance of the furniture type catalog controller, and initializes it
     * @param data The data controller that will give access to the objects this controller will use
     */
    public FurnitureTypesCatalogController(JAXBDataController data)
    {
        super(data, AvailableCatalog.FURNITURE_TYPES);
        
        // Temporary default catalog overwrite
        NamedCatalog defaultCatalog;
        defaultCatalog = DefaultFurnitureTypesCatalogFactory.getCatalog();
        loadedCatalogs.put(defaultCatalog.getName(), defaultCatalog);
        
        // Now, we create a "session" catalog that can be manipulated, and set it active
        NamedCatalog sessionCatalog = new NamedCatalog("session", defaultCatalog);
        loadedCatalogs.put("session", sessionCatalog);
        
        setActiveCatalog(sessionCatalog);
    }
    
    /**
     * Creates a new furniture type catalog with a specified name
     * @param catalogName The name of the new furniture type catalog
     */
    @Override
    public void create(String catalogName) {
        super.create(catalogName);
        notify(new FTCatalogSetModifiedEvent(catalogName,true));
    }
    
    /**
     * Removes the named furniture type catalog
     * @param catalogName The name of the furniture type catalog to delete
     */
    @Override
    public void remove(String catalogName) {
        super.remove(catalogName);
        notify(new FTCatalogSetModifiedEvent(catalogName, false));
    }
    
    /**
     * Merges a named furniture type catalog with the currently active one.
     * If replacement is enabled, it replaces all the furniture types named
     * identically, with the other catalog's ones
     * @param catalogName The name of the other furniture type catalog
     * @param replace Turns on or off the replacement option for the merge
     */
    @Override
    public void merge(String catalogName, boolean replace) {
        super.merge(catalogName,replace);
        // We notify a checkout event, as this operation performs a great change
        // upon the currently selected catalog, and thus we are somehow changing
        // into a completely different catalog
        notify(new FTCatalogCheckoutEvent(getNameActiveCatalog()));
    }
    
    /**
     * Switches the active catalog to the one specified
     * @param catalogName The name of the furniture type catalog to activate
     */
    @Override
    public void checkout(String catalogName) {
        super.checkout(catalogName);
        notify(new FTCatalogCheckoutEvent(catalogName));
    }
    
    /**
     * Loads a furniture type catalog (previously saved) from disk
     * @param path The absolute or relative directory path
     * @throws JAXBException 
     */
    @Override
    public void load(String path) throws JAXBException {
        super.load(path);
        notify(new FTCatalogSetModifiedEvent(lastLoaded, true));
    }
    
    /**
     * Obtains all the selected furniture types. This function is used in order to
     * determine whether performing a checkout operation leaves some selected furniture unresolved
     * @return A collection of strings containing all the currently selected furniture type in the wish list
     */
    public Collection<String> getSelectedFurnitureTypes()
    {
        return getWishList().getElementTypes();
    }
}
