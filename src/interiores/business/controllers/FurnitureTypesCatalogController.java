package interiores.business.controllers;

import interiores.business.controllers.abstracted.CatalogController;
import interiores.business.models.FurnitureType;
import interiores.business.models.catalogs.AvailableCatalog;
import interiores.business.models.catalogs.NamedCatalog;
import interiores.business.models.catalogs.factories.DefaultFurnitureTypesCatalogFactory;
import interiores.core.business.BusinessException;
import interiores.core.data.JAXBDataController;

/**
 *
 * @author hector
 */
public class FurnitureTypesCatalogController
    extends CatalogController<FurnitureType>
{
    private static final String CATALOG_TYPE_NAME = "typesCatalog";
    
    public FurnitureTypesCatalogController(JAXBDataController data) {
        super(data, AvailableCatalog.FURNITURE_TYPES);
        
        // Temporary default catalog overwrite
        NamedCatalog defaultCatalog;
        
        try {
            defaultCatalog = DefaultFurnitureTypesCatalogFactory.getCatalog();
        }
        catch(BusinessException e) {
            defaultCatalog = new NamedCatalog();
        }
        
        loadedCatalogs.put(defaultCatalog.getName(), defaultCatalog);
        setActiveCatalog(defaultCatalog);
    }
}
