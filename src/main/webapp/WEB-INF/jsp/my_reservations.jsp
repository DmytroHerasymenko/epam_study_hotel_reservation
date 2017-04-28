<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<html>
<head>
    <title>My Reservations</title>
</head>
<body>
<p align="center">${requestScope.error}</p>
<div div style="text-align: center">
    <a href="./dates">book a room</a>
</div>
<h1 align="center">My reservations</h1>

<c:forEach var="reservation" items="${requestScope.reservs}">
    <p>
    reservation#${reservation.getReservationId()}
    reservation date: ${reservation.getReservationDate()}.
    Accommodation: ${reservation.getArrivingDate()}
    - ${reservation.getDepartureDate()}. Rooms:
    <c:forEach var="resRoom" items="${reservation.getReservedRoomList()}">
        ${resRoom.getRoomNumber()},
    </c:forEach>

    <br/>
    </p>
</c:forEach>
</body>
</html>
