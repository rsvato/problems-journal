package net.paguo.controller.auxilliary;

import net.paguo.dao.StreetsDao;
import net.paguo.domain.auxilliary.StreetDictionary;

import java.util.List;

/**
 * @author Reyentenko
 */
public class StreetsController {
    private StreetsDao streetsDao;

    public StreetsDao getStreetsDao() {
        return streetsDao;
    }

    public void setStreetsDao(StreetsDao dao) {
        this.streetsDao = dao;
    }

    public List<StreetDictionary> findOrdered() {
        return getStreetsDao().findOrdered();
    }

    public List<StreetDictionary> findByName(String name) {
        name = name.trim() + "%";
        return getStreetsDao().findByName(name);
    }

    public List<StreetDictionary> findByNameStrict(String name){
        return getStreetsDao().findByName(name);
    }
}
