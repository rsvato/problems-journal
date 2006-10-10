package net.paguo.domain.problems;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * @version $Id $
 * @hibernate.joined-subclass table="network_problems"
 * @hibernate.joined-subclass-key column="p_id"
 * @hibernate.query name="NetworkProblem.findByStatus" query="from NetworkProblem where restoreAction.completed = ? order by failureTime"
 * @hibernate.cache usage="read-write"
 */
public class NetworkProblem extends NetworkFailure {
    public String toString(){
        return ToStringBuilder.reflectionToString(this);
    }
}
