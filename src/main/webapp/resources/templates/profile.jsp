<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title}</title>
    <link rel="icon" href="<%=request.getContextPath()%>/resources/static/img/favicon.ico">
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/static/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/static/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/static/css/style.css">

</head>

<body>
<jsp:include page="/resources/templates/navbar.jsp" flush="true"/>

<div class="container">
    <div class="row">
        <t:alert></t:alert>
        <t:form>
            <form method="POST" name="profileform">
                <div class="form-group">
                    <label for="newname">Новое имя</label>
                    <input type="text" class="form-control form-control-40" name="newname"
                           placeholder="${user.getName()}"
                           id="newname">
                </div>
                <div class="form-group">
                    <label for="passold">Старый пароль</label>
                    <input type="password" class="form-control" name="passwordold" id="passold"
                           placeholder="********">
                </div>
                <div class="form-group">
                    <label class="control-pwd" for="passnew">Новый пароль</label>
                    <input type="password" class="form-control form-control-40" name="passwordnew"
                           placeholder="********"
                           id="passnew">
                </div>
                <div class="form-group">
                    <label for="songcount">Количество песен в поиске</label>
                    <input type="text" class="form-control form-control-40" name="songcount"
                           placeholder="${user.getSongCount()}"
                           id="songcount">
                </div>

                <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>

                <button type="submit" id="changeprofile" class="btn btn-default login-submit">Сохранить</button>
            </form>
        </t:form>
        <t:form>
            <form method="POST" action="profile">
                <div class="form-group" style="margin-top: 40px">
                    <label for="passdelete">Введите пароль для удаления аккаунта</label>
                    <input type="password" class="form-control" name="passworddelete" id="passdelete"
                           placeholder="********">
                </div>
                <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>

                <button type="submit" id="deleteprofile" class="btn btn-right btn-danger login-submit">Удалить аккаунт</button>
            </form>
        </t:form>
    </div>
</div>

<jsp:include page="/resources/templates/footer.jsp" flush="true"/>

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/static/js/profileSubmitPreventer.js"></script>

</body>

</html>