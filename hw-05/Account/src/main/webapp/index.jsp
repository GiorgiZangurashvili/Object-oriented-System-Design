<%--<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>--%>
<%--<!DOCTYPE html>--%>
<%--<html>--%>
<%--<head>--%>
<%--    <title>JSP - Hello World</title>--%>
<%--</head>--%>
<%--<body>--%>
<%--<h1><%= "Hello World!" %>--%>
<%--</h1>--%>
<%--<br/>--%>
<%--<a href="hello-servlet">Hello Servlet</a>--%>
<%--</body>--%>
<%--</html>--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome</title>
</head>
<body>
<h1>Welcome to Homework 5</h1>
<p>Please log in.</p>

<form action="LoginServlet" method="post">
    <label for="username">User Name: </label>
    <input type="text" id="username" name="username"/><br/>
    <label for="password">Password: </label>
    <input type="password" id="password" name="password"/><br/>
    <input type="submit" value="Login"/>
</form>
<a href="createAccount.jsp">Create Account</a>
</body>
</html>