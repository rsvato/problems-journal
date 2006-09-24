package net.paguo.generic.dao;

import junit.framework.TestCase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Svyatoslav Reyentenko mailto:rsvato@gmail.com
 * @version $Id$
 *          created 23.08.2006 0:32:21
 */
public abstract class AbstractDAOTest extends TestCase {
    ApplicationContext ctx;

    protected void setUp(){
        String path = "file:///home/ubuntu/IdeaProjects/problems-journal/conf/ds-context.xml";
        ctx = new ClassPathXmlApplicationContext(path);
    }


    public ApplicationContext getCtx() {
        return ctx;
    }
}
