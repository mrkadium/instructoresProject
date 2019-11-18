<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error500!</title>
    </head>
    <body>
        <h1>Error500</h1>
        <p>${class}</p>
        <p>${ex}</p>
        <p>${cause}</p>
        <p>${message}</p>
        <p><i>${request_uri}</i></p>
        <p><i>${servlet_name}</i></p>
    </body>
</html>
