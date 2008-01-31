package net.paguo.domain.users;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@DiscriminatorValue("application")
@UniqueConstraint(columnNames = {"className", "action"})
@NamedQueries({@NamedQuery(name = "ApplicationRole.findByClassAndAction", query = "select ar from ApplicationRole ar where ar.action = :action and ar.className = :className"),
        @NamedQuery(name = "ApplicationRole.findByClass", query = "from ApplicationRole ar where ar.className = :className")})
public class ApplicationRole extends AbstractRole implements Serializable {
    private String className;
    public Action action;

    @Column
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Column
    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public static enum Action {
        CREATE("CREATE"),
        CHANGE("CHANGE"),
        VIEW("VIEW"),
        DELETE("DELETE"),
        OVERRIDE("OVERRIDE");

        String label;

        Action(String s) {
            this.label = s;
        }
    }
}
