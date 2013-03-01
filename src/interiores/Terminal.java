package interiores;

/**
 *
 * @author hector0193
 */
public class Terminal
{
    private GUI gui;
    
    public Terminal(GUI app)
    {
        this.gui = app;
    }
    
    public void init()
    {
        gui.init();
        
    }
}
