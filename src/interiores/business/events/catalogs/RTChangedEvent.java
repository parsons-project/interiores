/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.events.catalogs;

/**
 *
 * @author larribas
 */
public class RTChangedEvent extends ElementChangedEvent {
    
    public RTChangedEvent(String name, boolean isAdded) {
        super(name,isAdded);
    }
    
}
