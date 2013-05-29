/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.events.catalogs;

/**
 * Represents the event of a furniture type being modified
 * @author larribas
 */
public class FTModifiedEvent extends ElementModifiedEvent {
    
    public FTModifiedEvent(String fname, String name) {
        super(fname,name);
    }
}
