<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>
        <decorator:title default="-"/>
    </title>
    <meta content="no-cache" http-equiv="Cache-control"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/style/all.css"/>">
    <script type="text/javascript" src="<c:url value="/script/datetimepicker.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/script/application.js"/>"></script>
</head>
<body>
<div id="page">
    <div id="sidebar-menu">
        <c:import url="/WEB-INF/layout/menu.jsp"/>
    </div>
    <div id="content">
        <decorator:body/>
    </div>
</div>
</body>
</html>