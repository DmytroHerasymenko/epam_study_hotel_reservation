<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="bill_tag" uri="/WEB-INF/tld/taglib.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>bill</title>
</head>
<body>
${requestScope.error}
<h1 align="center">Welcome to the Java-hotel &#9733;&#9733;&#9733;&#9733;&#9733;</h1>

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
        <td><%=session.getAttribute("name")%></td>
    </tr>
    <tr>
        <th>arriving date: </th>
        <td><%=session.getAttribute("arrive")%></td>
    </tr>
    <tr>
        <th>departure date: </th>
        <td><%=session.getAttribute("departure")%></td>
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
        <td>${requestScope.totalPrice}$</td>
    </tr>
</table>
</body>
</html>