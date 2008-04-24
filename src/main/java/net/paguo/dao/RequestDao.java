package net.paguo.dao;

import net.paguo.domain.testing.Request;
import net.paguo.domain.testing.ProcessStage;
import net.paguo.generic.dao.GenericDao;

import java.util.List;

import org.apache.commons.lang.math.IntRange;

/**
 * @author Reyentenko
 */
public interface RequestDao  extends GenericDao<Request, Integer> {

    List<Request> findByStatus(ProcessStage status, String orderBy, boolean asc, IntRange range);

    List<Request> findAll(String orderBy, boolean asc, IntRange range);

    Integer countAll();

    Integer countWithStage(ProcessStage status);
}
