<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Web crawler</title>
  <link rel="stylesheet" href="static/styles.css" />
</head>
<body>
  <h2>Enter the data for crawling</h2>
  <p>Note: Please, separate terms with commas (,)</p>
  <p>If you want to use default values for max depth and max visited pages, then leave those fields empty.</p>
  <form action="${pageContext.request.contextPath}/controller?command=crawl" method="post">
    <label for="url">Url:  </label>
    <input type="url" id="url" name="url" placeholder="https://example">
    <br><br>
    <label for="terms">Terms:  </label>
    <textarea id="terms" name="terms">Term 1, Term 2</textarea>
    <br><br>
    <label for="maxDepth">Max depth:  </label>
    <input type="number" id="maxDepth" name="maxDepth">
    <br><br>
    <label for="maxPages">Max visited pages:  </label>
    <input type="number" id="maxPages" name="maxPages">
    <br><br>
    <button type="submit" value="submit">Start</button>
  </form>
</body>
</html>
