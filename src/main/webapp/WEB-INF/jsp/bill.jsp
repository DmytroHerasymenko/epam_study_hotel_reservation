<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="ua.study.domain.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>bill</title>
</head>
<body>
<form action="./bill_handler" method="post">
<h1 align="center">Java-hotel &#9733;&#9733;&#9733;&#9733;&#9733;</h1>

<p align="center">${requestScope.error}</p>

<label>
    <p align="center">
        BILL
    </p>
</label>

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
    <div div style="text-align: center">
        <input type="submit" value="pay">
    </div>

    <div div style="text-align: center">
        <a href="./dates">choose another dates</a>
    </div>
    <div div style="text-align: center">
        <a href="./reservation">back to the rooms selection</a>
    </div>

</form>
</body>
</html>