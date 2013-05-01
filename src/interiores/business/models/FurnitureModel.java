/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models;

import interiores.core.Utils;
import interiores.data.adapters.ColorAdapter;
import interiores.utils.Dimension;
import java.awt.Color;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Represents a furniture model of a specific type. A model has its own name, size, price, color, and material
 * @author larribas
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class FurnitureModel {
    @XmlAttribute
    private String type;       // Furniture's type of this model
        
    @XmlAttribute
    private String name;        // Comercial name of the furniture model
    
    @XmlElement
    private Dimension size;     // Size of the furniture model
    
    @XmlAttribute
    private float price;          // Market price of the furniture model
    
    @XmlAttribute
    @XmlJavaTypeAdapter(ColorAdapter.class)
    private Color color;        // Color of the furniture model
    
    @XmlAttribute
    private String material;    // Material the furniture model is made in
    
    /**
     * Default constructor.
     */
    public FurnitureModel() {
        
    }
    
    /**
     * Full constructor that specifies all of the features of a furniture model.
     * @param type Furniture model's type
     * @param name Comercial name of the furniture model
     * @param size Size of the furniture model
     * @param price Market price of the furniture model
     * @param color Color of the furniture model
     * @param material Material the furniture model is made in
     */
    public FurnitureModel(String name, Dimension size, float price, Color color,
            String material)
    {
        this.name = name;
        this.size = size;
        this.price = price;
        this.color = color;
        this.material = material;
    }
    
    /**
     * Gets the type of the furniture
     * @return The furniture type of the model
     */
    public String getType() {
        return type;
    }
    
    /**
     * Sets the type of the furniture
     * @param type The type of this piece of furniture
     */
    public void setType(String type) {
        this.type = type;
    }
    
    /**
     * Gets the name of the furniture model
     * @return String object representing the name of the model
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the size of the furniture model
     * @return Dimension object representing the size of the model
     */
    public Dimension getSize() {
        return size;
    }
    
    /**
     * Gets the color of the furniture model
     * @return Color object representing the color of the model
     */
    public Color getColor() {
        return color;
    }

    /**
     * Gets the material of the furniture model
     * @return String representing the material the model is made from
     */
    public String getMaterial() {
        return material;
    }

    /**
     * Gets the price of the furniture model
     * @return float object representing the market price of the model 
     */
    public float getPrice() {
        return price;
    }
    
    @Override
    public String toString() {
        String colorString = "r=" + color.getRed() + ",g=" + color.getGreen() + ",b=" + color.getBlue();
        
        return Utils.padRight(name, 20) + "Size[" + size + "], Price[" + price + "], Color[" + colorString
                + "], [Material: " + material + "]";
    }
}
