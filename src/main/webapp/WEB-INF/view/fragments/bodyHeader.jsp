<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<nav class="navbar navbar-dark bg-dark py-0">
    <div class="container">
        <a href="expenses" class="navbar-brand"><img src="resources/images/ruble.png"> <spring:message code="app.title"/></a>
        <form class="form-inline my-2">
            <a class="btn btn-info mr-2" href="users"><spring:message code="user.title"/></a>
            <a class="btn btn-primary">
                <span class="fa fa-sign-out"></span>
            </a>
        </form>
    </div>
</nav>
