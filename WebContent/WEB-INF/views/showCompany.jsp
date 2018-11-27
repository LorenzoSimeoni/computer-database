<!DOCTYPE html>

<html>
    <head>
        <meta charset="utf-8" />
        <title>Test</title>
    </head>
    <body>
 		<c:forEach items="${ listCompany }" var="company">
    		<p><c:out value="${ company.id }" /> <c:out value="${ company.name }" /></p>
		</c:forEach>
    </body>
</html>