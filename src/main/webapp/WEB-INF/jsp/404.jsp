<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored ="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html>
<head>

    <title>404</title>
</head>
<body>
<main>
    <div class="content error_page center">
        <div class="image_error">
            <p align="center">
                * <fmt:message key='error.page_not_found'/>
                <br>
                <a href="<c:url value="/index.jsp"/>"><fmt:message key="header.home"/></a>
            </p>
        </div>
    </div>
</main>
</body>
</html>
