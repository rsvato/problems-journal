<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="menu" uri="http://struts-menu.sf.net/tag-el" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html-el" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean-el" %>
<h1>
    <fmt:message key="list.failures.title"/>
</h1>
<c:if test="${not empty requestScope.failures}">
    <table class="list">
        <caption>
            <fmt:message key="list.failures.title"/>
        </caption>
        <tbody>
            <tr>
                <th/>
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
                    <fmt:message key="restore.cause"/>
                </th>
                <th>
                    <fmt:message key="restore.action"/>
                </th>
                <th>
                    <fmt:message key="restore.time"/>
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
                        <c:if test="${empty failure.restoreAction or not failure.restoreAction.completed}">
                        <c:set var="rlink">
                            <c:url value="/dropCrash.action">
                                <c:param name="failureId">
                                    ${failure.id}
                                </c:param>
                            </c:url>
                        </c:set>
                        <nobr>
                        <a href="${rlink}" class="cmd"><img src="<c:url value="/img/delete.gif"/>"
                                                            alt="remove"/></a>
                        <c:set var="editlink">
                            <c:url value="/prepareCrashEdit.action">
                                <c:param name="failureId">
                                    ${failure.id}
                                </c:param>
                            </c:url>
                        </c:set>
                        <a href="${editlink}" class="cmd">
                            <img src="<c:url value="/img/edit.gif"/>" alt="edit"/>
                        </a>

                        </nobr>
                        </c:if>
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
                        <c:set var="apcause">
                            <c:url value="/showAddCause.action">
                                <c:param name="failureId">
                                    ${failure.id}
                                </c:param>
                                <c:param name="kind">crash</c:param>
                            </c:url>
                        </c:set>
                        <c:choose>
                            <c:when test="${not empty failure.restoreAction}">
                                ${failure.restoreAction.failureCause}
                                <br/>
                                <c:if test="${not failure.restoreAction.completed}">
                                    <a href="${apcause}" class="cmd">
                                        <fmt:message key="change.cause"/>
                                    </a>
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                <a href="${apcause}" class="cmd">
                                    <fmt:message key="add.cause"/>
                                </a>
                            </c:otherwise>
                        </c:choose>

                    </td>
                    <td>
                        <c:set var="resaction">
                            <c:url value="/showAddRestoreAction.action">
                                <c:param name="failureId">
                                    ${failure.id}
                                </c:param>
                                <c:param name="kind">crash</c:param>
                            </c:url>
                        </c:set>
                        <c:if test="${not empty failure.restoreAction}">
                                ${failure.restoreAction.restoreAction}
                                <br/>
                                <c:if test="${not failure.restoreAction.completed}">
                                    <a href="${resaction}" class="cmd">
                                        <img src="<c:url value="/img/edit.gif"/>" alt="edit"/>
                                    </a>
                                </c:if>
                         </c:if>
                    </td>
                    <td>
                        <c:if test="${not empty failure.restoreAction and not empty failure.restoreAction.restoreTime}">
                            <fmt:formatDate value="${failure.restoreAction.restoreTime}" type="date"/>
                            <fmt:formatDate value="${failure.restoreAction.restoreTime}" type="time"/>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</c:if>
<menu:useMenuDisplayer name="CoolMenu">
    <menu:displayMenu name="addCrash"/>
</menu:useMenuDisplayer>