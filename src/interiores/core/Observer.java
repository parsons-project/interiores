package interiores.core;

import java.util.Map;

/**
 * All the instances that implement this interface can recieve notifications from Observable instances.
 * The Observer interface is part of the observer pattern.
 * @author hector
 */
public interface Observer
{
    public void notify(String name, Map<String, ?> data);
}
