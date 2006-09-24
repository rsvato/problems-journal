<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<ul class="menu">
    <li>
        <fmt:message key="menu.problems.label"/>
    </li>
    <ul>
        <li>
            <c:url var="clientjournal" value="/problems/clients-journal.html"/>
            <a href="${clientjournal}">
                <fmt:message key="journal.clients"/>
            </a>
        </li>
        <li>
            <c:url var="problemjournal" value="/problems/journal.html"/>
            <a href="${problemjournal}">
                <fmt:message key="journal.network"/>
            </a>
        </li>
        <li><a href="<c:url value="/problems/addComplaint.html"/>">
            <fmt:message key="new.complain"/>
        </a></li>
        <li><a href="<c:url value="/problems/addCrash.html"/>">
            <fmt:message key="new.problem"/>
        </a></li>
    </ul>
</ul>
