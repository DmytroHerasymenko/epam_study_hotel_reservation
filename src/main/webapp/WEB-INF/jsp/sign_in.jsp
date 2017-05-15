<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<!DOCTYPE html>
<html lang="${language}">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <style type="text/css" rel="stylesheet">
        <%@include file="/css/styles.css"%>
    </style>
    <title><fmt:message key="java.hotel"/></title>
</head>
<body>
<form style="float: right">
    <select id="language" name="language" onchange="submit()">
        <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
        <option value="ru" ${language == 'ru' ? 'selected' : ''}>Русский</option>
    </select>
</form>
<form action="./sign_in_handler" method="post">
    <div id="container">
        <div id="header">
            <h1><fmt:message key="java.hotel"/></h1>
            <h2><fmt:message key="header.like_home"/></h2>
            <ul class="menu">
                <li class="btn_1"><a href="./index"><fmt:message key="header.home"/></a></li>
                <li class="line"></li>
                <li class="btn_2"><a href="./dates"><fmt:message key="header.dates"/></a></li>
                <li class="line"></li>
                <li class="btn_3"><a href="./my_reservations"><fmt:message key="header.my_reservs"/></a></li>
                <li class="line"></li>
                <li class="btn_4"><a href="./contacts"><fmt:message key="header.contacts"/></a></li>
            </ul>
        </div>
        <div id="content">
            <div id="panel">
                <div id="welcome">
                    <h2><fmt:message key="registr.sign_in"/></h2>
                    <p id="side">
                        <span id="headline1" class="headline">
                            <fmt:message key="sign_in.input"/>
                        </span>
                        <br>
                        <span id="error" class="headline">
                            ${requestScope.error}
                        </span>
                        <br>
                        <label for="login">
                            <span id="headline2" class="headline">
                                <fmt:message key="registr.login"/>
                                <input type="email" name="login" id="login"
                                       pattern="^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" required>
                            </span>
                        </label>
                        <br>
                        <label for="password">
                            <span id="headline3" class="headline">
                                <fmt:message key="registr.password"/>
                                <input type="password" name="password" id="password" maxlength="20" required>
                            </span>
                        </label>
                        <input type="submit" value="<fmt:message key="registr.confirm"/>">
                        <br>
                        <a href="./registration"><fmt:message key="sign_in.registr"/></a>
                    </p>
                    <div class="clear" id="welClear"></div>
                </div>
            </div>
            <div class="clear"></div>
        </div>
        <div id="footer">
            <p><a href="./index"><fmt:message key="header.home"/></a> |
                <a href="./about"><fmt:message key="header.about"/></a> |
                <a href="./news"><fmt:message key="footer.news"/></a> |
                <a href="./contacts"><fmt:message key="header.contacts"/></a><br/>
        </div>
    </div>
</form>
</body>
</html>