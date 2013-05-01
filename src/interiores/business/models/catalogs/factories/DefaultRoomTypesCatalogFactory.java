package interiores.business.models.catalogs.factories;

import interiores.business.models.RoomType;
import interiores.business.models.catalogs.NamedCatalog;
import interiores.utils.Dimension;

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
                new String[]{"worktop"}
        ));
        
        catalog.add(new RoomType("kitchen",
                new Dimension(120, 240),
                new String[]{},
                new String[]{"bedSingle", "bedDouble"}
        ));
        
        catalog.add(new RoomType("bathroom",
                new Dimension(100, 210),
                new String[]{},
                new String[]{"bedSingle", "bedDouble", "armchair", "sofa", "worktop"}
        ));
        
        catalog.add(new RoomType("lounge",
                new Dimension(270, 270),
                new String[]{},
                new String[]{"bedSingle", "bedDouble", "worktop"}
        ));
        
        catalog.add(new RoomType("study",
                new Dimension(180, 200),
                new String[]{},
                new String[]{}
        ));
        
        catalog.add(new RoomType("livingroom",
                new Dimension(270, 270),
                new String[]{"table"},
                new String[]{"bedSingle", "bedDouble"}
        ));
        
        return catalog;
    }
}
