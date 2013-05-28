package interiores.presentation.terminal.commands;

import interiores.business.controllers.FurnitureTypeController;
import interiores.core.Options;
import interiores.core.presentation.terminal.annotation.Command;
import interiores.core.presentation.terminal.annotation.CommandOptions;
import interiores.core.presentation.terminal.annotation.CommandSubject;
import interiores.presentation.terminal.commands.abstracted.CatalogElementCommands;
import java.util.Collection;

/**
 *
 * @author hector
 */
@CommandSubject(name = "ft", description = "Furniture type related commands")
public class FurnitureTypeCommands
    extends CatalogElementCommands
{
    private static final String PATTERN_PLACE_RESTRICTIONS = "^(next-to|away-from|in-front-of)$";
    
    private FurnitureTypeController fTypeController;
    
    public FurnitureTypeCommands(FurnitureTypeController fTypeController) {
        super(fTypeController, "furniture types");
        
        this.fTypeController = fTypeController;
    }
    
    @Command("Add a furniture type to the type catalog")
    @CommandOptions({"walls"})
    public void add(Options options) {
        String name = readString("Enter the name of the furniture type you want to add");
        
        int minWidth = readInt("Enter the minimum width of the type");
        int maxWidth = readInt("Enter the maximum width of the type");
        
        int minDepth = readInt("Enter the minimum depth of the type");
        int maxDepth = readInt("Enter the maximum depth of the type");
        
        String clingToWalls = readString("Should this type be clung to a wall? (yes/no)");
        
        fTypeController.add(name, minWidth, maxWidth, minDepth, maxDepth, options.isEnabled("walls") );
    }
    
    @Command("Select a furniture type you want for your room")
    public void select() {
        Collection<String> names = readStrings("Enter the name of the furniture types you want to select");
        
        for(String name : names) {
            if(! fTypeController.exists(name))
                println("Warning: The furniture type " + name + " does not exist in the current catalog.");
            
            fTypeController.select(name);
        }
    }
    
    @Command("Remove a furniture type from the list of wanted furniture")
    public void unselect() {        
        String name = readString("Please, enter the name of the furniture type you want to unselect");
        fTypeController.unselect(name);
    }
    
    @Command("Obtain a list containing all the selected furniture")
    public void selected() {
        Collection types = fTypeController.getRoomFurniture();
        
        if(types.isEmpty())
            println("You have not selected any furniture yet");
        else {
            println("These are the furniture types you want in your room: ");
            print(types);
        }
    }
    
    @Command("List all the types of furniture you can place in the current room")
    public void selectable() {
        Collection types = fTypeController.getSelectableFurniture();
        
        if(types.isEmpty())
            println("You have not selected any furniture yet");
        else {
            println("These are the furniture types you can select for your room: ");
            print(types);
        }
    }
    
    @Command("Position a furniture with respect to the types around it")
    public void place() throws NoSuchMethodException {
        String type1 = readString("Over which furniture do you want to define this restriction?");
        
        String bctype = readString("Which kind of constraint do you want to add? Possibilities are: \n" +
                "next-to , away-from , in-front-of");
        String type2 = readString("With respect to which furniture type?");
        
        if (bctype.matches(PATTERN_PLACE_RESTRICTIONS)) fTypeController.addBinaryConstraint(bctype,type1,type2);
        else throw new NoSuchMethodException(bctype + " is not a valid place restriction");
    }
}
