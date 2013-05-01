package interiores.business.controllers;

import interiores.business.exceptions.DefaultCatalogOverwriteException;
import interiores.business.exceptions.ElementNotFoundBusinessException;
import interiores.business.exceptions.NoRoomCreatedException;
import interiores.business.models.FurnitureType;
import interiores.business.models.WantedFurniture;
import interiores.business.models.catalogs.AvailableCatalog;
import interiores.core.business.BusinessException;
import interiores.core.data.JAXBDataController;
import interiores.utils.Range;
import java.util.Collection;

/**
 *
 * @author hector
 */
public class FurnitureTypeController
    extends CatalogElementController<FurnitureType>
{
    public FurnitureTypeController(JAXBDataController data) {
        super(data, AvailableCatalog.FURNITURE_TYPES);
    }
    
    public void add(String name, int minWidth, int maxWidth, int minDepth, int maxDepth)
            throws DefaultCatalogOverwriteException
    {
        Range widthRange = new Range(minWidth, maxWidth);
        Range depthRange = new Range(minDepth, maxDepth);
        
        FurnitureType toAdd = new FurnitureType(name, widthRange, depthRange);
        
        super.add(toAdd);
    }
    
    public void select(String name)
            throws ElementNotFoundBusinessException, NoRoomCreatedException
    {
        WantedFurniture wf = new WantedFurniture(get(name));
        getWishList().addWantedFurniture(wf);
    }
    
    public void unselect(String name)
            throws BusinessException
    {
        getWishList().removeWantedFurniture(name);
    }
    
    public Collection getRoomFurniture()
            throws NoRoomCreatedException
    {
        return getWishList().getFurnitureNames();
    }
}
