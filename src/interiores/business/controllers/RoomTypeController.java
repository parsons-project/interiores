package interiores.business.controllers;

import interiores.core.data.JAXBDataController;

/**
 *
 * @author hector
 */
public class RoomTypeController
    extends CatalogElementController
{
    public RoomTypeController(JAXBDataController data) {
        super(data, RoomTypesCatalogController.getCatalogTypeName());
    }
}
