package interiores.presentation.terminal.commands;

import interiores.presentation.terminal.commands.abstracted.CatalogCommands;
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
