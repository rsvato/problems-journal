<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>
        <fmt:message key="problem.create.new"/>
    </title>
    <link rel="stylesheet" href="<c:url value="/style/controls.css"/>"/>
</head>
<body>
<h1>
    <fmt:message key="problem.create.new"/>
</h1>

<form action="<c:url value="/complaints/add/addComplaint.html"/>" method="post">
    <spring:bind path="problem.id">
        <c:if test="${not empty status.value}">
            <input type="hidden" name="id" value="${status.value}"/>
        </c:if>
    </spring:bind>
    <fieldset>
        <spring:bind path="problem.failureTime">
            <div class="inputDiv">
                <label for="ftime">
                    <fmt:message key="problem.time"/>
                </label>
            <span class="control">
                <input class="input" type="text" id="ftime" name="failureTime" value="${status.value}"/>
                <a href="javascript:NewCal('ftime','yyymmdd',true,24)"><img src="<c:url value="/img/cal.gif"/>" width="16"
                                                                            height="16" border="0" alt="Pick a date"></a>
                <span class="errorspan">
                        ${status.errorMessage}
                </span>
                </span>
            </div>
        </spring:bind>
        <br class="clear"/>
        <spring:bind path="problem.failureDescription">
            <div class="inputDiv"><label for="fdsc">
                <fmt:message key="problem.description"/>
            </label>
            <span class="control"><textarea class="input" rows="10" cols="60" name="failureDescription" id="fdsc">${status.value}</textarea>
            <span class="errorspan">
                    ${status.errorMessage}
            </span></span></div>
        </spring:bind>
        <br class="clear"/>
        <spring:bind path="problem.client">
            <div class="inputDiv"><label for="client">
                <fmt:message key="problem.client"/>
            </label>
            <span class="control"><select id="client" name="client" class="input">
                <c:forEach var="client" items="${clients}">
                    <c:choose>
                        <c:when test="${status.value eq client.id}">
                            <option value="${client.id}" selected="selected">${client.clientName}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${client.id}">${client.clientName}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select>
            <span class="errorspan">
                    ${status.errorMessage}
            </span></span></div>
        </spring:bind>
    </fieldset>
    <fmt:message key="save.button" var="slabel"/>
    <input type="submit" value="${slabel}"/>
</form>
</body>
</html>