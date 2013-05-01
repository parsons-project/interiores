package interiores.business.controllers;

import interiores.business.controllers.abstracted.CatalogAccessController;
import interiores.business.exceptions.ElementNotFoundBusinessException;
import interiores.business.exceptions.NoRoomCreatedException;
import interiores.business.models.Room;
import interiores.business.models.RoomType;
import interiores.business.models.WishList;
import interiores.business.models.catalogs.AvailableCatalog;
import interiores.core.business.BusinessException;
import interiores.core.data.JAXBDataController;
import javax.xml.bind.JAXBException;

/**
 * Controller for rooms!
 * @author hector
 */
public class RoomController
    extends CatalogAccessController<RoomType>
{
    public RoomController(JAXBDataController data)
    {
        super(data, AvailableCatalog.ROOM_TYPES);
    }
    
    public void create(String typeName, int width, int depth)
            throws ElementNotFoundBusinessException, BusinessException
    {
        RoomType type = get(typeName);
        Room room = new Room(type, width, depth);

        setRoom(room);
        
        WishList wishList = new WishList();
        setWishList(wishList);
        
        notify("roomCreated", room);
    }
    
    public void save(String path) throws JAXBException, NoRoomCreatedException
    {   
        data.save(getRoom(), path);
    }
    
    public void load(String path) throws JAXBException
    {
        Room room = (Room) data.load(Room.class, path);
        
        setRoom(room);
        
        notify("roomLoaded", room);
    }
}
