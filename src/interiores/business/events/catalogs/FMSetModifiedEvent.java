/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.events.catalogs;

/**
 *
 * @author larribas
 */
public class FMSetModifiedEvent extends ElementSetModifiedEvent {
    
    private String furniture;
    
    public FMSetModifiedEvent(String furniture, String model, boolean isAdded) {
        super(model,isAdded);
        this.furniture = furniture; 
    }
    
    public String getFurniture() {
        return furniture;
    }
    
}
