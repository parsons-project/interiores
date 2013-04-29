package drivers;

import interiores.business.models.FurnitureModel;
import interiores.utils.Dimension;
import interiores.business.models.FurnitureType;
import interiores.business.models.RoomType;
import interiores.core.presentation.terminal.IOStream;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author alvaro
 */
public abstract class AbstractDriver {
    
    private static IOStream iostream = new IOStream(System.in, System.out);
    
    public Dimension readDimension() {
        int w = iostream.readInt("Enter the width: ");
        int h = iostream.readInt("Enter the depth: ");
        
        return new Dimension(w, h);
    }
    
    public Color readColor() {
        
        int r = iostream.readInt("R:");
        int g = iostream.readInt("G: ");
        int b = iostream.readInt("B: ");
        
        return new Color(r, g, b);
    }
    
    public FurnitureType readFurnitureType() {
        
        String name = iostream.readString("Enter the name of the furniture type: ");
        
        iostream.println("Enter the minimum dimensions of the furniture model: ");
        Dimension minDim = readDimension();
        
        iostream.println("Enter the maximum dimensions of the furniture model: ");
        Dimension maxDim = readDimension();
                
        return new FurnitureType(name, minDim, maxDim);
    }
    
    public FurnitureModel readFurnitureModel() {
        
        FurnitureType ftype = readFurnitureType();
        
        String name = iostream.readString("Enter the name of the furniture model: ");
        
        iostream.println("Enter the dimensions of the model: ");
        Dimension dim = readDimension();
        
        float price = iostream.readFloat("Enter the price of the furniture model: ");
        
        iostream.println("Introduce un color");
        Color color = readColor();
        
        String material = iostream.readString("Enter the material of the furniture model");
        
        
        return new FurnitureModel(ftype, name, dim, price, color, material);
        
    }
    
    public Collection<FurnitureType> readFurnitureTypeCollection() {
        
        int n = iostream.readInt("How many?: ");
        Collection<FurnitureType> furnitureTypes = new ArrayList<FurnitureType>(n);
                
        for (FurnitureType ft : furnitureTypes) {
            ft = readFurnitureType();
        }
        
        return furnitureTypes;
    }
    
    public RoomType readRoomType() {
        
        String name = iostream.readString("Enter the name of the room type: ");
        
        iostream.println("Enter the furnitures that have to be in this type of Room: ");
        Collection<FurnitureType> accepted = readFurnitureTypeCollection();
        
        iostream.println("Enter the furnitures that can't to be in this type of Room: ");
        Collection<FurnitureType> forbidden = readFurnitureTypeCollection();
        
        return new RoomType(name, accepted, forbidden);
    }
    
    
    public abstract boolean test();
}
