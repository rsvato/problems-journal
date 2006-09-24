<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>
        <decorator:title default="-"/>
    </title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/style/all.css"/>">
    <script type="text/javascript" src="<c:url value="/script/datetimepicker.js"/>"></script>
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