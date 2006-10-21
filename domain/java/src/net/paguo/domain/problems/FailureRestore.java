package net.paguo.domain.problems;

import javax.persistence.*;
import java.util.Date;
import java.io.Serializable;

/**
 * @author Svyatoslav Reyentenko mailto:rsvato@gmail.com
 * @version $Id$
 *          created 26.08.2006 0:40:06
 */
@Embeddable
public class FailureRestore implements Serializable {
    private String failureCause;
    private String restoreAction;
    private Date restoreTime;
    private boolean completed;

    /**
     * @hibernate.property type="text"
     * @return
     */
    @Column @Lob
    public String getFailureCause() {
        return failureCause;
    }

    public void setFailureCause(String failureCause) {
        this.failureCause = failureCause;
    }

    /**
     * @hibernate.property type="text"
     * @return
     */
    @Column @Lob
    public String getRestoreAction() {
        return restoreAction;
    }

    public void setRestoreAction(String restoreAction) {
        this.restoreAction = restoreAction;
    }

    /**
     * @hibernate.property
     * @return
     */
    @Column
    public Date getRestoreTime() {
        return restoreTime;
    }

    public void setRestoreTime(Date restoreTime) {
        this.restoreTime = restoreTime;
    }

    /**
     * @hibernate.property
     * @return
     */
    @Column
    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
