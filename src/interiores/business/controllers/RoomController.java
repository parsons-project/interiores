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
 * Business controller covering the operations performed over a room
 * @author hector
 */
public class RoomController
    extends CatalogAccessController<RoomType>
{
    /**
     * Creates a particular instance of the room controller
     * @param data The data controller that will give access to the objects this controller will use
     */
    public RoomController(JAXBDataController data)
    {
        super(data, AvailableCatalog.ROOM_TYPES);
    }
    
    /**
     * Creates a new room with a given type and measures
     * @param typeName The type of room to create
     * @param width The width of the new room
     * @param depth The depth of the new room
     * @throws ElementNotFoundBusinessException
     * @throws BusinessException 
     */
    public void create(String typeName, int width, int depth)
            throws ElementNotFoundBusinessException, BusinessException
    {
        RoomType type = get(typeName);
        Room room = new Room(type, width, depth);

        setRoom(room);
        
        WishList wishList = new WishList();
        setWishList(wishList);
        
        notify("roomCreated", room.toMap());
    }
    
    /**
     * Stores the current room in disk, under a specific path
     * @param path The path where we want to store the room
     * @throws JAXBException
     * @throws NoRoomCreatedException 
     */
    public void save(String path) throws JAXBException, NoRoomCreatedException
    {   
        data.save(getRoom(), path);
    }
    
    /**
     * Loads the current room from disk, out of a specified path
     * @param path The path where the room we want to load is
     * @throws JAXBException 
     */
    public void load(String path) throws JAXBException
    {
        Room room = (Room) data.load(Room.class, path);
        
        setRoom(room);
        
        notify("roomLoaded", room.toMap());
    }
}
