<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="ISO-8859-1">
<title>Demos curso</title>
<%--
	<link rel="stylesheet" href="/css/tema.css" >
--%>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.8.1/css/all.css"
	integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf"
	crossorigin="anonymous">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<a class="navbar-brand" href="/"><img src="/logo.png"
			title="Logotipo corporativo" height="30"></a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarSupportedContent"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item"><a class="nav-link" href="/">Inicio</a></li>
				<li class="nav-item"><a class="nav-link" href="/actores">Actores</a></li>
				<li class="nav-item"><a class="nav-link" href="/peliculas">Peliculas</a></li>
				<li class="nav-item"><a class="nav-link" href="/ajax">Ajax</a></li>
			</ul>
			<div class="btn-group btn-group-sm float-right">
				<sec:authorize access="isAnonymous()">
					<a class="nav-link" href="${pageContext.request.contextPath}/mylogin">Log In</a>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<sec:authentication property="principal.username" />
					<a class="nav-link" href="${pageContext.request.contextPath}/logout">Log Out</a>
				</sec:authorize>
				<a href="?lang=es" class="btn btn-primary btn-sm">ES</a>
				<a href="?lang=en" class="btn btn-primary btn-sm">EN</a>
			</div>
		</div>
	</nav>
	<div class="container-fluid">