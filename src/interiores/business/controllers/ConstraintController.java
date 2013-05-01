/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.controllers;

import interiores.business.controllers.abstracted.InterioresController;
import interiores.business.exceptions.NoRoomCreatedException;
import interiores.business.models.Orientation;
import interiores.business.models.WantedFurniture;
import interiores.business.models.constraints.BinaryConstraint;
import interiores.business.models.constraints.UnaryConstraint;
import interiores.business.models.constraints.binary.MaxDistanceConstraint;
import interiores.business.models.constraints.binary.MinDistanceConstraint;
import interiores.business.models.constraints.unary.AreaConstraint;
import interiores.business.models.constraints.unary.ColorConstraint;
import interiores.business.models.constraints.unary.MaterialConstraint;
import interiores.business.models.constraints.unary.OrientationConstraint;
import interiores.business.models.constraints.unary.PriceConstraint;
import interiores.business.models.constraints.unary.SizeConstraint;
import interiores.core.business.BusinessException;
import interiores.core.data.JAXBDataController;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author larribas
 */
public class ConstraintController
    extends InterioresController {
    
    public ConstraintController(JAXBDataController data)
    {
        super(data);
    }

    public Collection getConstraints(String id)
            throws NoRoomCreatedException
    {
        return getWishList().getConstraints(id);
    }

    public void add(String type, List<Object> parameters, String furnitureID) throws NoRoomCreatedException
    {
        if (type.equals("width") || type.equals("depth")) {
            // We get the SizeConstraint of that furniture. If there isn't one, we create it
            if (getWantedFurniture(furnitureID).getConstraint("size")==null)
                getWantedFurniture(furnitureID).addConstraint("size", new SizeConstraint());
            
            SizeConstraint sc = (SizeConstraint) getWantedFurniture(furnitureID).getConstraint("size");
            int min = (Integer) parameters.get(0);
            int max = (Integer) parameters.get(1);
            
            if (type.equals("width")) sc.changeWidth(min,max);
            else sc.changeDepth(min, max);
        }
        else {
            UnaryConstraint uc = null;

            if (type.equals("color")) uc = new ColorConstraint((String) parameters.get(0));
            else if (type.equals("material")) uc = new MaterialConstraint((String) parameters.get(0));
            else if (type.equals("orientation")) uc = new OrientationConstraint(Orientation.valueOf((String) parameters.get(0)));
            else if (type.equals("price")) uc = new PriceConstraint((Integer) parameters.get(0));
            else if (type.equals("position")) {
                List<Point> validPositions = new ArrayList();
                String mode = (String) parameters.get(0);
                if (mode.equals("at")) validPositions.add(new Point((Integer) parameters.get(1), (Integer) parameters.get(2)));
                else if (mode.equals("range")) {
                    for (int i = (Integer) parameters.get(1); i <= (Integer) parameters.get(3); i++)
                        for (int j = (Integer) parameters.get(2); j <= (Integer) parameters.get(4); j++)
                            validPositions.add(new Point(i,j));
                }
                else if (mode.equals("walls")) {
                    String whichWalls = (String) parameters.get(1);
                    int roomWidth = getRoom().getWidth();
                    int roomDepth = getRoom().getHeight();
                    if (whichWalls.equals("N") || whichWalls.equals("all"))
                        for (int i = 0; i < roomWidth; i++) validPositions.add(new Point(0,i));
                    if (whichWalls.equals("S") || whichWalls.equals("all"))
                        for (int i = 0; i < roomWidth; i++) validPositions.add(new Point(roomDepth-1,i));
                    if (whichWalls.equals("W") || whichWalls.equals("all"))
                        for (int i = 0; i < roomDepth; i++) validPositions.add(new Point(i,0));
                    if (whichWalls.equals("E") || whichWalls.equals("all"))
                        for (int i = 0; i < roomDepth; i++) validPositions.add(new Point(i,roomWidth-1));
                }
                
                uc = new AreaConstraint(validPositions);
            }

            getWantedFurniture(furnitureID).addConstraint(type, uc);
        }
        
    }
    
    public void add(String type, List<Object> parameters, String furn1, String furn2)
            throws BusinessException
    {
        if (type.equals("distance"))
        {
            String rel = (String) parameters.get(0);
            int dist = (Integer) parameters.get(1);
            BinaryConstraint bc = null;
            if (rel.equals("min")) bc = new MinDistanceConstraint(dist);
            else if (rel.equals("max")) bc = new MaxDistanceConstraint(dist);
            else throw new BusinessException(rel + " constraint doesn't exist");
            
            getWishList().addBinaryConstraint(rel, bc, furn1, furn2);
        }
    }
    
    public void remove(String ctype, String furnitureID)
            throws NoRoomCreatedException
    {
        getWantedFurniture(furnitureID).removeConstraint(ctype);
    }
    
    public void remove(String ctype, String furn1, String furn2)
            throws NoRoomCreatedException
    {
        getWishList().removeBinaryConstraint(ctype, furn1, furn2);
    }
    
    private WantedFurniture getWantedFurniture(String id)
            throws NoRoomCreatedException
    {
        return getWishList().getWantedFurniture(id);
    }
}
