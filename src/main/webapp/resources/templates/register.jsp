<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
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

<div class="container">
    <div class="row">
        <t:alert></t:alert>
        <t:form>
            <form method="POST" name="registerform">
                <div class="form-group">
                    <label for="exampleInputEmail1">Имя</label>
                    <input type="text" class="form-control" name="name" id="exampleName1" placeholder="Иван" value="${empty personForm ? "Иван" : personForm.getName()}" aria-describedby="textHelp">
                </div>
                <div class="form-group">
                    <label for="exampleInputEmail1">Почта</label>
                    <input type="email" class="form-control" name="email" id="exampleInputEmail1"
                    <c:if test="${not empty personForm}">
                            value="${personForm.getEmail()}"
                           </c:if>
                           placeholder="example@email.com" aria-describedby="emailHelp">
                </div>
                <div class="form-group">
                    <label class="control-pwd" for="exampleInputPassword1">Пароль</label>
                    <input type="password" class="form-control form-control-40" name="pwd1" placeholder="********" id="exampleInputPassword1">
                    <input type="password" class="form-control form-control-40 down" name="pwd2" placeholder="********" id="exampleInputPassword2">
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" name="agree" value="on" id="defaultCheck1">
                    <label class="form-check-label" for="defaultCheck1">
                        Я согласен с правилами сайта
                    </label>
                </div>
                <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
                <button type="submit" class="btn btn-primary login-submit" id="register">Зарегистрироваться</button>

            </form>
        </t:form>
    </div>
</div>

<jsp:include page="/resources/templates/footer.jsp" flush="true"/>
<script src="<%=request.getContextPath()%>/resources/static/js/submitPreventer.js"></script>

</body>

</html>