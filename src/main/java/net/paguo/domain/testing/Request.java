package net.paguo.domain.testing;

import net.paguo.domain.users.LocalUser;
import org.hibernate.validator.NotNull;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

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

    private Integer id;
    private Date creationDate;
    private ClientInformation clientInformation;
    private LocalUser author;
    private RequiredService service;
    private BuildingInformation buildingInformation;
    private static final long serialVersionUID = -5455347232298219310L;

    public Request() {
    }

    public Request(ClientInformation information) {
        this.clientInformation = information;
    }

    @Id @GeneratedValue(generator = "seq")
    @GenericGenerator(name = "seq", strategy = "sequence",
            parameters = {@Parameter(name = "sequence",
                    value = "wf_req_seq")})
    public Integer getId() {
        return id;
    }

    public void setId(Integer v) {
        this.id = v;
    }

    @Column(name="stage")
    public ProcessStage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(ProcessStage stage) {
        this.currentStage = stage;
    }

    @Column @Temporal(TemporalType.TIMESTAMP)
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date date) {
        this.creationDate = date;
    }

    @OneToOne(optional = false)
    @PrimaryKeyJoinColumn
    public ClientInformation getClientInformation() {
        return clientInformation;
    }

    public void setClientInformation(ClientInformation ci) {
        this.clientInformation = ci;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    public LocalUser getAuthor() {
        return author;
    }

    public void setAuthor(LocalUser localUser) {
        this.author = localUser;
    }

    @Embedded
    @NotNull
    public RequiredService getService() {
        return service;
    }

    public void setService(RequiredService s) {
        this.service = s;
    }

    @Embedded
    public BuildingInformation getBuildingInformation() {
        return buildingInformation;
    }

    public void setBuildingInformation(BuildingInformation bi) {
        this.buildingInformation = bi;
    }
}
