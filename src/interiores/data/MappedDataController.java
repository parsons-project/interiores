package interiores.data;

import interiores.core.data.JAXBDataController;
import interiores.shared.data.DataController;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXBException;

/**
 *
 * @author hector
 */
public class MappedDataController
    extends DataController
    implements JAXBDataController
{
    private Map<String, Object> data;
    
    public MappedDataController() {
        super();
        
        data = new HashMap();
    }
    
    /**
     * Returns the object previosuly saved with the name given.
     * @param name Name of the object to get
     * @return Object previously saved as name
     */
    @Override
    public Object get(String name)
    {
        return data.get(name);
    }
    
    /**
     * Stores an object with the name given.
     * @param name Name of the object
     * @param o Object to store
     */
    @Override
    public void set(String name, Object o)
    {
        data.put(name, o);
    }
    
    @Override
    public boolean has(String name) {
        return data.containsKey(name);
    }
}
