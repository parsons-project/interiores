package interiores.business.models;

import interiores.business.models.backtracking.FurnitureValue;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author hector
 */
public class RoomDesign
{
    private Map<String, FurnitureValue> furniture;
    
    public RoomDesign() {
        furniture = new TreeMap();
    }
    
    public void put(String name, FurnitureValue value) {
        furniture.put(name, value);
    }
    
    public void remove(String name) {
        furniture.remove(name);
    }
    
    public Set<Entry<String, FurnitureValue>> getEntries() {
        return furniture.entrySet();
    }
}
