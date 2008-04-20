package net.paguo.domain.testing;

import net.paguo.domain.users.LocalUser;
import org.hibernate.validator.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * User: sreentenko
 * Date: 30.03.2008
 * Time: 0:47:28
 */
@Entity
@Table(name = "wf_requests")
public class Request implements Serializable {
    private ProcessStage currentStage = ProcessStage.OPENED;

    private Date creationDate;
    private ClientInformation clientInformation;
    private LocalUser author;
    private RequiredService service;

    public Request() {
    }

    public Request(ClientInformation information) {
        this.clientInformation = information;
    }

    public ProcessStage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(ProcessStage currentStage) {
        this.currentStage = currentStage;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @OneToOne(optional = false)
    @PrimaryKeyJoinColumn
    public ClientInformation getClientInformation() {
        return clientInformation;
    }

    public void setClientInformation(ClientInformation clientInformation) {
        this.clientInformation = clientInformation;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    public LocalUser getAuthor() {
        return author;
    }

    public void setAuthor(LocalUser author) {
        this.author = author;
    }

    @Embedded
    @NotNull
    public RequiredService getService() {
        return service;
    }

    public void setService(RequiredService service) {
        this.service = service;
    }
}
