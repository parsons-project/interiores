package interiores.controllers;

import interiores.models.Room;
import interiores.mvc.Controller;

/**
 * 
 * @author hector
 */
public class MainController extends Controller
{
    private Room room;
    
    public void init()
    {
        loadView("Main");
    }
    
    public void newRoom()
    {
        if(room != null)
            loadView("SaveRoomChanges", room);
        
        loadView("NewRoom", room);
    }
}
