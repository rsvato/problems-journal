<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tiles" uri="http://struts.apache.org/tags-tiles" %>
<%@ taglib prefix="menu" uri="http://struts-menu.sf.net/tag-el" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
[<a href="<c:url value="/j_acegi_logout"/>"><fmt:message key="logout.title"/></a>]
    <menu:useMenuDisplayer name="TabbedMenu">
        <menu:displayMenu name="crashes"/>
        <menu:displayMenu name="complaints"/>
    </menu:useMenuDisplayer>