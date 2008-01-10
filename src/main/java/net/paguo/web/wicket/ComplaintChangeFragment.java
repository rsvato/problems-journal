package net.paguo.web.wicket;

import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;

/**
 * User: sreentenko
 * Date: 11.01.2008
 * Time: 1:07:34
 */
@AuthorizeInstantiation({"ROLE_CHANGE_COMPLAINT", "ROLE_OVERRIDE_COMPLAINT"})
public final class ComplaintChangeFragment extends SecuredWebMarkupContainer {

    public ComplaintChangeFragment(String id) {
        super(id);
    }

}
