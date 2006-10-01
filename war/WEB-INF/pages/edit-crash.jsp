<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html-el" %>
<html:form action="/doCrashEdit">
    <html:hidden property="failureId"/>
    <div class="inputDiv">
        <label for="ftime"><fmt:message key="problem.time"/></label>
        <span class="control">
            <html:text property="failureTime" styleClass="input" styleId="ftime"/>
            <a href="javascript:NewCal('ftime','yyymmdd',true,24)">
                <img src="<c:url value="/img/cal.gif"/>" width="16"  height="16" border="0" alt="Pick a date">
            </a>
        </span>
    </div>
    <br class="clear"/>
    <div class="inputDiv">
        <label for="failureDescription">
            <fmt:message key="problem.description"/>
        </label>
        <span class="control">
            <html:textarea property="failureDescription" styleClass="input area" styleId="failureDescription"/>
        </span>
    </div>
    <fmt:message key="save.button" var="slabel"/>
    <html:submit value="${slabel}"/>
</html:form>