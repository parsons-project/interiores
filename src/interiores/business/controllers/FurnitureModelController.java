package interiores.business.controllers;

import interiores.business.controllers.abstracted.CatalogAccessController;
import interiores.business.models.SpaceAround;
import interiores.business.models.catalogs.AvailableCatalog;
import interiores.business.models.room.FurnitureModel;
import interiores.business.models.room.FurnitureType;
import interiores.core.data.JAXBDataController;
import interiores.utils.Dimension;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Business controller covering the operations related to consulting or modifying furniture models 
 * @author hector
 */
public class FurnitureModelController
    extends CatalogAccessController<FurnitureType>
{
    
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
    
    public Collection<String> getFurnitureModelNames(String furnitureTypeName) {
        Collection<FurnitureModel> fmodels = getFurnitureModels(furnitureTypeName);
        
        Collection<String> furnitureNames = new ArrayList();
        for (FurnitureModel fm : fmodels) {
            furnitureNames.add(fm.getName());
        }
        return furnitureNames;
    }
}
