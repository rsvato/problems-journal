package net.paguo.search.controller;

import org.hibernate.SessionFactory;
import org.apache.lucene.queryParser.ParseException;
import net.paguo.domain.problems.ClientComplaint;

import java.util.List;

/**
 * User: sreentenko
 * Date: 28.11.2007
 * Time: 0:39:35
 */
public interface SearchController<T> {
    SessionFactory getSessionFactory();

    List<T> search(String criteria) throws ParseException;

    void reindex();
}
