/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.controllers;

import interiores.business.controllers.abstracted.InterioresController;
import interiores.business.exceptions.NoRoomCreatedException;
import interiores.core.data.JAXBDataController;
import interiores.utils.Dimension;
import java.awt.Point;
import java.util.Collection;

/**
 *
 * @author alvaro
 */
public class FixedElementController extends InterioresController{
    
    
    public FixedElementController(JAXBDataController data) {
        super(data);
    }
    
    
    public void add(String name, Point point, Dimension dimension) 
            throws NoRoomCreatedException
    {
        getWishList().addWantedFixed(name, point, dimension);
    }
    
    public void remove(String name) throws NoRoomCreatedException
    {
        getWishList().removeWantedFixed(name);
    }
    
    /**
     * Gets all the furniture in the wish list, that is, all the wanted furniture for the room being designed
     * @return A collection containing all the pieces of furniture in the wish list
     * @throws NoRoomCreatedException 
     */
    public Collection<String> getFixedFurniture()
            throws NoRoomCreatedException
    {
        return getWishList().getFixedNames();
    }

}

