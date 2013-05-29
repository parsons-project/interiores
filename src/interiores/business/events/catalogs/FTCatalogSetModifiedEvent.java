/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.events.catalogs;

/**
 * Represents the event of the set of furniture type catalogs being modified
 * @author larribas
 */
public class FTCatalogSetModifiedEvent extends ElementSetModifiedEvent {
    
    public FTCatalogSetModifiedEvent(String name, boolean added) {
        super(name,added);
    }
}
