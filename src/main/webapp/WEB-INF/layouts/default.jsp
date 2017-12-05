<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%-- Setting the context root --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>


<html>
<head>

    <%-- Insert default title from tiles.xml --%>
    <title><tiles:insertAttribute name="title"/></title>

    <%-- This is required to set the context root --%>
    <c:set var="contextRoot" value="${pageContext.request.contextPath}"/>

    <%-- Required meta tags --%>
    <%-- bootstrap start --%>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
          integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7"
          crossorigin="anonymous">
    <%-- bootstrap end --%>

    <link rel="stylesheet" href="${contextRoot}/css/main.css">

</head>
<body>

<%--NavBar Start--%>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="${contextRoot}/">Spring Boot Demo</a>
        </div>
        <ul class="nav navbar-nav">
            <li><a href="${contextRoot}/"><span class="glyphicon glyphicon-home"></span> Home</a></li>
            <li><a href="${contextRoot}/about"> <span class="glyphicon glyphicon-user"></span> About</a></li>
            <li><a href="${contextRoot}/viewStatus"> <span class="glyphicon glyphicon-user"></span> View Status</a></li>
        </ul>


        <ul class="nav navbar-nav navbar-right">


            <%--This appears when the user is not authenticated--%>
            <sec:authorize access="!isAuthenticated()">
                <li><a href="${contextRoot}/login"> Login</a></li>
                <li><a href="${contextRoot}/register"> Register</a></li>
            </sec:authorize>

            <%--This content will only be visible by authenticated users --%>
            <sec:authorize access="isAuthenticated()">

                <li><a href="${contextRoot}/profile"> Profile</a></li>

                <%--The logout link has to submit the logout form using JavaScript--%>
                <li><a href="javascript:$('#logoutForm').submit();"> Logout</a></li>

            </sec:authorize>

            <%--Only ADMINS should have access here--%>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Status Actions
                        <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="${contextRoot}/addStatus">
                            <span class="glyphicon glyphicon-log-in"></span> Add Status</a></li>
                        <li><a href="${contextRoot}/viewStatus">
                            <span class="glyphicon glyphicon-log-in"></span> View Status Updates</a></li>
                    </ul>
                </li>
            </sec:authorize>

        </ul>


    </div>
</nav>
<%--NavBar End--%>


<%--This is the logout process. A form need to be submitted containing the CSRF token.--%>
<c:url var="logoutLink" value="/logout"/>
<form id="logoutForm" method="post" action="${logoutLink}">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
</form>


<%--Main content container--%>
<div class="container">
    <tiles:insertAttribute name="content"/>
</div>


<%--bootstrap start --%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.2/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
        integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS"
        crossorigin="anonymous"></script>
<%-- bootstrap end --%>

</body>
</html>
