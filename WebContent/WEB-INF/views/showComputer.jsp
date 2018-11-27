<!DOCTYPE html>

<html>
    <head>
        <meta charset="utf-8" />
        <title>Test</title>
    </head>
    <body>
 		<c:forEach items="${ listComputer }" var="computer">
    		<p><c:out value="${ computer.id }" /> <c:out value="${ computer.name }" /> <c:out value="${ computer.introduced }" /> 
    		<c:out value="${ computer.discontinued }" />
    		<c:out value="${ computer.company.id }" /> </p>
		</c:forEach>
    </body>
</html>