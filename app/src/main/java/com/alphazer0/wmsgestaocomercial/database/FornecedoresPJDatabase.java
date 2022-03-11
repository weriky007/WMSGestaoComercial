package com.alphazer0.wmsgestaocomercial.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.alphazer0.wmsgestaocomercial.database.converter.ConversorCalendar;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomFornecedorPJDAO;
import com.alphazer0.wmsgestaocomercial.model.FornecedorPJ;

@Database(entities = {FornecedorPJ.class},version = 1, exportSchema = false)
@TypeConverters({ConversorCalendar.class})
public abstract class FornecedoresPJDatabase extends RoomDatabase {
    private static final String NOME_BD = "fornecedorespj.db";
    public abstract RoomFornecedorPJDAO getFornecedorPJDAO();

    public static FornecedoresPJDatabase getInstance(Context context){
        return Room.databaseBuilder(context, FornecedoresPJDatabase.class, NOME_BD)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }
}
