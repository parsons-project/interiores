/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.events.catalogs;

import interiores.core.Event;

/**
 * Represents the event of the currently active room type catalog changing
 * @author larribas
 */
public class RTCatalogCheckoutEvent implements Event {
    
    private String name;
    
    public RTCatalogCheckoutEvent(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}
