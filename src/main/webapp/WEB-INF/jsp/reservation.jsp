<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Reservation</title>
</head>
<body>
<form action="./reservation_handler" method="post">
    <h1 align="center">Java-hotel &#9733;&#9733;&#9733;&#9733;&#9733;</h1>
<p align="center">
    accommodation <%=session.getAttribute("arrive")%> - <%=session.getAttribute("departure")%> :
</p>

<p align="center">${requestScope.error}</p>

    <p align="center">
<c:forEach var="freeRoom" items="${requestScope.freeRooms}">
        <c:forEach var="roomType" items="${requestScope.roomTypes}">
            <c:if test="${freeRoom.key.getRoomTypeId() == roomType.getRoomTypeId()}">
                ${roomType.roomCategory}-${roomType.getBedspace()} -
                ${roomType.getPrice()}$/night. Select rooms:
                <input type="hidden" name="${roomType.getRoomCategory()}_${roomType.getBedspace()}_KEY" value="${roomType}" />
                <input type="number" min="0" max="${freeRoom.value}"
                       name="${roomType.getRoomCategory()}_${roomType.getBedspace()}" pattern="[0-9]+" value="0" required>
                <br/>
            </c:if>
        </c:forEach>
    </c:forEach>
    </p>
    <div div style="text-align: center">
        <input type="submit" value="confirm reservation">
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
