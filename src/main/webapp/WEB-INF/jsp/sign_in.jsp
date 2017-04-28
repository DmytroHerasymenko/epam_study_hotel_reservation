<%--<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>--%>
<html>
<head>
    <%--<meta http-equiv="Content-Type" content="text/html;charset=utf-8">--%>
    <title>Authorization</title>
</head>
<body>
<form action="./sign_in_handler" method="post">
    <h1 align="center">Java-hotel &#9733;&#9733;&#9733;&#9733;&#9733;</h1>
    <h1 align="center">Authorization</h1>

    <label >
        <div style="text-align: center">
        <ul class="input-requirements">
            <li>Please, input your login and password for authorization:</li>
        </ul>
        </div>
    </label>

    <p align="center">${requestScope.error}</p>

    <label for="login">
        <p align="center">Login (e-mail)

            <input type="email" name="login" id="login" pattern="^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" required>
        </p>
    </label>

    <label for="password">
        <p align="center">Password

            <input type="password" name="password" id="password" maxlength="20" required>
        </p>
    </label>

    <br/>

    <div div style="text-align: center">
    <input type="submit" value="confirm">
    </div>

    <p/>

    <div div style="text-align: center">
        <a href="./registration">registration</a>
    </div>

</form>
</body>
</html>