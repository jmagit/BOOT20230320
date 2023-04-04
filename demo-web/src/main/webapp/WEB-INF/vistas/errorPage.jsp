<%@ include file="parts/header.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<h1 class="display-1 text-center">${status} ${error}</h1>
<h3>${message}</h3>
<p>
	date: <fmt:formatDate type="both" dateStyle = "short" timeStyle="short" value="${timestamp}" />
	<br>
	url: ${path}
</p>
<%@ include file="parts/footer.jsp" %>
