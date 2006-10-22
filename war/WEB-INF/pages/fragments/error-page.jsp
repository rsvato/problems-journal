<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html-el" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic-el" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<logic:messagesPresent>
    <table class="error">
        <tr>
            <td valign="top">
                    <img src="<c:url value="/img/icon-warning.gif"/>" alt="warning"/>
            </td>
            <td>
                <fmt:message key="errors.heading"/>
                <ul>
                    <html:messages id="error">
                        <li>${error}</li>
                    </html:messages>
                </ul>
            </td>

        </tr>
    </table>
    <p>
</logic:messagesPresent>