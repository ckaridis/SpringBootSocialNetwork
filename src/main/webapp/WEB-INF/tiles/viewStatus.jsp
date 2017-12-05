<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--This is the standard Java tag library--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--We import the JSTL Formater to use to format the date--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<%--Here we include our custom pagination tag we've created--%>
<%@ taglib prefix="pagg" tagdir="/WEB-INF/tags" %>


<%--We set a variable "url" with the current url + the page we are in.
The c:url adds automatically the context root --%>
<c:url var="url" value="/viewStatus"/>


<div class="row">
    <div class="col-md-8 col-md-offset-2">


        <%--Here we create the pagination and we give the url, page to the .tag file
        and the size of the pagination--%>
        <pagg:pagination url="${url}" page="${page}" size="10"/>

        <%--This is a loop that itterates through the Page content --%>
        <%--In JSTL we don't acces with .getContent. we acces it like it's a property--%>
        <c:forEach var="statusUpdate" items="${page.content}">

            <%--This is the variable we're going to use to edit the status updates--%>
            <c:url var="editLink" value="/editStatus?id=${statusUpdate.id}"/>

            <%--This is the variable we're going to use to edit the status updates--%>
            <c:url var="deleteLink" value="/deleteStatus?id=${statusUpdate.id}"/>

            <div class="panel panel-default">
                <div class="panel-heading">
                        <%--Here we get the date from the latest object to display as title. (It's named added).--%>
                    <div class="panel-title"> Status update added on:
                        <fmt:formatDate pattern="EEEE d MMMM y 'at' H:mm:ss" value="${statusUpdate.added}"/>
                    </div>
                </div>

                <%--This is where the post update appears--%>
                <div class="panel-body">
                    <div>
                            ${statusUpdate.text}
                    </div>

                    <%--Here we got the edit and delete links--%>
                    <div class="edit-links pull-right">
                        <a href="${editLink}">edit</a>
                        |
                        <a onclick="return confirm ('Are you sure you want to delete?')"
                           href="${deleteLink}">delete</a>
                    </div>
                </div>


            </div>
        </c:forEach>
    </div>
</div>

