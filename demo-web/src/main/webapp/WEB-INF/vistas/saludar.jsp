<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Curso</title>
<link rel="stylesheet" href="/webjars/bootstrap/4.3.1/css/bootstrap.min.css">
<script src="/webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</head>
<body>
	<main class="container-fluid">
		<c:if test="${language == 'es' }">
			<h1>Hola ${nombre}</h1>
		</c:if>
		<c:if test="${language != 'es' }">
			<h1>Hello ${nombre}</h1>
		</c:if>
		<h2>Idioma ${language}</h2>
	</main>
</body>
</html>