/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.presentation.swing;

import interiores.core.presentation.PresentationController;
import interiores.core.presentation.SwingController;
import interiores.core.presentation.View;
import javax.swing.JFrame;

/**
 *
 * @author hector0193
 */
abstract public class SwingFrame extends JFrame implements View
{
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
    public void onLoad() throws Exception
    {
        
    }
}
