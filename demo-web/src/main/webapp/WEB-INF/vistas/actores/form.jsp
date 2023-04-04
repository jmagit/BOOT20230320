<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ include file="../parts/header.jsp" %>
<sf:form modelAttribute="elemento" action="${pageContext.request.contextPath}/${action}">
	<div class="form-group">
		<sf:label path="actorId"><s:message code="actores.form.id" /></sf:label>
		<c:if test = '${modo == "add"}'>
			<sf:input path="actorId" cssClass="form-control" cssErrorClass="is-invalid form-control"/>
			<sf:errors path="actorId" cssClass="invalid-feedback" />
		</c:if>
		<c:if test = '${modo != "add"}'>
			${elemento.actorId}
			<sf:hidden path="actorId"/>
			<sf:errors path="actorId" cssClass="error" />
		</c:if>		
	</div>
	<div class="form-group">
		<sf:label path="firstName"><s:message code="actores.form.nombre" /></sf:label>
		<sf:input required="required" path="firstName" cssClass="form-control" cssErrorClass="is-invalid form-control"/>
		<sf:errors path="firstName" cssClass="invalid-feedback" />
	</div>
	<div class="form-group">
		<sf:label path="lastName"><s:message code="actores.form.apellidos" /></sf:label>
		<sf:input path="lastName" cssClass="form-control" cssErrorClass="is-invalid form-control"/>
		<sf:errors path="lastName" cssClass="invalid-feedback" />
	</div>
	<div class="form-group">
		<input type="submit" value="<s:message code="form.enviar" />" class="btn btn-primary">
		<a href="/actores" class="btn btn-primary" ><s:message code="form.volver" /></a>
	</div>
</sf:form>
<%@ include file="../parts/footer.jsp" %>
