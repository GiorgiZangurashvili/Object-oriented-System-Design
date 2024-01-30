package com.example.hw5part_2;

import java.util.HashMap;

public class ShoppingCart {
    public HashMap<String, Integer> idQuantityProductMap;

    public ShoppingCart(){
        idQuantityProductMap = new HashMap<>();
    }

    public void addProductToCart(String id, int quantity){
        if(idQuantityProductMap.containsKey(id)){
            idQuantityProductMap.put(id, idQuantityProductMap.get(id) + quantity);
        }else{
            idQuantityProductMap.put(id, quantity);
        }
    }
}
