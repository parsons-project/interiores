package interiores.business.controllers;

import interiores.business.models.RoomType;
import interiores.core.data.JAXBDataController;

/**
 *
 * @author hector
 */
public class RoomTypesCatalogController
    extends CatalogController<RoomType>
{
    private static final String CATALOG_TYPE_NAME = "roomTypesCatalog";
    
    public static String getCatalogTypeName() {
        return CATALOG_TYPE_NAME;
    }
        
    public RoomTypesCatalogController(JAXBDataController data) {
        super(data, CATALOG_TYPE_NAME);
    }
    
}
