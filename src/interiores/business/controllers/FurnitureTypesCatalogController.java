package interiores.business.controllers;

import interiores.business.controllers.abstracted.CatalogController;
import interiores.business.exceptions.NoRoomCreatedException;
import interiores.business.models.FurnitureType;
import interiores.business.models.catalogs.AvailableCatalog;
import interiores.business.models.catalogs.NamedCatalog;
import interiores.business.models.catalogs.factories.DefaultFurnitureTypesCatalogFactory;
import interiores.core.Debug;
import interiores.core.business.BusinessException;
import interiores.core.data.JAXBDataController;
import java.util.Collection;

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
            throws BusinessException
    {
        super(data, AvailableCatalog.FURNITURE_TYPES);
        
        // Temporary default catalog overwrite
        NamedCatalog defaultCatalog;
        
        try {
            defaultCatalog = DefaultFurnitureTypesCatalogFactory.getCatalog();
        }
        catch(BusinessException e) {
            defaultCatalog = new NamedCatalog();
            Debug.println("Error creating furniture types catalog becasuse: " + e.getMessage());
        }
        
        loadedCatalogs.put(defaultCatalog.getName(), defaultCatalog);
        setActiveCatalog(defaultCatalog);
    }
    
    public Collection<String> getSelectedFurnitureTypes()
            throws NoRoomCreatedException
    {
        return getWishList().getFurnitureTypes();
    }
}
