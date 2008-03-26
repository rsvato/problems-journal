package net.paguo.controller;

import net.paguo.controller.exception.ControllerException;
import net.paguo.dao.ChangeStatusRequestDao;
import net.paguo.dao.ClientEndpointDao;
import net.paguo.domain.equipment.ClientEndpoint;
import net.paguo.domain.requests.*;
import static net.paguo.domain.requests.ChangeRequestType.CANCEL;
import static net.paguo.domain.requests.ChangeRequestType.RESTORE;
import static net.paguo.domain.requests.RequestInformationType.REQUEST;
import static net.paguo.domain.requests.RequestInformationType.RESPONSE;
import static net.paguo.domain.requests.RequestNextStage.*;
import org.apache.commons.lang.math.IntRange;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * User: slava
 * Date: 17.12.2006
 * Time: 2:10:51
 * Version: $Id$
 */
public class ChangeStatusRequestController implements Controller<ChangeStatusRequest> {
    private static final long serialVersionUID = -328200909928598119L;


    /**
     * Classess for unwind functionaly.
     */
    private interface IUnwindCallback {

        /**
         * Possible cleaning action for concrete stage
         *
         * @param request request to clean before unwind
         */
        void execute(ChangeStatusRequest request);

    }

    private static final class UnwindCancelExecutionCallback implements IUnwindCallback {

        public void execute(ChangeStatusRequest request) {
            request.setEndpoint(null);
        }

    }

    private static final class InformationChooser {


        private static final EnumMap<RequestNextStage, String> propertiesMap;

        private static final EnumMap<RequestNextStage, String> permPropertiesMap;

        private static final EnumMap<RequestNextStage, IUnwindCallback> unwindCallbacks;

        private static final EnumMap<RequestNextStage, IUnwindCallback> permUnwindCallbacks;

        static {
            propertiesMap = new EnumMap<RequestNextStage, String>(RequestNextStage.class);
            propertiesMap.put(RESTORE_REQUEST, "cancelExec");
            propertiesMap.put(RESTORE_CONFIRM, "restoreRequest");
            propertiesMap.put(END_OF_REQUEST, "restoreExec");

            permPropertiesMap = new EnumMap<RequestNextStage, String>(RequestNextStage.class);
            permPropertiesMap.put(END_OF_REQUEST, "cancelExec");

            unwindCallbacks = new EnumMap<RequestNextStage, IUnwindCallback>(RequestNextStage.class);
            unwindCallbacks.put(RESTORE_REQUEST, new UnwindCancelExecutionCallback());

            permUnwindCallbacks = new EnumMap<RequestNextStage, IUnwindCallback>(RequestNextStage.class);
            permUnwindCallbacks.put(END_OF_REQUEST, new UnwindCancelExecutionCallback());
        }

        interface Chooser {

            public String choose();

        }

        static Chooser getChooser(ChangeStatusRequest request) {
            if (request.isPermanent()) {
                return new AbstractChooser(request) {

                    public String choose() {
                        return permPropertiesMap.get(getRequest().getNextStage());
                    }
                };
            }
            return new AbstractChooser(request) {

                public String choose() {
                    return propertiesMap.get(getRequest().getNextStage());
                }
            };
        }

        static IUnwindCallback getCallback(ChangeStatusRequest request) {
            if (request.isPermanent()) {
                return permUnwindCallbacks.get(request.getNextStage());
            }
            return unwindCallbacks.get(request.getNextStage());
        }

        private static abstract class AbstractChooser implements Chooser {

            private ChangeStatusRequest request;

            public AbstractChooser(ChangeStatusRequest req) {
                this.request = req;
            }

            public ChangeStatusRequest getRequest() {
                return request;
            }

            public void setRequest(ChangeStatusRequest rq) {
                this.request = rq;
            }

        }

    }

    private static final Log log = LogFactory.getLog(ChangeStatusRequestController.class);

    private transient ChangeStatusRequestDao requestDao;

    private transient ClientEndpointDao endpointDao;

    public void setEndpointDao(ClientEndpointDao dao) {
        this.endpointDao = dao;
    }

    public ClientEndpointDao getEndpointDao() {
        return endpointDao;
    }

    public ChangeStatusRequestDao getRequestDao() {
        return requestDao;
    }

    public void setRequestDao(ChangeStatusRequestDao dao) {
        this.requestDao = dao;
    }


    public Collection<ChangeStatusRequest> getAll() {
        return getRequestDao().readAll();
    }

    public Collection<ChangeStatusRequest> getPart(Integer count, Integer from) {
        return getRequestDao().readPart(count, from);
    }

    public Integer getTotalCount() {
        return getRequestDao().maxCount();
    }

    public void save(ChangeStatusRequest request) throws ControllerException {
        try {
            if (request.getId() == null) {
                if (request.getCancelRequest() != null) {
                    request.setNextStage(RequestNextStage.CANCEL_CONFIRM);
                }
                getRequestDao().create(request);
            } else {
                ChangeStatusRequest original = getRequestDao().read(request.getId());

                // on switch to 'permanent' request clean unsupported stages 
                if (!original.isPermanent() && request.isPermanent()) {
                    request.setRestoreRequest(null);
                    request.setRestoreExec(null);
                    if (EnumSet.of(RESTORE_REQUEST, RESTORE_CONFIRM).contains(request.getNextStage())) {
                        request.setNextStage(END_OF_REQUEST);
                    }
                } else if (original.isPermanent() && !request.isPermanent()) {
                    if (EnumSet.of(END_OF_REQUEST).contains(request.getNextStage())) {
                        request.setNextStage(RESTORE_REQUEST);
                    }
                }
                getRequestDao().update(request);
            }
        } catch (Throwable e) {
            throw new ControllerException(e);
        }
    }

