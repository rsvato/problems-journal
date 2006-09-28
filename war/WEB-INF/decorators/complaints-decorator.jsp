<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="menu" uri="http://struts-menu.sf.net/tag-el" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>
        <decorator:title default="-"/>
    </title>
    <meta content="no-cache" http-equiv="Cache-control"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/style/all.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/style/tabs.css"/>"/>
    <script type="text/javascript" src="<c:url value="/script/datetimepicker.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/script/application.js"/>"></script>
    <decorator:head/>
</head>
<body onload="initMenu();">
[<a href="<c:url value="/j_acegi_logout"/>">
    <fmt:message key="logout.title"/>
</a>]
<div id="page">
    <menu:useMenuDisplayer name="TabbedMenu">
        <menu:displayMenu name="crashes"/>
        <menu:displayMenu name="complaints"/>
    </menu:useMenuDisplayer>
    <div id="sidebar-menu">
        <menu:useMenuDisplayer name="SimpleMenu">
            <menu:displayMenu name="addComplaint"/>
        </menu:useMenuDisplayer>
    </div>
    <div id="content">
        <decorator:body/>
    </div>
</div>
</body>
</html>