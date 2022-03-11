package com.alphazer0.wmsgestaocomercial.database.converter;

import androidx.room.TypeConverter;

import com.alphazer0.wmsgestaocomercial.model.Produto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class ConversorDeObjetosParaJSON {
    static Gson gson = new Gson();

    @TypeConverter
    public static List<Produto> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Produto>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<Produto> someObjects) {
        return gson.toJson(someObjects);
    }
}
