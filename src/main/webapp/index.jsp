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
    <h2>Hello!</h2>
    <p>Press 'Begin' to start crawling</p>

    <form class="login-form" action="${pageContext.request.contextPath}/controller?command=start" method="post">
        <button type="submit" value="submit">Begin</button>
    </form>
</body>
</html>
