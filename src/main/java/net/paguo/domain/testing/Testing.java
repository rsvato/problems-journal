package net.paguo.domain.testing;

import javax.persistence.*;
import java.util.Date;
import java.io.Serializable;

/**
 * User: sreentenko
 * Date: 20.04.2008
 * Time: 1:13:28
 */
@Entity
@Table(name = "wf_all_testing")
public class Testing  implements Serializable {
    @Id @GeneratedValue
    private Integer id;

    private Request request;

    @Column
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date creationDate;

    @OneToOne(optional = false)
    @JoinColumn(name = "plan_id")
    private TestingPlan plan;

    @OneToOne
    @JoinColumn(name = "result_id")
    private TestingResults result;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TestingPlan getPlan() {
        return plan;
    }

    public void setPlan(TestingPlan plan) {
        this.plan = plan;
    }

    public TestingResults getResult() {
        return result;
    }

    public void setResult(TestingResults result) {
        this.result = result;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "request_id", nullable = false)
    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}
