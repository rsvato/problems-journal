package net.paguo.generic.dao.finder.impl;

import java.lang.reflect.Method;

import net.paguo.generic.dao.finder.FinderNamingStrategy;

/**
 * Looks up Hibernate named queries based on the simple name of the invoced class and the method name of the invocation
 */
public class SimpleFinderNamingStrategy implements FinderNamingStrategy
{
    public String queryNameFromMethod(Class findTargetType, Method finderMethod)
    {
        return findTargetType.getSimpleName() + "." + finderMethod.getName();
    }
}
