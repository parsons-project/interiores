/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.controllers;

import interiores.business.controllers.abstracted.InterioresController;
import interiores.business.exceptions.NoRoomCreatedException;
import interiores.business.models.Orientation;
import interiores.business.models.Room;
import interiores.business.models.WantedFurniture;
import interiores.business.models.constraints.BinaryConstraint;
import interiores.business.models.constraints.UnaryConstraint;
import interiores.business.models.constraints.binary.PartialFacingConstraint;
import interiores.business.models.constraints.binary.MaxDistanceConstraint;
import interiores.business.models.constraints.binary.MinDistanceConstraint;
import interiores.business.models.constraints.binary.StraightFacingConstraint;
import interiores.business.models.constraints.unary.AreaConstraint;
import interiores.business.models.constraints.unary.ColorConstraint;
import interiores.business.models.constraints.unary.MaterialConstraint;
import interiores.business.models.constraints.unary.ModelConstraint;
import interiores.business.models.constraints.unary.OrientationConstraint;
import interiores.business.models.constraints.unary.PriceConstraint;
import interiores.business.models.constraints.unary.WallConstraint;
import interiores.business.models.constraints.unary.DepthConstraint;
import interiores.business.models.constraints.unary.WidthConstraint;
import interiores.core.Utils;
import interiores.core.business.BusinessException;
import interiores.core.data.JAXBDataController;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Business Controller covering the use cases related to constraints
 * @author larribas
 */
public class UnaryConstraintController
    extends InterioresController {
    
    /**
     * Creates a particular instance of the unary constraint controller
     * @param data The data controller that will give access to the objects this controller will use
     */
    public UnaryConstraintController(JAXBDataController data)
    {
        super(data);
    }

     /**
     * Gets all all the unary and binary constraints related to the specified wanted furniture
     * @param id Identifier of the furniture
     * @return A collection of both unary and binary constraints
     * @throws NoRoomCreatedException 
     */
    public Collection getConstraints(String id)
            throws NoRoomCreatedException
    {
        return getWishList().getUnaryConstraints(id);
    }
    
    public void addWidthConstraint(String furnitureId, int minWidth, int maxWidth)
            throws NoRoomCreatedException
    {
        addConstraint(furnitureId, new WidthConstraint(minWidth, maxWidth));
    }
    
    public void addDepthConstraint(String furnitureId, int minWidth, int maxWidth)
            throws NoRoomCreatedException
    {
        addConstraint(furnitureId, new DepthConstraint(minWidth, maxWidth));
    }
    
    public void addColorConstraint(String furnitureId, String color)
            throws NoRoomCreatedException, BusinessException
    {
        addConstraint(furnitureId, new ColorConstraint(color));
    }
    
    public void addMaterialConstraint(String furnitureId, String material)
            throws NoRoomCreatedException
    {
        addConstraint(furnitureId, new MaterialConstraint(material));
    }
    
    public void addModelConstraint(String furnitureId, String modelName)
            throws NoRoomCreatedException
    {
        addConstraint(furnitureId, new ModelConstraint(modelName));
    }
    
    public void addOrientationConstraint(String furnitureId, String orientation)
            throws NoRoomCreatedException
    {
        addConstraint(furnitureId, new OrientationConstraint(orientation));
    }
    
    public void addPriceConstraint(String furnitureId, float maxPrice)
            throws NoRoomCreatedException
    {
        addConstraint(furnitureId, new PriceConstraint(maxPrice));
    }
    
    public void addPositionConstraint(String furnitureId, List<Point> validPositions)
            throws NoRoomCreatedException
    {
        addConstraint(furnitureId, new AreaConstraint(validPositions));
    }
    
    public void addPositionAtConstraint(String furnitureId, int x, int y)
            throws NoRoomCreatedException
    {
        List<Point> validPositions = new ArrayList();
        validPositions.add(new Point(x, y));
        
        addPositionConstraint(furnitureId, validPositions);
    }
    
    public void addPositionRangeConstraint(String furnitureId, int x1, int y1, int x2, int y2)
            throws NoRoomCreatedException
    {
        List<Point> validPositions = new ArrayList();
        
        for(int i = x1; i <= x2; ++i)
            for(int j = y1; j <= y2; ++j)
                validPositions.add(new Point(i, j));
        
        addPositionConstraint(furnitureId, validPositions);
    }
    
    public void addWallConstraint(String furnitureId, Orientation[] whichWalls)
            throws NoRoomCreatedException
    {
        Room room = getRoom();
        
        addConstraint(furnitureId, new WallConstraint(room.getWidth(), room.getDepth(), whichWalls));
    }
    
    private void addConstraint(String furnitureId, UnaryConstraint unaryConstraint)
            throws NoRoomCreatedException
    {
        getWantedFurniture(furnitureId).addUnaryConstraint(unaryConstraint);
    }
    
    
    /**
     * Removes a constraint of a specific type that has been defined over a certain piece of furniture.
     * If there was no constraint of that type over that furniture, it does nothing
     * @param ctype The type of the constraint we want to remove
     * @param furnitureID A valid ID of the piece of furniture whose constraint we want to remove
     * @throws NoRoomCreatedException
     */
    public void remove(String ctype, String furnitureId)
            throws NoRoomCreatedException, BusinessException
    {
        try {
            Class unaryConstraintClass = Class.forName("interiores.business.models.constraints.unary." +
                    Utils.capitalize(ctype) + "Constraint");
            
            remove(furnitureId, unaryConstraintClass);
            
        }
        catch(ClassNotFoundException e) {
            throw new BusinessException("The constraint type " + ctype + " does not exist");
        }
    }
    
    public void remove(String furnitureId, Class<UnaryConstraint> unaryConstraintClass)
            throws NoRoomCreatedException
    {
        getWantedFurniture(furnitureId).removeUnaryConstraint(unaryConstraintClass);
    }
    
    /**
     * Gets a specific piece of wanted furniture, defined by its ID within our wish list
     * @param id The ID of the furniture you want to obtain
     * @return The particular furniture piece with ID = id
     * @throws NoRoomCreatedException 
     */
    private WantedFurniture getWantedFurniture(String id)
            throws NoRoomCreatedException
    {
        return getWishList().getWantedFurniture(id);
    }
       
}
