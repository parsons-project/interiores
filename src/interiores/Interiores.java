/*
 */
package interiores;

import interiores.core.Application;

/**
 *
 * @author hector
 */
public class Interiores
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception
    {
        Application app = new Application("interiores");
        
        app.addCommandGroup("room");
        
        app.enableGUI();
        app.init();
    }
}
