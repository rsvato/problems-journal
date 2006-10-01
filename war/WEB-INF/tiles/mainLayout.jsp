<%--
  Created by IntelliJ IDEA.
  User: slava
  Date: 01.10.2006
  Time: 2:01:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tiles" uri="http://struts.apache.org/tags-tiles-el" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html-el" %>
<tiles:importAttribute scope="request"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>
         <tiles:getAsString name="title"/>
    </title>
    <meta content="no-cache" http-equiv="Cache-control"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/style/all.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/style/tabs.css"/>"/>
    <script type="text/javascript" src="<c:url value="/script/datetimepicker.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/script/application.js"/>"></script>
</head>
<body>
   <div id="page">
       <div id="top">
           <tiles:insert attribute="header"/>
       </div>

       <div id="content">
           <div id="errors">
               <html:errors/>
           </div>
           <div id="messages">
               <html:messages id="message"/>
           </div>
           <tiles:insert attribute="content"/>
       </div>
   </div>
</body>
</html>
