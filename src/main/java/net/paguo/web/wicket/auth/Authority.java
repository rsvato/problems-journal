package net.paguo.web.wicket.auth;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * User: sreentenko
 * Date: 02.06.2007
 * Time: 1:11:14
 */
public class Authority implements Serializable {
    private String authority;

    public Authority(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Authority authority1 = (Authority) o;

        if (!authority.equals(authority1.authority)) return false;

        return true;
    }

    public int hashCode() {
        return authority.hashCode();
    }

    public String toString(){
        return ToStringBuilder.reflectionToString(this);
    }
}
