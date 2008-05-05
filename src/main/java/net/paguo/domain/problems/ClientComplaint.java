package net.paguo.domain.problems;

import net.paguo.domain.clients.AbstractItem;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

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
@Indexed
@Entity
@Table(name="complaints")
@PrimaryKeyJoinColumn(name="c_id")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NamedQueries({
    @NamedQuery(name="ClientComplaint.findByClient", query = "from ClientComplaint where client = :client"),
    @NamedQuery(name="ClientComplaint.findAll", query = "from ClientComplaint order by failureTime desc")})
public class ClientComplaint extends NetworkFailure {
    private AbstractItem client;
    private NetworkProblem parent;
    private String enteredClient;

    /**
     * @hibernate.many-to-one class="net.paguo.domain.clients.ClientItem" not-null="true"
     * @return client
     */
    @ManyToOne
    @IndexedEmbedded
    public AbstractItem getClient() {
        return client;
    }

    public void setClient(AbstractItem client) {
        this.client = client;
    }

    @ManyToOne(cascade = {CascadeType.ALL, CascadeType.REMOVE})
    public NetworkProblem getParent() {
        return parent;
    }

    public void setParent(NetworkProblem parent) {
        this.parent = parent;
    }

    public String toString(){
        return ToStringBuilder.reflectionToString(this);
    }

    @Column
    public String getEnteredClient() {
        return enteredClient;
    }

    public void setEnteredClient(String enteredClient) {
        this.enteredClient = enteredClient;
    }
}
