package interiores.business.models.catalogs.factories;

import interiores.business.models.catalogs.NamedCatalog;
import interiores.business.models.room.RoomType;
import interiores.utils.Dimension;
import interiores.utils.Functionality;

/**
 *
 * @author hector
 */
public class DefaultRoomTypesCatalogFactory {
    public static NamedCatalog<RoomType> getCatalog() {
        NamedCatalog<RoomType> catalog = new NamedCatalog();
        
        catalog.add(new RoomType("bedroom",
                new Dimension(180, 240),
                new String[]{},
                new String[]{"sink", "oven", "microwave", "hob", "stove", "extractor", "fridge", "dishwasher", "washer",
                             "bidet", "washbasin", "toilet", "bathtub", "shower", "dryer", "worktop", "wringer"},
                new Functionality[]{ Functionality.SLEEP }
        ));
        
        catalog.add(new RoomType("kitchen",
                new Dimension(120, 240),
                new String[]{"sink", "fridge", "wringer"},
                new String[]{"bedSingle", "bedDouble", "tableBedside", "washer", "bidet", "washbasin", "toilet", 
                             "bathtub", "shower", "dryer"}
        ));
        
        catalog.add(new RoomType("bathroom",
                new Dimension(150, 210),
                new String[]{"mirror", "bidet", "washbasin", "toilet"},
                new String[]{"bedSingle", "bedDouble", "tableBedside", "sink", "oven", "microwave", "hob", "stove",
                             "extractor", "fridge", "dishwasher", "armchair", "worktop", "wringer","armchair", "sofa"},
                new Functionality[]{ Functionality.BATH }
        ));
        
        catalog.add(new RoomType("lounge",
                new Dimension(270, 270),
                new String[]{},
                new String[]{"bedSingle", "bedDouble", "sink", "oven", "microwave", "hob", "stove", "extractor",
                             "fridge", "dishwasher", "washer", "bidet", "washbasin", "toilet", "bathtub", "shower",
                             "dryer", "worktop", "wringer"}
        ));
        
        catalog.add(new RoomType("studio",
                new Dimension(180, 200),
                new String[]{},
                new String[]{"sink", "oven", "microwave", "hob", "stove", "extractor", "fridge", "dishwasher", "washer",
                             "bidet", "washbasin", "toilet", "bathtub", "shower", "dryer", "worktop", "wringer"}
        ));
        
        catalog.add(new RoomType("livingRoom",
                new Dimension(270, 270),
                new String[]{"table", "chair"},
                new String[]{"bedSingle", "bedDouble", "tableBedside", "cabinet", "sink", "oven", "hob", "stove",
                             "extractor", "fridge", "dishwasher", "washer", "bidet", "washbasin", "toilet", "bathtub",
                             "shower", "dryer", "wringer"  }
        ));
        
        return catalog;
    }
}
