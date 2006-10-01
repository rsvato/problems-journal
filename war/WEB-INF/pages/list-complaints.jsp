<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="menu" uri="http://struts-menu.sf.net/tag-el" %>
<h1><fmt:message key="list.complaints.title"/></h1>
<c:if test="${not empty requestScope.failures}">
    <table class="list">
        <caption>
            <fmt:message key="list.complaints.title"/>
        </caption>
        <tbody>
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
                            <c:url value="/dropComplaint.action">
                                <c:param name="failureId">
                                    ${failure.id}
                                </c:param>
                            </c:url>
                        </c:set>
                        <a href="${rlink}" class="cmd">
                            <img src="<c:url value="/img/delete.gif"/>" alt="remove"/></a>
                        <c:set var="editlink">
                            <c:url value="/prepareComplaintEdit.action">
                                <c:param name="failureId">
                                    ${failure.id}
                                </c:param>
                            </c:url>
                        </c:set>
                        <a href="${editlink}" class="cmd">
                            <img src="<c:url value="/img/edit.gif"/>" alt="edit"/>
                        </a>
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
        </tbody>
    </table>
</c:if>
<menu:useMenuDisplayer name="CoolMenu">
    <menu:displayMenu name="addComplaint"/>
</menu:useMenuDisplayer>

