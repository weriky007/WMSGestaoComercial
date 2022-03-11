package com.alphazer0.wmsgestaocomercial.database.roomDAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alphazer0.wmsgestaocomercial.model.Produto;
import com.alphazer0.wmsgestaocomercial.model.Venda;

import java.util.List;

@Dao
public interface RoomVendasDAO {
    @Insert
    void salvaVenda(Venda venda);

    @Query("SELECT * FROM venda")
    List<Venda> todasVendas();

    @Delete
    void removeVenda(Venda venda);

    @Update
    void editaVenda(Venda venda);
}
