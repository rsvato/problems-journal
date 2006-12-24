package net.paguo.domain.requests;

import net.paguo.domain.clients.ClientItem;
import net.paguo.domain.users.LocalUser;

import javax.persistence.*;
import java.util.Date;

/**
 * @version $Id $
 */
@Entity(name = "ChangeStatusRequest")
@Table(name="change_status")
public class ChangeStatusRequest {
    private Integer id;
    private ClientItem client;
    private LocalUser author;
    private Date date;
    private Date execDate;
    private LocalUser executor;
    private ChangeStatusRequestType type;
    private String description;

    /**
     * 
     * @return
     */
    @Id @GeneratedValue
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    public ClientItem getClient() {
        return client;
    }

    public void setClient(ClientItem client) {
        this.client = client;
    }

    @ManyToOne
    public LocalUser getAuthor() {
        return author;
    }

    public void setAuthor(LocalUser author) {
        this.author = author;
    }

    @Temporal(value = TemporalType.TIMESTAMP)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Temporal(value = TemporalType.TIMESTAMP)
    public Date getExecDate() {
        return execDate;
    }


    public void setExecDate(Date execDate) {
        this.execDate = execDate;
    }

    @ManyToOne
    public LocalUser getExecutor() {
        return executor;
    }

    public void setExecutor(LocalUser executor) {
        this.executor = executor;
    }

    @ManyToOne
    public ChangeStatusRequestType getType() {
        return type;
    }

    public void setType(ChangeStatusRequestType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
