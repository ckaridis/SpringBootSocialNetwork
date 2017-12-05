<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--This is the standard Java tag library--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--We import the JSTL Formater to use to format the date--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--We add here the Spring tags library which will be used for our form--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<div class="row">
    <div class="col-md-8 col-md-offset-2">

        <div class="panel panel-default">
            <div class="panel-heading">
                <div class="panel-title">Add a status update</div>
            </div>

            <%--This is now a Spring Form --%>
            <%--This command name (or ModelAttribute) tells the form where to find the object to fill --%>
            <form:form modelAttribute="statusUpdate">

                <div class="errors">
                        <%--This line of code will show any errors on the field named "text" (path).--%>
                    <form:errors path="text"/>
                </div>

                <div class="form-group">
                        <%--Path = "text" declares the property we're going to use to store the data.--%>
                        <%--we've got a property named "text" on our class.--%>
                    <form:textarea path="text" name="text" rows="10" cols="50"></form:textarea>
                </div>
                <input type="submit" class="btn btn-success" name="submit" value="Add Status"/>
            </form:form>
        </div>


        <div class="panel panel-default">
            <div class="panel-heading">
                <%--Here we get the date from the latest object to display as title. (It's named added).--%>
                <div class="panel-title"> Status update added on:
                    <fmt:formatDate pattern="EEEE d MMMM y 'at' H:mm:ss" value="${latestStatusUpdate.added}"/>
                </div>

                <div class="panel-body">
                    ${latestStatusUpdate.text}

                </div>
            </div>


        </div>
    </div>
</div>

<%--
    &lt;%&ndash;This is the Script for TinyMCE &ndash;%&gt;
    <script src='https://cloud.tinymce.com/stable/tinymce.min.js'></script>
    <script>
        tinymce.init({
            selector: 'textarea',
            plugins: "link"
        });
    </script>--%>
