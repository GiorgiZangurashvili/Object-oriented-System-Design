<%--
  Created by IntelliJ IDEA.
  User: gio
  Date: 6/26/2022
  Time: 7:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title> <%= "Welcome " + request.getParameter("username") %> </title>
</head>
<body>
    <h1> <%= "Welcome " + request.getParameter("username") %> </h1>
</body>
</html>
