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
    
    public void setDesign(Map<String, FurnitureValue> design)
    {
        this.design = design;
        this.hasDesign = true;
    }
    
    public Set<Entry<String, FurnitureValue>> getDesign()
    {
        return design.entrySet();
    }
    
    public boolean hasDesign()
    {
        return hasDesign;
    }
}
