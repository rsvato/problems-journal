<%@ page import="org.acegisecurity.ui.AbstractProcessingFilter" %>
<%@ page import="org.acegisecurity.AuthenticationException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head><title><fmt:message key="login.title"/></title>
  <meta content="no-cache" http-equiv="Cache-control"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/style/all.css"/>"/>
  </head>

  <body>
  <c:if test="${not empty param.login_error}">
        <span class="errors">
          <strong><fmt:message key="auth.error"/></strong><br/><br/>
          <fmt:message key="auth.error.reason"/> <%= ((AuthenticationException) session.getAttribute(AbstractProcessingFilter.ACEGI_SECURITY_LAST_EXCEPTION_KEY)).getMessage() %>
        </span>
      </c:if>
    <form action="<c:url value="j_acegi_security_check"/>">
        <label for="j_username"><fmt:message key="username.label"/></label>
        <input type="text" name="j_username" class="inputText" id="j_username" maxlength="13"/>
        <br class="clear"/>
        <label for="j_password"><fmt:message key="password.label"/></label>
        <input type="password" name="j_password" class="inputText" id="j_password" maxlength="13"/>
        <br class="clear"/>
        <fmt:message key="login.button.label" var="sublabel"/>
        <input name="submit" type="submit" class="submit" value="${sublabel}"/>
    </form>
  </body>
</html>