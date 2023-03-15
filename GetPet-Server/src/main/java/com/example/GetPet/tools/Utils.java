package com.example.GetPet.tools;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static String integerListToString(List<Integer> list){
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    public static List<Integer> stringToIntegerList(String str){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Integer>>() {}.getType();
        return gson.fromJson(str,type);
    }

    public static String stringListToString(List<String> list){
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    public static List<String> stringToStringList(String str){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(str,type);
    }
}