    public void saveAndProgress(ChangeStatusRequest request, RequestInformation information) throws ControllerException {
        advanceRequest(request, information);

        try {
            getRequestDao().update(request);
        } catch (Throwable e) {
            throw new ControllerException(e);
        }
    }

    public void saveAndRewind(ChangeStatusRequest request) throws ControllerException {
        rewindRequest(request);
        try {
            getRequestDao().update(request);
        } catch (Throwable e) {
            throw new ControllerException(e);
        }
    }

    void rewindRequest(ChangeStatusRequest request) throws ControllerException {
        final RequestNextStage nextStage = request.getNextStage();
        RequestNextStage prev = getPreviousStage(nextStage, request.isPermanent());
        if (prev == null) {
            throw new ControllerException("Illegal rewind attempt");
        }
        IUnwindCallback callback = InformationChooser.getCallback(request);
        if (callback != null) {
            callback.execute(request);
        }
        saveRequestInformation(request, null);
        request.setNextStage(prev);
    }

    void advanceRequest(ChangeStatusRequest request, RequestInformation information) throws ControllerException {
        RequestNextStage next = getNextStage(request.getNextStage(), request.isPermanent());
        if (next == null) {
            throw new ControllerException("Illegal advance attempt");
        }
        request.setNextStage(next);
        saveRequestInformation(request, information);
    }

    private void saveRequestInformation(ChangeStatusRequest request, RequestInformation information) throws ControllerException {
        String propertyName = InformationChooser.getChooser(request).choose();

        if (propertyName == null) {
            throw new IllegalArgumentException("No property for stage " + request.getNextStage());
        }

        String setter = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
        try {
            final Method setMethod = ChangeStatusRequest.class.getMethod(setter, RequestInformation.class);
            setMethod.invoke(request, information);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("No method with name " + setter);
        } catch (IllegalAccessException e) {
            throw new ControllerException(e);
        } catch (InvocationTargetException e) {
            throw new ControllerException(e);
        }
    }

    public ChangeStatusRequest get(int requestId) {
        return getRequestDao().read(requestId);
    }

    public ClientEndpoint getExisting(ClientEndpoint ce) {
        final List<ClientEndpoint> endpoints = getEndpointDao().
                findByDesignationAndNumber(ce.getVlanNumber(), ce.getDesignation());
        return endpoints.isEmpty() ? null : endpoints.iterator().next();
    }


    public void saveEndpointToRequest(ClientEndpoint ce, ChangeStatusRequest changeRequest,
                                      RequestInformation ri) throws ControllerException {
        ClientEndpoint fromDB = getExisting(ce);
        if (fromDB == null) { //no such endpoint
            getEndpointDao().create(ce);
        } else {
            ce = fromDB;
        }
        getRequestDao().refresh(changeRequest);
        changeRequest.setEndpoint(ce);
        saveAndProgress(changeRequest, ri);
    }

    public Iterator getSortableIterator(int first, int count, String property, boolean ascending) {
        log.debug("Get property: " + property);
        SortProperties props = SortProperties.value(property);
        if (props == null) {
            props = SortProperties.DISC_REQ_DATE;
        }

        IntRange range = new IntRange(first, first + count);
        //awful       
        switch (props) {
            case CLIENT:
                return getByClientIterator(range, ascending);
            case DISC_REQ_DATE:
                return getByStatesIterator(range, ascending, REQUEST, CANCEL);
            case DISC_EXEC_DATE:
                return getByStatesIterator(range, ascending, RESPONSE, CANCEL);
            case CON_REQ_DATE:
                return getByStatesIterator(range, ascending, REQUEST, RESTORE);
            case CON_EXEC_DATE:
                return getByStatesIterator(range, ascending, RESPONSE, RESTORE);
            default:
                return Collections.EMPTY_LIST.iterator();
        }
    }


    private Iterator getByStatesIterator(IntRange range, boolean ascending,
                                         RequestInformationType informationType,
                                         ChangeRequestType requestType) {
        List result = ascending ?
                getRequestDao().findOrderedByDate(range, informationType, requestType) :
                getRequestDao().findOrderedByDateDesc(range, informationType,
                        requestType);
        return result.iterator();
    }

    private Iterator getByClientIterator(IntRange range, boolean ascending) {
        log.debug("Ascending? " + ascending);
        List result = ascending ? getRequestDao().findOrderedByClient(range) :
                getRequestDao().findOrderedByClientDesc(range);
        return result.iterator();
    }

    public void deleteRequest(ChangeStatusRequest request) throws ControllerException {
        try {
            getRequestDao().delete(request);
        } catch (Throwable e) {
            throw new ControllerException(e);
        }
    }

    public Collection<ChangeStatusRequest> getByDates(Date start, Date end){
        return getRequestDao().findByDates(start, end);
    }

    public enum SortProperties {
        CLIENT("client"),
        DISC_REQ_DATE("disc_req_date"),
        DISC_EXEC_DATE("disc_exec_date"),
        CON_REQ_DATE("con_req_date"),
        CON_EXEC_DATE("con_exec_date");

        private String label;

        SortProperties(String s) {
            this.label = s;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String s) {
            this.label = s;
        }

        public static SortProperties value(String s) {
            for (SortProperties property : SortProperties.values()) {
                if (property.label.equals(s)) {
                    return property;
                }

            }
            return null;
        }
    }
}
