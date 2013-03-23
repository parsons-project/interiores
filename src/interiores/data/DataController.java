package interiores.data;

import interiores.core.business.Model;
import java.util.HashMap;

/**
 *
 * @author hector
 */
public class DataController {
    private HashMap<String, Model> data;
    
    public DataController()
    {
        data = new HashMap<String, Model>();
    }
    
    public Model get(String name)
    {
        return data.get(name);
    }
    
    public void set(String name, Model model)
    {
        data.put(name, model);
    }
}
