package net.paguo.domain.problems;

import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @version $Id $
 * @hibernate.joined-subclass table="network_problems"
 * @hibernate.joined-subclass-key column="p_id"
 * @hibernate.query name="NetworkProblem.findByStatus" query="from NetworkProblem where restoreAction.completed = ? order by failureTime"
 * @hibernate.cache usage="read-write"
 */
@Entity
@Table(name = "network_problems")
@PrimaryKeyJoinColumn(name="p_id")
@NamedQuery(name="NetworkProblem.findOpen",query="from NetworkProblem where restoreAction is null or restoreAction.completed = false order by failureTime")
public class NetworkProblem extends NetworkFailure {
    private List<ClientComplaint> dependedComplaints;

    @OneToMany(mappedBy = "parent")
    public List<ClientComplaint> getDependedComplaints() {
        return dependedComplaints;
    }

    public void setDependedComplaints(List<ClientComplaint> dependedComplaints) {
        this.dependedComplaints = dependedComplaints;
    }

    public String toString(){
        return ToStringBuilder.reflectionToString(this);
    }
}
