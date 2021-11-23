<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="topjava" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="ru.jegensomme.homeaccountant.util.CurrencyUtil" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>

<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron pt-4">
    <div class="container">
        <div class="row">
            <div class="col-5 offset-3">
                <h3>
                    <sec:authorize access="isAuthenticated()">
                        <sec:authentication property="principal.userTo.name"/>
                    </sec:authorize>
                    <spring:message code="${register ? 'app.register' : 'app.profile'}"/>
                </h3>
                <form:form class="form-group" modelAttribute="user" method="post" action="${register ? 'profile/register' : 'profile'}"
                           charset="utf-8" accept-charset="UTF-8">

                    <input name="id" value="${user.id}" type="hidden">
                    <topjava:inputField labelCode="user.name" name="name"/>
                    <topjava:inputField labelCode="user.email" name="email"/>
                    <topjava:inputField labelCode="user.password" name="password" inputType="password"/>
                    <topjava:inputField labelCode="user.monthlyLimit" name="monthlyLimit" inputType="number"/>
                    <spring:bind path="currency">
                        <div class="form-group ${status.error ? 'error' : '' }">
                            <label class="col-form-label"><spring:message code="common.currency"/></label>
                            <form:select path="currency" type="text" class="form-control ${status.error ? 'is-invalid' : '' }">
                                <c:forEach var="currency" items="${CurrencyUtil.SUPPORTED_CURRENCIES}">
                                    <form:option value="${currency.currencyCode}">${CurrencyUtil.getDisplayName(currency, pageContext.response.locale)}</form:option>
                                </c:forEach>
                            </form:select>
                            <div class="invalid-feedback">${status.errorMessage}</div>
                        </div>
                    </spring:bind>

                    <div class="text-right">
                        <a class="btn btn-secondary" href="#" onclick="window.history.back()">
                            <span class="fa fa-close"></span>
                            <spring:message code="common.cancel"/>
                        </a>
                        <button type="submit" class="btn btn-primary">
                            <span class="fa fa-check"></span>
                            <spring:message code="common.save"/>
                        </button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>