package interiores.business.models.catalogs.factories;

import interiores.business.models.FurnitureModel;
import interiores.business.models.FurnitureType;
import interiores.business.models.SpaceAround;
import interiores.business.models.catalogs.NamedCatalog;
import interiores.core.business.BusinessException;
import interiores.utils.Dimension;
import interiores.utils.Range;
import java.awt.Color;

/**
 *
 * @author hector
 */
public class DefaultFurnitureTypesCatalogFactory
{
    public static NamedCatalog<FurnitureType> getCatalog() throws BusinessException {
        NamedCatalog<FurnitureType> catalog = new NamedCatalog();
        
        catalog.add(bedDouble());
        catalog.add(bedSingle());
        catalog.add(chair());
        catalog.add(armchair());
        catalog.add(worktop());
        catalog.add(sofa());
        catalog.add(table());
        
        return catalog;
    }
    
    private static FurnitureType bedDouble() throws BusinessException {
        FurnitureType ft = new FurnitureType("bedDouble", new Range(120, 240), new Range(180, 210));
        
        ft.addFurnitureModel(new FurnitureModel("SFORANVIK", new Dimension(140, 205), 269, Color.GRAY,
                "pine"));
        ft.addFurnitureModel(new FurnitureModel("SLL", new Dimension(140, 205), 359, Color.GRAY, "birch"));
        
        return ft;
    }
    
    private static FurnitureType bedSingle() throws BusinessException {
        FurnitureType ft = new FurnitureType("bedSingle", new Range(70, 150), new Range(180, 210));
        
        ft.addFurnitureModel(new FurnitureModel("TERESJI", new Dimension(90, 190), 129.99f, Color.GRAY,
                "beech"));
        ft.addFurnitureModel(new FurnitureModel("EGBJERT", new Dimension(90, 200), 189.99f, Color.BLACK,
                "agglomerate"));
        
        return ft;
    }
    
    private static FurnitureType chair() throws BusinessException {
        FurnitureType ft = new FurnitureType("chair", new Range(40, 60), new Range(40, 60),
                new SpaceAround(30, 20, 0, 20));
        
        ft.addFurnitureModel(new FurnitureModel("ILLSA", new Dimension(45, 45), 16.99f, Color.WHITE,
                "plastic"));
        ft.addFurnitureModel(new FurnitureModel("NADDA", new Dimension(50, 50), 22.99f, Color.GRAY,
                "pine"));
        
        return ft;
    }
    
    private static FurnitureType armchair() throws BusinessException {
        FurnitureType ft = new FurnitureType("armchair", new Range(40, 80), new Range(40, 80),
                new SpaceAround(40, 0, 0, 0));
        
        ft.addFurnitureModel(new FurnitureModel("JOSSUN", new Dimension(60, 60), 38.99f, Color.BLUE,
                "agglomerate"));
        ft.addFurnitureModel(new FurnitureModel("SKATJA", new Dimension(70, 70), 59.99f, Color.GRAY,
                "agglomerate"));
        
        return ft;
    }
    
    private static FurnitureType worktop() throws BusinessException {
        FurnitureType ft = new FurnitureType("worktop", new Range(5, 300), new Range(60, 80));
        
        ft.addFurnitureModel(new FurnitureModel("WIBJO", new Dimension(60, 60), 30.99f, Color.WHITE,
                "marble"));
        ft.addFurnitureModel(new FurnitureModel("HELMMA", new Dimension(90, 60), 49.99f, Color.WHITE,
                "marble"));
        
        return ft;
    }
    
    private static FurnitureType sofa() throws BusinessException {
        FurnitureType ft = new FurnitureType("sofa", new Range(60, 300), new Range(40, 80));
        
        ft.addFurnitureModel(new FurnitureModel("SJEF", new Dimension(180, 60), 599.60f, Color.WHITE,
                "beech"));
        ft.addFurnitureModel(new FurnitureModel("RAZA", new Dimension(180, 60), 239.30f, Color.GRAY,
                "steel"));
        
        return ft;
    }
    
    private static FurnitureType table() throws BusinessException {
        FurnitureType ft = new FurnitureType("table", new Range(40, 300), new Range(40, 500));
        
        ft.addFurnitureModel(new FurnitureModel("JENGO", new Dimension(90, 140), 59.99f, Color.GRAY,
                "pine"));
        ft.addFurnitureModel(new FurnitureModel("PAFLLA", new Dimension(120, 135), 199.99f, Color.GRAY,
                "pine"));
        
        return ft;
    }
}
