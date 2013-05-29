/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.events.catalogs;

/**
 * Represents the event of the set of room type catalogs being modified
 * @author larribas
 */
public class RTCatalogSetModifiedEvent extends ElementSetModifiedEvent {
    
    public RTCatalogSetModifiedEvent(String name, boolean added) {
        super(name,added);
    }
}
