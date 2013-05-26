package interiores.business.controllers.abstracted;

import interiores.business.exceptions.NoRoomCreatedException;
import interiores.business.models.Room;
import interiores.business.models.WishList;
import interiores.business.models.catalogs.AvailableCatalog;
import interiores.business.models.catalogs.NamedCatalog;
import interiores.core.business.BusinessController;
import interiores.core.data.JAXBDataController;

/**
 *
 * @author hector
 */
public class InterioresController
    extends BusinessController
{
    protected static final String KEY_WISH_LIST = "wishList";
    
    public InterioresController(JAXBDataController data) {
        super(data);
    }
    
    protected Room getRoom() {
        return getWishList().getRoom();
    }
    
    protected WishList getWishList() {
        if(! data.has(KEY_WISH_LIST))
            throw new NoRoomCreatedException();
        
        return (WishList) data.get(KEY_WISH_LIST);
    }
    
    protected void setWishList(WishList wishList) {
        data.set(KEY_WISH_LIST, wishList);
    }
    
    protected NamedCatalog getCatalog(AvailableCatalog catalog) {
        return (NamedCatalog) data.get(getCatalogKeyData(catalog));
    }
    
    protected String getCatalogKeyData(AvailableCatalog catalog) {
        return catalog.toString() + "Catalog";
    }
}
