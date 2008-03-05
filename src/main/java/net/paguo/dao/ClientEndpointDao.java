package net.paguo.dao;

import net.paguo.domain.equipment.ClientEndpoint;
import net.paguo.generic.dao.GenericDao;

import java.util.List;

/**
 * User: sreentenko
 * Date: 02.03.2008
 * Time: 0:56:18
 */
public interface ClientEndpointDao extends GenericDao<ClientEndpoint, Integer> {
    List<ClientEndpoint> findByDesignationAndNumber(Integer vlanNumber, String designation);
}
