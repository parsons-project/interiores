/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.events.catalogs;

/**
 *
 * @author larribas
 */
public class FTCatalogChangedEvent extends CatalogChangedEvent {
    
    public FTCatalogChangedEvent(String name, boolean added) {
        super(name,added);
    }
}
