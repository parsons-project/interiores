/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.controllers;

import interiores.business.controllers.abstracted.InterioresController;
import interiores.business.events.room.WantedFixedChangedEvent;
import interiores.business.models.room.elements.WantedFixed;
import interiores.business.models.room.elements.fixed.Door;
import interiores.business.models.room.elements.fixed.Pillar;
import interiores.business.models.room.elements.fixed.Window;
import interiores.core.data.JAXBDataController;
import interiores.utils.Dimension;
import java.awt.Point;
import java.util.Arrays;
import java.util.Collection;

/**
 *
 * @author alvaro
 */
public class FixedElementController extends InterioresController{
    
    private final Collection<String> selectable = Arrays.asList(new String[]{"door", "window", "pillar"});
    
    public FixedElementController(JAXBDataController data) {
        super(data);
    }
    
    
    public void addDoor(String wall, int displacement, int length) {
        getWishList().addWantedFixed(new Door(wall, displacement, length, getRoom().getDimension()));
        
        notify(new WantedFixedChangedEvent());
    }
    
    public void addWindow(String wall, int displacement, int length) {
        getWishList().addWantedFixed(new Window(wall, displacement, length, getRoom().getDimension()));
        
        notify(new WantedFixedChangedEvent());
    }
    
    public void addPillar(Point point, Dimension dimension) {
        getWishList().addWantedFixed(new Pillar(point, dimension));
        
        notify(new WantedFixedChangedEvent());
    }
    
    public void remove(String name) {
        getWishList().removeWantedFixed(name);
        
        notify(new WantedFixedChangedEvent());
    }
    
    public Collection<WantedFixed> getWantedFixed() {
        return getWishList().getWantedFixed();
    }
    
    public Collection<String> getFixedNames() {
        return getWishList().getFixedNames();
    }

    public Collection<String> getSelectable() {
        return selectable;
    }
}
