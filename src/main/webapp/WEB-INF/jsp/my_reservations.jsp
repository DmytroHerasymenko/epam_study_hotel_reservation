<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />

<fmt:setLocale value="${language}" />
<fmt:setBundle basename="messages"/>
<!DOCTYPE html>
<html lang="${language}">
<head>
    <%--<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>--%>
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
<form action="./bill_handler" method="post">
    <div id="container">
        <div id="header">
            <h1><fmt:message key="java.hotel"/></h1>
            <h2><fmt:message key="header.like_home"/></h2>
            <ul class="menu">
                <li class="btn_1"><a href="<c:url value="/index.jsp"/>"><fmt:message key="header.home"/></a></li>
                <li class="line"></li>
                <li class="btn_2"><a href="#"><fmt:message key="header.about"/></a></li>
                <li class="line"></li>
                <li class="btn_3"><a href="./dates"><fmt:message key="header.dates"/></a></li>
                <li class="line"></li>
                <li class="btn_5"><a href="#"><fmt:message key="header.contacts"/></a></li>
            </ul>
        </div>
        <div id="content">
            <div id="panel">
                <div id="welcome">
                    <h2><fmt:message key="myreservs.my_reservs"/></h2>
                    <p id="side">
                        <br>
                        <span id="headline1" class="headline">
                            <c:forEach var="reservation" items="${requestScope.reservations}">
                                <fmt:message key="myreservs.reservation"/>#${reservation.reservationId}
                                <fmt:message key="myreservs.reserv_date"/>: ${reservation.reservationDate}.
                                <br>
                                <fmt:message key="reserv.accomodation"/> ${reservation.arrivingDate}
                                - ${reservation.departureDate}. <fmt:message key="confirm.rooms"/>:
                                <c:forEach var="resRoom" items="${reservation.reservedRooms}">
                                ${resRoom.roomNumber},
                                </c:forEach>
                                <br>
                                -----------------------------------------------------------------------------------
                                <br>
                            </c:forEach>
                        </span>
                    </p>
                    <div class="clear" id="welClear"></div>
                </div>
            </div>
            <div class="clear"></div>
        </div>
        <div id="footer">
            <p><a href="<c:url value="/index.jsp"/>"><fmt:message key="header.home"/></a> |
                <a href="#"><fmt:message key="header.about"/></a> |
                <a href="#"><fmt:message key="footer.news"/></a> |
                <a href="#"><fmt:message key="header.contacts"/></a><br/>
        </div>
    </div>
</form>
</body>
</html>