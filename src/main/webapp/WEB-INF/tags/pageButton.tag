<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ attribute name="pageName" required="true" %>
<%@ attribute name="currentPage" required="true" %>
<%@ attribute name="body" fragment="true" %>

<c:choose>
    <c:when test="${pageName.equals(currentPage)}">
        <a class="btn btn-secondary mr-1" href=${pageName}>
            <jsp:invoke fragment="body" />
        </a>
    </c:when>
    <c:otherwise>
        <a class="btn btn-info mr-1" href=${pageName}>
            <jsp:invoke fragment="body" />
        </a>
    </c:otherwise>
</c:choose>