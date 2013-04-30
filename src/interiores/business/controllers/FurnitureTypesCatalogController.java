package interiores.business.controllers;

import interiores.business.models.FurnitureType;
import interiores.business.models.catalogs.DefaultFurnitureTypesCatalog;
import interiores.business.models.catalogs.NamedCatalog;
import interiores.core.data.JAXBDataController;

/**
 *
 * @author hector
 */
public class FurnitureTypesCatalogController
    extends CatalogController<FurnitureType>
{
    public FurnitureTypesCatalogController(JAXBDataController data) {
        super("types", data);
        
        // Temporary default catalog overwrite
        NamedCatalog defaultCatalog = new DefaultFurnitureTypesCatalog();
        
        loadedCatalogs.put(defaultCatalog.getName(), defaultCatalog);
        data.set("typesCatalog", defaultCatalog);
    }
}
