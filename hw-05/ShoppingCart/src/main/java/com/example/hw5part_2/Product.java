package com.example.hw5part_2;

public class Product {
    public String productId;
    public String productName;
    public String imageFileName;
    public double productPrice;

    public Product(String productId, String productName, String imageFileName, double productPrice){
        this.productId = productId;
        this.productName = productName;
        this.imageFileName = imageFileName;
        this.productPrice = productPrice;
    }

    public String toString(){
        return "product_id: " + this.productId + " name: " + this.productName + " image_name: " + this.imageFileName + " price: " + this.productPrice;
    }
}
