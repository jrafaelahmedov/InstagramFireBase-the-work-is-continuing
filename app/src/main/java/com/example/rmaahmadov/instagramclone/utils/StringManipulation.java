package com.example.rmaahmadov.instagramclone.utils;

public class StringManipulation {
    
    public static String expendUsername(String username){
        return username.replace("."," ");
    }
    
    public static String condenseUsername(String username){
        return username.replace(" ",".");
    }
}
