package interiores.presentation.terminal;

import interiores.business.controllers.CatalogController;
import interiores.core.business.BusinessException;
import interiores.core.presentation.terminal.CommandGroup;
import java.util.Collection;
import javax.xml.bind.JAXBException;

/**
 *
 * @author alvaro
 */
public class CatalogCommands 
    extends CommandGroup 
{
    private static final String LIST_MSG = "Listing names of available %s catalogs:";
    private static final String NEW_MSG = "Enter the name of the %s catalog you want to create:";
    private static final String CHECKOUT_MSG = "Enter the name of the %s catalog you want to set as active:";
    private static final String LOAD_MSG = "Enter the path where to load a %s catalog:";
    private static final String SAVE_MSG = "Enter the path where to save the %s %s catalog:";
    private static final String MERGE_MSG = "Enter the name of the %s catalog you want to merge with the"
            + "current catalog:";
    
    
    private CatalogController catalogController;
    private String catalogTypeName;
    
    public CatalogCommands(CatalogController catalogController, String catalogTypeName) {
        this.catalogController = catalogController;
        this.catalogTypeName = catalogTypeName;
    }
    
    public void list() {
        Collection<String> catalogNames = catalogController.getNamesLoadedCatalogs();
        String activeCatalog = catalogController.getNameActiveCatalog();
        
        println(String.format(LIST_MSG, catalogTypeName));
        
        for(String name : catalogNames) {
            if(name.equals(activeCatalog)) name = "*" + name;
            
            println(name);
        }
    }
    
    public void _new() throws BusinessException {
        String question = String.format(NEW_MSG, catalogTypeName);
        String catalogName = readString(question);
        
        catalogController.create(catalogName);
    }
    
    public void checkout() throws BusinessException {
        String question = String.format(CHECKOUT_MSG, catalogTypeName);
        String catalogName = readString(question);
        
        catalogController.checkout(catalogName);
    }
    
    public void load() throws JAXBException, BusinessException {
        String question = String.format(LOAD_MSG, catalogTypeName);
        String path = readString(question);
        
        catalogController.load(path);
    }
    
    public void save() throws JAXBException {
        String activeCatalog = catalogController.getNameActiveCatalog();
        String question = String.format(SAVE_MSG, activeCatalog, catalogTypeName);
        String path = readString(question);
        
        catalogController.save(path);
    }
    
    public void merge() throws BusinessException {
        String question = String.format(MERGE_MSG, catalogTypeName);
        String catalogName = readString(question);
        
        catalogController.merge(catalogName);
    }
}
