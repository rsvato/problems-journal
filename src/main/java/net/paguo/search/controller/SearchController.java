package net.paguo.search.controller;

import org.apache.lucene.queryParser.ParseException;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * User: sreentenko
 * Date: 28.11.2007
 * Time: 0:39:35
 */
public interface SearchController<T> {
    SessionFactory getSessionFactory();

    void reindex();

    List<T> search(String criteria, int from, int count) throws ParseException;

    int getResultSize(String criteria) throws ParseException;
}
