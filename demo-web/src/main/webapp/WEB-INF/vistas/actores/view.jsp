<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ include file="../parts/header.jsp" %>
<p>
Código: ${elemento.actorId}<br>
Nombre: ${elemento.firstName}<br>
Apellidos: ${elemento.lastName}
</p>
<p>
	<a href="/actores" class="btn btn-primary" >Volver</a>
</p>
<%@ include file="../parts/footer.jsp" %>
