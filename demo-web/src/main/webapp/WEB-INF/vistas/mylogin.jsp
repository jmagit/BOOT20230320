<%@ include file="parts/header.jsp" %>
<c:url value="/mylogin" var="loginUrl" />
<form action="${loginUrl}" method="post">
	<c:if test="${error == true}">
		<div class="alert alert-danger">Invalid username and password.</div>
	</c:if>
	<c:if test="${param != null && param.logout != null}">
		<div class="alert alert-info">You have been logged out.</div>
	</c:if>
	<div class="form-group">
		<label for="username">Username</label>
		<input type="text" id="username" name="username" class="form-control"/>
	</div>
	<div class="form-group">
		<label for="password">Password</label>
		<input type="password" id="password" name="password" class="form-control"/>
	</div>
	<div class="form-group">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<input type="submit" class="btn btn-primary">
	</div>
</form>

<%@ include file="parts/footerEnd.jsp" %>
