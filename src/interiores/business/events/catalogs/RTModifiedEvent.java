/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.events.catalogs;

/**
 * Represents the event of a room type being modified
 * @author larribas
 */
public class RTModifiedEvent extends ElementModifiedEvent {
    
    public RTModifiedEvent(String fname, String name) {
        super(fname,name);
    }
}
