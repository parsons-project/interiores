package interiores.core;

import java.util.Map;

/**
 *
 * @author hector
 */
public interface Observer
{
    public void notify(String name, Map<String, ?> data);
}
