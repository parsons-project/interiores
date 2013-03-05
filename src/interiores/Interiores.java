/*
 */
package interiores;

import interiores.controllers.MainController;
import interiores.mvc.ViewLoader;

/**
 *
 * @author hector
 */
public class Interiores
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, Exception
    {
        ViewLoader loader = new ViewLoader("terminal");
        
        loader.addViews(MainController.class, new String[]{ "Main" });
        loader.loadView("Main", null);
    }
}
