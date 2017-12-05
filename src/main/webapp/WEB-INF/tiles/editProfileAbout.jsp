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
                <div class="panel-title">Edit your about text</div>
            </div>

            <%--This is now a Spring Form --%>
            <%--This command name (or ModelAttribute) tells the form where to find the object to fill --%>
            <form:form modelAttribute="profile">

                <div class="form-group">
                        <%--Path = "text" declares the property we're going to use to store the data.--%>
                        <%--we've got a property named "text" on our class.--%>
                    <form:textarea path="about" name="about" rows="10" cols="50"></form:textarea>
                </div>
                <input type="submit" class="btn btn-success" name="submit" value="Update Profile Description"/>
            </form:form>
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
