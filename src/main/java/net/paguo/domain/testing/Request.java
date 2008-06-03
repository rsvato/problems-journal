package net.paguo.domain.testing;

import net.paguo.domain.users.LocalUser;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.Valid;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;

/**
 * User: sreentenko
 * Date: 30.03.2008
 * Time: 0:47:28
 */
@Entity
@Table(name = "wf_requests")
public class Request implements Serializable {
    private ProcessStage currentStage;

    private Integer id;
    private Date creationDate;
    @Valid
    private ClientInformation clientInformation;
    private LocalUser author;
    private RequiredService service;
    private BuildingInformation buildingInformation;
    private static final long serialVersionUID = -5455347232298219310L;

    private Set<Testing> testings = new HashSet<Testing>();

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

    @Column(name="stage", nullable = false)
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

    @OneToOne(optional = false, cascade = CascadeType.ALL)
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "request")
    public Set<Testing> getTestings() {
        return testings;
    }

    public void setTestings(Set<Testing> testings) {
        this.testings = testings;
    }
}
