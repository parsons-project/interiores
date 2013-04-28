package horarios.shared;

/**
 *
 * @author hector
 */
public class StringIdObject
    extends IdObject<String> 
{
    public StringIdObject(String id) {
        super(id);
    }
    
    @Override
    public int compareTo(IdObject o) {
        return identifier.compareTo((String) o.identifier);
    }
}
