<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
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

        <input type="email" name="login" id="login" minlength="6" required>
        </p>

        <ul class="input-requirements">
        </ul>
    </label>

    <label for="password">
        <p align="center">Password

        <input type="password" name="password" id="password"
               maxlength="20" minlength="4" required>
        </p>

        <ul class="input-requirements">
        </ul>
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