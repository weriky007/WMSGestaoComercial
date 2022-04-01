package com.alphazer0.wmsgestaocomercial.database.roomDAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alphazer0.wmsgestaocomercial.model.Cliente;
import com.alphazer0.wmsgestaocomercial.model.ContaAPagar;
import com.alphazer0.wmsgestaocomercial.ui.activities.contas.ContasAPagar;

import java.util.List;

@Dao
public interface RoomContaAPagarDAO {
    @Insert
    void salvaContaAPagar(ContasAPagar contasAPagar);

    @Query("SELECT * FROM contaapagar")
    List<ContasAPagar> todasContasAPagar();

    @Delete
    void removeContaAPagar(ContaAPagar contaAPagar);

    @Update
    void editaContaAPagar(ContaAPagar contaAPagar);
}
