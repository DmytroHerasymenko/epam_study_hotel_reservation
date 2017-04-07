<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Epam-hotel</title>
    <script>
        var ns6=document.getElementById&&!document.all?1:0;
        var folder='';
        function expandit(curobj){
            folder=ns6?curobj.nextSibling.nextSibling.style:document.all[curobj.sourceIndex+1].style;
            if (folder.display=="none")
                folder.display="";
            else
                folder.display="none";
        }
    </script>
</head>
<body>
<h1 align="center">Welcome to the Epam-hotel &#9733;&#9733;&#9733;&#9733;&#9733;</h1>

<label>
    <p align="center">
        To book a room, go through the simple <a href="./service/registration">registration</a>
    or <a href="./service/login">sign in</a>.
    </p>
</label>

<label>
    <br/>
    <p align="center">Room categories:</p>
    <p align="center">Standard:</p>
    <p align="center" style="cursor:pointer" onClick="expandit(this)">
        Standard-single - 130$/night (click for more info)
    </p>
    <p align="center" style="display: none">
        One-room with quin size bed for one person with breakfast.
        Free wi-fi, safe-deposit, TV, wake-up call.
    </p>
    <p align="center" style="cursor:pointer" onClick="expandit(this)">
        Standard-double - 140$/night (click for more info)
    </p>
    <p align="center" style="display:none">
        One-room with king size bed for two persons with two breakfasts.
        Free wi-fi, safe-deposit, TV, wake-up call.
    </p>
    <p align="center" style="cursor:pointer" onClick="expandit(this)">
        Standard-twin - 140$/night (click for more info)
    </p>
    <p align="center" style="display:none">
        One-room with two quin size beds for two persons with two breakfasts.
        Free wi-fi, safe-deposit, TV, wake-up call.
    </p>
    <p align="center">Suite:</p>
    <p align="center" style="cursor:pointer" onClick="expandit(this)">
        Suite-double - 260$/night (click for more info)
    </p>
    <p align="center" style="display:none">
        Two-room with king size bed for two persons with two breakfasts.
        Free wi-fi, safe-deposit, TV, wake-up call, bathrobe, mini-bar.
    </p>
    <p align="center" style="cursor:pointer" onClick="expandit(this)">
        Suite-twin - 260$/night (click for more info)
    </p>
    <p align="center" style="display:none">
        Two-room with two quin size beds for two persons with two breakfasts.
        Free wi-fi, safe-deposit, TV, wake-up call, bathrobe, mini-bar.
    </p>
    <p align="center">Deluxe:</p>
    <p align="center" style="cursor:pointer" onClick="expandit(this)">
        Deluxe-double - 370$/night (click for more info)
    </p>
    <p align="center" style="display:none">
        Three-room with king size bed for two persons with two breakfasts.
        Free wi-fi, safe-deposit, TV, wake-up call, bathrobe, mini-bar, laundry service.
    </p>
</label>

</body>
</html>