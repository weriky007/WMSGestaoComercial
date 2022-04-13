package com.alphazer0.wmsgestaocomercial.database.roomDAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alphazer0.wmsgestaocomercial.model.TotalCaixa;
import com.alphazer0.wmsgestaocomercial.model.TotalContasAPagar;

@Dao
public interface RoomTotalCaixaDAO {
    @Insert
    void salvaTotal(TotalCaixa totalCaixa);

    @Query("SELECT * FROM totalcaixa")
    TotalCaixa totalCaixa();

    @Delete
    void removeTotal(TotalCaixa totalCaixa);

    @Update
    void editaTotal(TotalCaixa totalCaixa);
}