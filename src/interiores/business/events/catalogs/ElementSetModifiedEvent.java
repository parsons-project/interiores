/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.events.catalogs;

import interiores.core.Event;

/**
 *
 * @author larribas
 */
public abstract class ElementSetModifiedEvent
implements Event
{
    
    private String name;
    private boolean added;
    
    public ElementSetModifiedEvent(String name, boolean added) {
        this.name = name;
        this.added = added;
    }
    
    public String getName() {
        return name;
    }
    
    public boolean isAdded() {
        return added;
    }
    
}
