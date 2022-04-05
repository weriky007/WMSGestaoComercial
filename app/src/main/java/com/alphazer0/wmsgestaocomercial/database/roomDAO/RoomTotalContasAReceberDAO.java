package com.alphazer0.wmsgestaocomercial.database.roomDAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alphazer0.wmsgestaocomercial.model.TotalContasAPagar;
import com.alphazer0.wmsgestaocomercial.model.TotalContasAReceber;

import java.util.List;

@Dao
public interface RoomTotalContasAReceberDAO {
    @Insert
    void salvaTotal(TotalContasAReceber totalContasAReceber);

    @Query("SELECT * FROM totalcontasareceber")
    List<TotalContasAReceber> totalContasAReceber();

    @Delete
    void removeTotal(TotalContasAReceber totalContaAReceber);

    @Update
    void editaTotal(TotalContasAReceber totalContaAReceber);
}
