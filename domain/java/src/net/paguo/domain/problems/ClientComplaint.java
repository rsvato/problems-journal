package net.paguo.domain.problems;

import net.paguo.domain.clients.ClientItem;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * @author Svyatoslav Reyentenko mailto:rsvato@gmail.com
 * @version $Id$
 *          created 26.08.2006 0:19:15
 * @hibernate.joined-subclass table="complaints"
 * @hibernate.joined-subclass-key column="c_id"
 * @hibernate.query name="ClientComplaint.findByClient" query="from ClientComplaint where client = ?"
 * @hibernate.query name="ClientComplaint.findAll" query="from ClientComplaint order by failureTime"
 * @hibernate.cache usage="read-write"
 */
@Entity
@Table(name="complaints")
@PrimaryKeyJoinColumn(name="c_id")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ClientComplaint extends NetworkFailure {
    private ClientItem client;

    /**
     * @hibernate.many-to-one class="net.paguo.domain.clients.ClientItem" not-null="true"
     * @return client
     */
    public ClientItem getClient() {
        return client;
    }

    public void setClient(ClientItem client) {
        this.client = client;
    }

    public String toString(){
        return ToStringBuilder.reflectionToString(this);
    }
}
