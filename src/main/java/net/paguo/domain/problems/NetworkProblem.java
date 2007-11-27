package net.paguo.domain.problems;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.util.List;

/**
 * @version $Id $
 * @hibernate.joined-subclass table="network_problems"
 * @hibernate.joined-subclass-key column="p_id"
 * @hibernate.query name="NetworkProblem.findByStatus" query="from NetworkProblem where restoreAction.completed = ?
 * order by failureTime"
 * @hibernate.cache usage="read-write"
 */
@Indexed
@Entity
@Table(name = "network_problems")
@PrimaryKeyJoinColumn(name = "p_id")
@NamedQueries({
@NamedQuery(name = "NetworkProblem.findOpen",
        query = "from NetworkProblem where restoreAction is null or restoreAction.completed = false order by failureTime"),
@NamedQuery(name = "NetworkProblem.findAll",
        query = "from NetworkProblem np order by np.failureTime desc")
        }
)
public class NetworkProblem extends NetworkFailure {
    private List<ClientComplaint> dependedComplaints;

    @OneToMany(mappedBy = "parent")
    public List<ClientComplaint> getDependedComplaints() {
        return dependedComplaints;
    }

    public void setDependedComplaints(List<ClientComplaint> dependedComplaints) {
        this.dependedComplaints = dependedComplaints;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
