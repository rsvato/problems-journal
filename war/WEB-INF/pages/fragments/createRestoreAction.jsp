<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<div style="display:none" id="resform">
    <form action="">
        <input type="hidden" name="failureId" id="failureId"/>
        <label for="action">
            <fmt:message key="restore.action"/>
        </label>
        <textarea id="action" rows="10" cols="20" name="restoreAction"></textarea>
        <label for="ftime">
            <fmt:message key="restore.time"/>
        </label>
        <input type="text" id="ftime" name="restoreTime"/>
        <a href="javascript:NewCal('ftime','yyymmdd',true,24)"><img src="<c:url value="/img/cal.gif"/>" width="16" height="16" border="0" alt="Pick a date"></a>
        <input type="submit" value="  OK   " class="">
    </form>
</div>
