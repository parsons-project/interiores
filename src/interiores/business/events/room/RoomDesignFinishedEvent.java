package interiores.business.events.room;

import interiores.business.models.backtracking.FurnitureValue;
import interiores.core.Event;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author hector
 */
public class RoomDesignFinishedEvent 
    implements Event
{
    private Map<String, FurnitureValue> design;
    private boolean hasDesign = false;
    private long time = -1;
    
    public void setDesign(Map<String, FurnitureValue> design)
    {
        this.design = design;
        this.hasDesign = true;
    }
    
    public void setTime(long time) {
        this.time = time;
    }
    
    public Set<Entry<String, FurnitureValue>> getDesign()
    {
        return design.entrySet();
    }
    
    public long getTime() {
        return time;
    }
    
    public boolean hasDesign()
    {
        return hasDesign;
    }
    
    public boolean hasTime() {
        return time > 0;
    }
}
