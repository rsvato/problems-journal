package net.paguo.domain.clients;


import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.NotNull;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;
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
@Table(name = "cl")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NamedQuery(name = "ClientItem.findActive",
        query="select cl from ClientItem cl where cl.deleted = false order by cl.clientName")
public class ClientItem implements Serializable {
    private Integer id;
    private String clientName;
    private Boolean deleted;

    /**
     * @hibernate.id generator-class="increment"
     * @return
     */
    @Id @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment",strategy = "increment")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @hibernate.property column="client" not-null="true"
     * @return
     */
    @NotNull  @Column(name="client")
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * @hibernate.property column="deleted"
     * @return
     */
    @Column
    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String toString(){
        return ToStringBuilder.reflectionToString(this);
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientItem item = (ClientItem) o;

        if (!clientName.equals(item.clientName)) return false;
        if (deleted != null ? !deleted.equals(item.deleted) : item.deleted != null) return false;
        if (!id.equals(item.id)) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = id.hashCode();
        result = 31 * result + clientName.hashCode();
        result = 31 * result + (deleted != null ? deleted.hashCode() : 0);
        return result;
    }
}
