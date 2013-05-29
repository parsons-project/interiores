/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.events.catalogs;

import interiores.core.Event;

/**
 * Represents the event of a furniture model being modified
 * @author larribas
 */
public class FMModifiedEvent implements Event {
    
    private String name; // Name of the model
    private String furniture; // Name of the furniture type
    
    public FMModifiedEvent(String name, String furniture) {
        this.name = name;
        this.furniture = furniture;
    }
    
    public String getName() {
        return name;
    }
    
    public String getFurniture() {
        return furniture;
    }
}
