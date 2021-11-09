<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron pt-4">
    <div class="container">
        <h3 class="text-center"><spring:message code="category.title"/></h3>
        <button class="btn btn-primary">
            <span class="fa fa-plus"></span>
            <spring:message code="common.add"/>
        </button>
        <table class="table table-striped" id="datatable">
            <thead>
            <tr>
                <th><spring:message code="category.name"/></th>
                <th><spring:message code="category.limit"/></th>
                <th><spring:message code="category.period"/></th>
                <th><spring:message code="app.currency"/></th>
                <th></th>
                <th></th>
            </tr>
            </thead>
        </table>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
