<%@ page import="com.example.hw5part_2.ShoppingCart" %>
<%@ page import="com.example.hw5part_2.DataBaseManager" %>
<%@ page import="java.sql.SQLException" %><%--
  Created by IntelliJ IDEA.
  User: gio
  Date: 6/29/2022
  Time: 10:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shopping Cart</title>
</head>
<body>
    <h1>Shopping Cart</h1>
    <form action="ShoppingCartServlet" method="post">
        <ul>
            <%
                ShoppingCart sc = (ShoppingCart) session.getAttribute("ShoppingCart");
                DataBaseManager dbm = (DataBaseManager)application.getAttribute("DataBaseManager");

                double total = 0.0;
                for(String id : sc.idQuantityProductMap.keySet()){
                    total += dbm.getProduct(id).productPrice * sc.idQuantityProductMap.get(id);
                    out.println("<li><input type='number' name='" + id + "' value='" + sc.idQuantityProductMap.get(id) + "'>" +
                            dbm.getProduct(id).productName + ", " + dbm.getProduct(id).productPrice + "</li>");
                }
            %>
        </ul>
        <p>Total: $<%=total%></p>
        <input type="submit" value="Update Cart"/>
    </form>
    <a href="index.jsp">Continue Shopping</a>
</body>
</html>
