package interiores.presentation.swing;

import interiores.core.presentation.PresentationController;
import interiores.core.presentation.SwingController;
import interiores.core.presentation.View;
import javax.swing.JPanel;

/**
 *
 * @author hector
 */
public class SwingPanel extends JPanel implements View {
    protected SwingController presentation;
    
    @Override
    public void showView()
    {
        setVisible(true);
    }
    
    @Override
    public void setPresentation(PresentationController presentation)
    {
        this.presentation = (SwingController) presentation;
    }
    
    @Override
    public String[] getEvents()
    {
        return new String[]{};
    }
}
