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
<link href="ressources/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="ressources/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="ressources/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="/computer-databases/"> Application -
				Computer Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">
				<c:out value="${ sizeComputerFound }  Computer found" />
				<spring:message code="greeting" text="default"/>
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" method="GET" class="form-inline">
						<input type="hidden" value="${nbElement}" id="nbElement"
							name="nbElement" /> <input type="hidden" value="${numPage}"
							id="page" name="page" /> <input type="search" id="searchbox"
							name="search" class="form-control" placeholder="Search name" />
						<input type="submit" id="searchsubmit" value="Filter by name"
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
			<div class="hide">
			
			<c:set var="url" value="?" scope="page" />
			<c:set var="nbElementValue" value="nbElement=" scope="page" />
			<c:set var="pageValue" value="&page=" scope="page" />
			<c:set var="searchValue" value="&search=" scope="page" />
			<c:set var="modeValue" value="&mode=" scope="page" />
			<c:set var="orderValue" value="&order=" scope="page" />
			<c:if test="${ nbElement != null }">
								${url = url.concat(nbElementValue)};
							    ${url = url.concat(nbElement)};
							</c:if>
			<c:if test="${ numPage != null }">
								${url = url.concat(pageValue)};
							    ${url = url.concat(numPage)};
							</c:if>
			<c:if test="${ search != null }">
								${url = url.concat(searchValue)};
							    ${url = url.concat(search)};
							</c:if>
			</div>

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
											onclick="location.replace('${url}'+'&order=name&mode=asc')"
											value="Computer name" />
									</c:when>
									<c:when test="${mode.equals('asc')}">
										<input type="button"
											onclick="location.replace('${url}'+'&order=name&mode=desc')"
											value="Computer name" />
									</c:when>
									<c:otherwise>
										<input type="button"
											onclick="location.replace('${url}'+'&order=name&mode=asc')"
											value="Computer name" />
									</c:otherwise>
								</c:choose></th>
							<th><c:choose>
									<c:when test="${mode == null}">
										<input type="button"
											onclick="location.replace('${url}'+'&order=introduced&mode=asc')"
											value="Introduced date" />
									</c:when>
									<c:when test="${mode.equals('asc')}">
										<input type="button"
											onclick="location.replace('${url}'+'&order=introduced&mode=desc')"
											value="Introduced date" />
									</c:when>
									<c:otherwise>
										<input type="button"
											onclick="location.replace('${url}'+'&order=introduced&mode=asc')"
											value="Introduced date" />
									</c:otherwise>
								</c:choose></th>
							<th><c:choose>
									<c:when test="${mode == null}">
										<input type="button"
											onclick="location.replace('${url}'+'&order=discontinued&mode=asc')"
											value="Discontinued date" />
									</c:when>
									<c:when test="${mode.equals('asc')}">
										<input type="button"
											onclick="location.replace('${url}'+'&order=discontinued&mode=desc')"
											value="Discontinued date" />
									</c:when>
									<c:otherwise>
										<input type="button"
											onclick="location.replace('${url}'+'&order=discontinued&mode=asc')"
											value="Discontinued date" />
									</c:otherwise>
								</c:choose></th>
							<th><c:choose>
									<c:when test="${mode == null}">
										<input type="button"
											onclick="location.replace('${url}'+'&order=company&mode=asc')"
											value="Company" />
									</c:when>
									<c:when test="${mode.equals('asc')}">
										<input type="button"
											onclick="location.replace('${url}'+'&order=company&mode=desc')"
											value="Company" />
									</c:when>
									<c:otherwise>
										<input type="button"
											onclick="location.replace('${url}'+'&order=company&mode=asc')"
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
			<ul class="pagination">
				<li><a
					href='?nbElement=${param["nbElement"]!=null?param["nbElement"] : "10"}&page=${param["page"]!=null?param["page"]-1>0?param["page"]-1:param["page"]:"1"}
					<c:choose>
						<c:when test="${search == null}"></c:when>
						<c:otherwise>&search=<c:out value="${search}"/></c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${order == null}"></c:when>
						<c:otherwise>&order=<c:out value="${order}"/></c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${mode == null}"></c:when>
						<c:otherwise>&mode=<c:out value="${mode}"/></c:otherwise>
					</c:choose>'
					aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
				</a></li>
				<c:forEach var="i" begin="${minListPage}" end="${maxListPage}"
					step="1">
					<li><a
						href='?nbElement=${param["nbElement"]!=null?param["nbElement"] : "10"}&page=${i}
					<c:choose>
						<c:when test="${search == null}"></c:when>
						<c:otherwise>&search=<c:out value="${search}"/></c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${order == null}"></c:when>
						<c:otherwise>&order=<c:out value="${order}"/></c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${mode == null}"></c:when>
						<c:otherwise>&mode=<c:out value="${mode}"/></c:otherwise>
					</c:choose>'>
							<c:out value="${i}" />
					</a></li>
				</c:forEach>
				<li><a
					href='?nbElement=${param["nbElement"]!=null?param["nbElement"] : "10"}&page=${param["page"]!=null?nbElementShown!=param["nbElement"]?param["page"]:param["page"]+1:"2"}
					<c:choose>
						<c:when test="${search == null}"></c:when>
						<c:otherwise>&search=<c:out value="${search}"/></c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${order == null}"></c:when>
						<c:otherwise>&order=<c:out value="${order}"/></c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${mode == null}"></c:when>
						<c:otherwise>&mode=<c:out value="${mode}"/></c:otherwise>
					</c:choose>'
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
	<script src="ressources/js/jquery.min.js"></script>
	<script src="ressources/js/bootstrap.min.js"></script>
	<script src="ressources/js/dashboard.js"></script>
</body>
</html>