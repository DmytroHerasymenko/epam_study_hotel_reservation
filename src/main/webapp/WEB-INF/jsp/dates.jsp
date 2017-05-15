<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />

<fmt:setLocale value="${language}" />
<fmt:setBundle basename="messages"/>
<!DOCTYPE html>
<html lang="${language}">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style type="text/css" rel="stylesheet">
        <%@include file="/css/styles.css"%>
    </style>
    <title><fmt:message key="java.hotel"/></title>
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script>
        $(function(){
            var minDate = 1;
            var maxDate = 366;
            $("#arriveDatepicker").datepicker({
                dateFormat: 'dd.mm.yy',
                changeMonth: true,
                minDate: 0,
                maxDate: 365,
                onSelect: function(selectedDate) {
                    minDate = $.datepicker.parseDate("dd.mm.yy", selectedDate);
                    minDate.setDate(minDate.getDate()+1);
                    $('#departureDatepicker').datepicker("option", "minDate", minDate);
                    maxDate = new Date(minDate.getTime());
                    maxDate.setDate(maxDate.getDate()+30);
                    $('#departureDatepicker').datepicker("option", "maxDate", maxDate);
                }
            }).datepicker("setDate", "0");
            $("#departureDatepicker").datepicker({
                dateFormat: 'dd.mm.yy',
                changeMonth: true,
                minDate: minDate,
                maxDate: maxDate,
            }).datepicker("setDate", "+1");
        });
    </script>
</head>
<body>
<form style="float: right">
    <select id="language" name="language" onchange="submit()">
        <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
        <option value="ru" ${language == 'ru' ? 'selected' : ''}>Русский</option>
    </select>
</form>
<form action="./dates_handler" method="post">
    <div id="container">
        <div id="header">
            <h1><fmt:message key="java.hotel"/></h1>
            <h2><fmt:message key="header.like_home"/></h2>
            <ul class="menu">
                <li class="btn_1"><a href="./index"><fmt:message key="header.home"/></a></li>
                <li class="line"></li>
                <li class="btn_2"><a href="./dates"><fmt:message key="header.dates"/></a></li>
                <li class="line"></li>
                <li class="btn_3"><a href="./my_reservations"><fmt:message key="header.my_reservs"/></a></li>
                <li class="line"></li>
                <li class="btn_4"><a href="./contacts"><fmt:message key="header.contacts"/></a></li>
            </ul>
        </div>
        <div id="content">
            <div id="panel">
                <div id="welcome">
                    <h2><fmt:message key="dates.accomodation"/></h2>
                    <p id="side">
                        <span id="headline1" class="headline">
                            <fmt:message key="dates.choose_date"/>
                        </span>
                        <br>
                        <span id="error" class="headline">
                            ${requestScope.error}
                        </span>
                        <br>
                        <label for="arriveDatepicker">
                            <span id="headline2" class="headline">
                                <fmt:message key="dates.arrive_date"/>
                                <input type="date" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01]).(0[1-9]|1[012]).[0-9]{4}"
                                       name="arriveDatepicker" id="arriveDatepicker" required>
                            </span>
                        </label>
                        <label for="departureDatepicker">
                            <span id="headline3" class="headline">
                                <fmt:message key="dates.departure_date"/>
                                <input type="date" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01]).(0[1-9]|1[012]).[0-9]{4}"
                                       name="departureDatepicker" id="departureDatepicker" required>
                            </span>
                        </label>
                        <input type="submit" value="<fmt:message key="dates.button_choose"/>">
                    </p>
                    <div class="clear" id="welClear"></div>
                </div>
            </div>
            <div class="clear"></div>
        </div>
        <div id="footer">
            <p><a href="./index"><fmt:message key="header.home"/></a> |
                <a href="./about"><fmt:message key="header.about"/></a> |
                <a href="./news"><fmt:message key="footer.news"/></a> |
                <a href="./contacts"><fmt:message key="header.contacts"/></a><br/>
        </div>
    </div>
</form>
</body>
</html>




