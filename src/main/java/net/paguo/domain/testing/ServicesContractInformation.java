package net.paguo.domain.testing;

import net.paguo.domain.users.LocalUser;

import java.util.Date;
import java.util.Set;

/**
 * User: sreentenko
 * Date: 30.03.2008
 * Time: 0:50:02
 */
public class ServicesContractInformation {
    private Integer id;
    private Set<ServiceInContract> services;
    private String contractNumber;
    private Date contractDate;
    private ContractClientInformation contractClientInformation;
    private Date plannedDate;
    private LocalUser creator;
    private Date registrationDate;

    public Set<ServiceInContract> getServices() {
        return services;
    }

    public void setServices(Set<ServiceInContract> services) {
        this.services = services;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public Date getContractDate() {
        return contractDate;
    }

    public void setContractDate(Date contractDate) {
        this.contractDate = contractDate;
    }

    public ContractClientInformation getContractClientInformation() {
        return contractClientInformation;
    }

    public void setContractClientInformation(ContractClientInformation contractClientInformation) {
        this.contractClientInformation = contractClientInformation;
    }

    public Date getPlannedDate() {
        return plannedDate;
    }

    public void setPlannedDate(Date plannedDate) {
        this.plannedDate = plannedDate;
    }

    public LocalUser getCreator() {
        return creator;
    }

    public void setCreator(LocalUser creator) {
        this.creator = creator;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
