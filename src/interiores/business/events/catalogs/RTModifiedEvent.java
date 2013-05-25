/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.events.catalogs;

/**
 *
 * @author larribas
 */
public class RTModifiedEvent extends ElementModifiedEvent {
    
    public RTModifiedEvent(String fname, String name) {
        super(fname,name);
    }
}
