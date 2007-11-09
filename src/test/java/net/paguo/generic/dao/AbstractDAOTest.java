package net.paguo.generic.dao;

import junit.framework.TestCase;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;

/**
 * @author Svyatoslav Reyentenko mailto:rsvato@gmail.com
 * @version $Id$
 *          created 23.08.2006 0:32:21
 */
public abstract class AbstractDAOTest extends TestCase {
    ApplicationContext ctx;

    protected void setUp(){
        ctx = new GenericApplicationContext();
        XmlBeanDefinitionReader xmlReader =
                new XmlBeanDefinitionReader((BeanDefinitionRegistry) ctx);
        xmlReader.loadBeanDefinitions(new ClassPathResource("ds-context.xml"));
    }


    public ApplicationContext getCtx() {
        return ctx;
    }
}
