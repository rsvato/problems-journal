package net.paguo.dao;

import net.paguo.domain.problems.NetworkFailure;
import net.paguo.domain.clients.ClientItem;
import net.paguo.generic.dao.GenericDao;

import java.util.List;

/**
 * @author Svyatoslav Reyentenko mailto:rsvato@gmail.com
 * @version $Id$
 *          created 25.08.2006 0:29:32
 */
public interface NetworkFailureDao extends GenericDao<NetworkFailure, Integer> {
     public List<NetworkFailure> findOpen();
}
