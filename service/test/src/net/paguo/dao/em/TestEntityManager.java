package net.paguo.dao.em;

import junit.framework.TestCase;

import javax.persistence.Persistence;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.ejb.EntityManagerFactoryImpl;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.impl.SessionFactoryImpl;
import net.paguo.domain.users.LocalRole;

/**
 * User: slava
 * Date: 08.10.2006
 * Time: 1:17:19
 * Version: $Id$
 */
public class TestEntityManager extends TestCase {
    public void testEntityManager(){
        SessionFactory sf = new AnnotationConfiguration().configure().buildSessionFactory();
        LocalRole role = new LocalRole();
        role.setRole("ROLE_TEST");
        role.setRoleDescription("Тестовая роль");
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(role);
        Query q = session.getNamedQuery("LocalRole.findByName");
        q.setString(0, role.getRole());
        System.err.println(q.list().size());
        tx.commit();
        session.close();
        fail();
    }
}
