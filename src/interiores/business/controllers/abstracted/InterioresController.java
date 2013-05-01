package interiores.business.controllers.abstracted;

import interiores.business.exceptions.NoRoomCreatedException;
import interiores.business.models.Room;
import interiores.business.models.WishList;
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
    protected static final String KEY_WISH_LIST = "wishList";
    
    public InterioresController(JAXBDataController data) {
        super(data);
    }
    
    protected WishList getWishList()
            throws NoRoomCreatedException
    {
        if(! data.has(KEY_WISH_LIST))
            throw new NoRoomCreatedException();
        
        return (WishList) data.get(KEY_WISH_LIST);
    }
    
    protected void setWishList(WishList wishList) {
        data.set(KEY_WISH_LIST, wishList);
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
}
