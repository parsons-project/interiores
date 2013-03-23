package interiores.core;

import java.util.Map;

/**
 *
 * @author hector
 */
public interface Observable
{
    public void registerObserver(Observer obs);
    public void notify(String name, Map<String, Object> data);
}
