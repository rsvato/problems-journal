package net.paguo.web.wicket.bpm.utils;

import org.jbpm.JbpmContext;
import org.jbpm.JbpmConfiguration;
import org.springmodules.workflow.jbpm31.JbpmTemplate;

/**
 * User: sreentenko
 * Date: 24.09.2008
 * Time: 1:34:02
 */
public class ProcessUtils {
    public static JbpmContext getContext(JbpmTemplate template) {
        final JbpmConfiguration jbpmConfiguration = template.getJbpmConfiguration();
        JbpmContext jbpmContext = jbpmConfiguration.getCurrentJbpmContext();
        if (jbpmContext == null){
            jbpmContext = jbpmConfiguration.createJbpmContext();
        }
        return jbpmContext;
    }
}
