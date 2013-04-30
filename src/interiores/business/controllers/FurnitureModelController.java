package interiores.business.controllers;

import interiores.business.exceptions.DefaultCatalogOverwriteException;
import interiores.business.exceptions.ElementNotFoundBusinessException;
import interiores.business.models.FurnitureModel;
import interiores.business.models.FurnitureType;
import interiores.business.models.catalogs.NamedCatalog;
import interiores.core.business.BusinessController;
import interiores.core.business.BusinessException;
import interiores.core.data.JAXBDataController;
import interiores.utils.Dimension;
import java.awt.Color;

/**
 *
 * @author hector
 */
public class FurnitureModelController
    extends BusinessController
{
    public FurnitureModelController(JAXBDataController data) {
        super(data);
    }
    
    public void add(String furnitureTypeName, String name, int width, int depth, float price,
            String color, String material)
            throws ElementNotFoundBusinessException, DefaultCatalogOverwriteException, BusinessException
    {
        FurnitureType furnitureType = getFurnitureType(furnitureTypeName);
        
        Dimension size = new Dimension(width, depth);
        Color modelColor = Color.decode(color);
        
        FurnitureModel furnitureModel = new FurnitureModel(name, size, price, modelColor,
                material);
        
        furnitureType.addFurnitureModel(furnitureModel);
    }
    
    public void rm(String furnitureTypeName, String name)
            throws ElementNotFoundBusinessException, DefaultCatalogOverwriteException, BusinessException
    {
        FurnitureType furnitureType = getFurnitureType(furnitureTypeName);
        
        furnitureType.removeFurnitureModel(name);
    }
    
    private FurnitureType getFurnitureType(String furnitureTypeName)
            throws ElementNotFoundBusinessException, DefaultCatalogOverwriteException
    {
        String ftCatalogTypeName = FurnitureTypesCatalogController.getCatalogTypeName();
        NamedCatalog<FurnitureType> ftCatalog = (NamedCatalog) data.get(ftCatalogTypeName);
        
        return ftCatalog.getForWrite(furnitureTypeName);
    }
}
