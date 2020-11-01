<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title}</title>
    <link rel="icon" href="<%=request.getContextPath()%>/resources/static/img/favicon.ico">
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/static/js/jquery.min.js"></script>
    <script src="<%=request.getContextPath()%>/resources/static/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/static/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/static/css/audioplayer.css">
</head>

<body>
<jsp:include page="/resources/templates/navbar.jsp" flush="true"/>
<div class="container">

    <div class="row">


    </div>

</div>

<div class="container">

    <div class="row">
        <t:alert></t:alert>
        <div class="col-12 limited">
            <c:if test="${empty songs}">
                <div class="no-music">
                    <h1>${songemptytext}</h1>
                </div>
            </c:if>
            <c:forEach var="song" items="${songs}">
                <div class="audio-div img-song">
                    <audio src="${song.getUrl()}" name="${song.getSongName()}" id="${song.getId()}"
                           orig="${song.getOriginalUrl()}" own="${song.isOwn()}" csrf="${csrfPreventionSalt}" preload="auto" controls></audio>
                </div>
            </c:forEach>

        </div>
        <div class="col-4">
            <div class="song-text"></div>
        </div>

    </div>

</div>


<jsp:include page="/resources/templates/footer.jsp" flush="true"/>


<script src="<%=request.getContextPath()%>/resources/static/js/audioplayer.js"></script>
<script src="<%=request.getContextPath()%>/resources/static/js/notify.min.js"></script>

<script>$(document).ready(function () {
    $('audio').audioPlayer();

});</script>

</body>

</html>