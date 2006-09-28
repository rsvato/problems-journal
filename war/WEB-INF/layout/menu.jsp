<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<ul class="menu">
    <li>
        <c:url var="clientjournal" value="/complaints/index.html"/>
        <a href="${clientjournal}">
            <fmt:message key="journal.clients"/>
        </a>
    </li>
    <li>
        <c:url var="problemjournal" value="/crashes/index.html"/>
        <a href="${problemjournal}">
            <fmt:message key="journal.network"/>
        </a>
    </li>
</ul>
