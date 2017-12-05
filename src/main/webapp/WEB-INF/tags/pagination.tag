<%--This is our custom pagination tags we're going to use to seperate the pages to
page groups.--%>

<%--This is the standard Java tag library--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ tag language="java" pageEncoding="UTF-8" %>

<%--Here we create an attribute named page of the type spring page--%>
<%--The page object will be passed here from the main page--%>
<%@ attribute name="page" required="true" type="org.springframework.data.domain.Page" %>

<%--Here will be passed the URL of the page for the pagination to work. Type is not needed because
it's a String--%>
<%@ attribute name="url" required="true" %>

<%--Number of page numbers to display at once--%>
<%@ attribute name="size" required="false" %>

<%--Here we set a default value for the variable "size". If it has no value, it's 10.
otherwise, it keeps its value--%>
<c:set var="size" value="${empty size ? 10 : size}"/>


<%--Using this code we'll remember on which page we're currently. We use a short version of IF.
If it's empty, it should have value 0. If it has a value, we set the block value to that value.--%>
<c:set var="block" value="${empty param.b ? 0 : param.b}"/>


<%--This is the starting page for our page block. We need +1 because it's 0 based.
For example if we're on the first block (block 0), 0 * 5 = 0. 0 + 1 = 1.
This means that the first block will start from number 1. Which is correct.
for the second block: 1*5=5, 5+1=6. It will start from page 6.  --%>
<c:set var="startPage" value="${block * size + 1}"/>

<%--Same here. First block will end on 5, second on 10. Which is correct.--%>
<c:set var="endPage" value="${(block + 1) * size}"/>

<%--Here we validate if the end page is greater than the total number of pages we got.
if it is, then the endPage = the number of our total pages. Otherwise, it keeps its value--%>
<c:set var="endPage" value="${endPage > page.totalPages ? page.totalPages : endPage}"/>


<%--This is needed to hide the pagination if there is only one page --%>
<c:if test="${page.totalPages != 1}">

    <%--This is the DIV where the page numbers will appear--%>
    <div class="pagination">

            <%--We display the << only if the page block is greater than 0--%>
        <c:if test="${block != 0}">

            <%--This produces a << --%>
            <a href="${url}?b=${block-1}&p=${(block - 1) * size + 1}"> &lt;&lt; </a>

        </c:if>

            <%--Each page "knows" the number of total pages. We call that method here--%>
        <c:forEach var="pageNumber" begin="${startPage}" end="${endPage}">

            <%--Here we deactivate the link of the page we're currently in. --%>
            <c:choose>

                <%--When the page number is different from the page we're on, we make it hyperlink.--%>
                <c:when test="${page.number != pageNumber - 1}">

                    <%--We create a link for each page. Each page will have as link the url variable,
                     followed by ?p=, the ?=b block number and the number of the page from the forEach loop--%>
                    <a href="${url}?p=${pageNumber}&b=${block} "><c:out value="${pageNumber}"/></a>

                </c:when>

                <%--If it is the page we're actually on, it's not a link and it's strong--%>
                <c:otherwise>
                    <strong><c:out value="${pageNumber}"/></strong>
                </c:otherwise>
            </c:choose>


            <%--This is to output a | between each page, and not on the latest one of our block--%>
            <c:if test="${pageNumber != endPage}">
                |
            </c:if>

        </c:forEach>

            <%--If it's not the final block, then show the >> button--%>
        <c:if test="${endPage != page.totalPages}">
            <%--This produces a >> --%>
            <a href="${url}?b=${block+1}&p=${(block + 1) * size + 1}"> &gt;&gt; </a>
        </c:if>
    </div>

</c:if>