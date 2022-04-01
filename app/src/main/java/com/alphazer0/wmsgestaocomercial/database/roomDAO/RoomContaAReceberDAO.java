package com.alphazer0.wmsgestaocomercial.database.roomDAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alphazer0.wmsgestaocomercial.model.ContaAPagar;
import com.alphazer0.wmsgestaocomercial.model.ContaAReceber;
import com.alphazer0.wmsgestaocomercial.ui.activities.contas.ContasAPagar;
import com.alphazer0.wmsgestaocomercial.ui.activities.contas.ContasAReceber;

import java.util.List;

@Dao
public interface RoomContaAReceberDAO {
    @Insert
    void salvaContaAReceber(ContasAReceber contasAReceber);

    @Query("SELECT * FROM contaareceber")
    List<ContasAReceber> todasContasAReceber();

    @Delete
    void removeContaAReceber(ContaAReceber contaAReceber);

    @Update
    void editaContaAReceber(ContaAReceber contaAReceber);
}
