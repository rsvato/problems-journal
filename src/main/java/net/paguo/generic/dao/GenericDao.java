package net.paguo.generic.dao;

import org.hibernate.Query;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, ID extends Serializable> {
    ID create(T newInstance);
    T read(ID pk);
    void update(T transientObject);
    void delete(T persistentObject);
    List<T> readAll();
    public List<T> readPart(Integer count, Integer from);
    public Integer count();

    Integer maxCount();

    @SuppressWarnings("uncheked")
    List<T> readPart(Integer count, Integer from, String sortBy, boolean ascending);
}
