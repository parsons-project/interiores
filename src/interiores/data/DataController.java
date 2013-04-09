package interiores.data;

import interiores.core.business.Model;
import java.io.File;
import java.util.HashMap;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author hector
 */
public class DataController {
    private HashMap<String, Object> data;
    
    public DataController()
    {
        data = new HashMap<String, Object>();
    }
    
    public Object get(String name)
    {
        return data.get(name);
    }
    
    public void set(String name, Object o)
    {
        data.put(name, o);
    }
    
    public void save(String name, String path) throws JAXBException
    {
        JAXBContext jc = JAXBContext.newInstance(get(name).getClass());
        Marshaller m = jc.createMarshaller();
        
        m.marshal(get(name), new File("data/" + path));
    }
    
    public Object load(Class c, String path) throws JAXBException
    {
        JAXBContext jc = JAXBContext.newInstance(c);
        Unmarshaller u = jc.createUnmarshaller();
        
        return u.unmarshal(new File("data/" + path));
    }
}
