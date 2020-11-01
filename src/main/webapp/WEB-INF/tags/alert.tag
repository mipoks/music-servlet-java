<%@tag description="Default Layout Tag" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div class="alert alert-${empty info ? "light" : info.getColor()}" role="alert">
    <h4 class="alert-heading">${empty info ? "Мы ищем спонсоров!" : info.getHead()}</h4>
    <p><c:if test="${empty info}"> Поддержи разработку сайта. В наших планах добавить поддержку музыки Вконтакте, Яндекс музыки</c:if>
        <c:if test="${not empty info}">${info.getBody()}</c:if>
    </p>
    <hr>
    <p class="mb-0">Наши реквизиты: Сбербанк 490123817471294, Яндекс 5023846217381</p>
</div>