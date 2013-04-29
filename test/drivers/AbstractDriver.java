package drivers;

import interiores.business.models.FurnitureModel;
import interiores.utils.Dimension;
import interiores.business.models.FurnitureType;
import interiores.core.presentation.terminal.IOStream;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 * @author alvaro
 */
public abstract class AbstractDriver {
    
    private static IOStream iostream = new IOStream(System.in, System.out);
    
    public Color readColor() {
        iostream.println("Introduce un color");
        int r = iostream.readInt("R:");
        int g = iostream.readInt("G: ");
        int b = iostream.readInt("B: ");
        
        return new Color(r, g, b);
    }
    
    public FurnitureType readFurnitureType() {
        
        String name = iostream.readString("Enter the name of the furniture type: ");
        
        int minWidth = iostream.readInt("Enter the minimum width of the type: ");
        int minDepth = iostream.readInt("Enter the minimum depth of the type: ");
        
        int maxWidth = iostream.readInt("Enter the maximum width of the type: ");
        int maxDepth = iostream.readInt("Enter the maximum depth of the type: ");
        
        return new FurnitureType(name, new Dimension(minWidth, minDepth),
                                 new Dimension(maxWidth, maxDepth));
    }
    
    public FurnitureModel readFurnitureModel() {
        
        FurnitureType ftype = readFurnitureType();
        
        String name = iostream.readString("Enter the name of the furniture model: ");
        
        int width = iostream.readInt("Enter the minimum width of the model: ");
        int depth = iostream.readInt("Enter the minimum depth of the model: ");
        
        float price = iostream.readFloat("Enter the price of the furniture model: ");
        
        Color color = readColor();
        
        String material = iostream.readString("Enter the material of the furniture model");
        
        
        return new FurnitureModel(ftype, name, Dimension(width, depth), price, color, material);
        
    }
    
    public abstract readInput(String filename);
        BufferedReader br = new BufferedReader(new FileReader(filename));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            String everything = sb.toString();
        } finally {
            br.close();
       
    }
    
    
    public abstract boolean test();
}
