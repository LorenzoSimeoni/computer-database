<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="showComputer"> Application -
				Computer Database </a>
			<p>
				<c:out value="${ pageContext.request.contextPath }" />
			</p>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">
				<c:out value="${ sizeComputerFound } Computer found" />
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" method="GET" class="form-inline">
						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="Search name" /> <input
							type="submit" id="searchsubmit" value="Filter by name"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="addComputer">Add
						Computer</a> <a class="btn btn-default" id="editComputer" href="#"
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
							style="vertical-align: top;"> - 
							<a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash"></i>
							</a>
						</span></th>
						<th>Computer name</th>
						<th>Introduced date</th>
						<!-- Table header for Discontinued Date -->
						<th>Discontinued date</th>
						<!-- Table header for Company -->
						<th>Company</th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach items="${ listComputer }" var="computer">
						<tr>
							<td class="editMode">
								<input type="checkbox" name="cb" class="cb" value="${ computer.id }">
							</td>
							<td><a href="updateComputer?id=${ computer.id }"><c:out value="${ computer.name }" /></a></td>
							<td><c:out value="${ computer.introduced }" /></td>
							<td><c:out value='${ computer.discontinued }' /></td>
							<td><c:out value='${ computer.company.id == 0?"":computer.company.id }' /></td>
						</tr>
					</c:forEach>

				</tbody>
			</table>
		</div>
		</form>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<ul class="pagination">
				<li><a
					href='?nbElement=${param["nbElement"]!=null?param["nbElement"] : "10"}&page=${param["page"]!=null?param["page"]-1>0?param["page"]-1:param["page"]:"1"}'
					aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
				</a></li>
				<c:forEach var="i" begin="${minListPage}" end="${maxListPage}"
					step="1">
					<li><a
						href='?nbElement=${param["nbElement"]!=null?param["nbElement"] : "10"}&page=${i}'><c:out
								value="${i}" /></a></li>
				</c:forEach>
				<li><a
					href='?nbElement=${param["nbElement"]!=null?param["nbElement"] : "10"}&page=${param["page"]!=null?sizeComputerFound!=param["nbElement"]?param["page"]:param["page"]+1:"2"}'
					aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
			</ul>

			<div class="btn-group btn-group-sm pull-right" role="group">
				<button type="button" class="btn btn-default"
					onclick="location.replace('showComputer?nbElement=10')">10</button>
				<button type="button" class="btn btn-default"
					onclick="location.replace('showComputer?nbElement=50')">50</button>
				<button type="button" class="btn btn-default"
					onclick="location.replace('showComputer?nbElement=100')">100</button>
			</div>
		</div>
	</footer>
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/dashboard.js"></script>

</body>
</html>