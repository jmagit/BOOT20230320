<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ include file="../parts/header.jsp" %>
<h1>Listado de Actores</h1>

<table class="table table-hover table-striped table-bordered">
	<tr>
		<th>Actores</th>
		<td><a class="btn btn-primary" href="/actores/add"><i class="fas fa-plus"></i></a></td>
	</tr>
	<c:forEach var="elemento" items="${listado.getContent()}">
		<tr>
			<td><a href="/actores/${elemento.actorId}/${elemento.firstName}/${elemento.lastName}">${elemento.firstName} ${elemento.lastName}</a></td>
			<td>
				<a href="/actores/${elemento.actorId}/edit" class="btn btn-success"><i class="fas fa-pen"></i></a>
				<a href="/actores/${elemento.actorId}/delete"class="btn btn-danger"><i class="fas fa-trash"></i></a>
			</td>
		</tr>
	</c:forEach>
</table>
<ul class="pagination">
  	<c:if test = "${listado.hasPrevious()}">
	    <li class="page-item">
	    	<a class="page-link" href="${pageContext.request.contextPath}/actores?page=${listado.getNumber()-1}">
	    		<span aria-hidden="true">&laquo;</span>
	    	</a>
	    </li>
	</c:if>
	<c:forEach var = "i" begin = "0" end = "${listado.getTotalPages() -1}">
    	<li class="page-item<c:if test = "${i==listado.getNumber()}"> active</c:if>">
    		<a class="page-link" href="${pageContext.request.contextPath}/actores?page=${i}">${i + 1}</a>
    	</li>
	</c:forEach>
  	<c:if test = "${listado.hasNext()}">
	    <li class="page-item">
	    	<a class="page-link" href="${pageContext.request.contextPath}/actores?page=${listado.getNumber()+1}">
	    		<span aria-hidden="true">&raquo;</span>
	    	</a>
	    </li>
	</c:if>
</ul>

<%@ include file="../parts/footer.jsp" %>
