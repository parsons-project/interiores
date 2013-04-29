package interiores.business.models.catalogs;

import interiores.business.models.FurnitureType;
import interiores.utils.Range;

/**
 *
 * @author hector
 */
public class DefaultFurnitureTypesCatalog
    extends NamedCatalog<FurnitureType>
{
    public DefaultFurnitureTypesCatalog() {
        super();
        
        fillCatalog();
    }
    
    private void fillCatalog() {
        add(new FurnitureType("bedDouble", new Range(120, 240), new Range(180, 210)));
        add(new FurnitureType("bedSingle", new Range(70, 150), new Range(180, 210)));
        add(new FurnitureType("chair", new Range(40, 60), new Range(40, 60)));
        add(new FurnitureType("armchair", new Range(40, 80), new Range(40, 80)));
        add(new FurnitureType("worktop", new Range(5, 300), new Range(60, 80)));
        add(new FurnitureType("sofa", new Range(60, 300), new Range(40, 80)));
        add(new FurnitureType("table", new Range(40, 300), new Range(40, 500)));
    }
}
