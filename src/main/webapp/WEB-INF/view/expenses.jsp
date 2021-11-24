<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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
                <div class="row">
                    <form id="categoryFilterForm" class="col-4">
                        <label for="categoryFilter" class="sr-only"><spring:message code="expense.category"/></label>
                        <select type="text" id="categoryFilter" name="categoryFilter" class="custom-select mr-sm-2">
                        </select>
                    </form>
                    <div class="offset-6">
                        <button class="btn btn-danger" onclick="clearFilter()">
                            <span class="fa fa-remove"></span>
                            <spring:message code="common.cancel"/>
                        </button>
                        <button class="btn btn-primary" onclick="ctx.updateTable()">
                            <span class="fa fa-filter"></span>
                            <spring:message code="expense.filter"/>
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <br/>
        <div>
            <button class="btn btn-primary" onclick="add()">
                <span class="fa fa-plus"></span>
                <spring:message code="common.add"/>
            </button>
        </div>
        <br/>
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
            <tfoot>
            <tr>
                <th colspan="2"><spring:message code="expense.total.amount"/></th>
                <th id="totalAmount"></th>
                <th><sec:authentication property="principal.userTo.currency"/></th>
            </tr>
            </tfoot>
        </table>
        <br/>
        <div id="monthTotalPanel" class="alert my-alert">
            <spring:message code="expense.month.amount.title"/>
            <label for="monthTotal"></label>
            <input id="monthTotal" type="text" disabled="disabled">
            <sec:authentication property="principal.userTo.currency"/>
        </div>
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
                        <label for="category" class="col-form-label"><spring:message code="expense.category"/></label>
                        <select type="text" class="form-control" id="category" name="category" autocomplete="off">
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="dateTime" class="col-form-label"><spring:message code="expense.dateTime"/></label>
                        <input class="form-control" id="dateTime" name="dateTime" autocomplete="off"
                               placeholder="<spring:message code="expense.dateTime"/>">
                    </div>

                    <div class="form-group">
                        <label for="amount" class="col-form-label"><spring:message code="expense.amount"/></label>
                        <input type="number" class="form-control" id="amount" name="amount">
                    </div>

                    <div class="form-group">
                        <label for="description" class="col-form-label"><spring:message
                                code="expense.description"/></label>
                        <input type="text" class="form-control" id="description" name="description"
                               placeholder="<spring:message code="expense.description"/>">
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
    <jsp:param name="page" value="expense"/>
</jsp:include>
<script type="text/javascript">
    const currency = '<sec:authentication property="principal.userTo.currency"/>';
    i18n['without.category'] = '<spring:message code="without.category"/>';
    i18n['all.categories'] = '<spring:message code="all.categories"/>';
</script>
</html>
