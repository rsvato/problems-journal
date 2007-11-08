package net.paguo.web.struts.forms;

import net.paguo.web.struts.CrashKind;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * User: slava
 * Date: 09.10.2006
 * Time: 1:10:14
 * Version: $Id$
 */
public class MultiactionForm extends SelectFailureForm{
    private String kind;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public CrashKind getCrashKind(){
        if (kind == null) return null;
       return CrashKind.valueOf(kind.toUpperCase());
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
       this.kind = null;
       super.reset(actionMapping, httpServletRequest);
    }
}
