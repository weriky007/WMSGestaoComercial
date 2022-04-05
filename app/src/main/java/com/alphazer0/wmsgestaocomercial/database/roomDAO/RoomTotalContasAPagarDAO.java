package com.alphazer0.wmsgestaocomercial.database.roomDAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alphazer0.wmsgestaocomercial.model.Cliente;
import com.alphazer0.wmsgestaocomercial.model.TotalContasAPagar;

import java.util.List;

@Dao
public interface RoomTotalContasAPagarDAO {
    @Insert
    void salvaTotal(TotalContasAPagar totalContasAPagar);

    @Query("SELECT * FROM totalcontasapagar")
    List<TotalContasAPagar> totalContasAPagar();

    @Delete
    void removeTotal(TotalContasAPagar totalContaAPagar);

    @Update
    void editaTotal(TotalContasAPagar totalContaAPagar);
}
