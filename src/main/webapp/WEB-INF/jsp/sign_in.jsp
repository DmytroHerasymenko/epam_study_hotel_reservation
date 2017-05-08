<%@ page contentType="text/html;charset=UTF-8"%>
<%--<%request.setCharacterEncoding("UTF-8");%>--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html lang="${language}">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
    <%--<script type="text/javascript" src="/resource/js/main.js" defer></script>--%>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <%--<link href="/resource/css/main.css" rel="stylesheet" type="text/css">--%>
    <title><fmt:message key="java.hotel"/></title>
</head>
<body>
<form>
    <select id="language" name="language" onchange="submit()">
        <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
        <option value="ru" ${language == 'ru' ? 'selected' : ''}>Русский</option>
    </select>
</form>
<form action="./sign_in_handler" method="post">
    <h1 class="text-center">
        <fmt:message key="registr.sign_in"/>
    </h1>

    <label >
        <div style="text-align: center">
        <ul class="input-requirements">
            <li><fmt:message key="sign_in.input"/></li>
        </ul>
        </div>
    </label>

    <p align="center">${requestScope.error}</p>

    <label for="login">
        <p align="center">
            <fmt:message key="registr.login"/>
            <input type="email" name="login" id="login" pattern="^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" required>
        </p>
    </label>

    <label for="password">
        <p align="center">
            <fmt:message key="registr.password"/>
            <input type="password" name="password" id="password" maxlength="20" required>
        </p>
    </label>

    <br/>

    <div div style="text-align: center">
    <input type="submit" value="<fmt:message key="registr.confirm"/>">
    </div>

    <p/>

    <div div style="text-align: center">
        <a href="./registration"><fmt:message key="sign_in.registr"/></a>
    </div>

</form>
</body>
</html>