package net.paguo.domain.problems;

import net.paguo.domain.clients.ClientItem;

import javax.persistence.*;
import java.util.Date;
import java.io.Serializable;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.NotNull;

/**
 * @author Svyatoslav Reyentenko mailto:rsvato@gmail.com
 * @version $Id$
 *          created 25.08.2006 0:23:19
 * @hibernate.class table="failures"
 * @hibernate.cache usage="read-write"
 * @hibernate.query name="NetworkFailure.findByDate" query="from NetworkFailure where failureTime = ?"
 * @hibernate.query name="NetworkFailure.findOpen" query="from NetworkFailure where restoreAction is null or restoreAction.completed = false"
 * @hibernate.query name="NetworkFailure.findAll" query="from NetworkFailure order by failureTime"
 */
@Entity
@Table(name = "failures")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Inheritance(strategy = InheritanceType.JOINED)
public class NetworkFailure implements Serializable {
    private Integer id;
    private Date failureTime;
    private String failureDescription;
    private FailureRestore restoreAction;

    /**
     * @hibernate.id generator-class="increment"
     * @return
     */
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment",strategy = "increment")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @hibernate.property not-null="true"
     * @return
     */
    @Column @NotNull
    public Date getFailureTime() {
        return failureTime;
    }

    public void setFailureTime(Date failureTime) {
        this.failureTime = failureTime;
    }

    /**
     * @hibernate.property not-null="true" type="text"
     * @return
     */
    @Column @NotNull @Lob
    public String getFailureDescription() {
        return failureDescription;
    }

    public void setFailureDescription(String failureDescription) {
        this.failureDescription = failureDescription;
    }

    /**
     * @hibernate.component class="net.paguo.domain.problems.FailureRestore" not-null="false"
     * @return
     */
    @Embedded
    public FailureRestore getRestoreAction() {
        return restoreAction;
    }

    public void setRestoreAction(FailureRestore restoreAction) {
        this.restoreAction = restoreAction;
    }
}
