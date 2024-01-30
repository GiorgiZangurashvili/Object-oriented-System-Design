package com.example.HW5_part_1;

import java.util.HashMap;

public class AccountManager {
    private HashMap<String, String> accounts;

    public AccountManager(){
        accounts = new HashMap<>();
        accounts.put("Patrick", "1234");
        accounts.put("Molly", "FloPup");
    }

    public boolean accountExists(String username){
        return accounts.containsKey(username);
    }

    public boolean isCorrectPassword(String username, String password){
        return accounts.get(username).equals(password);
    }

    public void createNewAccount(String username, String password){
        accounts.put(username, password);
    }
}
