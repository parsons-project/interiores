package interiores.business.controllers;

import interiores.business.controllers.abstracted.CatalogAccessController;
import interiores.business.events.room.RoomCreatedEvent;
import interiores.business.events.room.RoomLoadedEvent;
import interiores.business.models.Room;
import interiores.business.models.room.RoomType;
import interiores.business.models.WishList;
import interiores.business.models.catalogs.AvailableCatalog;
import interiores.core.Debug;
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
     */
    public void create(String typeName, int width, int depth)
    {
        RoomType type = get(typeName);
        Room room = new Room(type, width, depth);
        WishList wishList = new WishList(room);
        
        setWishList(wishList);
        
        notify(new RoomCreatedEvent(room));
    }
    
    /**
     * Stores the current room in disk, under a specific path
     * @param path The path where we want to store the room
     * @throws JAXBException
     */
    public void save(String path) throws JAXBException
    {
        Debug.println("Saving:" + path);
        
        data.save(getWishList(), path);
        
        Debug.println("Saved successfully.");
    }
    
    /**
     * Loads the current room from disk, out of a specified path
     * @param path The path where the room we want to load is
     * @throws JAXBException 
     */
    public void load(String path) throws JAXBException
    {
        Debug.println("Loading: " + path);
        
        WishList wishList = (WishList) data.load(WishList.class, path);
        setWishList(wishList);
        
        notify(new RoomLoadedEvent(wishList.getRoom()));
        
        Debug.println("Loaded successfully.");
    }
    
    /**
     * Gets the current resolution the room works with.
     * The resolution means a tradeoff between quality and speed.
     * A higher resolution means only a subset of positions will be
     * considered as valid for holding the initial position of a furniture
     * @return 
     */
    public int getResolution() {
        return Room.getResolution();
    }
    
    public int getWidth() {
        return getRoom().getWidth();
    }
    
    public int getDepth() {
        return getRoom().getDepth();
    }
}
