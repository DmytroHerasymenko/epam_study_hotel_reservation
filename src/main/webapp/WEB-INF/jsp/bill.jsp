<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="bill_tag" uri="/WEB-INF/tld/taglib.tld" %>
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
        <td>data</td>
    </tr>
    <tr>
        <th>name: </th>
        <td>data</td>
    </tr>
    <tr>
        <th>arriving date: </th>
        <td>data</td>
    </tr>
    <tr>
        <th>departure date: </th>
        <td>data</td>
    </tr>
    <tr>
        <th>rooms: </th>
        <td>data</td>
    </tr>
    <tr>
        <th>total price: </th>
        <td>data</td>
    </tr>
</table>


</body>
</html>