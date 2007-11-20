package net.paguo.generic.dao.finder.impl;

import net.paguo.generic.dao.finder.FinderExecutor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.math.IntRange;
import org.springframework.aop.IntroductionInterceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * Connects the Spring AOP magic with the Hibernate DAO magic
 * For any method beginning with "find" this interceptor will use the FinderExecutor to call a Hibernate named query
 */
public class FinderIntroductionInterceptor implements IntroductionInterceptor
{

    public Object invoke(MethodInvocation methodInvocation) throws Throwable
    {

        FinderExecutor executor = (FinderExecutor) methodInvocation.getThis();

        String methodName = methodInvocation.getMethod().getName();
        if(methodName.startsWith("find") || methodName.startsWith("list"))
        {
            Object[] arguments = methodInvocation.getArguments();

            // search for IntRange in arguments
            if (arguments.length > 0) {
                IntRange range = null;
                final List<Object> objects = new ArrayList<Object>();
                for (Object argument : arguments) {
                    if (! (argument instanceof IntRange)){
                       objects.add(argument);
                    }else{
                        range = (IntRange) argument;
                    }
                }

                if (range != null){
                    return executor.executeFinder(methodInvocation.getMethod(),
                            range, objects.toArray());
                }
            }


            return executor.executeFinder(methodInvocation.getMethod(), arguments);
        }
        else if(methodName.startsWith("iterate"))
        {
            Object[] arguments = methodInvocation.getArguments();
            return executor.iterateFinder(methodInvocation.getMethod(), arguments);
        }
//        else if(methodName.startsWith("scroll"))
//        {
//            Object[] arguments = methodInvocation.getArguments();
//            return executor.scrollFinder(methodInvocation.getMethod(), arguments);
//        }
        else
        {
            return methodInvocation.proceed();
        }
    }

    public boolean implementsInterface(Class intf)
    {
        return intf.isInterface() && FinderExecutor.class.isAssignableFrom(intf);
    }
}
