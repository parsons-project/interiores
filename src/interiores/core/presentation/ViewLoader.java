/*
 */
package interiores.core.presentation;

import interiores.core.presentation.View;

/**
 *
 * @author hector
 */
public interface ViewLoader
{
    public void init();
    public boolean isLoaded(String name);
    public void load(String name) throws Exception;
    public void unload(String name) throws Exception;
    public View get(String name) throws Exception;
}
