package net.paguo.domain.clients;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.io.Serializable;

/**
 * @author Svyatoslav Reyentenko mailto:rsvato@gmail.com
 * @version $Id$
 *          created 22.08.2006 23:20:25
 * @hibernate.class table="cl"
 * @hibernate.cache usage="read-write"
 * @hibernate.query name="ClientItem.findByName" query="from ClientItem where clientName = ?"
 * @hibernate.query name="ClientItem.findAll" query="from ClientItem where deleted = false"
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NamedQueries(
        {@NamedQuery(name = "ClientItem.findActive",
                query = "select cl from ClientItem cl where cl.deleted = false order by cl.clientName"),
        @NamedQuery(name = "ClientItem.findByName",
                query = "select cl from ClientItem  cl where lower(cl.clientName) = lower(:name)"),
        @NamedQuery(name = "ClientItem.findForRequest",
                query = "select distinct client from ClientItem client where client.deleted = false and client.addresses.size > 0 order by client.clientName")
                }
)
@DiscriminatorValue(value = "client")
public class ClientItem extends AbstractItem implements Serializable, Comparable<ClientItem> {
    public int compareTo(ClientItem clientItem) {
        return getClientName().compareTo(clientItem.getClientName());
    }
}
