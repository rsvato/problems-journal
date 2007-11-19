package net.paguo.dao;

import net.paguo.domain.application.ApplicationSettings;
import net.paguo.generic.dao.GenericDao;

import java.util.List;

/**
 * User: sreentenko
 * Date: 20.11.2007
 * Time: 0:21:37
 */
public interface ApplicationSettingsDao extends GenericDao<ApplicationSettings, Integer> {
    public List<ApplicationSettings> findByKey(String key);
}
