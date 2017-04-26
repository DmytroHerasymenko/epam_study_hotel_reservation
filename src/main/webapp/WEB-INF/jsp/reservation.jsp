<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Reservation</title>
    <%--<script>
        function getReservedRooms() {
            var reservedRoomTypes = ${requestScope.freeRooms};
            for(var key in reservedRoomTypes) {
                reservedRoomTypes.set(document.getElementById(key).value)
            }
            var json = JSON.stringify(reservedRoomTypes);
            var request = new XMLHttpRequest();
            request.open("POST", "http://localhost:8081/reservation_handler");
            request.setRequestHeader('Content-type', 'application/json; charset=utf-8');
            request.onreadystatechange = function () {
                if (request.readyState == 4 && request.status == 200)
                    document.getElementById("confirm").innerHTML=request.responseText;
            }
            request.send(json);
        }
        document.getElementById("confirm").onclick = getReservedRooms();
    </script>--%>
</head>
<body>
<form action="./reservation_handler" method="post">
    <h1 align="center">Java-hotel &#9733;&#9733;&#9733;&#9733;&#9733;</h1>
<p align="center">
    accommodation ${sessionScope.arrive} - ${sessionScope.departure} :
</p>

<p align="center">${requestScope.error}</p>

    <p align="center">
<c:forEach var="freeRoom" items="${requestScope.freeRooms}">
                ${freeRoom.key.roomCategory}-${freeRoom.key.bedspace} - ${freeRoom.key.price}$/night. Select rooms:
                <input type="number" min="0" max="${freeRoom.value}"
                       name="${freeRoom.key.roomCategory}_${freeRoom.key.bedspace}" pattern="[0-9]+" value="0" required>
                <br/>
    </c:forEach>
    </p>
    <div div style="text-align: center">
        <input type="submit" value="confirm reservation" id="confirm">
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
