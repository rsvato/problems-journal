package net.paguo.controller;

import net.paguo.domain.requests.ChangeStatusRequest;
import net.paguo.dao.ChangeStatusRequestDao;

import java.util.Collection;

/**
 * User: slava
 * Date: 17.12.2006
 * Time: 2:10:51
 * Version: $Id$
 */
public class ChangeStatusRequestController implements Controller<ChangeStatusRequest>{
    private ChangeStatusRequestDao requestDao;


    public ChangeStatusRequestDao getRequestDao() {
        return requestDao;
    }

    public void setRequestDao(ChangeStatusRequestDao requestDao) {
        this.requestDao = requestDao;
    }

    public Collection<ChangeStatusRequest> getAll() {
        return getRequestDao().readAll();
    }

    public Collection<ChangeStatusRequest> getPart(Integer count, Integer from){
        return getRequestDao().readPart(count, from);
    }

    public Integer getTotalCount(){
        return getRequestDao().count();
    }
}
