package net.paguo.domain.messaging;

import net.paguo.domain.testing.ProcessStage;
import net.paguo.domain.users.LocalUser;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Reyentenko
 */
@Entity
@Table(name = "wf_req_subscription")
@NamedQueries(@NamedQuery(name = "RequestSubscription.findByStage", query = "from RequestSubscription rs where rs.stage = :stage"))
public class RequestSubscription implements ISubscribers {

    private Integer id;
    private ProcessStage stage;
    private Set<LocalUser> subscribers;

    @Id
    @GeneratedValue
    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    @Column
    public ProcessStage getStage() {
        return stage;
    }

    public void setStage(ProcessStage stage) {
        this.stage = stage;
    }

    @ManyToMany
    @JoinTable(name = "wf_rqsubs_users")
    public Set<LocalUser> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Set<LocalUser> subscribers) {
        this.subscribers = subscribers;
    }
}
