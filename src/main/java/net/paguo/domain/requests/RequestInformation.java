package net.paguo.domain.requests;

import net.paguo.domain.users.LocalUser;

import javax.persistence.*;
import java.util.Date;
import java.io.Serializable;
// from ChangeStatusRequest cs join fetch cs.stages css where css.requestInformationType = net.paguo.domain.requests.RequestInformationType.CHALLENGE and css.requestType = net.paguo.domain.requests.ChangeRequestType.OPEN
// order by css.dateEntered desc
@Embeddable
public class RequestInformation implements Serializable {
    private LocalUser author;
    private Date dateEntered;

    @ManyToOne
    @JoinColumn(name = "auth_id")
    public LocalUser getAuthor() {
        return author;
    }

    public void setAuthor(LocalUser author) {
        this.author = author;
    }

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "date_ent")
    public Date getDateEntered() {
        return dateEntered;
    }

    public void setDateEntered(Date dateEntered) {
        this.dateEntered = dateEntered;
    }
}
