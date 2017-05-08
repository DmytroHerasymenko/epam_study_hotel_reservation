<%@ page contentType="text/html;charset=UTF-8"%>
<%--<%request.setCharacterEncoding("UTF-8");%>--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<%--<fmt:setLocale value="${pageContext.request.locale.language}"/>--%>

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<!DOCTYPE html>
<html lang="${language}">
<head>
    <%--<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>--%>
    <%--<script type="text/javascript" src="/resource/js/main.js" defer></script>--%>
    <%--<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>--%>
    <%--<link href="/resource/css/main.css" rel="stylesheet" type="text/css">--%>

    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <meta name="author" content="Theme-Paradise" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <!-- Google Fonts -->
    <link href='http://fonts.googleapis.com/css?family=Playfair+Display:400,700,900,400italic,700italic' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Roboto:400,700' rel='stylesheet' type='text/css'>

    <link href='themeforest-11586272-moon-hotel-html-template/moon/fonts/fontawesome/css/font-awesome.min.css' rel='stylesheet' type='text/css'>
    <link href='themeforest-11586272-moon-hotel-html-template/moon/fonts/icomoon/style.css' rel='stylesheet' type='text/css'>

    <link href='themeforest-11586272-moon-hotel-html-template/moon/css/jquery-ui.min.css' rel='stylesheet' type='text/css'>
    <link href='themeforest-11586272-moon-hotel-html-template/moon/bootstrap/css/bootstrap.min.css' rel='stylesheet' type='text/css'>
    <link href='themeforest-11586272-moon-hotel-html-template/moon/css/animate.css' rel='stylesheet' type='text/css'>
    <link href='themeforest-11586272-moon-hotel-html-template/moon/css/swiper.min.css' rel='stylesheet' type='text/css'>

    <link href='themeforest-11586272-moon-hotel-html-template/moon/css/style.css' rel='stylesheet' type='text/css'>



    <title><fmt:message key="java.hotel"/></title>
</head>
<body class="loading">
<form>
    <select id="language" name="language" onchange="submit()">
        <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
        <option value="ru" ${language == 'ru' ? 'selected' : ''}>Русский</option>
    </select>

</form>

<h1 class="hidden">Facilities</h1>
<div id="preloader-wrapper">
    <div id="preloader"></div>
