/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.controllers;

import interiores.business.controllers.abstracted.InterioresController;
import interiores.business.exceptions.MandatoryFurnitureException;
import interiores.business.exceptions.NoRoomCreatedException;
import interiores.business.models.fixed.Door;
import interiores.business.models.fixed.Pillar;
import interiores.business.models.fixed.Window;
import interiores.core.business.BusinessException;
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
    
    
    public void addDoor(Point point, int length) 
            throws NoRoomCreatedException {
        getWishList().addWantedFixed(new Door(point, length, getRoom().getDimension()));

    }
    
    public void addWindow(Point point, int length) 
            throws NoRoomCreatedException {
        getWishList().addWantedFixed(new Window(point, length, getRoom().getDimension()));
    }
    
    public void addPillar(Point point, Dimension dimension) 
            throws NoRoomCreatedException {
        getWishList().addWantedFixed(new Pillar(point, dimension));
    }
    
    public void remove(String name) 
            throws NoRoomCreatedException, MandatoryFurnitureException  {
        getWishList().removeWantedFixed(name);
    }
    
    public Collection<String> getFixedFurniture()
            throws NoRoomCreatedException {
        return getWishList().getFixedNames();
    }

    public Collection<String> getSelectable() {
        return selectable;
    }

}

