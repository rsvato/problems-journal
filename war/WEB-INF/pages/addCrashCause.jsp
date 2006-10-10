<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html-el" %>
<html:form action="/addCause">
    <html:hidden property="failureId"/>
    <html:hidden property="kind"/>
    <div class="inputDiv">
        <label for="failureDescription">
            <fmt:message key="restore.cause"/>
        </label>
        <span class="control">
            <html:textarea property="discoveredCause" styleClass="input area" styleId="failureDescription"/>
        </span>
    </div>
    <br class="clear"/>
    <div class="inputDiv"><label for="closeFailure">
        <fmt:message key="close.failure.label"/>
    </label>
        <span class="control">
            <html:checkbox property="closeFlag"/>
        </span>
    </div>

    <fmt:message key="save.button" var="slabel"/>
    <fmt:message key="clear.button" var="clear"/>
    <html:submit value="${slabel}"/> <html:reset value="${clear}"/>
</html:form>