package drivers;

import interiores.business.models.FurnitureModel;
import interiores.utils.Dimension;
import interiores.business.models.FurnitureType;
import interiores.business.models.Orientation;
import interiores.business.models.OrientedRectangle;
import interiores.business.models.Room;
import interiores.business.models.RoomType;
import interiores.business.models.backtracking.FurnitureValue;
import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.constraints.UnaryConstraint;
import interiores.business.models.constraints.unary.AreaConstraint;
import interiores.business.models.constraints.unary.ColorConstraint;
import interiores.business.models.constraints.unary.MaterialConstraint;
import interiores.business.models.constraints.unary.ModelConstraint;
import interiores.business.models.constraints.unary.OrientationConstraint;
import interiores.business.models.constraints.unary.PriceConstraint;
import interiores.business.models.constraints.unary.SizeConstraint;
import interiores.core.presentation.terminal.IOStream;
import interiores.utils.Range;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author alvaro
 */
public abstract class AbstractDriver {
    
    private static IOStream iostream = new IOStream(System.in, System.out);
    
    public static Dimension readDimension() {
        int w = iostream.readInt("Enter the width: ");
        int h = iostream.readInt("Enter the depth: ");
        
        return new Dimension(w, h);
    }
    
    public static Point readPoint() {
        int x = iostream.readInt("Enter the x: ");
        int y = iostream.readInt("Enter the y: ");
        
        return new Point(x, y);
    }
    
    public static Collection<Point> readPointCollection() {
        int n = iostream.readInt("How many points?: ");
        Collection<Point> points = new ArrayList<Point>(n);
                
        for (Point pt : points) {
            pt = readPoint();
        }
        
        return points;
    }
    
    public static Range readRange() {
        int min = iostream.readInt("Enter the minimum value: ");
        int max = iostream.readInt("Enter the maximum value: ");
        
        return new Range(min,max);
    }
    
    public static Color readColor() {
        
        int r = iostream.readInt("R:");
        int g = iostream.readInt("G: ");
        int b = iostream.readInt("B: ");
        
        return new Color(r, g, b);
    }
    
    public static FurnitureType readFurnitureType() {
        
        String name = iostream.readString("Enter the name of the furniture type: ");
        
        iostream.println("Enter the width range: ");
        Range widthRange = readRange();
        
        iostream.println("Enter the maximum dimensions of the furniture model: ");
        Range depthRange = readRange();
                
        return new FurnitureType(name, widthRange, depthRange);
    }
    
    public static FurnitureModel readFurnitureModel() {
        
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
    
    public static Collection<FurnitureModel> readFurnitureModelCollection() {
        int n = iostream.readInt("How many models?: ");
        Collection<FurnitureModel> models = new ArrayList<FurnitureModel>(n);
                
        for (FurnitureModel m : models) {
            m = readFurnitureModel();
        }
        
        return models;
    }
    
    
    public static Collection<FurnitureType> readFurnitureTypeCollection() {
        
        int n = iostream.readInt("How many types?: ");
        Collection<FurnitureType> furnitureTypes = new ArrayList<FurnitureType>(n);
                
        for (FurnitureType ft : furnitureTypes) {
            ft = readFurnitureType();
        }
        
        return furnitureTypes;
    }
    
    public static RoomType readRoomType() {
        
        String name = iostream.readString("Enter the name of the room type: ");
        
        iostream.println("Enter the furnitures that have to be in this type of Room: ");
        Collection<FurnitureType> accepted = readFurnitureTypeCollection();
        
        iostream.println("Enter the furnitures that can't to be in this type of Room: ");
        Collection<FurnitureType> forbidden = readFurnitureTypeCollection();
        
        return new RoomType(name, accepted, forbidden);
    }
    
    public static Orientation readOrientation() {
        String orientation = iostream.readString("Enter the orientation (N,S,W or E): ");
        
        switch(orientation.charAt(0)) {
            case 'N':
                return Orientation.N;
            case 'E':
                return Orientation.E;
            case 'W':
                return Orientation.W;
            default:
                return Orientation.S;
        }
                
    }
    
    public static Collection<Orientation> readOrientationCollection() {
        int n = iostream.readInt("How many orientations?: ");
        Collection<Orientation> os = new ArrayList<Orientation>(n);
                
        for (Orientation o : os) {
            o = readOrientation();
        }
        
        return os;
    }
    
    
    public static OrientedRectangle readOrientedRectangle() {
        
        iostream.println("Enter the position of the rectangle: ");
        Point pt = readPoint();
        
        iostream.println("Enter the size of the rectangle: ");
        Dimension dim = readDimension();
        
        Orientation o = readOrientation();
        
        return new OrientedRectangle(pt, dim, o);
    }
    
    public static FurnitureValue readFurnitureValue() {
        OrientedRectangle or = readOrientedRectangle();
        FurnitureModel fm = readFurnitureModel();
        
        return new FurnitureValue(or, fm);
    }
    
    
    public static Room readRoom() {
        
        iostream.println("Enter the Room: ");
        
        RoomType rt = readRoomType();
        Dimension dim = readDimension();
        
        return new Room(rt, dim);
    }
    
    public static UnaryConstraint readUnaryConstraint() {
        iostream.println("Reading Unary Constraint: ");
        iostream.println("0) Area");
        iostream.println("1) Color");
        iostream.println("2) Material");
        iostream.println("3) Model");
        iostream.println("4) Orientation");
        iostream.println("5) Price");
        iostream.println("*) Size");
        int opt = iostream.readInt(">> ");
        
        switch (opt) {
            case 0:
                List<Point> points = (List) readPointCollection();
                return new AreaConstraint(points);
            case 1:
                Color c = readColor();
                return new ColorConstraint(c);
            case 2:
                String material = iostream.readString("Enter the material of this constraint: ");
                return new MaterialConstraint(material);
            case 3:
                String model = iostream.readString("Enter the model of the constraint: ");
                return new ModelConstraint(model);
            case 4:
                iostream.println("Enter the valid orientations: ");
                List<Orientation> ors = (List) readOrientationCollection();
                return new OrientationConstraint(ors);
            case 5:
                float price = iostream.readFloat("Enter the price of the constraint: ");
                return new PriceConstraint(price);
            default:
                iostream.println("Enter the minimum dimension of the constraint: ");
                Dimension min = readDimension();
                iostream.println("Enter the maximum dimension of the constraint: ");
                Dimension max = readDimension();
                return new SizeConstraint(min, max);           
        }
    }
    
    public static Collection<UnaryConstraint> readUnaryConstraintCollection() {
        int n = iostream.readInt("How many unary constraints?: ");
        Collection<UnaryConstraint> unaries = new ArrayList<UnaryConstraint>(n);
                
        for (UnaryConstraint uc : unaries) {
            uc = readUnaryConstraint();
        }
        
        return unaries;
    }
    
    public static FurnitureVariable readFurnitureVariable() {
        iostream.println("Enter the models of the variable: ");
        List<FurnitureModel> models = (List) readFurnitureModelCollection();
        
        Room room = readRoom();
        
        iostream.println("Enter the unary contraints of this variable: ");
        List<UnaryConstraint> ucs = (List) readUnaryConstraintCollection();
        
        int varCount = iostream.readInt("Enter the variable count: ");
        
        return new FurnitureVariable(models, room, ucs, varCount);
    }
}
