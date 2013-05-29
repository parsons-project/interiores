package interiores.business.controllers;

import interiores.business.controllers.abstracted.CatalogAccessController;
import interiores.business.events.catalogs.FMModifiedEvent;
import interiores.business.events.catalogs.FMSetModifiedEvent;
import interiores.business.exceptions.ElementNotFoundBusinessException;
import interiores.business.models.SpaceAround;
import interiores.business.models.catalogs.AvailableCatalog;
import interiores.business.models.room.FurnitureModel;
import interiores.business.models.room.FurnitureType;
import interiores.core.data.JAXBDataController;
import interiores.utils.CoolColor;
import interiores.utils.Dimension;
import interiores.utils.Material;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Business controller covering the operations related to consulting or modifying furniture models 
 * @author hector
 */
public class FurnitureModelController
    extends CatalogAccessController<FurnitureType>
{
    
    // Since there is no easy way to obtain the properties of an element by its name
    // in constant time, we will cache the last model accessed.
    private FurnitureModel cached_fm = null;
    
    /**
     * Creates a particular instance of the furniture model controller
     * @param data The data controller that will give access to the objects this controller will use
     */
    public FurnitureModelController(JAXBDataController data) {
        super(data, AvailableCatalog.FURNITURE_TYPES);
    }
    
    /**
     * Adds a furniture model of a certain type to the furniture catalog
     * @param furnitureTypeName The name of the type of furniture
     * @param name The name of the model
     * @param width The width of the specific model
     * @param depth The depth of the specific model
     * @param price The price the model costs
     * @param color The color of the specific model
     * @param material The material the specific model is made from
     * @param passiveOffsets The cardinal offsets (N,E,S,W) of the passive space the furniture should hold by default
     */
    public void add(String furnitureTypeName, String name, int width, int depth, float price,
            String color, String material, int[] passiveOffsets)
    {
        FurnitureType furnitureType = getForWrite(furnitureTypeName);
        
        Dimension size = new Dimension(width, depth);
        SpaceAround passiveSpace = new SpaceAround(passiveOffsets[0], passiveOffsets[1], passiveOffsets[2],
                passiveOffsets[3]);
        
        FurnitureModel furnitureModel = new FurnitureModel(name, size, price, color, material,
                passiveSpace);
        
        furnitureType.addFurnitureModel(furnitureModel);
        
        notify(new FMSetModifiedEvent(furnitureTypeName, name, true));
    }
    
    /**
     * Removes a model of a specific type
     * @param furnitureTypeName The name of the type of furniture the model belongs to
     * @param name The name of the model
     */
    public void rm(String furnitureTypeName, String name)
    {
        FurnitureType furnitureType = getForWrite(furnitureTypeName);
        
        furnitureType.removeFurnitureModel(name);
        
        notify(new FMSetModifiedEvent(furnitureTypeName, name, false));
    }
    
    /**
     * Gets all the models of a certain type
     * @param furnitureTypeName The name of the type of furniture
     * @return A collection of furniture models
     */
    public Collection<FurnitureModel> getFurnitureModels(String furnitureTypeName)
    {
        FurnitureType furnitureType = get(furnitureTypeName);
        
        return furnitureType.getFurnitureModels();
    }
    
    /**
     * Obtains the name of all the furniture models available for a certain type of furniture, within the active catalog
     * @param furnitureTypeName The type of furniture whose models we want
     * @return A collection of strings containing all the names of the particular models of 'furnitureTypeName'
     */
    public Collection<String> getFurnitureModelNames(String furnitureTypeName) {
        Collection<FurnitureModel> fmodels = getFurnitureModels(furnitureTypeName);
        
        Collection<String> furnitureNames = new ArrayList();
        for (FurnitureModel fm : fmodels) {
            furnitureNames.add(fm.getName());
        }
        return furnitureNames;
    }
    
    // INDIVIDUAL-ATTRIBUTE GETTERS
    
    public Dimension getSize(String furnitureTypeName, String name) {
        return getModel(furnitureTypeName, name).getSize();
    }
    
    public String getMaterial(String furnitureTypeName, String name) {
        return getModel(furnitureTypeName, name).getMaterial();
    }
    
    public String getColor(String furnitureTypeName, String name) {
        return getModel(furnitureTypeName, name).getColorName();
    }
    
    public float getPrice(String furnitureTypeName, String name) {
        return getModel(furnitureTypeName, name).getPrice();
    }
    
    /**
     * Gets the passive space there should be around a model
     * @return An array of four positions with offsets >= 0 representing N,E,S,W coordinates, in that order
     */
    public int[] getPassiveSpace(String furnitureTypeName, String name) {
        return getModel(furnitureTypeName, name).getPassiveSpace().getOffsets();
    }
    
    /**
     * Replaces all the properties of a given furniture by new ones
     * @param furnitureTypeName The type of furniture the model pertains to
     * @param name The name of the furniture model we want to modify
     * @param width A particular width
     * @param depth A particular depth
     * @param price The price the model costs
     * @param color The color the model is painted in
     * @param material The material the model is made from
     * @param passiveOffsets An array of size 4 representing N,E,S,W offsets to the passive space around the model
     */
    public void replace(String furnitureTypeName, String name, int width, int depth, float price,
            String color, String material, int[] passiveOffsets)
    {
        FurnitureModel fm = getModel(furnitureTypeName, name);
        fm.setSize(new Dimension(width,depth));
        fm.setPrice(price);
        fm.setPassiveSpace(new SpaceAround(passiveOffsets[0], passiveOffsets[1], passiveOffsets[2],passiveOffsets[3]));
        fm.setMaterial(Material.getEnum(material));
        fm.setColor(CoolColor.getEnum(color));
        
        notify(new FMModifiedEvent(name,furnitureTypeName));
    }
    
    /**
     * Obtains a particular model as such. Used internally
     */
    private FurnitureModel getModel(String furnitureTypeName, String name) {
        // If the model is chached, we return it
        if (cached_fm != null && cached_fm.getType().equals(furnitureTypeName) && cached_fm.getName().equals(name) );
        // Else, we find it and cache it!
        else {
            boolean wasFound = false;
            for (FurnitureModel fm : getFurnitureModels(furnitureTypeName))
                if (fm.getName().equals(name)) { cached_fm = fm; wasFound = true; break; }
        
            if(!wasFound) throw new ElementNotFoundBusinessException(null);
        }
        
        return cached_fm;
    }
    
}
