package interiores.presentation.terminal.commands;

import interiores.business.controllers.FurnitureModelController;
import interiores.business.exceptions.DefaultCatalogOverwriteException;
import interiores.business.exceptions.ElementNotFoundBusinessException;
import interiores.business.models.FurnitureModel;
import interiores.core.business.BusinessException;
import interiores.core.presentation.terminal.AdvancedCommandGroup;
import interiores.core.presentation.terminal.annotation.Command;
import interiores.core.presentation.terminal.annotation.CommandSubject;
import java.util.Collection;

/**
 *
 * @author hector
 */
@CommandSubject(name = "fm", description = "Furniture model related commands")
public class FurnitureModelCommands
    extends AdvancedCommandGroup
{
    private FurnitureModelController fModelController;
    
    public FurnitureModelCommands(FurnitureModelController fModelController) {
        this.fModelController = fModelController;
    }
    
    @Command("Add a furniture model to the current furniture type catalog")
    public void add()
            throws ElementNotFoundBusinessException, DefaultCatalogOverwriteException, BusinessException
    {
        String fTypeName = readString("Enter the name of the furniture type to which you want to add the "
                + "model:");
        
        String name = readString("Enter the name of the furniture model:");
        int width = readInt("Enter the width of the furniture model:");
        int depth = readInt("Enter the depth of the furniture model:");
        float price = readFloat("Enter the price of the furniture model:");
        String color = readString("Enter the color of the furniture model:");
        String material = readString("Enter the material of the furniture model:");
        
        fModelController.add(fTypeName, name, width, depth, price, color, material);
    }
    
    @Command("Remove a furniture model from the type catalog")
    public void rm()
            throws ElementNotFoundBusinessException, DefaultCatalogOverwriteException, BusinessException
    {
        String fTypeName = readString("Enter the name of the furniture type to which you want to remove the "
                + "model:");
        
        String name = readString("Enter the name of the furniture model you want to delete:");
        
        fModelController.rm(fTypeName, name);
    }
    
    @Command("Obtain a list of particular models belonging to a type")
    public void list()
            throws ElementNotFoundBusinessException
    {
        String fTypeName = readString("Enter the name of the furniture type you want to list the models:");
        
        Collection<FurnitureModel> models = fModelController.getFurnitureModels(fTypeName);
        
        println("Listing furniture models of the furniture type: " + fTypeName);
        print(models);
        
        if(models.isEmpty())
            println("There are no furniture models of this furniture type.");
    }
}
