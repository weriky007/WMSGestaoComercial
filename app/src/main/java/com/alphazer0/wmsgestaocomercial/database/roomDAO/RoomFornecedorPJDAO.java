package com.alphazer0.wmsgestaocomercial.database.roomDAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alphazer0.wmsgestaocomercial.model.FornecedorPJ;

import java.util.List;

@Dao
public interface RoomFornecedorPJDAO {
    @Insert
    void salvaFornecedorPJ(FornecedorPJ fornecedorPJ);

    @Query("SELECT * FROM fornecedorpj")
    List<FornecedorPJ> todosFornecedoresPJ();

    @Delete
    void removeFornecedorPJ(FornecedorPJ fornecedorPJ);

    @Update
    void editaFornecedorPJ(FornecedorPJ fornecedorPJ);
}
