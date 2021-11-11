<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<nav class="navbar navbar-dark bg-dark py-0">
    <div class="container">
        <div class="navbar-brand"><img src="resources/images/ruble.png"> <spring:message code="app.title"/></div>
        <div class="my-2">
            <button class="btn bnt-lg btn-success mr-1 " onclick="login('user@yandex.ru', 'password')">
                <spring:message code="app.login"/> User
                <span class="fa fa-sign-in"></span>
            </button>
            <button class="btn bnt-lg btn-success mr-1" onclick="login('admin@gmail.com', 'admin')">
                <spring:message code="app.login"/> Admin
                <span class="fa fa-sign-in"></span>
            </button>
        </div>
    </div>
</nav>

<div class="jumbotron py-0">
    <div class="container">
        <c:if test="${param.error}">
            <div class="error">${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</div>
        </c:if>
        <c:if test="${not empty param.message}">
            <div class="message"><spring:message code="${param.message}"/></div>
        </c:if>
        <div class="lead py-4"><spring:message code="app.stackTitle"/> <br>
            <a href="http://projects.spring.io/spring-security/">Spring Security</a>,
            <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html">Spring MVC</a>,
            <a href="http://projects.spring.io/spring-data-jpa/">Spring Data JPA</a>,
            <a href="http://spring.io/blog/2014/05/07/preview-spring-security-test-method-security">Spring Security
                Test</a>,
            <a href="http://hibernate.org/orm/">Hibernate ORM</a>,
            <a href="http://hibernate.org/validator/">Hibernate Validator</a>,
            <a href="http://www.slf4j.org/">SLF4J</a>,
            <a href="https://github.com/FasterXML/jackson">Json Jackson</a>,
            <a href="http://ru.wikipedia.org/wiki/JSP">JSP</a>,
            <a href="http://en.wikipedia.org/wiki/JavaServer_Pages_Standard_Tag_Library">JSTL</a>,
            <a href="http://tomcat.apache.org/">Apache Tomcat</a>,
            <a href="http://www.webjars.org/">WebJars</a>,
            <a href="http://datatables.net/">DataTables plugin</a>,
            <a href="http://ehcache.org">EHCACHE</a>,
            <a href="http://www.postgresql.org/">PostgreSQL</a>,
            <a href="http://junit.org/">JUnit</a>,
            <a href="http://hamcrest.org/JavaHamcrest/">Hamcrest</a>,
            <a href="http://jquery.com/">jQuery</a>,
            <a href="http://ned.im/noty/">jQuery notification</a>,
            <a href="http://getbootstrap.com/">Bootstrap</a>.
        </div>
    </div>
</div>
<br/>
<div class="col-3 offset-4 text-center">
    <form id="login_form" class="row offset-1 col-9 form-signin justify-content-center" action="spring_security_check" method="post">
        <div>
            <img class="mb-4" src="resources/images/login-icon.png" alt="" width="72" height="72">
            <h1 class="h3 mb-3 font-weight-normal"><spring:message code="login.title"/></h1>
        </div>
        <label for="username" class="sr-only">Email address</label>
        <input type="email" name="username" id="username" class="form-control mb-2" placeholder="Email address" required autofocus>
        <label for="password" class="sr-only">Password</label>
        <input type="password" name="password" id="password" class="form-control mb-3" placeholder="Password" required>
        <button class="btn btn-lg btn-primary btn-block mb-3" type="submit">
            <spring:message code="login.signin"/>
        </button>
    </form>
</div>

<jsp:include page="fragments/footer.jsp"/>
<script type="text/javascript">
    function login(username, password) {
        $('input[name="username"]').val(username);
        $('input[name="password"]').val(password);
        $("#login_form").submit();
    }
</script>
</body>
</html>
