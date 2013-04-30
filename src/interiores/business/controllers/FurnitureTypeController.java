package interiores.business.controllers;

import horarios.shared.Catalog;
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
        if(getCatalogName().equals(NamedCatalog.getDefaultName()))
            throw new DefaultCatalogOverwriteException();
        
        Range widthRange = new Range(minWidth, maxWidth);
        Range depthRange = new Range(minDepth, maxDepth);
        
        getTypesCatalog().add(new FurnitureType(name, widthRange, depthRange));
    }
    
    public Collection<FurnitureType> getCatalogObjects() {
        return getTypesCatalog().getObjects();
    }
    
    public String getCatalogName() {
        return (String) data.get("typesCatalogName");
    }
    
    private Catalog<FurnitureType> getTypesCatalog() {
        return (Catalog<FurnitureType>) data.get("typesCatalog");
    }
    
    public void select(String name) throws ElementNotFoundException {
        List<WantedFurniture> l = getRoom().getWishList();
        WantedFurniture wf = new WantedFurniture(getTypesCatalog().getObject(name),l.size());
        l.add(wf);
    }
    
    public void unselect(String name) throws BusinessException, ElementNotFoundException {
        getRoom().removeWantedFurniture(name);
    }
    
    private Room getRoom() {
        return (Room) data.get("room");
    }

    public List<WantedFurniture> getRoomFurniture() {
        return getRoom().getWishList();
    }
    
}
