package interiores.data;

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
    
    /**
     * Creates a new data controller.
     * 
     * You can use this class to store and get objects in memory. Additionally, 
     * the class can load and save objects represented as XML files.
     * 
     * Example:
     * DataController data = new DataController();
     * 
     * Foo foo = new Foo();
     * foo.setName("bar");
     * 
     * // Store foo in memory with the name "foobar"
     * data.set("foobar", foo);
     * 
     * // Get the saved object
     * foo = data.get("foobar")
     * 
     * // Save foo as XML into foobar.xml
     * data.save(foo, "foobar.xml");
     * 
     * // Load foobar.xml as Foo object
     * foo = data.load(Foo.class, "foobar.xml");
     */
    public DataController()
    {
        data = new HashMap<String, Object>();
    }
    
    /**
     * Returns the object previosuly saved with the name given.
     * @param name Name of the object to get
     * @return Object previously saved as name
     */
    public Object get(String name)
    {
        return data.get(name);
    }
    
    /**
     * Stores an object with the name given.
     * @param name Name of the object
     * @param o Object to store
     */
    public void set(String name, Object o)
    {
        data.put(name, o);
    }
    
    /**
     * Saves the given object as XML in the given path.
     * @param o Object to save as XML
     * @param path Path where to save the file with the generated XML
     * @throws JAXBException 
     */
    public void save(Object o, String path) throws JAXBException
    {
        JAXBContext jc = JAXBContext.newInstance(o.getClass());
        Marshaller m = jc.createMarshaller();
        m.setProperty("jaxb.formatted.output", true);
        
        m.marshal(o, new File(path));
    }
    
    /**
     * Load an instance of the given class from the XML found in path
     * @param c Class of the object to load
     * @param path Path to the XML file to load
     * @return The loaded object
     * @throws JAXBException 
     */
    public Object load(Class c, String path) throws JAXBException
    {
        JAXBContext jc = JAXBContext.newInstance(c);
        Unmarshaller u = jc.createUnmarshaller();
        
        return u.unmarshal(new File(path));
    }
}
