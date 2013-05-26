package interiores.business.events.furniture;

import interiores.core.Event;

/**
 *
 * @author hector
 */
public class ElementSelectedEvent
    implements Event
{
    private String name;
    
    public ElementSelectedEvent(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}
