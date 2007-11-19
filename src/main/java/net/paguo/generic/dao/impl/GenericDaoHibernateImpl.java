package net.paguo.generic.dao.impl;

import net.paguo.generic.dao.GenericDao;
import net.paguo.generic.dao.finder.FinderArgumentTypeFactory;
import net.paguo.generic.dao.finder.FinderExecutor;
import net.paguo.generic.dao.finder.FinderNamingStrategy;
import net.paguo.generic.dao.finder.impl.SimpleFinderArgumentTypeFactory;
import net.paguo.generic.dao.finder.impl.SimpleFinderNamingStrategy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Hibernate implementation of GenericDao
 * A typesafe implementation of CRUD and finder methods based on Hibernate and Spring AOP
 * The finders are implemented through the executeFinder method. Normally called by the FinderIntroductionInterceptor
 */
public class GenericDaoHibernateImpl<T, PK extends Serializable> implements GenericDao<T, PK>, FinderExecutor {
    private SessionFactory sessionFactory;
    private FinderNamingStrategy namingStrategy = new SimpleFinderNamingStrategy(); // Default. Can override in config
    private FinderArgumentTypeFactory argumentTypeFactory = new SimpleFinderArgumentTypeFactory(); // Default. Can override in config
    private static final Log log = LogFactory.getLog(GenericDaoHibernateImpl.class);

    private Class<T> type;

    public GenericDaoHibernateImpl(Class<T> type) {
        this.type = type;
    }

    @SuppressWarnings("unchecked")
    public PK create(T o) {
        Session session = getSession();
        try{
            return (PK) session.save(o);
        }finally{
            releaseSession(session);
        }
    }
    
    @SuppressWarnings("unchecked")
    public T read(PK id) {
        return (T) getSession().get(type, id);
    }

    @SuppressWarnings("unchecked")
    public List<T> readAll(){
       Criteria c = getSession().createCriteria(type);
       return c.list();
    }
    @SuppressWarnings("unchecked")
    public List<T> readPart(Integer count, Integer from) {
       Criteria c = getSession().createCriteria(type);
        c.setFetchSize(count);
        c.setFirstResult(from);
        return c.list();
    }

    @SuppressWarnings("unchecked")
    public List<T> readPart(Integer count, Integer from, String sortBy, boolean ascending){
        log.debug(count + " " + from);
        Criteria c = getSession().createCriteria(type);
        c.setMaxResults(count);
        c.setFirstResult(from);
        c.addOrder(ascending ? Order.asc(sortBy) : Order.desc(sortBy));
        return c.list();
    }

    public Integer count(){
        String name = type.getName();
        StringTokenizer st = new StringTokenizer(name, ".");
        String n = null;
        while (st.hasMoreElements()) {
            n = (String) st.nextElement();
        }
        if (n != null) {
            String s = "select count(*) from " + n;
            return (Integer) getSession().createQuery(s).uniqueResult();
        } else {
            return 0;
        }

    }

    public Integer maxCount(){
        return (Integer) getSession().createCriteria(type).
                setProjection(Projections.rowCount()).uniqueResult();
    }

    public void update(T o) {
        Session session = getSession();
        try{
            session.update(o);
        }finally{
            releaseSession(session);
        }
    }

    public void delete(T o) {
        Session session = getSession();
        try{
            session.delete(o);
        }finally{
            releaseSession(session);
        }

    }

    @SuppressWarnings("unchecked")
    public List<T> executeFinder(Method method, final Object[] queryArgs) {
        final Query namedQuery = prepareQuery(method, queryArgs);
        return (List<T>) namedQuery.list();
    }

    @SuppressWarnings("unchecked")
    public Iterator<T> iterateFinder(Method method, final Object[] queryArgs) {
        final Query namedQuery = prepareQuery(method, queryArgs);
        return (Iterator<T>) namedQuery.iterate();
    }

//    public ScrollableResults scrollFinder(Method method, final Object[] queryArgs)
//    {
//        final Query namedQuery = prepareQuery(method, queryArgs);
//        return (ScrollableResults) namedQuery.scroll();
//    }

    private Query prepareQuery(Method method, Object[] queryArgs) {
        final String queryName = getNamingStrategy().queryNameFromMethod(type, method);
        final Query namedQuery = getSession().getNamedQuery(queryName);
        String[] namedParameters = namedQuery.getNamedParameters();
        if (namedParameters.length == 0) {
            setPositionalParams(queryArgs, namedQuery);
        } else {
            setNamedParams(namedParameters, queryArgs, namedQuery);
        }
        return namedQuery;
    }

    private void setPositionalParams(Object[] queryArgs, Query namedQuery) {
        // Set parameter. Use custom Hibernate Type if necessary
        if (queryArgs != null) {
            for (int i = 0; i < queryArgs.length; i++) {
                Object arg = queryArgs[i];
                Type argType = getArgumentTypeFactory().getArgumentType(arg);
                if (argType != null) {
                    namedQuery.setParameter(i, arg, argType);
                } else {
                    namedQuery.setParameter(i, arg);
                }
            }
        }
    }

    private void setNamedParams(String[] namedParameters, Object[] queryArgs, Query namedQuery) {
        // Set parameter. Use custom Hibernate Type if necessary
        if (queryArgs != null) {
            for (int i = 0; i < queryArgs.length; i++) {
                Object arg = queryArgs[i];
                Type argType = getArgumentTypeFactory().getArgumentType(arg);
                if (argType != null) {
                    namedQuery.setParameter(namedParameters[i], arg, argType);
                } else {
                    if (arg instanceof Collection) {
                        namedQuery.setParameterList(namedParameters[i], (Collection) arg);
                    } else {
                        namedQuery.setParameter(namedParameters[i], arg);
                    }
                }
            }
        }
    }

    public Session getSession() {
        boolean allowCreate = false;
        return SessionFactoryUtils.getSession(sessionFactory, allowCreate);
    }

    public void releaseSession(Session session) {
        try {
            if (session.isDirty()) {
                session.flush();
            }
        } finally {
            //SessionFactoryUtils.releaseSession(session, sessionFactory);
        }
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public FinderNamingStrategy getNamingStrategy() {
        return namingStrategy;
    }

    public void setNamingStrategy(FinderNamingStrategy namingStrategy) {
        this.namingStrategy = namingStrategy;
    }

    public FinderArgumentTypeFactory getArgumentTypeFactory() {
        return argumentTypeFactory;
    }

    public void setArgumentTypeFactory(FinderArgumentTypeFactory argumentTypeFactory) {
        this.argumentTypeFactory = argumentTypeFactory;
    }
}
