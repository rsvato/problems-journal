package net.paguo.generic.dao;

import junit.framework.TestCase;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;

/**
 * @author Svyatoslav Reyentenko mailto:rsvato@gmail.com
 * @version $Id$
 *          created 23.08.2006 0:32:21
 */
public abstract class AbstractDAOTest extends TestCase{
    ApplicationContext ctx;
    private org.hibernate.classic.Session session;

    protected void setUp(){
        ctx = new GenericApplicationContext();
        XmlBeanDefinitionReader xmlReader =
                new XmlBeanDefinitionReader((BeanDefinitionRegistry) ctx);
        xmlReader.loadBeanDefinitions(new ClassPathResource("ds-context.xml"));
        final SessionFactory sessionFactory = (SessionFactory) ctx.getBean("sessionFactory");

        session = sessionFactory.openSession();
        session.

        session.getTransaction();
    }

    protected void tearDown(){
        session.getTransaction().rollback();

    }


    public ApplicationContext getCtx() {
        return ctx;
    }
}
