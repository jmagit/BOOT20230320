<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ include file="../parts/header.jsp" %>
<sf:form modelAttribute="elemento" action="${pageContext.request.contextPath}/${action}">
	<div class="form-group">
		<sf:label path="actorId">Código:</sf:label>
		<c:if test = '${modo == "add"}'>
			<sf:input path="actorId" cssClass="form-control" cssErrorClass="is-invalid form-control"/>
			<sf:errors path="actorId" cssClass="invalid-feedback" />
		</c:if>
		<c:if test = '${modo != "add"}'>
			${elemento.actorId}
		</c:if>		
	</div>
	<div class="form-group">
		<sf:label path="firstName">Nombre:</sf:label>
		<sf:input required="required" path="firstName" cssClass="form-control" cssErrorClass="is-invalid form-control"/>
		<sf:errors path="firstName" cssClass="invalid-feedback" />
	</div>
	<div class="form-group">
		<sf:label path="lastName">Apellidos:</sf:label>
		<sf:input path="lastName" cssClass="form-control" cssErrorClass="is-invalid form-control"/>
		<sf:errors path="lastName" cssClass="invalid-feedback" />
	</div>
	<div class="form-group">
		<sf:label path="lastUpdate">Fecha:</sf:label>
		<sf:input type="date" path="lastUpdate" cssClass="form-control" cssErrorClass="is-invalid form-control"/>
		<sf:errors path="lastUpdate" cssClass="invalid-feedback" />
	</div>
	<div class="form-group">
		<input type="submit" value="Enviar" class="btn btn-primary">
		<a href="/actores" class="btn btn-primary" >Volver</a>
	</div>
</sf:form>
<%@ include file="../parts/footer.jsp" %>
