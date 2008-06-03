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
 * Time: 0:48:35
 */
@Entity
@Table(name = "wf_testing_plan")
public class TestingPlan implements Serializable {
    @Id @GeneratedValue
    private Integer id;
    private static final long serialVersionUID = 1988468348137000821L;

    private Date creationDate;
    private LocalUser creator;

    @InterfaceField(order = 0, editor = EditorEnum.DATETIME) @NotNull
    private Date plannedDate;

    @InterfaceField(order = 1) @NotNull @Length(max = 15)
    private String staffLead;

    @InterfaceField(order = 2, editor = EditorEnum.LONGTEXT)
    private String requiredStuff;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column @Temporal(TemporalType.TIMESTAMP)
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    public LocalUser getCreator() {
        return creator;
    }

    public void setCreator(LocalUser creator) {
        this.creator = creator;
    }

    @Column @Temporal(TemporalType.TIMESTAMP)
    public Date getPlannedDate() {
        return plannedDate;
    }

    public void setPlannedDate(Date plannedDate) {
        this.plannedDate = plannedDate;
    }

    @Column
    public String getStaffLead() {
        return staffLead;
    }


    public void setStaffLead(String staffLead) {
        this.staffLead = staffLead;
    }

    @Column(name = "stuff", columnDefinition = "TEXT")
    public String getRequiredStuff() {
        return requiredStuff;
    }

    public void setRequiredStuff(String requiredStuff) {
        this.requiredStuff = requiredStuff;
    }
}
