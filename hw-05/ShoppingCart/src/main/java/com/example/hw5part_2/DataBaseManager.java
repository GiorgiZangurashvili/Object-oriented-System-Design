package com.example.hw5part_2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class DataBaseManager {
    private DataBase db;
    private HashMap<String, Product> idProducts;

    public DataBaseManager() throws SQLException, ClassNotFoundException {
        this.db = new DataBase();
        this.idProducts = new HashMap<>();
    }

    public ArrayList<Product> getProducts() throws SQLException {
        ArrayList<Product> result = new ArrayList<>();
        ResultSet rs = queryProducts();

        while(rs.next()){
            Product p = new Product(rs.getString("productid"), rs.getString("name"), rs.getString("imagefile"), rs.getDouble("price"));
            result.add(p);
            if(!idProducts.containsKey(p.productId)){
                idProducts.put(p.productId, p);
            }
        }

        return result;
    }

    private ResultSet queryProducts() throws SQLException {
        Statement st = this.db.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        String query = "SELECT * FROM products;";

        return st.executeQuery(query);
    }

    public Product getProduct(String id){
        return idProducts.get(id);
    }

    public Product getProductSQL(String id) throws SQLException {
        Statement st = this.db.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = st.executeQuery("SELECT * FROM products WHERE productid = " + id + ";");
        if(rs.next()){
            return new Product(rs.getString("productid"), rs.getString("name"), rs.getString("imagefile"), rs.getDouble("price"));
        }
        return null;
    }
}
