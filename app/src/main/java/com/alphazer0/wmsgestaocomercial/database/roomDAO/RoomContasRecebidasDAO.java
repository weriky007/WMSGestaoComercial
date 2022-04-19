package com.alphazer0.wmsgestaocomercial.database.roomDAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alphazer0.wmsgestaocomercial.model.ContaAReceber;
import com.alphazer0.wmsgestaocomercial.model.ContaRecebida;

import java.util.List;

@Dao
public interface RoomContasRecebidasDAO {
    @Insert
    void salvaContaRecebida(ContaRecebida contaRecebida);

    @Query("SELECT * FROM contarecebida")
    List<ContaRecebida> todasContasRecebidas();

    @Delete
    void removeContaRecebida(ContaRecebida contaRecebida);

    @Update
    void editaContaRecebida(ContaRecebida contaARecebida);
}