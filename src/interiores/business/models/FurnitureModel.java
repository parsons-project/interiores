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
    private int price;          // Market price of the furniture model
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
    public FurnitureModel(String name, Dimension size, int price, Color color, String material) {
        this.name = name;
        this.size = size;
        this.price = price;
        this.color = color;
        this.material = material;
    }
    
    /**
     * Gets the size of the furniture model
     * @return Dimension object representing the size of the model
     */
    public Dimension getSize() {
        return size;
    }
    
    
    
    
    
}
