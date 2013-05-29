/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.events.catalogs;

/**
 * Represents the event of a particular set of furniture models
 * (for instance, those pertaining to the same type) being modified.
 * @author larribas
 */
public class FMSetModifiedEvent extends ElementSetModifiedEvent {
    
    private String furniture; // The type of furniture
    
    public FMSetModifiedEvent(String furniture, String model, boolean isAdded) {
        super(model,isAdded);
        this.furniture = furniture; 
    }
    
    public String getFurniture() {
        return furniture;
    }
    
}
