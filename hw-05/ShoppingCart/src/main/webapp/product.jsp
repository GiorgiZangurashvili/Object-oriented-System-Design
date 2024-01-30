<%@ page import="com.example.hw5part_2.DataBaseManager" %><%--
  Created by IntelliJ IDEA.
  User: gio
  Date: 6/29/2022
  Time: 7:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><%
                DataBaseManager dbm = (DataBaseManager)application.getAttribute("DataBaseManager");
                out.println(dbm.getProduct(request.getParameter("id")).productName);
            %>
    </title>
</head>
<body>
    <%=
        "<h1>" + dbm.getProduct(request.getParameter("id")).productName + "</h1>\n"
        +"<img src=store-images/" + dbm.getProduct(request.getParameter("id")).imageFileName + ">\n"
        +"<p>$" + dbm.getProduct(request.getParameter("id")).productPrice + "</p>\n"
        +"<form action=\"ShoppingCartServlet\" method=\"post\">\n"
        +"<input name=\"productId\" type=\"hidden\" value=\"" + request.getParameter("id") + "\"/>\n"
        +"<input type=\"submit\" value=\"Add to Cart\"/>\n"
        +"</form>\n"
    %>
</body>
</html>
