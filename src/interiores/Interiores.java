/*
 */
package interiores;

import interiores.controllers.RoomController;
import interiores.models.Room;
import interiores.mvc.Controller;
import interiores.views.RoomInfoView;

/**
 *
 * @author hector
 */
public class Interiores
{
    private static Room room;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        room = new Room();
        
        Controller controller = new RoomController(room);
        room.addListener(controller);
        
        controller.addView(new RoomInfoView());
    }
}
