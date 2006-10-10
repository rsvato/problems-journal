package net.paguo.web.struts.resolutions.form;

import net.paguo.web.struts.forms.SelectFailureForm;
import net.paguo.web.struts.forms.MultiactionForm;

/**
 * User: slava
 * Date: 09.10.2006
 * Time: 0:32:24
 * Version: $Id$
 */
public class DiscoveredCauseForm extends MultiactionForm {
    private String discoveredCause;
    private boolean closeFlag;

    public String getDiscoveredCause() {
        return discoveredCause;
    }

    public void setDiscoveredCause(String discoveredCause) {
        this.discoveredCause = discoveredCause;
    }


    public boolean isCloseFlag() {
        return closeFlag;
    }

    public void setCloseFlag(boolean closeFlag) {
        this.closeFlag = closeFlag;
    }
}
