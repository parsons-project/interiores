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
public class FMModifiedEvent implements Event {
    
    private String name;
    private String furniture;
    
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
