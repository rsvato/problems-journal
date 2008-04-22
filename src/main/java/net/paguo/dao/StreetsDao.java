package net.paguo.dao;

import net.paguo.domain.auxilliary.StreetDictionary;
import net.paguo.generic.dao.GenericDao;

import java.util.List;

/**
 * @author Reyentenko
 */
public interface StreetsDao extends GenericDao<StreetDictionary, Integer> {
    List<StreetDictionary> findOrdered();
    List<StreetDictionary> findByName(String name);
}
