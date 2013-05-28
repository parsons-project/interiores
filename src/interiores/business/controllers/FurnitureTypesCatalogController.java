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
    
    @Override
    public void create(String catalogName) {
        super.create(catalogName);
        notify(new FTCatalogSetModifiedEvent(catalogName,true));
    }
    
    @Override
    public void remove(String catalogName) {
        super.remove(catalogName);
        notify(new FTCatalogSetModifiedEvent(catalogName, false));
    }
    
    @Override
    public void merge(String catalogName, boolean replace) {
        super.merge(catalogName,replace);
        // We notify a checkout event, as this operation performs a great change
        // upon the currently selected catalog, and thus we are somehow changing
        // into a completely different catalog
        notify(new FTCatalogCheckoutEvent(getNameActiveCatalog()));
    }
    
    @Override
    public void checkout(String catalogName) {
        super.checkout(catalogName);
        notify(new FTCatalogCheckoutEvent(catalogName));
    }
    
    @Override
    public void load(String path) throws JAXBException {
        super.load(path);
        notify(new FTCatalogSetModifiedEvent(lastLoaded, true));
    }
    
    
    public Collection<String> getSelectedFurnitureTypes()
    {
        return getWishList().getElementTypes();
    }
}
