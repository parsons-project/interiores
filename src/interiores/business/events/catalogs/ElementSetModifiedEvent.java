/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.events.catalogs;

import interiores.core.Event;

/**
 * Abstraction. Represents the event of a particular set of elements being modified.
 * It holds the name of the element being modified, and and a boolean telling whether
 * the modification has been an addition or a substraction
 * @author larribas
 */
public abstract class ElementSetModifiedEvent
implements Event
{
    
    private String name; // The name of the element
    private boolean added; // 'true' if it has been added. 'false' if it has been removed
    
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
