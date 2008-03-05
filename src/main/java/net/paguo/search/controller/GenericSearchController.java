package net.paguo.search.controller;

import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import java.util.List;

/**
 * User: sreentenko
 * Date: 28.11.2007
 * Time: 0:39:15
 */
public class GenericSearchController<T> implements SearchController<T> {
    private SessionFactory sessionFactory;
    private Class<T> actualClass;

    public GenericSearchController(Class<T> actualClass){
        this.actualClass = actualClass;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }



    public List<T> search(String criteria, int from, int count) throws ParseException {
        final Session hibSession = SessionFactoryUtils.getSession(sessionFactory, false);

        final FullTextSession session = Search.createFullTextSession(hibSession);
        final Transaction transaction = session.beginTransaction();

        FullTextQuery ftqQuery = prepareQuery(criteria, session);


        final Sort sort = new Sort("day", true);
        ftqQuery.setSort(sort);
        ftqQuery.setFirstResult(from);
        ftqQuery.setMaxResults(from + count);
        List<T> result = ftqQuery.list();
        transaction.commit();
        return result;
    }

    public int getResultSize(String criteria) throws ParseException {
        final Session hibSession = SessionFactoryUtils.getSession(sessionFactory, false);

        final FullTextSession session = Search.createFullTextSession(hibSession);
        final Transaction transaction = session.beginTransaction();

        FullTextQuery ftqQuery = prepareQuery(criteria, session);
        int result = ftqQuery.getResultSize();
        transaction.commit();
        return result;
    }

    private FullTextQuery prepareQuery(String criteria, FullTextSession session) throws ParseException {
        MultiFieldQueryParser parser = new MultiFieldQueryParser(
                new String[]{"desc", "day", "client.client",
            "restoreAction.action", "restoreAction:cause"}, new RussianAnalyzer());
        final Query query = parser.parse(criteria);
        FullTextQuery ftqQuery = session.createFullTextQuery(query, actualClass);
        return ftqQuery;
    }

    public void reindex() {
        final Session hibSession = SessionFactoryUtils.getSession(sessionFactory, false);

        final FullTextSession session = Search.createFullTextSession(hibSession);
        final Transaction transaction = session.beginTransaction();
        List<T> items = hibSession.createCriteria(actualClass).list();
        for (T item : items) {
            session.index(item);
        }
        transaction.commit();
    }
}