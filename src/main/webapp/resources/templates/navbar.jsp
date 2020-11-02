<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
    <meta charset="UTF-8"/>
</head>
<body>
<div fragment="navbar">
    <nav class="navbar navbar-expand-lg navbar-light nav-color">
        <a class="navbar-brand" href="<%=request.getContextPath()%>/search">Главная</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon my-menu-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="<%=request.getContextPath()%>/me">Моя музыка <span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<%=request.getContextPath()%>/actual">Рекомендации</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link disabled" href="<%=request.getContextPath()%>/login">
                        Привет, ${empty user ? "Слушатель" : user.getName()}
                    </a>
                </li>
                <c:if test="${not empty user}">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Аккаунт
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item" href="<%=request.getContextPath()%>/profile">Настройки</a>
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item" href="<%=request.getContextPath()%>/logout">Выйти</a>
                        </div>
                    </li>
                </c:if>
                <c:if test="${empty user}">
                    <li class="nav-item">
                        <a class="nav-link" href="<%=request.getContextPath()%>/login">Войти</a>
                    </li>
                </c:if>
            </ul>
            <form method="GET" action="<%=request.getContextPath()%>/search" class="form-inline my-2 my-lg-0">
                <input class="form-control form-control-2 mr-sm-2" name="search" type="search" placeholder="Слушать"
                       aria-label="Search">
                <button class="btn btn-default my-2 my-sm-0" type="submit">Найти</button>
            </form>
        </div>
    </nav>
</div>

</body>

</html>