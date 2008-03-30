package net.paguo.domain.testing;

/**
 * User: sreentenko
 * Date: 30.03.2008
 * Time: 0:47:28
 */
public class Request {
    private ProcessStage currentStage = ProcessStage.OPENED;

    private ClientInformation clientInformation;
    private TestingPlan testingPlan;
    private TestingResults testingResults;
    private ServicesContractInformation servicesContractInformation;
    private EnablingPlan enablingPlan;
    private EnablingResults enablingResults;

    public Request() {
    }

    public Request(ClientInformation information) {
        this.clientInformation = information;
    }

    public ProcessStage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(ProcessStage currentStage) {
        this.currentStage = currentStage;
    }

    public ClientInformation getClientInformation() {
        return clientInformation;
    }

    public void setClientInformation(ClientInformation clientInformation) {
        this.clientInformation = clientInformation;
    }

    public TestingPlan getTestingPlan() {
        return testingPlan;
    }

    public void setTestingPlan(TestingPlan testingPlan) {
        this.testingPlan = testingPlan;
    }

    public TestingResults getTestingResults() {
        return testingResults;
    }

    public void setTestingResults(TestingResults testingResults) {
        this.testingResults = testingResults;
    }

    public ServicesContractInformation getServicesContractInformation() {
        return servicesContractInformation;
    }

    public void setServicesContractInformation(ServicesContractInformation servicesContractInformation) {
        this.servicesContractInformation = servicesContractInformation;
    }

    public EnablingPlan getEnablingPlan() {
        return enablingPlan;
    }

    public void setEnablingPlan(EnablingPlan enablingPlan) {
        this.enablingPlan = enablingPlan;
    }

    public EnablingResults getEnablingResults() {
        return enablingResults;
    }

    public void setEnablingResults(EnablingResults enablingResults) {
        this.enablingResults = enablingResults;
    }
}
