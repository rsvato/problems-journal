package net.paguo.generic.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, ID extends Serializable> {
    ID create(T newInstance);
    T read(ID pk);
    void update(T transientObject);
    void delete(T persistentObject);
    List<T> readAll();
}
