<%--
  Created by IntelliJ IDEA.
  User: gio
  Date: 6/26/2022
  Time: 7:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Information Incorrect</title>
</head>
<body>
    <h1>Please Try Again</h1>
    <p>Either your user name or password is incorrect. Please try</p>
    <p>again.</p>
    <form action="LoginServlet" method="post">
        <label for="username">User Name: </label>
        <input type="text" id="username" name="username"/><br/>
        <label for="password">Password: </label>
        <input type="password" id="password" name="password"/><br/>
        <input type="submit" value="Login"/>
    </form>
    <a href="createAccount.jsp">Create New Account</a>
</body>
</html>
