package interiores.business.events.furniture;

import interiores.core.Event;

/**
 *
 * @author hector
 */
public class FurnitureTypeSelectedEvent
    implements Event
{
    private String name;
    
    public FurnitureTypeSelectedEvent(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}
