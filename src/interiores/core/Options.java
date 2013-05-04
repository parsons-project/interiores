package interiores.core;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author hector
 */
public class Options
{
    private Map<String, Boolean> options;
    
    public Options() {
        options = new HashMap();
    }
    
    public void enable(String option) {
        options.put(option, Boolean.TRUE);
    }
    
    public void disable(String option) {
        options.put(option, Boolean.FALSE);
    }
    
    public void disableIfNotPresent(String[] options) {
        for(String option : options)
            if(! this.options.containsKey(option))
                this.options.put(option, Boolean.FALSE);
    }
    
    public boolean isEnabled(String option) {
        return options.get(option);
    }
    
    public boolean hasOptions() {
        return !options.isEmpty();
    }
}
