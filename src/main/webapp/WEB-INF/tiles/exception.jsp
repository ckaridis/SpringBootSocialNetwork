<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--This is the standard Java tag library--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row">

    <div class="col-md 8 col-md-offset-2 col-sm-8 col-sm-offset-2 text-center">

        <div class="message">

            <c:out value="${message}" />

        </div>

        Exception: <c:out value="${exception}" />
        Failed UR: <c:out value="${url}" />
        Exception message: <c:out value="${exception.message}" />

        <c:forEach var="line" items="${exception.stackTrace}">
            <c:out value="${line}" />
        </c:forEach>


    </div>

</div>
