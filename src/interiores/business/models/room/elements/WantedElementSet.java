package interiores.business.models.room.elements;

import interiores.business.exceptions.WantedElementNotFoundException;
import interiores.business.models.backtracking.InterioresVariable;
import java.util.Collection;
import java.util.TreeMap;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hector
 */
@XmlRootElement
public class WantedElementSet<T extends InterioresVariable>
{
    @XmlAttribute
    String name;
    
    @XmlElementWrapper
    private TreeMap<String, T> elements;
    
    @XmlElementWrapper
    private TreeMap<String, Integer> typesCount;
    
    public WantedElementSet()
    { }
    
    public WantedElementSet(String name) {
        this.name = name;
        elements = new TreeMap();
        typesCount = new TreeMap();
    }
    
    public T get(String elementId) {
        if(! elements.containsKey(elementId))
            throw new WantedElementNotFoundException(name, elementId);
        
        return elements.get(elementId);
    }
    
    public Collection<T> getAll() {
        return elements.values();
    }
    
    public String add(T element) {
        String typeName = element.getTypeName();
        
        if(typesCount.containsKey(typeName))
            typesCount.put(typeName, typesCount.get(typeName) + 1);
        else
            typesCount.put(typeName, 1);
        
        String elementId = nextIdFor(typeName);
        
        element.setName(elementId);
        elements.put(elementId, element);
        
        return elementId;
    }
    
    public void remove(String elementId) {
        String typeName = getElementTypeName(elementId);
        
        typesCount.put(typeName, typesCount.get(typeName) - 1);
        elements.remove(elementId);
    }
    
    public boolean containsElement(String elementId) {
        return elements.containsKey(elementId);
    }
    
    public int getTypeCount(String typeName) {
        if(! typesCount.containsKey(typeName))
            return 0;
        
        return typesCount.get(typeName);
    }
    
    public int getElementTypeCount(String elementId) {
        String typeName = getElementTypeName(elementId);
        
        return getTypeCount(typeName);
    }
        
    public String getElementTypeName(String elementId) {
        if(! elements.containsKey(elementId))
            throw new WantedElementNotFoundException(name, elementId);
                
        return elements.get(elementId).getTypeName();
    }
    
    public int size() {
        return elements.size();
    }
    
    public Collection<String> getElementNames() {
        return elements.keySet();
    }
    
    public Collection<String> getTypeNames() {
        return typesCount.keySet();
    }
    
    private String nextIdFor(String typeName) {
        String nextId;
        int i = 1;
        
        do {
            // find the first free identifier for the given typename
            nextId = typeName + String.valueOf(i);
            i++;
        } while(elements.containsKey(nextId));
        
        return nextId;
    }
}
