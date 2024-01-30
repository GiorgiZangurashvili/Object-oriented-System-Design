<%--
  Created by IntelliJ IDEA.
  User: gio
  Date: 6/26/2022
  Time: 7:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Account</title>
</head>
<body>
    <h1>Create New Account</h1>
    <p>Please enter proposed name and password.</p>
    <form action="CreateAccountServlet" method="post">
        <label for="username">User Name: </label>
        <input type="text" id="username" name="username"/><br/>
        <label for="password">Password: </label>
        <input type="password" id="password" name="password"/><br/>
        <input type="submit" value="Sign Up"/>
    </form>
</body>
</html>
