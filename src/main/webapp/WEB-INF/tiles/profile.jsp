<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--This is the standard Java tag library--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--We import the JSTL Formater to use to format the date--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--We add here the Spring tags library which will be used for our form--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<c:url var="img" value="/img"/>
<c:url var="editProfileAbout" value="/editProfileAbout"/>

<%--Here we display any errors on login--%>
<c:if test="${param.error != null}">
    <div class="alert alert-danger alert-dismissable">
        <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
        <strong> LOGIN FAILED.</strong>
        Incorrect username or password.
    </div>
</c:if>

<div class="row">
    <div class="col-md-10 col-md-offset-1">

        <div class="profile-about">

            <div class="profile-image">
                <img src="${img}/default_avatar.png"/>
            </div>

            <div class="profile-text">

                <%--If profile text is null, then display this text--%>
                <c:choose>
                    <c:when test="${profile.about == null}">
                        Click "edit" to add information about yourself to your profile
                    </c:when>
                    <c:otherwise>
                        <c:out value="${profile.about}"/>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="profile-about-edit">
                <a href="${editProfileAbout}"> Edit </a>
            </div>
        </div>

    </div>
</div>
