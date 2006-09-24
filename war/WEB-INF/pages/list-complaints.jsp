<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head><title>
    <fmt:message key="list.failures.title"/>
</title></head>
<body>
<div id="pageContent">
    <h1>
        <fmt:message key="list.complaints.title"/>
    </h1>
    <c:if test="${not empty requestScope.failures}">
        <table>
            <tr>
                <th>
                    <fmt:message key="label.date"/>
                </th>
                <th>
                    <fmt:message key="label.time"/>
                </th>
                <th>
                    <fmt:message key="label.description"/>
                </th>
                <th>
                    <fmt:message key="label.client"/>
                </th>
            </tr>
            <c:forEach var="failure" items="${requestScope.failures}">
                <tr>
                    <td>
                        <fmt:formatDate value="${failure.failureTime}" type="date"/>
                    </td>
                    <td>
                        <fmt:formatDate value="${failure.failureTime}" type="time"/>
                    </td>
                    <td>
                            ${failure.failureDescription}
                    </td>
                    <td>
                            ${failure.client.clientName}
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>
</body>
</html>