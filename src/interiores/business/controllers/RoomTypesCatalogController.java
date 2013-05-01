package interiores.business.controllers;

import interiores.business.models.RoomType;
import interiores.business.models.catalogs.AvailableCatalog;
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
    }
}
