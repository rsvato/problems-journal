package net.paguo.generic.dao;

import net.paguo.domain.clients.ClientItem;
import net.paguo.dao.ClientItemDAO;

import java.util.List;

/**
 * @author Svyatoslav Reyentenko mailto:rsvato@gmail.com
 * @version $Id$
 *          created 23.08.2006 0:35:01
 */
public class ClientItemDAOTest extends AbstractDAOTest {
    private static final String FIRST_CLIENT = "Charles Dickens" + String.valueOf(System.currentTimeMillis());

    public void testCreate(){
        ClientItem item = new ClientItem();
        item.setClientName(FIRST_CLIENT);
        item.setDeleted(false);
        Integer id = getDao().create(item);
        assertNotNull(id);
    }

    public void testFind(){
        List clients = getDao().findByName(FIRST_CLIENT);
        int s = clients.size();
        assertTrue(s > 0);
    }

    @SuppressWarnings("unchecked")
    private ClientItemDAO getDao(){
        return (net.paguo.dao.ClientItemDAO) ctx.getBean("clientItemDao");
    }
}
