package interiores.presentation.terminal.commands.abstracted;

import interiores.business.controllers.abstracted.CatalogController;
import interiores.core.Options;
import interiores.core.business.BusinessException;
import interiores.core.presentation.terminal.AdvancedCommandGroup;
import interiores.core.presentation.terminal.annotation.Command;
import interiores.core.presentation.terminal.annotation.CommandOptions;
import java.util.Collection;
import javax.xml.bind.JAXBException;

/**
 *
 * @author alvaro
 */
abstract public class CatalogCommands 
    extends AdvancedCommandGroup 
{
    private static final String LIST_MSG = "Listing names of available %s catalogs:";
    private static final String NEW_MSG = "Enter the name of the %s catalog you want to create:";
    private static final String CHECKOUT_MSG = "Enter the name of the %s catalog you want to set as active:";
    private static final String LOAD_MSG = "Enter the path where to load a %s catalog:";
    private static final String SAVE_MSG = "Enter the path where to save the %s %s catalog:";
    private static final String MERGE_MSG = "Enter the name of the %s catalog you want to merge with the"
            + "current catalog:";
    private static final String RMV_MSG = "Enter the name of the %s catalog you want to remove";
    
    
    protected CatalogController catalogController;
    private String catalogTypeName;

    public CatalogCommands(CatalogController catalogController, String catalogTypeName) {
        this.catalogController = catalogController;
        this.catalogTypeName = catalogTypeName;
    }

    @Command("List the elements of the catalog")
    public void list() {
        Collection<String> catalogNames = catalogController.getNamesLoadedCatalogs();
        String activeCatalog = catalogController.getNameActiveCatalog();
        
        println(String.format(LIST_MSG, catalogTypeName));
        
        for(String name : catalogNames) {
            if(name.equals(activeCatalog)) println("* " + name);
            else println("  " + name);
        }
    }
    
    @Command("Creates a new catalog given its name")
    public void _new() throws BusinessException {
        String question = String.format(NEW_MSG, catalogTypeName);
        Collection<String> catalogNames = readStrings(question);
        
        for(String catalogName : catalogNames)
            catalogController.create(catalogName);
    }
    
    @Command("Selects the catalog to be used")
    @CommandOptions("new")
    public void checkout(Options options)
            throws BusinessException
    {
        String question = String.format(CHECKOUT_MSG, catalogTypeName);
        String catalogName = readString(question);
        
        if(options.isEnabled("new"))
            catalogController.create(catalogName);
        
        catalogController.checkout(catalogName);
    }
    
    @Command("Loads a catalog given a path to a filename")
    public void load() throws JAXBException, BusinessException {
        String question = String.format(LOAD_MSG, catalogTypeName);
        String path = readString(question);
        
        catalogController.load(path);
    }
    
    @Command("Saves the catalog to the given path")
    public void save() throws JAXBException {
        String activeCatalog = catalogController.getNameActiveCatalog();
        String question = String.format(SAVE_MSG, activeCatalog, catalogTypeName);
        String path = readString(question);
        
        catalogController.save(path);
    }
    
    @Command("Merges two or more catalogs with the current being used")
    @CommandOptions({"replace"})
    public void merge(Options options) throws BusinessException {
        String question = String.format(MERGE_MSG, catalogTypeName);
        Collection<String> catalogNames = readStrings(question);
        
        for(String catalogName : catalogNames)
            catalogController.merge(catalogName,options.isEnabled("replace"));
    }
    
    @Command("Removes the specified catalog")
    public void remove() {
        String question = String.format(RMV_MSG, catalogTypeName);
        String cat_to_remove = readString(question);
        catalogController.remove(cat_to_remove);
    }
    
}
