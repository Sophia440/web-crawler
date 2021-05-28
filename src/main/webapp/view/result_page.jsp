<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Web crawler</title>
    <link rel="stylesheet" href="static/styles.css" />
</head>
<body>
    <h2>Results</h2>
    <c:if test="${not empty errorMessage}">
        <p>${errorMessage}</p>
    </c:if>
    <ol>
        <c:if test="${not empty termsSet}">
            <c:forEach items="${termsSet}" var="term">
                <li>${term}</li>
            </c:forEach>
        </c:if>
    </ol>
</body>
</html>
