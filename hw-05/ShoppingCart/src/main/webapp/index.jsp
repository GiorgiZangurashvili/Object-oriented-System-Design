<%@ page import="com.example.hw5part_2.Product" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="com.example.hw5part_2.DataBaseManager" %>
<%@ page import="javax.xml.crypto.Data" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Student Store</title>
</head>
<body>
    <h1>Student Store</h1>
    <p>Items available:</p>

    <ul>
        <%
            DataBaseManager dbm = (DataBaseManager) application.getAttribute("DataBaseManager");
            ArrayList<Product> products = dbm.getProducts();
            for(int i = 0; i < products.size(); i++){
                out.println("<li><a href=\"product.jsp?id=" + products.get(i).productId + "\">" + products.get(i).productName + "</a></li>");
            }
        %>
    </ul>
</body>
</html>