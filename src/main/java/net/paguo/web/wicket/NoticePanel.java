package net.paguo.web.wicket;

import net.paguo.domain.requests.EmailNotice;
import net.paguo.domain.requests.Notice;
import net.paguo.domain.requests.PhoneNotice;
import org.apache.wicket.extensions.yui.calendar.DateField;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

/**
 * @author Reyentenko
 */
public class NoticePanel extends Panel {
    private static final long serialVersionUID = 712117970107736707L;

    public NoticePanel(String id, Notice notice) {
        super(id, new CompoundPropertyModel(notice));
        add(new DateField("date"));
        TextField email = new TextField("email");
        email.setVisible(notice instanceof EmailNotice);
        TextField phone = new TextField("phone");
        final boolean isPhoneNotice = notice instanceof PhoneNotice;
        phone.setVisible(isPhoneNotice);
        IModel labeModel = new ResourceModel(isPhoneNotice ? "phone" : "email");
        add(new Label("label", labeModel));
        add(email);
        add(phone);
    }
}
