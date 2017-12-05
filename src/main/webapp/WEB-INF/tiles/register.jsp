<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--This is the standard Java tag library--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--We import the JSTL Formater to use to format the date--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--We add here the Spring tags library which will be used for our form--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<c:url var="loginUrl" value="/login"/>
<%--
&lt;%&ndash;Here we display any errors on login&ndash;%&gt;
<div class="alert alert-danger alert-dismissable">
    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
    <form:errors path="user.*"/>
</div>
--%>

<div class="row">
    <div class="col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">

        <%--<div class="errors">--%>
        <form:errors path="user.*" cssClass="alert alert-danger fade in" element="div"/>
        <%--</div>--%>

        <div class="panel panel-default">

            <div class="panel-heading">

                <div class="panel-title">Create an account</div>

            </div>


            <div class="panel-body">

                <form:form method="post" modelAttribute="user" class="login-form">

                    <div class="input-group">
                        <form:input type="text" path="email" placeholder="mail@example.com" class="form-control"/>
                    </div>

                    <div class="input-group">
                        <form:input type="password" path="plainPassword" placeholder="******" class="form-control"/>
                    </div>

                    <div class="input-group">
                        <form:input type="password" path="repeatPassword" placeholder="******" class="form-control"/>
                    </div>

                    <div class="input-group">
                        <button type="submit" class="btn btn-success pull-right">Register</button>
                    </div>

                </form:form>

            </div>
        </div>

    </div>

</div>
