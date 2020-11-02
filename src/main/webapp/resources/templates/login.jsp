<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="icon" href="<%=request.getContextPath()%>/resources/static/img/favicon.ico">
    <title>${title}</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/static/js/jquery.min.js"></script>
    <script src="<%=request.getContextPath()%>/resources/static/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/static/css/style.css">
</head>

<body>
<jsp:include page="/resources/templates/navbar.jsp" flush="true"/>

<div class="container">
    <div class="row">
        <t:alert></t:alert>
        <t:form>
            <form method="POST">
                <div class="form-group">
                    <label for="exampleInputEmail1">Почта</label>
                    <input type="email" class="form-control" name="email" id="exampleInputEmail1"
                           placeholder="example@email.com" aria-describedby="emailHelp">
                </div>
                <div class="form-group">
                    <label class="control-pwd" for="exampleInputPassword1">Пароль</label>
                    <input type="password" class="form-control form-control-40" name="password" placeholder="********"
                           id="exampleInputPassword1">
                </div>
                <button type="submit" class="btn btn-default login-submit">Войти</button>
                <a href="register" class="btn btn-default login-submit left">Зарегистрироваться</a>

                <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
            </form>
        </t:form>
    </div>
</div>

<jsp:include page="/resources/templates/footer.jsp" flush="true"/>


</body>

</html>