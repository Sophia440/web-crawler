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
    <c:choose>
        <c:when test="${not empty errorMessage}">
            <p>${errorMessage}</p>
        </c:when>
        <c:otherwise>
            <p>Crawling is finished! List of terms:</p>
            <ol>
                <c:if test="${not empty termsSet}">
                    <c:forEach items="${termsSet}" var="term">
                        <li>${term}</li>
                    </c:forEach>
                </c:if>
            </ol
            <br><br>
            <p>Top 10 hits</p>
            <ol>
                <c:if test="${not empty topHits}">
                    <c:forEach items="${topHits}" var="link">
                        <li>${link.url}, ${link.totalHits} hits</li>
                    </c:forEach>
                </c:if>
            </ol>
            <p>Press the button below to print statistics into a CSV-file</p>
            <form action="${pageContext.request.contextPath}/controller?command=getCsvFiles" method="post">
                <button type="submit" value="submit">Get CSV</button>
            </form>
            <c:if test="${not empty printingMessage}">
                <p>${printingMessage}</p>
            </c:if>
        </c:otherwise>
    </c:choose>

</body>
</html>
