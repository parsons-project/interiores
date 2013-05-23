/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.events.catalogs;

/**
 *
 * @author larribas
 */
public class RTCatalogChangedEvent extends CatalogChangedEvent {
    
    public RTCatalogChangedEvent(String name, boolean added) {
        super(name,added);
    }
}
