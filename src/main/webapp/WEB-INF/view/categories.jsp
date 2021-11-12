<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/homeaccountant.common.js" defer></script>
<script type="text/javascript" src="resources/js/homeaccountant.categories.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron pt-4">
    <div class="container">
        <h3 class="text-center"><spring:message code="category.title"/></h3>
        <br/>
        <button class="btn btn-primary" onclick="add()">
            <span class="fa fa-plus"></span>
            <spring:message code="common.add"/>
        </button>
        <table class="table table-striped" id="datatable">
            <thead>
            <tr>
                <th><spring:message code="category.name"/></th>
                <th><spring:message code="category.limit"/></th>
                <th><spring:message code="category.period"/></th>
                <th></th>
                <th></th>
            </tr>
            </thead>
        </table>
    </div>
</div>

<div class="modal fade" tabindex="-1" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="modalTitle"></h4>
                <button type="button" class="close" data-dismiss="modal" onclick="closeNoty()">&times;</button>
            </div>
            <div class="modal-body">
                <form id="detailsForm">
                    <input type="hidden" id="id" name="id">

                    <div class="form-group">
                        <label for="name" class="col-form-label"><spring:message code="category.name"/></label>
                        <input type="text" class="form-control" id="name" name="name"
                               placeholder="<spring:message code="user.name"/>">
                    </div>

                    <div class="form-group">
                        <label for="limit" class="col-form-label"><spring:message code="category.limit"/></label>
                        <input type="number" class="form-control" id="limit" name="limit"
                               placeholder="<spring:message code="category.limit"/>">
                    </div>

                    <div class="form-group">
                        <label for="period" class="col-form-label"><spring:message code="category.period"/></label>
                        <select class="form-control" id="period" name="period">
                            <option value=""></option>
                            <option value="1$DAYS"><spring:message code="period.day"/></option>
                            <option value="1$WEEKS"><spring:message code="period.week"/></option>
                            <option value="1$DECADES"><spring:message code="period.decade"/></option>
                            <option value="1$MONTHS"><spring:message code="period.month"/></option>
                            <option value="6$MONTHS"><spring:message code="period.half.year"/></option>
                            <option value="1$YEARS"><spring:message code="period.year"/></option>
                        </select>
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="closeNoty()">
                    <span class="fa fa-close"></span>
                    <spring:message code="common.cancel"/>
                </button>
                <button type="button" class="btn btn-primary" onclick="save()">
                    <span class="fa fa-check"></span>
                    <spring:message code="common.save"/>
                </button>
            </div>
        </div>
    </div>
</div>

<jsp:include page="fragments/footer.jsp"/>
</body>
<jsp:include page="fragments/i18n.jsp">
    <jsp:param name="page" value="category"/>
</jsp:include>
</html>
