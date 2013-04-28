package horarios.shared;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Catalog represents a set of IdObjects
 * @author Jaume
 */
@XmlRootElement
public class Catalog<X extends IdObject> {

    /**
     * Hashmap with all the objects and identifiers in the Catalog
     */
    @XmlElementWrapper
    @XmlElement(name = "element")
    protected HashMap<String, X> table;
    
    
    /**
     * Empty constructor
     */
    public Catalog() {
        table = new HashMap();
    }
    
    
    /**
     * Returns the number of elements in the Catalog
     * @return Number of elements in the Catalog
     */
    public int size() {
        return table.size();
    }
    
    
    /**
     * Tests if the Catalog is empty
     * @return True if there no was any element, else False
     */
    public Boolean isEmpty() {
        return table.isEmpty();
    }
    
    
    /**
     * Tests if an object is in the Calalog
     * @params o Element to find
     * @return True if it was found, else False
     */
    public Boolean hasObject(X o) {
        return table.containsKey(o.getId());
    }
    
   
    /**
     * Tests if an object is in the Calalog
     * @params id Element identifier to find
     * @return True if it was found, else False
     */
    public Boolean hasObject(String id) {
        return table.containsKey(id);
    }
    
    
    /**
     * Add or replace a new element to the Catalog
     * @params o Element to add or replace
     */
    public void add(X o) {
        table.put(o.getId(), o);
    }
    
    
    /**
     * Delete an element of Catalog
     * @params id Code identifier of the element to delete
     */
    public void delete(String id) {
        table.remove(id);
    }
    
    
    /**
     * Delete an element of Catalog
     * @params o Element to delete
     */
    public void delete(X o) {
        table.remove(o.getId());
    }
    
    
    /**
     * Returns the element which has the specified code identifier
     * @throws ElementNotFoundException Exception when the asked element isn't into the Catalog
     * @params id Code identifier of the element to delete
     * @return Element with the code identifier or NULL if it isn't on the Catalog
     */
    public X getObject(String id)
        throws ElementNotFoundException {
        X o = table.get(id);
        if (o == null) throw new ElementNotFoundException(id);
        else return o;
    }
    
    
    /**
     * Getter of an ArrayList that contains a copy of all objects in the Catalog.
     * It creates a copy of all elements and can be some ineficient.
     * @return Unordered ArrayList with all objects in Catalog
     */
    public ArrayList<X> getCopyObjects() {
        return new ArrayList(table.values());
    }
    
    
    /**
     * Getter of a Collection that contains all objects in the Catalog.
     * Note that if you delete/modify an object in colection, it will repercute in the Catalog. But
     * if you add an object it won't be added to the Catalog. Some errors can happen when the
     * Catalog is modificated and a old Collection is used.
     * @return Collection with all objects in Catalog
     */
    public Collection<X> getObjects() {
        return table.values();
    }
    
    
    /**
     * Getter of a Set that contains all objects identifiers in the Catalog.
     * @return Set with all objects identifiers in Catalog
     */
    public Set<String> getKeys() {
        return table.keySet();
    }
}
