/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.events.catalogs;

/**
 * Represents the event of a particular set of furniture types being modified.
 * For instance, those pertaining to the same catalog.
 * @author larribas
 */
public class FTSetModifiedEvent extends ElementSetModifiedEvent {
    
    private String fullName; // The full name of the target furniture type
    
    public FTSetModifiedEvent(String fullName, String name, boolean isAdded) {
        super(name,isAdded);
        this.fullName = fullName;
    }
    
    public String getFullName() {
        return fullName;
    }
    
}
