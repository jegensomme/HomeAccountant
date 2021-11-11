<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/homeaccountant.common.js" defer></script>
<script type="text/javascript" src="resources/js/homeaccountant.expenses.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron pt-4">
    <div class="container">
        <h3 class="text-center"><spring:message code="expense.title"/></h3>
        <div class="card border-dark">
            <div class="card-body pb-0">
                <form id="filter">
                    <div class="row">
                        <div class="col-2">
                            <label for="startDate"><spring:message code="expense.startDate"/></label>
                            <input class="form-control" name="startDate" id="startDate" autocomplete="off">
                        </div>
                        <div class="col-2">
                            <label for="endDate"><spring:message code="expense.endDate"/></label>
                            <input class="form-control" name="endDate" id="endDate" autocomplete="off">
                        </div>
                        <div class="offset-2 col-3">
                            <label for="startTime"><spring:message code="expense.startTime"/></label>
                            <input class="form-control" name="startTime" id="startTime" autocomplete="off">
                        </div>
                        <div class="col-3">
                            <label for="endTime"><spring:message code="expense.endTime"/></label>
                            <input class="form-control" name="endTime" id="endTime" autocomplete="off">
                        </div>
                    </div>
                </form>
            </div>
            <div class="card-footer text-right">
                <button class="btn btn-danger" onclick="add()">
                    <span class="fa fa-remove"></span>
                    <spring:message code="common.cancel"/>
                </button>
                <button class="btn btn-primary" onclick="">
                    <span class="fa fa-filter"></span>
                    <spring:message code="expense.filter"/>
                </button>
            </div>
        </div>
        <br/>
        <div class="row">
            <div class="col-2">
                <button class="btn btn-primary">
                    <span class="fa fa-plus"></span>
                    <spring:message code="common.add"/>
                </button>
            </div>
            <div class="offset-5 col-2 text-end">
                <button class="btn btn-primary">
                    <span class="fa fa-check"></span>
                    Select
                </button>
            </div>
            <form class="col-3">
                <input type="text" id="category" class="col-1 form-control">
            </form>
        </div>
        <table class="table table-striped" id="datatable">
            <thead>
            <tr>
                <th><spring:message code="expense.category"/></th>
                <th><spring:message code="expense.dateTime"/></th>
                <th><spring:message code="expense.amount"/></th>
                <th><spring:message code="common.currency"/></th>
                <th><spring:message code="expense.description"/></th>
                <th></th>
                <th></th>
            </tr>
            </thead>
        </table>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
<jsp:include page="fragments/i18n.jsp">
    <jsp:param name="page" value="expense"/>
</jsp:include>
</html>
