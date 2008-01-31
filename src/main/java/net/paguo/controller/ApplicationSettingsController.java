package net.paguo.controller;

import net.paguo.dao.ApplicationSettingsDao;
import net.paguo.domain.application.ApplicationSettings;

import java.util.Collection;
import java.util.List;

/**
 * User: sreentenko
 * Date: 20.11.2007
 * Time: 0:24:17
 */
public class ApplicationSettingsController implements Controller<ApplicationSettings>{
    private ApplicationSettingsDao dao;

    public ApplicationSettingsDao getDao() {
        return dao;
    }

    public void setDao(ApplicationSettingsDao dao) {
        this.dao = dao;
    }

    public Collection<ApplicationSettings> getAll() {
        return getDao().readAll();
    }

    public ApplicationSettings findByKey(String key){
        ApplicationSettings result = null;
        final List<ApplicationSettings> results = getDao().findByKey(key);
        if (results != null && ! results.isEmpty()){
            result = results.iterator().next();
        }
        return result;
    }
}
