package com.alphazer0.wmsgestaocomercial.database.roomDAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alphazer0.wmsgestaocomercial.model.ContaAReceber;

import java.util.List;

@Dao
public interface RoomContaAReceberDAO {
    @Insert
    void salvaContaAReceber(ContaAReceber contaAReceber);

    @Query("SELECT * FROM contaareceber")
    List<ContaAReceber> todasContaAReceber();

    @Delete
    void removeContaAReceber(ContaAReceber contaAReceber);

    @Update
    void editaContaAReceber(ContaAReceber contaAReceber);
}