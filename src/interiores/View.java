package interiores;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JPanel;

/**
 * Base class for all the views.
 * @author hector
 */
abstract public class View extends JPanel implements PropertyChangeListener {
    
    public View()
    {
        super();
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        System.out.println("Property " + evt.getPropertyName() + " changed from " + evt.getOldValue() +
                "to" + evt.getNewValue());
    }
}
