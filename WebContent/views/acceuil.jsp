<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<html>
    <head>
        <meta charset="utf-8" />
        <title>Test</title>
    </head>
    <body>
    	
		<%@ include file="menu.jsp" %>
		
		<p><c:out value="${ pageContext.request.contextPath }" /></p>
    </body>
</html>