package interiores.business.controllers.abstracted;

import interiores.business.exceptions.NoRoomCreatedException;
import interiores.business.models.Room;
import interiores.business.models.WishList;
import interiores.business.models.catalogs.AvailableCatalog;
import interiores.business.models.catalogs.NamedCatalog;
import interiores.core.business.BusinessController;
import interiores.core.data.JAXBDataController;

/**
 *
 * @author hector
 */
public class InterioresController
    extends BusinessController
{
    protected static final String KEY_ROOM = "room";
    
    public InterioresController(JAXBDataController data) {
        super(data);
    }
    
    protected Room getRoom()
            throws NoRoomCreatedException
    {
        if(! data.has(KEY_ROOM))
            throw new NoRoomCreatedException();
        
        return (Room) data.get(KEY_ROOM);
    }
    
    protected void setRoom(Room room) {
        data.set(KEY_ROOM, room);
    }
    
    protected WishList getWishList() throws NoRoomCreatedException {
        return getRoom().getWishList();
    }
    
    protected NamedCatalog getCatalog(AvailableCatalog catalog) {
        return (NamedCatalog) data.get(getCatalogKeyData(catalog));
    }
       
    protected String getCatalogKeyData(AvailableCatalog catalog) {
        return catalog.toString() + "Catalog";
    }
}
