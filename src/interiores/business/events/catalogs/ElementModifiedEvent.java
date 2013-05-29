/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.events.catalogs;

import interiores.core.Event;

/**
 * Abstraction. Represents the event of a particular element in a set being modified.
 * It holds a full name and a short name of such an element
 * @author larribas
 */
public abstract class ElementModifiedEvent implements Event {
    
    private String shortName; // Internal name for the element
    private String fullName; // Visually formatted name
    
    public ElementModifiedEvent(String fname, String name) {
        fullName = fname;
        this.shortName = name;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public String getName() {
        return shortName;
    }
}
