package interiores.core.data;

import javax.xml.bind.JAXBException;

/**
 * Public interface for mapping and saving data using JAXB.
 * @author hector
 */
public interface JAXBDataController {
    
    /**
     * Returns the object previosuly saved with the name given.
     * @param name Name of the object to get
     * @return Object previously saved as name
     */
    public Object get(String name);
    
    /**
     * Stores an object with the name given.
     * @param name Name of the object
     * @param o Object to store
     */
    public void set(String name, Object o);
    
    /**
     * Tells whether there is a stored object with the name given.
     * @param name Name of the object
     * @return True if an object with that name is stored, false otherwise
     */
    public boolean has(String name);
    
    /**
     * Saves the given object as XML in the given path.
     * @param o Object to save as XML
     * @param path Path where to save the file with the generated XML
     * @throws JAXBException 
     */
    public void save(Object o, String path) throws JAXBException;
    
    /**
     * Load the given object as XML in the given path using the boundClasses to translate all the data.
     * This method is useful to say explicitly which classes JAXB has to take into account to save the data.
     * @param o Object to save as XML
     * @param path Path where to save the file with the generated XML
     * @param boundClasses Classes that JAXB has to take into account
     * @throws JAXBException 
     */
    public void save(Object o, String path, Class[] boundClasses) throws JAXBException;
    
    /**
     * Load an instance of the given class from the XML found in path
     * @param c Class of the object to load
     * @param path Path to the XML file to load
     * @return The loaded object
     * @throws JAXBException 
     */
    public Object load(Class c, String path) throws JAXBException;
    
    /**
     * Load an instance from the XML found in path using the boundClasses to understand all the data.
     * This method is useful to say explicitly which classes JAXB has to take into account to load the data.
     * @param boundClasses Classes that JAXB has to look at
     * @param path Path to the XML file to load
     * @return The loaded object
     * @throws JAXBException 
     */
    public Object load(Class[] boundClasses, String path) throws JAXBException;
}
