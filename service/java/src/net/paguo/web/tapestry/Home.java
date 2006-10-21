package net.paguo.web.tapestry;

import org.apache.tapestry.html.BasePage;
import org.apache.tapestry.IPage;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.annotations.InjectPage;
import org.apache.tapestry.annotations.InitialValue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * User: slava
 * Date: 06.10.2006
 * Time: 0:49:07
 * Version: $Id$
 */
public abstract class Home extends BasePage {
    private static final Log log = LogFactory.getLog(Home.class);

    @InjectPage("Result")
    public abstract IPage getResultPage();

    @InitialValue("literal:MSFT")
    public abstract String getStockId();

    public IPage onOk(IRequestCycle cycle) {
        log.info("Form submitted. Stock id is " + getStockId());
        Result resultPage = (Result) getResultPage();
        resultPage.setStockValue(getStockId().hashCode());
        return resultPage;
    }


    public String getSubjectName() {
        return "Taburetka";
    }


}
