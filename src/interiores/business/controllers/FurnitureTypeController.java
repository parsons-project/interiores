package interiores.business.controllers;

import horarios.shared.ElementNotFoundException;
import interiores.business.exceptions.DefaultCatalogOverwriteException;
import interiores.business.models.FurnitureType;
import interiores.business.models.Room;
import interiores.business.models.WantedFurniture;
import interiores.business.models.catalogs.NamedCatalog;
import interiores.core.business.BusinessController;
import interiores.core.business.BusinessException;
import interiores.core.data.JAXBDataController;
import interiores.utils.Range;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author hector
 */
public class FurnitureTypeController
    extends BusinessController
{
    public FurnitureTypeController(JAXBDataController data) {
        super(data);
    }
    
    public void add(String name, int minWidth, int maxWidth, int minDepth, int maxDepth)
            throws DefaultCatalogOverwriteException
    {
        NamedCatalog<FurnitureType> activeCatalog = getActiveCatalog();
        
        if(activeCatalog.isDefault())
            throw new DefaultCatalogOverwriteException();
        
        Range widthRange = new Range(minWidth, maxWidth);
        Range depthRange = new Range(minDepth, maxDepth);
        
        getActiveCatalog().add(new FurnitureType(name, widthRange, depthRange));
    }
    
    public void rm(String name)
            throws BusinessException
    {
        NamedCatalog<FurnitureType> activeCatalog = getActiveCatalog();
        
        if(activeCatalog.isDefault())
            throw new DefaultCatalogOverwriteException();
        
        if(!activeCatalog.hasObject(name))
            throw new BusinessException("The " + activeCatalog.getName() + " catalog has no furniture type "
                    + "named: " + name);
        
        activeCatalog.delete(name);
    }
    
    public Collection<FurnitureType> getCatalogObjects() {
        return getActiveCatalog().getObjects();
    }
    
    public void select(String name) throws ElementNotFoundException {
        List<WantedFurniture> l = getRoom().getWishList();
        WantedFurniture wf = new WantedFurniture(getActiveCatalog().getObject(name), l.size());
        l.add(wf);
    }
    
    public void unselect(String name) throws BusinessException, ElementNotFoundException {
        getRoom().removeWantedFurniture(name);
    }
    
    public String getNameActiveCatalog() {
        return getActiveCatalog().getName();
    }
    
    public List<WantedFurniture> getRoomFurniture() {
        return getRoom().getWishList();
    }
    
    private Room getRoom() {
        return (Room) data.get("room");
    }
    
    private NamedCatalog<FurnitureType> getActiveCatalog() {
        return (NamedCatalog<FurnitureType>) data.get("typesCatalog");
    }
}
