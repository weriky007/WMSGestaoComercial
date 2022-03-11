package com.alphazer0.wmsgestaocomercial.database.roomDAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alphazer0.wmsgestaocomercial.model.FornecedorPF;

import java.util.List;

@Dao
public interface RoomFornecedorPFDAO {
    @Insert
    void salvaFornecedorPF(FornecedorPF fornecedorPF);

    @Query("SELECT * FROM fornecedorpf")
    List<FornecedorPF> todosFornecedoresPF();

    @Delete
    void removeFornecedorPF(FornecedorPF fornecedorPF);

    @Update
    void editaFornecedorPF(FornecedorPF fornecedorPF);
}
