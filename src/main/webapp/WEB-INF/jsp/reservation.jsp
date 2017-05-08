<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />

<fmt:setLocale value="${language}" />
<fmt:setBundle basename="messages"/>
<html lang="${language}">
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="/resource/js/main.js" defer></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <link href="/resource/css/main.css" rel="stylesheet" type="text/css">
    <title><fmt:message key="java.hotel"/></title>
    <script>
        confirm.onclick = function () {
            var request = new XMLHttpRequest();
            var reservedRoomTypes = {};
            <c:forEach var="roomType" items="${requestScope.freeRoomTypes}">
            reservedRoomTypes[${roomType.key.roomTypeId}] = document.getElementById(${roomType.key}).value;
            </c:forEach>
            request.open("POST", "http://localhost:8081/reservation_handler");
            request.setRequestHeader('Content-type', 'application/json; charset=utf-8');
            /*request.onreadystatechange = function () {
                if (request.readyState == 4 && request.status == 200)
                    document.getElementById("confirm").innerHTML=request.responseText;
            }*/
            var json = {};
            json["reservedRoomTypes"] = reservedRoomTypes;
            request.send(JSON.stringify(json));
        }
    </script>
</head>
<body>
<form>
    <select id="language" name="language" onchange="submit()">
        <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
        <option value="ru" ${language == 'ru' ? 'selected' : ''}>Русский</option>
    </select>
</form>
<form action="./reservation_handler" method="post">
    <h1 align="center">Java-hotel &#9733;&#9733;&#9733;&#9733;&#9733;</h1>
<p align="center">
    accommodation ${sessionScope.reservation.arrivingDate} - ${sessionScope.reservation.departureDate} :
</p>

<p align="center">${requestScope.error}</p>

    <p align="center">
<c:forEach var="roomType" items="${requestScope.freeRoomTypes}">
                ${roomType.key.roomCategory}-${roomType.key.bedspace} - ${roomType.key.price}$/night. Select rooms:
    <input type="number" min="0" max="${roomType.value}" name="${roomType.key.roomTypeId}"
           id="${roomType.key.roomTypeId}" pattern="[0-9]+" value="0" required>
                <br/>
    </c:forEach>
    </p>

    <div div style="text-align: center">
        <input type="submit" value="continue" name="confirm" id="confirm">
    </div>
    <div div style="text-align: center">
        <a href="./dates">choose another dates</a>
    </div>
    <div div style="text-align: center">
        <a href="./my_reservations">my reservations</a>
    </div>
</form>
</body>
</html>
