<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<html>
<head>
    <title>Confirmation</title>
</head>
<body>
<h1 align="center">Java-hotel &#9733;&#9733;&#9733;&#9733;&#9733;</h1>
<p align="center">${requestScope.error}</p>
<div div style="text-align: center">
    Payment was successful! Your reservation:
</div>
<table align="center" id="bill" border="1">
    <tr>
        <th>reservation# </th>
        <td>${requestScope.reservation.reservationId}</td>
    </tr>
    <tr>
        <th>name: </th>
        <td>${sessionScope.client.name}</td>
    </tr>
    <tr>
        <th>arriving date: </th>
        <td>${requestScope.reservation.arrivingDate}</td>
    </tr>
    <tr>
        <th>departure date: </th>
        <td>${requestScope.reservation.departureDate}</td>
    </tr>
    <tr>
        <th>rooms: </th>
        <td><c:forEach var="reservedRoom" items="${requestScope.reservation.reservedRooms}">
            ${reservedRoom.roomNumber}(${reservedRoom.roomCategory}, ${reservedRoom.bedspace})
        </c:forEach> </td>
    </tr>
    <tr>
        <th>total price: </th>
        <td>${requestScope.totalPrice}$</td>
    </tr>
</table>

<div div style="text-align: center">
    <a href="./my_reservations">my reservations</a>
</div>

<div div style="text-align: center">
    <a href="./dates">book a room</a>
</div>


</body>
</html>
