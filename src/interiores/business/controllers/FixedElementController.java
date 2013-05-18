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
    
    
    public void add(String type, Point point, Dimension dimension) 
            throws NoRoomCreatedException, BusinessException  {
        if (type.equals("door")) {
            getWishList().addWantedFixed(new Door(point, dimension, getRoom().getDimension()));
        }
        else if (type.equals("window")) {
            getWishList().addWantedFixed(new Window(point, dimension, getRoom().getDimension()));
        }
        else if (type.equals("pillar")) {
            getWishList().addWantedFixed(new Pillar(point, dimension));
        }
        else
            throw new BusinessException(type + " is not a fixed element");
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

