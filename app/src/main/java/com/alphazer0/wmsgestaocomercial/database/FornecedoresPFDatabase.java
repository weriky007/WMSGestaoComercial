package com.alphazer0.wmsgestaocomercial.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.alphazer0.wmsgestaocomercial.database.converter.ConversorCalendar;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomFornecedorPFDAO;
import com.alphazer0.wmsgestaocomercial.model.FornecedorPF;

@Database(entities = {FornecedorPF.class},version = 1, exportSchema = false)
@TypeConverters({ConversorCalendar.class})
public abstract class FornecedoresPFDatabase extends RoomDatabase {
    private static final String NOME_BD = "fornecedorespf.db";
    public abstract RoomFornecedorPFDAO getFornecedorPFDAO();

    public static FornecedoresPFDatabase getInstance(Context context){
        return Room.databaseBuilder(context, FornecedoresPFDatabase.class, NOME_BD)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }
}