</div>
<header class="header-2">
    <div class="container">
        <a href="index2.html" class="logo-link">The <strong>Moon</strong> Hotel</a>
        <nav class="main-menu clearfix">
            <h2 class="hidden">Main Menu</h2>
            <ul>
                <li class="menu-item">
                    <a href="#">Home</a>
                    <ul class="submenu">
                        <li class="sub-menu-item"><a href="index1.html">Home Version 1</a></li>
                        <li class="sub-menu-item"><a href="index2.html">Home Version 2</a></li>
                    </ul>
                </li>
                <li class="menu-item">
                    <a href="about.html">About</a>
                </li>
                <li class="menu-item">
                    <a href="#">Rooms</a>
                    <ul class="submenu">
                        <li class="sub-menu-item"><a href="rooms.html">Rooms List</a></li>
                        <li class="sub-menu-item"><a href="room-single.html">Room single</a></li>
                    </ul>
                </li>
                <li class="menu-item">
                    <a href="facilities.html">Services</a>
                </li>
                <li class="menu-item">
                    <a href="#">Booking</a>
                    <ul class="submenu">
                        <li class="sub-menu-item"><a href="booking-choose-date.html">Choose Date</a></li>
                        <li class="sub-menu-item"><a href="booking-choose-room.html">Choose Room</a></li>
                        <li class="sub-menu-item"><a href="booking-reservation.html">Reservation</a></li>
                    </ul>
                </li>
                <li class="menu-item">
                    <a href="#">Gallery</a>
                    <ul class="submenu">
                        <li class="sub-menu-item"><a href="gallery.html">Gallery</a></li>
                        <li class="sub-menu-item"><a href="gallery-single.html">Gallery Single</a></li>
                    </ul>
                </li>
                <li class="menu-item">
                    <a href="#">Blog</a>
                    <ul class="submenu">
                        <li class="sub-menu-item"><a href="blog1.html">Blog version 1</a></li>
                        <li class="sub-menu-item"><a href="blog2.html">Blog version 2</a></li>
                        <li class="sub-menu-item"><a href="blog-single.html">Blog Single</a></li>
                    </ul>
                </li>
                <li class="menu-item">
                    <a href="contact.html">Contact</a>
                </li>
            </ul>
        </nav>

        <div id="menu-toggle">
            <div class="bar first"></div>
            <div class="bar second"></div>
            <div class="bar third"></div>
        </div>
    </div>

    <nav id="mobile-menu">
        <h2 class="hidden">Mobile Navigation Menu</h2>
        <ul>
            <li class="mobile-menu-item">
                <a class="menu-item" href="#">Home<i class="toggle-state-icon icon-angle-right"></i></a>
                <ul class="submenu">
                    <li class="sub-menu-item"><a href="index1.html">Home Version 1</a></li>
                    <li class="sub-menu-item"><a href="index2.html">Home Version 2</a></li>
                </ul>
            </li>
            <li class="mobile-menu-item">
                <a class="menu-item" href="about.html">About</a>
            </li>
            <li class="mobile-menu-item">
                <a class="menu-item" href="#">Rooms<i class="toggle-state-icon icon-angle-right"></i></a>
                <ul class="submenu">
                    <li class="sub-menu-item"><a href="rooms.html">Rooms List</a></li>
                    <li class="sub-menu-item"><a href="room-single.html">Room single</a></li>
                </ul>
            </li>
            <li class="mobile-menu-item">
                <a class="menu-item" href="facilities.html">Services</a>
            </li>
            <li class="mobile-menu-item">
                <a class="menu-item" href="#">Booking<i class="toggle-state-icon icon-angle-right"></i></a>
                <ul class="submenu">
                    <li class="sub-menu-item"><a href="booking-choose-date.html">Choose Date</a></li>
                    <li class="sub-menu-item"><a href="booking-choose-room.html">Choose Room</a></li>
                    <li class="sub-menu-item"><a href="booking-reservation.html">Reservation</a></li>
                </ul>
            </li>
            <li class="mobile-menu-item">
                <a class="menu-item" href="#">Gallery<i class="toggle-state-icon icon-angle-right"></i></a>
                <ul class="submenu">
                    <li class="sub-menu-item"><a href="gallery.html">Gallery</a></li>
                    <li class="sub-menu-item"><a href="gallery-single.html">Gallery Single</a></li>
                </ul>
            </li>
            <li class="mobile-menu-item">
                <a class="menu-item" href="#">Blog<i class="toggle-state-icon icon-angle-right"></i></a>
                <ul class="submenu">
                    <li class="sub-menu-item"><a href="blog1.html">Blog Version 1</a></li>
                    <li class="sub-menu-item"><a href="blog2.html">Blog Version 2</a></li>
                    <li class="sub-menu-item"><a href="blog-single.html">Blog Single</a></li>
                </ul>
            </li>
            <li class="mobile-menu-item">
                <a class="menu-item" href="contact.html">Contact</a>
            </li>
        </ul>
    </nav>
</header>

<script type="text/javascript" src="themeforest-11586272-moon-hotel-html-template/moon/js/jquery.js"></script>
<script type="text/javascript" src="themeforest-11586272-moon-hotel-html-template/moon/js/jquery-ui.js"></script>
<script type="text/javascript" src="themeforest-11586272-moon-hotel-html-template/moon/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="themeforest-11586272-moon-hotel-html-template/moon/js/plugins.js"></script>
<script type="text/javascript" src="themeforest-11586272-moon-hotel-html-template/moon/js/functions.js"></script>




<form>
    <input type="hidden" name="langgg" id="langgg" value="${language}">
    <h1 class="text-primary text-center">
        <fmt:message key="index.welcome"/>
    </h1>
    <h2 class="text-center">
        <fmt:message key="index.to_book_room"/>
        <a charset="UTF-8" href="./registration"><fmt:message key="index.registration"/></a>
        <fmt:message key="index.or"/>
        <a href="./sign_in"><fmt:message key="index.sign_in"/></a>.
    </h2>
</form>
</body>
</html>
