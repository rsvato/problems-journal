package net.paguo.generic.dao.finder;

import org.apache.commons.lang.math.IntRange;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;


public interface FinderExecutor<T>
{
    /**
     * Execute a finder method with the appropriate arguments
     */
    List<T> executeFinder(Method method, Object[] queryArgs);

    Iterator<T> iterateFinder(Method method, Object[] queryArgs);

    List<T> executeFinder(Method method, IntRange range, Object[] queryArgs);
}
