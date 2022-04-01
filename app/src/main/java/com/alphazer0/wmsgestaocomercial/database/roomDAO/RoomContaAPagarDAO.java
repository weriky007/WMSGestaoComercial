package com.alphazer0.wmsgestaocomercial.database.roomDAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alphazer0.wmsgestaocomercial.model.ContaAPagar;

import java.util.List;

@Dao
public interface RoomContaAPagarDAO {
    @Insert
    void salvaContaAPagar(ContaAPagar contaAPagar);

    @Query("SELECT * FROM contaapagar")
    List<ContaAPagar> todasContasAPagar();

    @Delete
    void removeContaAPagar(ContaAPagar contaAPagar);

    @Update
    void editaContaAPagar(ContaAPagar contaAPagar);
}
