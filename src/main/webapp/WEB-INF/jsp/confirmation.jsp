<%@ page import="ua.study.domain.User" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Confirmation</title>
</head>
<body>
<h1 align="center">Java-hotel &#9733;&#9733;&#9733;&#9733;&#9733;</h1>
Payment was successful!
Your reservation:
<table align="center" id="bill" border="1">
    <tr>
        <th>billing date: </th>
        <td><%=java.time.LocalDateTime.now().format(DateTimeFormatter
                .ofPattern("dd.MM.yyyy HH:mm:ss"))%></td>
    </tr>
    <tr>
        <th>name: </th>
        <td><%=((User) session.getAttribute("client")).getName()%></td>
    </tr>
    <tr>
        <th>arriving date: </th>
        <td>${requestScope.arrive}</td>
    </tr>
    <tr>
        <th>departure date: </th>
        <td>${requestScope.departure}</td>
    </tr>
    <tr>
        <th>rooms: </th>
        <td><c:forEach var="reservedRoom" items="${requestScope.reservedRooms}">
            <c:forEach var="roomType" items="${requestScope.roomTypes}">
                <c:if test="${reservedRoom.getRoomTypeId() == roomType.getRoomTypeId()}">
                    ${reservedRoom.getRoomNumber()}(${roomType.getRoomCategory()}, ${roomType.getBedspace()})
                    <br/>
                </c:if>
            </c:forEach>
        </c:forEach> </td>
    </tr>
    <tr>
        <th>total price: </th>
        <td><%=session.getAttribute("totalPrice")%>$</td>
    </tr>
</table>
<%=session.removeAttribute("totalPrice")%>
<%=session.removeAttribute("reservationId")%>
<%=session.removeAttribute("arrive")%>
<%=session.removeAttribute("departure")%>


<div div style="text-align: center">
    <a href="./my_reservations">my reservations</a>
</div>

<div div style="text-align: center">
    <a href="./dates">book a room</a>
</div>


</body>
</html>
