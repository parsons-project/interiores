/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.controllers;

import interiores.business.controllers.abstracted.InterioresController;
import interiores.business.models.fixed.Door;
import interiores.business.models.fixed.Pillar;
import interiores.business.models.fixed.Window;
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

    }
    
    public void addWindow(String wall, int displacement, int length) {
        getWishList().addWantedFixed(new Window(wall, displacement, length, getRoom().getDimension()));
    }
    
    public void addPillar(Point point, Dimension dimension) {
        getWishList().addWantedFixed(new Pillar(point, dimension));
    }
    
    public void remove(String name) {
        getWishList().removeWantedFixed(name);
    }
    
    public Collection<String> getFixedFurniture() {
        return getWishList().getFixedNames();
    }

    public Collection<String> getSelectable() {
        return selectable;
    }

}

