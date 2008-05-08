package net.paguo.dao;

import net.paguo.domain.problems.ClientComplaint;
import net.paguo.domain.clients.ClientItem;
import net.paguo.generic.dao.GenericDao;

import java.util.List;
import java.util.Date;

import org.apache.commons.lang.math.IntRange;
import org.apache.wicket.util.string.IStringSequence;

/**
 * @author Svyatoslav Reyentenko mailto:rsvato@gmail.com
 * @version $Id$
 *          created 26.08.2006 0:23:00
 */
public interface ClientComplaintDao extends GenericDao<ClientComplaint, Integer> {
    List<ClientComplaint> findByClient(ClientItem ci);
    List<ClientComplaint> findAll();

    List<ClientComplaint> findAll(IntRange range);
    List<ClientComplaint> findByDateRange(Date start, Date end);
}
