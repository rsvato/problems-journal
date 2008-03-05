package net.paguo.dao;

import net.paguo.generic.dao.GenericDao;
import net.paguo.domain.requests.ChangeStatusRequest;
import net.paguo.domain.requests.RequestInformationType;
import net.paguo.domain.requests.ChangeRequestType;

import java.util.List;

import org.apache.commons.lang.math.IntRange;

/**
 * User: slava
 * Date: 17.12.2006
 * Time: 2:08:39
 * Version: $Id$
 */
public interface ChangeStatusRequestDao extends GenericDao<ChangeStatusRequest, Integer> {
    public List<ChangeStatusRequest> findOrderedByDateDesc(IntRange range, RequestInformationType infoType,
                                                           ChangeRequestType requestType);

    public List<ChangeStatusRequest> findOrderedByDate(IntRange range, RequestInformationType infoType,
                                                           ChangeRequestType requestType);

    public List<ChangeStatusRequest> findOrderedByClient(IntRange range);
    public List<ChangeStatusRequest> findOrderedByClientDesc(IntRange range);
}
