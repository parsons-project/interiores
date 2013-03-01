/*
 */
package interiores;

/**
 *
 * @author hector
 */
public class Interiores
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        GUI gui = new GUI("swing");
        
        Terminal terminal = new Terminal();
        terminal.setGUI(gui);
        terminal.init();
    }
}
