/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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

    // These should be overriden if you want a diferent message to be printed 
    protected String LIST_MSG = "Listing names of available furniture types catalogs: ";
    protected String CREATE_MSG = "Enter the name of the catalog you want to create: ";
    protected String CHECKOUT_MSG = "Enter the name of the catalog you want to set as active: ";
    protected String LOAD_MSG = "Enter the path where to load a catalog: ";
    protected String SAVE_MSG;
    protected String MERGE_MSG = "Enter the name of the loaded catalog you want to merge with the current catalog";
    
    
    private CatalogController catalogController;
    
    public CatalogCommands(CatalogController catalogController) {
        this.catalogController = catalogController;
    }
    
    public void list() {
        Collection<String> catalogNames = catalogController.getNamesLoadedCatalogs();
        String activeCatalog = catalogController.getNameActiveCatalog();
        
        println(LIST_MSG);
        
        for(String name : catalogNames) {
            if(name.equals(activeCatalog)) name = "*" + name;
            
            println(name);
        }
    }
    
    public void _new() throws BusinessException {
        String catalogName = readString(CREATE_MSG);
        
        catalogController.create(catalogName);
    }
    
    public void checkout() throws BusinessException {
        String catalogName = readString(CHECKOUT_MSG);
        
        catalogController.checkout(catalogName);
    }
    
    public void load() throws JAXBException, BusinessException {
        String path = readString(LOAD_MSG);
        
        catalogController.load(path);
    }
    
    public void save() throws JAXBException {
        
        String path = "";
        
        // TODO: Avoid this if
        if (SAVE_MSG == null) {
            String activeCatalog = catalogController.getNameActiveCatalog();
            path = readString("Enter the path where to save the " + activeCatalog + " catalog");
        }
        else {
            path = readString(SAVE_MSG);
        }
        
        catalogController.save(path);
    }
    
    public void merge() throws BusinessException {
        String catalogName = readString(MERGE_MSG);
        
        catalogController.merge(catalogName);
    }
}
