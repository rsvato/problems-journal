package net.paguo.dao;

import net.paguo.domain.requests.ChangeRequestType;
import net.paguo.domain.requests.ChangeStatusRequest;
import net.paguo.domain.requests.RequestInformationType;
import net.paguo.generic.dao.GenericDao;
import org.apache.commons.lang.math.IntRange;

import java.util.Date;
import java.util.List;

/**
 * User: slava
 * Date: 17.12.2006
 * Time: 2:08:39
 * Version: $Id$
 */
public interface ChangeStatusRequestDao extends GenericDao<ChangeStatusRequest, Integer> {
    List<ChangeStatusRequest> findOrderedByDateDesc(IntRange range, RequestInformationType infoType,
                                                    ChangeRequestType requestType);

    List<ChangeStatusRequest> findOrderedByDate(IntRange range, RequestInformationType infoType,
                                                ChangeRequestType requestType);

    List<ChangeStatusRequest> findOrderedByClient(IntRange range);

    List<ChangeStatusRequest> findOrderedByClientDesc(IntRange range);

    List<ChangeStatusRequest> findByDates(Date start, Date end);
}
