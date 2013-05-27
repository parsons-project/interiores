/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.events.catalogs;

/**
 *
 * @author larribas
 */
public class FTSetModifiedEvent extends ElementSetModifiedEvent {
    
    private String fullName;
    
    public FTSetModifiedEvent(String fullName, String name, boolean isAdded) {
        super(name,isAdded);
        this.fullName = fullName;
    }
    
    public String getFullName() {
        return fullName;
    }
    
}
