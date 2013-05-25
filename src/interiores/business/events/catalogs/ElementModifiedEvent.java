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
public abstract class ElementModifiedEvent implements Event {
    
    private String fullname;
    private String name;
    
    public ElementModifiedEvent(String fname, String name) {
        fullname = fname;
        this.name = name;
    }
    
    public String getFullName() {
        return fullname;
    }
    
    public String getName() {
        return name;
    }
}
