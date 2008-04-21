package net.paguo.controller.request;

import net.paguo.dao.RequestDao;
import net.paguo.domain.testing.ProcessStage;
import net.paguo.domain.testing.Request;

import java.io.Serializable;

/**
 * @author Reyentenko
 */
public class RequestController implements Serializable {
    private transient RequestDao requestDao;

    private static final long serialVersionUID = 7319683758495601451L;

    public RequestDao getRequestDao() {
        return requestDao;
    }

    public void setRequestDao(RequestDao dao) {
        this.requestDao = dao;
    }

    public final void saveRequest(Request req){
        if (req.getId() == null){
            req.setCurrentStage(ProcessStage.OPENED);
            getRequestDao().create(req);
        }else{
            RequestProcessor processor = new RequestProcessor();
            processor.processRequest(req);
            getRequestDao().update(req);
        }
    }

    public Request get(Integer requestId) {
        return getRequestDao().read(requestId);
    }

    private static class RequestProcessor{
        public void processRequest(Request req){
            switch (req.getCurrentStage()){
                case OPENED:
                    break;
                default:
                    ;
            }
        }
    }
}
