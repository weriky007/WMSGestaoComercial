package com.alphazer0.wmsgestaocomercial.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.alphazer0.wmsgestaocomercial.database.converter.ConversorCalendar;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomContaAReceberDAO;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomContasRecebidasDAO;
import com.alphazer0.wmsgestaocomercial.model.ContaAReceber;
import com.alphazer0.wmsgestaocomercial.model.ContaRecebida;

@Database(entities = {ContaRecebida.class},version = 1, exportSchema = false)

//ADICIONANDO OS TIPOS DE CONVERSOR QUE VAMOS TER NO PROJETO
@TypeConverters({ConversorCalendar.class})
public abstract class ContasRecebidasDatabase extends RoomDatabase {
    /*CASO ADICIONE UM CAMPO A MAIS OU REMOVA DO FORMULARIO,
    UTILIZE O ".fallbackToDestructiveMigration()" QUE IRA DESTRUIR O BD E RECRIAR UM NOVO COM OS NOVOS
    DADOS. MAS SO FACA ISSO QUANDO NINGUEM ESTIVER USANDO O APLICATIVO, SOMENTE EM PRODUCAO SE NAO
    IRA APAGAR TODOS OS DADOS.
    
     */

    private static final String NOME_BD = "contasARecebidas.db";
    public abstract RoomContasRecebidasDAO getContasAReceberDAO();

    public static ContasRecebidasDatabase getInstance(Context context){
        return Room.databaseBuilder(context, ContasRecebidasDatabase.class, NOME_BD)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }
}
