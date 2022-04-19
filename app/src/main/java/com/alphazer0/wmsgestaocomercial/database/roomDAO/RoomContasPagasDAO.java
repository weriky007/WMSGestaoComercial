package com.alphazer0.wmsgestaocomercial.database.roomDAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alphazer0.wmsgestaocomercial.model.ContaAPagar;
import com.alphazer0.wmsgestaocomercial.model.ContaPaga;

import java.util.List;

@Dao
public interface RoomContasPagasDAO {
    @Insert
    void salvaContaPaga(ContaPaga contaPaga);

    @Query("SELECT * FROM contapaga")
    List<ContaPaga> todasContasPagas();

    @Delete
    void removeContaPaga(ContaPaga contaPaga);

    @Update
    void editaContaPaga(ContaPaga contaPaga);
}
