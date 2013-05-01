package interiores.business.controllers;

import interiores.business.models.FurnitureType;
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
    
    public static String getCatalogTypeName() {
        return CATALOG_TYPE_NAME;
    }
    
    public FurnitureTypesCatalogController(JAXBDataController data) {
        super(data, CATALOG_TYPE_NAME);
        
        // Temporary default catalog overwrite
        NamedCatalog defaultCatalog;
        
        try {
            defaultCatalog = DefaultFurnitureTypesCatalogFactory.getCatalog();
        }
        catch(BusinessException e) {
            defaultCatalog = new NamedCatalog();
        }
        
        loadedCatalogs.put(defaultCatalog.getName(), defaultCatalog);
        data.set("typesCatalog", defaultCatalog);
    }
}
