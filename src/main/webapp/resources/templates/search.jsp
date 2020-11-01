<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title}</title>
    <link rel="icon" href="<%=request.getContextPath()%>/resources/static/img/favicon.ico">
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/static/js/jquery.min.js"></script>
    <script src="<%=request.getContextPath()%>/resources/static/js/bootstrap.min.js"></script>
    <link rel="stylesheet"  href="<%=request.getContextPath()%>/resources/static/css/bootstrap.min.css">
    <link rel="stylesheet"  href="<%=request.getContextPath()%>/resources/static/css/style.css">
</head>

<body>
<jsp:include page="/resources/templates/navbar.jsp" flush="true"/>


<div class="container-important">
    <h1 class="text-important">Найду любую музыку</h1>
    <form method="GET" action="<%=request.getContextPath()%>/search">
        <input class="form-control form-control-3 in-big-block search-form" name="search" id="main-search" type="text"
               placeholder="Например, ${empty somemusic ? "Feduk останься" : somemusic}">
    </form>

</div>

<jsp:include page="/resources/templates/footer.jsp" flush="true"/>


</body>

</html>