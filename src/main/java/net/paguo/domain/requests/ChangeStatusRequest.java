package net.paguo.domain.requests;

import net.paguo.domain.clients.ClientItem;
import net.paguo.domain.clients.PostalAddress;
import net.paguo.domain.equipment.ClientEndpoint;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * @version $Id $
 */
@Entity(name = "ChangeStatusRequest")
@Table(name = "change_status")
@NamedQueries({
@NamedQuery(name = "ChangeStatusRequest.findOrderedByClientDesc",
        query = "select cs from ChangeStatusRequest cs order by cs.enteredClient desc"),
@NamedQuery(name = "ChangeStatusRequest.findOrderedByClient",
        query = "select cs from ChangeStatusRequest cs order by cs.enteredClient"),
@NamedQuery(name = "ChangeStatusRequest.findByDates",
        query = "select cs from ChangeStatusRequest cs where cs.cancelRequest.dateEntered between :start and :end order by cs.cancelRequest.dateEntered")
        })
public class ChangeStatusRequest implements Serializable {
    private Integer id;
    private ClientItem client;
    private String enteredClient;
    private String description;
    private boolean permanent;

    private RequestInformation cancelRequest;
    private RequestInformation restoreRequest;
    private RequestInformation cancelExec;
    private RequestInformation restoreExec;

    private RequestNextStage nextStage;

    private ClientEndpoint endpoint;
    private PostalAddress discAddress;

    private Set<Notice> notices;
    private static final long serialVersionUID = 6332831689619653572L;

    /**
     * @return id
     */
    @Id
    @GeneratedValue
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    public ClientItem getClient() {
        return client;
    }

    public void setClient(ClientItem client) {
        this.client = client;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnteredClient() {
        return enteredClient;
    }

    public void setEnteredClient(String enteredClient) {
        this.enteredClient = enteredClient;
    }

    public boolean isPermanent() {
        return permanent;
    }

    public void setPermanent(boolean permanent) {
        this.permanent = permanent;
    }


    @Transient
    public String getShowClient() {
        return client != null ? client.getClientName() : enteredClient;
    }

    @Embedded
    @AttributeOverrides({
    @AttributeOverride(name = "dateEntered", column = @Column(name = "cr_date", nullable = false))
            })
    @AssociationOverrides({@AssociationOverride(
            name = "author",
            joinColumns = {@JoinColumn(name = "cr_auth_id",
                    nullable = false)})})
    public RequestInformation getCancelRequest() {
        return cancelRequest;
    }


    public void setCancelRequest(RequestInformation cancelRequest) {
        this.cancelRequest = cancelRequest;
    }

    @Embedded
    @AttributeOverrides({
    @AttributeOverride(name = "dateEntered", column = @Column(name = "rr_date"))
            })
    @AssociationOverrides({@AssociationOverride(
            name = "author",
            joinColumns = {@JoinColumn(name = "rr_auth_id")})})
    public RequestInformation getRestoreRequest() {
        return restoreRequest;
    }

    public void setRestoreRequest(RequestInformation restoreRequest) {
        this.restoreRequest = restoreRequest;
    }

    @Embedded
    @AttributeOverrides({
    @AttributeOverride(name = "dateEntered", column = @Column(name = "ce_date"))
            })
    @AssociationOverrides({@AssociationOverride(
            name = "author",
            joinColumns = {@JoinColumn(name = "ce_auth_id")})})
    public RequestInformation getCancelExec() {
        return cancelExec;
    }

    public void setCancelExec(RequestInformation cancelExec) {
        this.cancelExec = cancelExec;
    }

    @Embedded
    @AttributeOverrides({
    @AttributeOverride(name = "dateEntered", column = @Column(name = "re_date"))
            })
    @AssociationOverrides({@AssociationOverride(
            name = "author",
            joinColumns = {@JoinColumn(name = "re_auth_id")})})
    public RequestInformation getRestoreExec() {
        return restoreExec;
    }

    public void setRestoreExec(RequestInformation restoreExec) {
        this.restoreExec = restoreExec;
    }

    @Column(name = "next_stage")
    public RequestNextStage getNextStage() {
        return nextStage;
    }

    public void setNextStage(RequestNextStage nextStage) {
        this.nextStage = nextStage;
    }

    @OneToOne
    @JoinColumns({@JoinColumn(name = "endpoint_id")})
    public ClientEndpoint getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(ClientEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "address_id")
    public PostalAddress getDiscAddress() {
        return discAddress;
    }

    public void setDiscAddress(PostalAddress discAddress) {
        this.discAddress = discAddress;
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "req_notices", joinColumns = {@JoinColumn(name = "req_id")},
    inverseJoinColumns = {@JoinColumn(name = "notice_id")})
    public Set<Notice> getNotices() {
        return notices;
    }

    public void setNotices(Set<Notice> notices) {
        this.notices = notices;
    }

    @Transient
    public Integer getNumericalType(){
       return isPermanent() ? 0 : 1; 
    }
}
