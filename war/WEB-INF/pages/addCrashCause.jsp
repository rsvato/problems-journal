<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html-el" %>
<html:form action="/addRestoreAction">
    <html:hidden property="failureId"/>
    <div class="inputDiv">
        <label for="failureDescription">
            <fmt:message key="problem.description"/>
        </label>
        <span class="control">
            <html:textarea property="discoveredCause" styleClass="input area" styleId="failureDescription"/>
        </span>
    </div>
    <br class="clear"/>
    <fmt:message key="save.button" var="slabel"/>
    <fmt:message key="clear.button" var="clear"/>
    <html:submit value="${slabel}"/> <html:reset value="${clear}"/>
</html:form>