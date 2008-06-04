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
public class RequestSubscription {

    private Integer id;
    private ProcessStage stage;
    private Set<LocalUser> subscribers;

    public Integer getId() {
        return id;
    }

    @Id
    @GeneratedValue
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
