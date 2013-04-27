/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models;

import java.awt.Color;
import java.awt.Dimension;

/**
 *
 * @author larribas
 */
public class FurnitureModel {
    
    private String name;        // Comercial name of the furniture model
    private Dimension size;     // Size of the furniture model
    private float price;          // Market price of the furniture model
    private Color color;        // Color of the furniture model
    private String material;    // Material the furniture model is made in
    
    /**
     * Default constructor.
     */
    public FurnitureModel() {
        
    }
    
    /**
     * Full constructor that specifies all of the features of a furniture model.
     * @param name Comercial name of the furniture model
     * @param size Size of the furniture model
     * @param price Market price of the furniture model
     * @param color Color of the furniture model
     * @param material Material the furniture model is made in
     */
    public FurnitureModel(String name, Dimension size, float price, Color color, String material) {
        this.name = name;
        this.size = size;
        this.price = price;
        this.color = color;
        this.material = material;
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
    
    
    
    
    
}
