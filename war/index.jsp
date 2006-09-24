<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
  <head><title><fmt:message key="welcome.title"/></title></head>
  <body>
    <div id="sidebar-menu">
        <c:import url="/WEB-INF/layout/menu.jsp"/>
    </div>
  </body>
</html>