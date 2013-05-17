package interiores.business.controllers;

import interiores.business.controllers.abstracted.CatalogAccessController;
import interiores.business.exceptions.DefaultCatalogOverwriteException;
import interiores.business.exceptions.ElementNotFoundBusinessException;
import interiores.business.models.FurnitureModel;
import interiores.business.models.FurnitureType;
import interiores.business.models.catalogs.AvailableCatalog;
import interiores.core.business.BusinessException;
import interiores.core.data.JAXBDataController;
import interiores.utils.Dimension;
import java.awt.Color;
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
            String color, String material)
            throws ElementNotFoundBusinessException, DefaultCatalogOverwriteException, BusinessException
    {
        FurnitureType furnitureType = getForWrite(furnitureTypeName);
        
        Dimension size = new Dimension(width, depth);
        Color modelColor = Color.decode(color);
        
        FurnitureModel furnitureModel = new FurnitureModel(name, size, price, modelColor, material);
        
        furnitureType.addFurnitureModel(furnitureModel);
    }
    
    /**
     * Removes a model of a specific type
     * @param furnitureTypeName The name of the type of furniture the model belongs to
     * @param name The name of the model
     * @throws ElementNotFoundBusinessException
     * @throws DefaultCatalogOverwriteException
     * @throws BusinessException 
     */
    public void rm(String furnitureTypeName, String name)
            throws ElementNotFoundBusinessException, DefaultCatalogOverwriteException, BusinessException
    {
        FurnitureType furnitureType = getForWrite(furnitureTypeName);
        
        furnitureType.removeFurnitureModel(name);
    }
    
    /**
     * Gets all the models of a certain type
     * @param furnitureTypeName The name of the type of furniture
     * @return A collection of furniture models
     * @throws ElementNotFoundBusinessException 
     */
    public Collection<FurnitureModel> getFurnitureModels(String furnitureTypeName)
            throws ElementNotFoundBusinessException
    {
        FurnitureType furnitureType = get(furnitureTypeName);
        
        return furnitureType.getFurnitureModels();
    }
}
