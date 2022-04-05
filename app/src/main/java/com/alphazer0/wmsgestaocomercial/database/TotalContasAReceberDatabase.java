package com.alphazer0.wmsgestaocomercial.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.alphazer0.wmsgestaocomercial.database.converter.ConversorCalendar;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomClienteDAO;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomTotalContasAReceberDAO;
import com.alphazer0.wmsgestaocomercial.model.Cliente;
import com.alphazer0.wmsgestaocomercial.model.TotalContasAReceber;

@Database(entities = {TotalContasAReceber.class},version = 2, exportSchema = false)

//ADICIONANDO OS TIPOS DE CONVERSOR QUE VAMOS TER NO PROJETO
@TypeConverters({ConversorCalendar.class})
public abstract class TotalContasAReceberDatabase extends RoomDatabase {
    /*CASO ADICIONE UM CAMPO A MAIS OU REMOVA DO FORMULARIO,
    UTILIZE O ".fallbackToDestructiveMigration()" QUE IRA DESTRUIR O BD E RECRIAR UM NOVO COM OS NOVOS
    DADOS. MAS SO FACA ISSO QUANDO NINGUEM ESTIVER USANDO O APLICATIVO, SOMENTE EM PRODUCAO SE NAO
    IRA APAGAR TODOS OS DADOS.
    
     */

    private static final String NOME_BD = "total_contas_a_receber.db";
    public abstract RoomTotalContasAReceberDAO getTotalContasAReceberDAO();

    public static TotalContasAReceberDatabase getInstance(Context context){
        return Room.databaseBuilder(context, TotalContasAReceberDatabase.class, NOME_BD)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }
}
