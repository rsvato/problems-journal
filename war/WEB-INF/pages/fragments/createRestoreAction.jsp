<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html-el" %>
<h2><fmt:message key="restore.action"/></h2>
<c:import url="/WEB-INF/pages/fragments/error-page.jsp"/>
<html:form action="/doAddRestoreAction">
    <html:hidden property="failureId"/>
    <html:hidden property="kind"/>
    <div class="inputDiv">
        <label for="action">
            <fmt:message key="restore.action"/>
        </label>
            <span class="control">
        <html:textarea styleClass="input area" styleId="action" property="actionDescription"/>
                </span>
    </div>
    <br class="clear"/>
    <div class="inputDiv">
    <label for="ftime">
        <fmt:message key="restore.time"/>
    </label>
        <span class="control">
    <html:text property="actionTime" styleId="ftime" styleClass="input"/>

    <a href="javascript:NewCal('ftime','yyymmdd',true,24)"><img src="<c:url value="/img/cal.gif"/>" width="16"
                                                                height="16" border="0" alt="Pick a date"></a>
            </span>
    </div>
    <br class="clear"/>
    <html:submit value="  OK   " styleClass="button"/>
</html:form>
