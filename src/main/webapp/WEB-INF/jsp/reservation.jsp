<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Reservation</title>
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
        <input type="submit" value="confirm reservation" name="confirm" id="confirm">
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
