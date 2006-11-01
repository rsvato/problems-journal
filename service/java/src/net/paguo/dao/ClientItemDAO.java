package net.paguo.dao;

import net.paguo.domain.clients.ClientItem;
import net.paguo.generic.dao.GenericDao;

import java.util.List;
import java.util.Collection;

/**
 * @author Svyatoslav Reyentenko mailto:rsvato@gmail.com
 * @version $Id$
 *          created 23.08.2006 1:18:17
 */
public interface ClientItemDAO extends GenericDao<ClientItem, Integer> {
    List<ClientItem> findByName(String name);

    Collection<ClientItem> findActive();
}
