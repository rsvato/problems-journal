package net.paguo.domain.testing;

import net.paguo.domain.users.LocalUser;
import net.paguo.visual.InterfaceField;
import net.paguo.visual.EditorEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.NotNull;
import org.hibernate.validator.Length;

/**
 * User: sreentenko
 * Date: 30.03.2008
 * Time: 0:48:48
 */
@Entity
@Table(name = "wf_test_results")
public class TestingResults implements Serializable {
    private Integer id;

    private static final long serialVersionUID = -5520581889467004201L;
    
    private Date creationDate;
    private LocalUser creator;
    
    @InterfaceField(order = 0) @NotNull @Length(max = 15)
    private String staffLead;

    @InterfaceField(order = 1) @NotNull @Length(min = 2, max = 4)
    private String baseStation;
    @InterfaceField(order = 2) @NotNull @Length(min = 1, max = 2)
    private String sector;

    @InterfaceField(order = 3) @NotNull @Length(min = 1, max = 6)
    private String signalStrength;

    @InterfaceField(order = 4) @NotNull @Length(min = 1, max = 4)
    private String connectionSpeed;

    @InterfaceField(order = 5, editor = EditorEnum.ENUM) @NotNull @Length(min = 2, max = 4)
    private TestingResultsEnum result;

    @InterfaceField(order = 1, editor = EditorEnum.LONGTEXT)
    private String recommendations;

    public TestingResultsEnum getResult() {
        return result;
    }

    public void setResult(TestingResultsEnum result) {
        this.result = result;
    }
    
    @Id @GeneratedValue
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id")
    public LocalUser getCreator() {
        return creator;
    }

    public void setCreator(LocalUser creator) {
        this.creator = creator;
    }

    public String getBaseStation() {
        return baseStation;
    }

    public void setBaseStation(String baseStation) {
        this.baseStation = baseStation;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getSignalStrength() {
        return signalStrength;
    }

    public void setSignalStrength(String signalStrength) {
        this.signalStrength = signalStrength;
    }

    public String getConnectionSpeed() {
        return connectionSpeed;
    }

    public void setConnectionSpeed(String connectionSpeed) {
        this.connectionSpeed = connectionSpeed;
    }

    public String getStaffLead() {
        return staffLead;
    }

    public void setStaffLead(String staffLead) {
        this.staffLead = staffLead;
    }

    @Column(name = "recommendations", columnDefinition = "TEXT")
    public String getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }
}
