/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.events.catalogs;

/**
 * Represents the event of the set of room types being modified.
 * @author larribas
 */
public class RTSetModifiedEvent extends ElementSetModifiedEvent {
    
    private String fullName; // The full name of the target room type
    
    public RTSetModifiedEvent(String fullName, String name, boolean isAdded) {
        super(name,isAdded);
        this.fullName = fullName;
    }
    
    public String getFullName() {
        return fullName;
    }
    
}
