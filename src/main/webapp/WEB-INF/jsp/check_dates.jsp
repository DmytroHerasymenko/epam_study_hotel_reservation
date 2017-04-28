<html>
<head>
    <title>Check dates</title>

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
                maxDate: 365,
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
    </script>
</head>
<body>
<form action="./dates_handler" method="post">
    <h1 align="center">Java-hotel &#9733;&#9733;&#9733;&#9733;&#9733;</h1>

    <label>
        <p align="center">Choose date and book comfortable room:</p>
    </label>

    <p align="center">${requestScope.error}</p>

    <label>
        <p align="center">Arriving date: <input type="date"
                                                pattern="(0[1-9]|1[0-9]|2[0-9]|3[01]).(0[1-9]|1[012]).[0-9]{4}"
                                                name="arriveDatepicker" id="arriveDatepicker" required></p>
        <p align="center">Departure date: <input type="date"
                                                 pattern="(0[1-9]|1[0-9]|2[0-9]|3[01]).(0[1-9]|1[012]).[0-9]{4}"
                                                 name="departureDatepicker" id="departureDatepicker" required></p>
    </label>

    <div div style="text-align: center">
        <input type="submit" value="choose dates">
    </div>

    <div div style="text-align: center">
        <a href="./my_reservations">my reservations</a>
    </div>

</form>

</body>
</html>
