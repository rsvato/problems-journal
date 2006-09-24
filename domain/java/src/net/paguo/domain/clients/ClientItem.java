package net.paguo.domain.clients;

import org.apache.commons.lang.builder.ToStringBuilder;

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
public class ClientItem implements Serializable {
    private Integer id;
    private String clientName;
    private Boolean deleted;

    /**
     * @hibernate.id generator-class="increment"
     * @return
     */
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
    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String toString(){
        return ToStringBuilder.reflectionToString(this);
    }
}
