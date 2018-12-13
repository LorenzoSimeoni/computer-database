<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
	<head>
	<title>Computer Database</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link href="static/css/bootstrap.min.css" rel="stylesheet" media="screen">
	<link href="static/css/font-awesome.css" rel="stylesheet" media="screen">
	<link href="static/css/main.css" rel="stylesheet" media="screen">
	</head>
	<body>
		<header class="navbar navbar-inverse navbar-fixed-top">
			<div class="container">
				<a class="navbar-brand" href="/computer-databases/"> Application
					- Computer Database </a>
			</div>
		</header>
	
		<section id="main">
			<div class="container">
				<div class="row">
					<div class="col-xs-8 col-xs-offset-2 box">
						<h1><spring:message code="lang.addComputer" /></h1>
						<form action="addComputer" method="POST">
							<fieldset>
								<div class="form-group">
									<label for="computerName"><spring:message code="lang.computerName" /></label> <input
										type="text" class="form-control required" id="computerName"
										name="computerName" placeholder="Computer name">
								</div>
								<div class="form-group">
									<label for="introduced"><spring:message code="lang.introduced" /></label> <input
										type="date" class="form-control" id="introduced"
										name="introduced" placeholder="Introduced date">
								</div>
								<div class="form-group">
									<label for="discontinued"><spring:message code="lang.discontinued" /></label> <input
										type="date" class="form-control" id="discontinued"
										name="discontinued" placeholder="Discontinued date">
								</div>
								<div class="form-group">
									<label for="companyId"><spring:message code="lang.company" /></label> <select
										class="form-control" id="companyId" name="companyId">
										<option value=""></option>
										<c:forEach items="${listCompany}" var="company">
											<option value="${company.id}">${company.id}</option>
										</c:forEach>
									</select>
								</div>
							</fieldset>
							<div class="actions pull-right">
								<input type="submit" value="<spring:message code="lang.add" />" class="btn btn-primary">
								<spring:message code="lang.or" /> <a href="/computer-databases/" class="btn btn-default">
								<spring:message code="lang.cancel" /></a>
							</div>
						</form>
					</div>
				</div>
			</div>
		</section>
	</body>
	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<div class="btn-group btn-group-sm pull-left" role="group">
					<button type="button" class="btn btn-default"
						onclick="location.replace('?lang=fr')">Français</button>
					<button type="button" class="btn btn-default"
						onclick="location.replace('?lang=en')">English</button>
			</div>
		</div>
	</footer>
<script src="static/js/jquery.min.js"></script>
<script type="text/javascript" charset="utf-8">
$('form').submit(function (event) {
    var name = $.trim($('#computerName').val());
    if (!name) {
        alert('You have to put a name on this computer');
        event.preventDefault(); 
    }
    
    var introduced = $.trim($('#introduced').val());
    var discontinued = $.trim($('#discontinued').val());

	if ((new Date(introduced).getTime()) > (new Date(discontinued).getTime())) {
		alert('The introduced Date must be before the discontinued Date.');
		e.preventDefault();
	} else if (discontinued && !introduced) {
		alert('Cannot create a computer with a discontinued date and without an introduced date');
		e.preventDefault();
	}
});
</script>
</html>