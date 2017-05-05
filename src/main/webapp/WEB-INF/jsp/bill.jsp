<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="${language}">
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

    <p align="center">
        rooms for booking:
    <td><c:forEach var="reservedRoomType" items="${sessionScope.reservedRoomTypes}">
    <c:if test="${reservedRoomType.value > 0}">
        (${reservedRoomType.key.roomCategory}-${reservedRoomType.key.bedspace} - ${reservedRoomType.value} rooms)
    </c:if>
    </c:forEach> </td>
        total price: ${requestScope.totalPrice}

    </p>


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