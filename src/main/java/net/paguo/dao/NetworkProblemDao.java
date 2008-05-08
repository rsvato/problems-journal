package net.paguo.dao;

import net.paguo.domain.problems.NetworkProblem;
import net.paguo.generic.dao.GenericDao;
import org.apache.commons.lang.math.IntRange;

import java.util.List;
import java.util.Date;
import java.util.Collection;

/**
 * @version $Id $
 */
public interface NetworkProblemDao extends GenericDao<NetworkProblem, Integer> {
    List<NetworkProblem> findAll();

    List<NetworkProblem> findOpen();

    List<NetworkProblem> findAll(IntRange p0);

    Collection<NetworkProblem> findByDateRange(Date start, Date end);
}
