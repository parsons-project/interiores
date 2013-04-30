package interiores.presentation.terminal;

import interiores.business.controllers.RoomTypesCatalogController;

/**
 *
 * @author hector
 */
public class RoomTypesCatalogCommands
    extends CatalogCommands
{
    private static final String CATALOG_TYPE_NAME = "room types";
    
    public RoomTypesCatalogCommands(RoomTypesCatalogController rtCatalogController) {
        super(rtCatalogController, CATALOG_TYPE_NAME);
    }
}
