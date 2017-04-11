<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration Form</title>
</head>
<body>
<form action="./registration_handler" method="post">
    <h1 align="center">Registration Form</h1>

    <label >
            <p align="center">Please, fill all fields correct:</p>
    </label>

    <p align="center">${requestScope.error}</p>

    <label for="name">
        <p align="center">Surname

        <input type="text" name="name" id="name" pattern="[a-Zа-Я]+" minlength="3" required>
        </p>

        <div style="text-align: center">
        <ul class="input-requirements">
             <li>At least 3 characters long</li>
            <li>Must only contain letters (no special characters)</li>
        </ul>
        </div>

    </label>

    <label for="login">
        <p align="center">Login (e-mail)

        <input type="email" name="login" id="login" minlength="3" required>
        </p>

        <div style="text-align: center">
        <ul class="input-requirements">
            <li>Must only contain unique e-mail address</li>
        </ul>
        </div>
    </label>

    <label for="password">
        <p align="center">Password

        <input type="password" name="password" id="password"
               maxlength="20" minlength="4" required>
        </p>

        <div style="text-align: center">
        <ul class="input-requirements">
            <li>At least 4 characters long (and less than 20 characters)</li>
            <li>Contains at least 1 number</li>
            <li>Contains at least 1 lowercase letter</li>
            <li>Contains at least 1 uppercase letter</li>
            <li>Contains a special character (e.g. @ !)</li>
        </ul>
        </div>
    </label>

    <br/>

    <div div style="text-align: center">
        <input type="submit" value="confirm">
    </div>

    <p/>

    <div div style="text-align: center">
        <a href="./login">login</a>
    </div>

</form>
</body>
</html>