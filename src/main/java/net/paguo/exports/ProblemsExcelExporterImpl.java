package net.paguo.exports;

import net.paguo.domain.problems.NetworkProblem;
import net.paguo.controller.NetworkFailureController;

import java.util.Collection;
import java.util.Date;

/**
 * @author Reyentenko
 */
public class ProblemsExcelExporterImpl extends AbstractExcelExporter<NetworkProblem> {
    private NetworkFailureController controller;

    public NetworkFailureController getController() {
        return controller;
    }

    public void setController(NetworkFailureController controller) {
        this.controller = controller;
    }

    public Collection<NetworkProblem> getData(Date start, Date end) {
        return getController().getProblems(start, end);
    }
}
