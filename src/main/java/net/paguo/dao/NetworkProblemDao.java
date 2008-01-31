package net.paguo.dao;

import net.paguo.domain.problems.NetworkProblem;
import net.paguo.generic.dao.GenericDao;
import org.apache.commons.lang.math.IntRange;

import java.util.List;

/**
 * @version $Id $
 */
public interface NetworkProblemDao extends GenericDao<NetworkProblem, Integer> {
    List<NetworkProblem> findAll();

    List<NetworkProblem> findOpen();

    List<NetworkProblem> findAll(IntRange p0);
}
