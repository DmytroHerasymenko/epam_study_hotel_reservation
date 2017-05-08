<%@ page contentType="text/html;charset=UTF-8"%>
<%--<%request.setCharacterEncoding("UTF-8");%>--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />

<fmt:setLocale value="${language}" />
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
<form action="./registration_handler" method="post">
    <h1 class="text-center">
        <fmt:message key="registr.registr_form"/>
    </h1>

    <p class="text-center">${requestScope.error}</p>

    <label for="name">
        <p>
            <fmt:message key="registr.name"/>
        <input type="text" name="name" id="name"
               pattern="^[A-Z]{1}[A-Za-z\s-]{2,29}|[\u0410-\u042f\u0401]{1}[\u0410-\u044f\u0401\u0451\s-]{2,29}$" required>
        </p>

        <div>
        <ul class="input-requirements">
             <li><fmt:message key="registr.name_length"/></li>
            <li><fmt:message key="registr.name_contain"/></li>
        </ul>
        </div>
    </label>

    <label for="login">
        <p>
            <fmt:message key="registr.login"/>
        <input type="email" name="login" id="login" pattern="^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" required>
        </p>

        <div>
        <ul class="input-requirements">
            <li><fmt:message key="registr.login_contain"/></li>
        </ul>
        </div>
    </label>

    <label for="password">
        <p>
            <fmt:message key="registr.password"/>
        <input type="password" name="password" id="password" maxlength="20" required>
        </p>

        <div>
        <ul class="input-requirements">
            <li><fmt:message key="registr.password_length"/></li>
            <li><fmt:message key="registr.password_number"/></li>
            <li><fmt:message key="registr.password_lowercase"/></li>
            <li><fmt:message key="registr.password_uppercase"/></li>
            <li><fmt:message key="registr.password_special_char"/></li>
        </ul>
        </div>
    </label>

    <br/>

    <div>
        <input type="submit" value="<fmt:message key="registr.confirm"/>">
    </div>

    <p/>

    <div>
        <a href="./sign_in"><fmt:message key="registr.sign_in"/></a>
    </div>

</form>
</body>
</html>