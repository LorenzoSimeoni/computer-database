<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="ressources/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="ressources/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="ressources/css/main.css" rel="stylesheet" media="screen">
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
					<h1>Add Computer</h1>
					<form action="addComputer" method="POST">
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label> <input
									type="text" class="form-control required" id="computerName"
									name="computerName" placeholder="Computer name">
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date</label> <input
									type="date" class="form-control" id="introduced"
									name="introduced" placeholder="Introduced date">
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date</label> <input
									type="date" class="form-control" id="discontinued"
									name="discontinued" placeholder="Discontinued date">
							</div>
							<div class="form-group">
								<label for="companyId">Company</label> <select
									class="form-control" id="companyId" name="companyId">
									<option value=""></option>
									<c:forEach items="${listCompany}" var="company">
										<option value="${company.id}">${company.id}</option>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Add" class="btn btn-primary">
							or <a href="/computer-databases/" class="btn btn-default">Cancel</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
</body>
<script src="ressources/js/jquery.min.js"></script>
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