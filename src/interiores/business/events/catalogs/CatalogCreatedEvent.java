/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.events.catalogs;

import interiores.business.models.catalogs.PersistentIdObject;
import interiores.core.Event;

/**
 *
 * @author larribas
 */
public class CatalogCreatedEvent<I extends PersistentIdObject>
implements Event
{
    
    private String name;
    
    public CatalogCreatedEvent(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
}
