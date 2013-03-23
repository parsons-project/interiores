package interiores.core.business;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

/**
 * Model base class.
 * @author hector
 */
abstract public class Model {
    public Model()
    {
        
    }
    
    public Map<String, Object> toMap()
    {
        Map<String, Object> result = new HashMap<String, Object>();
        
        try
        {
            BeanInfo info = Introspector.getBeanInfo(this.getClass(), Object.class);
            PropertyDescriptor[] props = info.getPropertyDescriptors();
        
            for(PropertyDescriptor pd : props)
                result.put(pd.getName(), pd.getReadMethod().invoke(this));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return result;
    }
}
