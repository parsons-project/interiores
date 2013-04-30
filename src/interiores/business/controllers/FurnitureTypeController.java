package interiores.business.controllers;

import interiores.business.exceptions.DefaultCatalogOverwriteException;
import interiores.business.exceptions.ElementNotFoundBusinessException;
import interiores.business.models.FurnitureType;
import interiores.business.models.Room;
import interiores.business.models.WantedFurniture;
import interiores.core.data.JAXBDataController;
import interiores.utils.Range;
import java.util.List;

/**
 *
 * @author hector
 */
public class FurnitureTypeController
    extends CatalogElementController<FurnitureType>
{
    public FurnitureTypeController(JAXBDataController data) {
        super(data, FurnitureTypesCatalogController.getCatalogTypeName());
    }
    
    public void add(String name, int minWidth, int maxWidth, int minDepth, int maxDepth)
            throws DefaultCatalogOverwriteException
    {
        Range widthRange = new Range(minWidth, maxWidth);
        Range depthRange = new Range(minDepth, maxDepth);
        
        FurnitureType toAdd = new FurnitureType(name, widthRange, depthRange);
        
        super.add(toAdd);
    }
    
        
    public void select(String name) throws ElementNotFoundBusinessException {
        List<WantedFurniture> l = getRoom().getWishList();
        WantedFurniture wf = new WantedFurniture(getActiveCatalog().get(name), l.size());
        l.add(wf);
    }
    
    public void unselect(String name) {
        getRoom().removeWantedFurniture(name);
    }
    
    public List<WantedFurniture> getRoomFurniture() {
        return getRoom().getWishList();
    }
    
    private Room getRoom() {
        return (Room) data.get("room");
    }
}
