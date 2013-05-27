/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.events.catalogs;

/**
 *
 * @author larribas
 */
public class RTSetModifiedEvent extends ElementSetModifiedEvent {
    
    private String fullName;
    
    public RTSetModifiedEvent(String fullName, String name, boolean isAdded) {
        super(name,isAdded);
        this.fullName = fullName;
    }
    
    public String getFullName() {
        return fullName;
    }
    
}
