package net.paguo.web.wicket.auth;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Set;

/**
 * User: sreentenko
 * Date: 02.06.2007
 * Time: 1:09:55
 */
public class UserView implements Serializable{
    String username;
    Set<Authority> authorities;

    public UserView(String login) {
        this.username = login;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public String toString(){
        return ToStringBuilder.reflectionToString(this);
    }
}
