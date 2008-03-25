package net.paguo.domain.workflow;

import net.paguo.domain.users.LocalUser;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Reyentenko
 */
@Entity
@Table(name = "workflow_requests")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class GenericRequest implements Serializable {
    private Integer id;
    private Date dateCreated;
    private LocalUser creator;
    private static final long serialVersionUID = 8112058002896819489L;

    @Id
    @GeneratedValue(generator = "seq")
    @GenericGenerator(name = "seq", strategy = "sequence",
            parameters = {@Parameter(name = "sequence",
                    value = "wrq_seq")})
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "date_created")
    @Temporal(value = TemporalType.DATE)
    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @ManyToOne
    public LocalUser getCreator() {
        return creator;
    }

    public void setCreator(LocalUser creator) {
        this.creator = creator;
    }
}
