<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="static/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
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
			<h1 id="homeTitle">
				<c:out value="${ sizeComputerFound }  Computer found" />
				<spring:message code="lang.greeting" />
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" method="GET" class="form-inline">
						<input type="search" id="searchbox"
							name="search" class="form-control" placeholder="Search name" />
						<input type="submit" id="searchsubmit" value="Filter by name"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="addComputer">Add
						Computer</a> 
					<a class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();">Edit</a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="#" method="POST">
			<input type="hidden" name="selection" value="">

			<div class="container" style="margin-top: 10px;">
				<table class="table table-striped table-bordered">
					<thead>
						<tr>
							<!-- Variable declarations for passing labels as parameters -->
							<!-- Table header for Computer Name -->

							<th class="editMode" style="width: 60px; height: 22px;"><input
								type="checkbox" id="selectall" /> <span
								style="vertical-align: top;"> - <a href="#"
									id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
										class="fa fa-trash"></i>
								</a>
							</span></th>
							<th><c:choose>
									<c:when test="${mode == null}">
										<input type="button"
											onclick="location.replace('?order=name&mode=asc')"
											value="Computer name" />
									</c:when>
									<c:when test="${mode.equals('asc')}">
										<input type="button"
											onclick="location.replace('?order=name&mode=desc')"
											value="Computer name" />
									</c:when>
									<c:otherwise>
										<input type="button"
											onclick="location.replace('?order=name&mode=asc')"
											value="Computer name" />
									</c:otherwise>
								</c:choose></th>
							<th><c:choose>
									<c:when test="${mode == null}">
										<input type="button"
											onclick="location.replace('?order=introduced&mode=asc')"
											value="Introduced date" />
									</c:when>
									<c:when test="${mode.equals('asc')}">
										<input type="button"
											onclick="location.replace('?order=introduced&mode=desc')"
											value="Introduced date" />
									</c:when>
									<c:otherwise>
										<input type="button"
											onclick="location.replace('?order=introduced&mode=asc')"
											value="Introduced date" />
									</c:otherwise>
								</c:choose></th>
							<th><c:choose>
									<c:when test="${mode == null}">
										<input type="button"
											onclick="location.replace('?order=discontinued&mode=asc')"
											value="Discontinued date" />
									</c:when>
									<c:when test="${mode.equals('asc')}">
										<input type="button"
											onclick="location.replace('?order=discontinued&mode=desc')"
											value="Discontinued date" />
									</c:when>
									<c:otherwise>
										<input type="button"
											onclick="location.replace('?order=discontinued&mode=asc')"
											value="Discontinued date" />
									</c:otherwise>
								</c:choose></th>
							<th><c:choose>
									<c:when test="${mode == null}">
										<input type="button"
											onclick="location.replace('?order=company&mode=asc')"
											value="Company" />
									</c:when>
									<c:when test="${mode.equals('asc')}">
										<input type="button"
											onclick="location.replace('?order=company&mode=desc')"
											value="Company" />
									</c:when>
									<c:otherwise>
										<input type="button"
											onclick="location.replace('?order=company&mode=asc')"
											value="Company" />
									</c:otherwise>
								</c:choose></th>
						</tr>
					</thead>
					<!-- Browse attribute computers -->
					<tbody id="results">
						<c:forEach items="${ listComputer }" var="computer">
							<tr>
								<td class="editMode"><input type="checkbox" name="cb"
									class="cb" value="${ computer.id }"></td>
								<td><a href="updateComputer?id=${ computer.id }"><c:out
											value="${ computer.name }" /></a></td>
								<td><c:out value="${ computer.introduced }" /></td>
								<td><c:out value='${ computer.discontinued }' /></td>
								<td><c:out value='${ computer.companyName }' /></td>
							</tr>
						</c:forEach>

					</tbody>
				</table>
			</div>
		</form>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<div class="btn-group btn-group-sm pull-left" role="group">
				<button type="button" class="btn btn-default"
					onclick="location.replace('?lang=fr')">Français</button>
				<button type="button" class="btn btn-default"
					onclick="location.replace('?lang=en')">English</button>
			</div>

			<ul class="pagination">
				<li><a
					href='?numPage=${pagination.pageNumber-1}'
					aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
				</a></li>
				<c:forEach var="i" begin="${pagination.minNumberPage}" end="${pagination.maxNumberPage}" step="1">
					<li><a
						href='?numPage=${i}'>
						<c:out value="${i}" />
					</a></li>
				</c:forEach>
				<li><a
					href='?numPage=${listComputer.size()!=pagination.offset?pagination.pageNumber:pagination.pageNumber+1}'
					aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
			</ul>

			<div class="btn-group btn-group-sm pull-right" role="group">
				<button type="button" class="btn btn-default"
					onclick="location.replace('?nbElement=10')">10</button>
				<button type="button" class="btn btn-default"
					onclick="location.replace('?nbElement=50')">50</button>
				<button type="button" class="btn btn-default"
					onclick="location.replace('?nbElement=100')">100</button>
			</div>
		</div>
	</footer>
	<script src="static/js/jquery.min.js"></script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/dashboard.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#locales").change(function() {
				var selectedOption = $('#locales').val();
				if (selectedOption != '') {
					window.location.replace('?lang=' + selectedOption);
				}
			});
		});
		$(function(){
		    $('.selectpicker').selectpicker();
		});
	</script>
	
</body>
</html>