package net.paguo.web.tapestry;

import org.apache.tapestry.html.BasePage;
import org.apache.tapestry.IPage;
import org.apache.tapestry.annotations.InjectPage;

/**
 * User: slava
 * Date: 06.10.2006
 * Time: 0:49:07
 * Version: $Id $
 */
public abstract class Home extends BasePage {
    @InjectPage("Result")
    public abstract IPage getNextPage();

    public String getSubjectName(){
        return "Taburetka";
    }

    
}
