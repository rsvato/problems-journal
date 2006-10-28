<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tiles" uri="http://struts.apache.org/tags-tiles-el" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html-el" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic-el" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean-el" %>
<tiles:importAttribute scope="request"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>
        <c:set var="titlekey">
            <tiles:getAsString name="title"/>
        </c:set>
        <c:catch var="error">
            <bean:message key="${titlekey}"/>
        </c:catch>
    </title>
    <meta content="no-cache" http-equiv="Cache-control"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/style/all.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/style/tabs.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/style/controls.css"/>"/>
    <script type="text/javascript" src="<c:url value="/script/datetimepicker.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/script/application.js"/>"></script>
</head>
<body>
   Active: ${active}
   <div id="page">
       <div id="top">
           <tiles:insert attribute="header"/>
           <div id="menuDiv">
               <ul id="menuList">
                   <c:forEach var="item" items="${menu}">
                       <li>
                           <c:set var="style">
                               <c:choose>
                                   <c:when test="${active eq item.key}">
                                       current
                                   </c:when>
                                   <c:otherwise>
                                       inactive
                                   </c:otherwise>
                               </c:choose>
                           </c:set>
                           <html:link action="${item.link}" styleClass="${style}">${item.value} (${item.key})</html:link>
                       </li>
                   </c:forEach>
               </ul>
           </div>
       </div>

       <div id="content">
           <logic:messagesPresent>
           <div id="errors">
               <table border="1" width="100%" align="center">
                   <tr>
                       <td valign="top">
                           <img src="<c:url value="/img/icon-warning.gif"/>" border="0"
                                vspace="2" hspace="10" align="middle"/>
                       </td>
                       <td>
                           <fmt:message key="errors.occured"/>
                           <ul>
                               <html:messages id="error">
                                   <li>${error}</li>
                               </html:messages>
                           </ul>
                       </td>

                   </tr>
               </table>
           </div>
           <div id="messages">
               <html:messages message="true" id="msg">
                   ${msg}<br/>
               </html:messages>
           </div>
           </logic:messagesPresent>
           <tiles:insert attribute="content"/>
       </div>
   </div>
</body>
</html>
