<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head><title>
    <fmt:message key="list.complaints.title"/>
</title></head>
<body>
<div id="pageContent">
    <h1>
        <fmt:message key="list.complaints.title"/>
    </h1>
    <c:if test="${not empty requestScope.failures}">
        <table class="list">
            <tr>
                <th>
                    &nbsp;
                </th>
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
            <c:forEach var="failure" items="${requestScope.failures}" varStatus="index">
                <c:set var="style">
                    <c:choose>
                        <c:when test="${index.index mod 2 eq 0}">
                            odd
                        </c:when>
                        <c:otherwise>
                            even
                        </c:otherwise>
                    </c:choose>
                </c:set>
                <tr class="${style}">
                    <td>
                        <c:set var="rlink">
                            <c:url value="/problems/removeComplaint.html">
                                <c:param name="complaintId">
                                    ${failure.id}
                                </c:param>
                            </c:url>
                        </c:set>
                        <a href="${rlink}" class="cmd"><img src="<c:url value="/img/remove.png"/>" alt="remove"/></a>
                    </td>
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