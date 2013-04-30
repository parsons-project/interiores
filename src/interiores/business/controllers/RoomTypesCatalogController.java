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
    public RoomTypesCatalogController(JAXBDataController data) {
        super("roomTypes", data);
    }
    
}
