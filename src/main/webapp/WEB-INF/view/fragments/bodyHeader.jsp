<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="homeaccountant" tagdir="/WEB-INF/tags" %>

<%
    String servletPath=request.getServletPath();
    String pageName = servletPath.substring(servletPath.lastIndexOf("/")+1, servletPath.length() - 4);
    request.setAttribute("pageName", pageName);
%>
<nav class="navbar navbar-expand-md navbar-dark bg-dark py-0">
    <div class="container">
        <a href="expenses" class="navbar-brand"><img src="resources/images/ruble.png"> <spring:message code="app.title"/></a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <sec:authorize access="isAuthenticated()">
                        <form:form class="form-inline my-2" action="logout" method="post">
                            <homeaccountant:pageButton pageName="expenses" currentPage="${pageName}">
                                <jsp:attribute name="body"><spring:message code="expense.title"/></jsp:attribute>
                            </homeaccountant:pageButton>
                            <homeaccountant:pageButton pageName="categories" currentPage="${pageName}">
                                <jsp:attribute name="body"><spring:message code="category.title"/></jsp:attribute>
                            </homeaccountant:pageButton>
                            <sec:authorize access="hasRole('ADMIN')">
                                <homeaccountant:pageButton pageName="users" currentPage="${pageName}">
                                    <jsp:attribute name="body"><spring:message code="user.title"/></jsp:attribute>
                                </homeaccountant:pageButton>
                            </sec:authorize>
                            <homeaccountant:pageButton pageName="profile" currentPage="${pageName}">
                                <jsp:attribute name="body">
                                    <sec:authentication property="principal.userTo.name"/>
                                    <spring:message code="app.profile"/>
                                </jsp:attribute>
                            </homeaccountant:pageButton>
                            <button class="btn btn-primary my-1" type="submit">
                                <span class="fa fa-sign-out"></span>
                            </button>
                        </form:form>
                    </sec:authorize>
                    <sec:authorize access="isAnonymous()">
                        <form:form class="form-inline my-2" id="login_form" action="spring_security_check" method="post">
                            <input class="form-control mr-1" type="text" placeholder="Email" name="username">
                            <input class="form-control mr-1" type="password" placeholder="Password" name="password">
                            <button class="btn btn-success" type="submit">
                                <span class="fa fa-sign-in"></span>
                            </button>
                        </form:form>
                    </sec:authorize>
                </li>
                <li class="nav-item dropdown">
                    <a class="dropdown-toggle nav-link my-1 ml-2" data-toggle="dropdown">${pageContext.response.locale}</a>
                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="${requestScope['javax.servlet.forward.request_uri']}?lang=en">English</a>
                        <a class="dropdown-item" href="${requestScope['javax.servlet.forward.request_uri']}?lang=ru">Русский</a>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</nav>
<script type="text/javascript">
    const localeCode = "${pageContext.response.locale}";
</script>
<sec:authorize access="isAuthenticated()">
    <script type="text/javascript">
        const monthlyLimit = "${monthlyLimit}";
    </script>
</sec:authorize>
