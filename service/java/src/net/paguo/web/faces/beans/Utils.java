package net.paguo.web.faces.beans;

import net.paguo.domain.problems.NetworkProblem;

import javax.faces.context.FacesContext;

/**
 * User: slava
 * Date: 24.12.2006
 * Time: 3:13:18
 * Version: $Id$
 */
public class Utils {
    public static void bindObjectToVar(String binding, NetworkProblem failure) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getApplication().createValueBinding(binding).setValue(context, failure);
    }
}
