package interiores.views;

import interiores.mvc.View;
import java.beans.PropertyChangeEvent;

/**
 * Base class for all the views.
 * @author hector
 */
abstract public class TerminalView implements View {
    
    public TerminalView()
    {
        
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        System.out.println("Property " + evt.getPropertyName() + " changed from " + evt.getOldValue() +
                "to" + evt.getNewValue());
    }
        
    @Override
    public void showView()
    {
       
    }
}
