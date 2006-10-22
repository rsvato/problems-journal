package net.paguo.domain.requests;

import net.paguo.domain.clients.ClientItem;
import net.paguo.domain.users.LocalUser;

import java.util.Date;

/**
 * @version $Id $
 */
public class ChangeStatusRequest {
    private Integer id;
    private ClientItem client;
    private LocalUser author;
    private Date created;

    /**
     * 
     * @return
     */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ClientItem getClient() {
        return client;
    }

    public void setClient(ClientItem client) {
        this.client = client;
    }

    public LocalUser getAuthor() {
        return author;
    }

    public void setAuthor(LocalUser author) {
        this.author = author;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
