package net.paguo.web.wicket;

import net.paguo.controller.NetworkFailureController;
import net.paguo.domain.problems.NetworkProblem;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * User: sreentenko
 * Date: 21.11.2007
 * Time: 0:55:33
 */
public class NetworkProblemCreatePage extends SecuredWebPage{
    @SpringBean
    NetworkFailureController controller;

    public NetworkFailureController getController() {
        return controller;
    }

    public void setController(NetworkFailureController controller) {
        this.controller = controller;
    }

    public NetworkProblemCreatePage(){
        add(new NetworkProblemCreateForm("form"));
    }

    public final class NetworkProblemCreateForm extends Form {
        private final NetworkProblem problem = new NetworkProblem();

        public NetworkProblemCreateForm(String id) {
            super(id);
            add(new TextField("failureDescription", new PropertyModel(problem,
                    "failureDescription")));
        }
    }
}
