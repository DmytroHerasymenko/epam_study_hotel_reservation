<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Reservation</title>

    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <link rel="stylesheet" href="/resources/demos/style.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script>
        $(function(){
            $("#arriveDatepicker").datepicker({
                dateFormat: 'dd.mm.yy',
                changeMonth: true,
                numberOfMonths: 1,
                minDate: 0,
                onSelect: function(selectedDate) {
                    var minDate = $.datepicker.parseDate("dd.mm.yy", selectedDate);
                    minDate.setDate(minDate.getDate()+1);
                    $("#departureDatepicker").datepicker("option", "minDate", minDate);
                    var maxDate = new Date(minDate.getTime());
                    maxDate.setDate(maxDate.getDate()+30);
                    $("#departureDatepicker").datepicker("option", "maxDate", maxDate);
                }
            }).datepicker("setDate", "0");
            $("#departureDatepicker").datepicker({
                dateFormat: 'dd.mm.yy',
                changeMonth: true,
                numberOfMonths: 1
            }).datepicker("setDate", "+1");
        });

        var ns6=document.getElementById&&!document.all?1:0;
        var folder='';
        function expandit(curobj){
            folder=ns6?curobj.nextSibling.nextSibling.style:document.all[curobj.sourceIndex+1].style;
            if (folder.display=="none") {
                folder.display = "";
            }
            else {
                folder.display = "none";
            }
        }
    </script>
</head>
<body>
<form action="./reservation_handler" method="post">
    <h1 align="center">Epam-hotel &#9733;&#9733;&#9733;&#9733;&#9733;</h1>

    <label>
        <p align="center">Choose date and book comfortable room:</p>
    </label>

    <p align="center">${requestScope.error}</p>

    <label>
    <p align="center">Arriving date: <input type="date"
                                            pattern="(0[1-9]|1[0-9]|2[0-9]|3[01]).(0[1-9]|1[012]).[0-9]{4}"
                                            name="arriveDatepicker" id="arriveDatepicker"></p>
    <p align="center">Departure date: <input type="date"
                                             pattern="(0[1-9]|1[0-9]|2[0-9]|3[01]).(0[1-9]|1[012]).[0-9]{4}"
                                             name="departureDatepicker" id="departureDatepicker"></p>
    </label>

    <label>
        <p align="center">Room categories:</p>
        <p align="center" style="cursor:pointer" onClick="expandit(this)">Standard (click):</p>
        <p align="center" style="display:none">
        <span >
            Standard-single - 130$/night. Select rooms:
            <select size="1" name="st_single">
                <optgroup label="rooms">
                    <option selected value="0">0</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                </optgroup>
            </select>
        </span>
            <br/>

        <span >
            Standard-double - 140$/night. Select rooms:
            <select size="1" name="st_double">
                <optgroup label="rooms">
                    <option selected value="0">0</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                </optgroup>
            </select>
        </span>
            <br/>

        <span >
            Standard-twin - 140$/night. Select rooms:
            <select size="1" name="st_twin">
                <optgroup label="rooms">
                    <option selected value="0">0</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                </optgroup>
            </select>
        </span>
        </p>

        <p align="center" style="cursor:pointer" onClick="expandit(this)">Suite (click):</p>
        <p align="center" style="display:none">
        <span>
            Suite-double - 260$/night. Select rooms:
            <select size="1" name="suite_double">
                <optgroup label="rooms">
                    <option selected value="0">0</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                </optgroup>
            </select>
        </span>
            <br/>
        <span>
            Suite-twin - 260$/night. Select rooms:
            <select size="1" name="suite_twin">
                <optgroup label="rooms">
                    <option selected value="0">0</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                </optgroup>
            </select>
        </span>
        </p>

        <p align="center" style="cursor:pointer" onClick="expandit(this)">Deluxe (click):</p>
        <p align="center" style="display:none">
        <span align="center">
            Deluxe-double - 370$/night. Select rooms:
            <select size="1" name="deluxe_double">
                <optgroup label="rooms">
                    <option selected value="0">0</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                </optgroup>
            </select>
        </span>
        </p>

    </label>

    <div div style="text-align: center">
        <input type="submit" value="make reservation">
    </div>

    <label>
        <p align="center" style="cursor:pointer" onClick="expandit(this)"> click here for rooms info: </p>
        <p align="center" style="display:none">
            <span style="cursor:pointer" onClick="expandit(this)">Standard-single(click): </span>
            <span style="display:none">
            One-room with quin size bed for one person with breakfast.
            Free wi-fi, safe-deposit, TV, wake-up call.
        </span>
            <br/>
            <span style="cursor:pointer" onClick="expandit(this)">Standard-double(click): </span>
            <span style="display:none">
            One-room with king size bed for two persons with two breakfasts.
            Free wi-fi, safe-deposit, TV, wake-up call.
        </span>
            <br/>
            <span style="cursor:pointer" onClick="expandit(this)">Standard-twin(click): </span>
            <span style="display:none">
            One-room with two quin size beds for two persons with two breakfasts.
            Free wi-fi, safe-deposit, TV, wake-up call.
        </span>
            <br/>
            <span style="cursor:pointer" onClick="expandit(this)">Suite-double(click): </span>
            <span style="display:none">
            Two-room with king size bed for two persons with two breakfasts.
            Free wi-fi, safe-deposit, TV, wake-up call, bathrobe, mini-bar.
        </span>
            <br/>
            <span style="cursor:pointer" onClick="expandit(this)">Suite-twin(click): </span>
            <span style="display:none">
            Two-room with two quin size beds for two persons with two breakfasts.
            Free wi-fi, safe-deposit, TV, wake-up call, bathrobe, mini-bar.
        </span>
            <br/>
            <span style="cursor:pointer" onClick="expandit(this)">Deluxe-double(click): </span>
            <span style="display:none">
            Three-room with king size bed for two persons with two breakfasts.
            Free wi-fi, safe-deposit, TV, wake-up call, bathrobe, mini-bar, laundry service.
        </span>
            <br/>
        </p>
    </label>

</form>
</body>
</html>