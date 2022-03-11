package com.alphazer0.wmsgestaocomercial.database.roomDAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alphazer0.wmsgestaocomercial.model.Produto;

import java.util.List;

@Dao
public interface RoomProdutoDAO {
    @Insert
    void salvaProduto(Produto produto);

    @Query("SELECT * FROM produto")
    List<Produto> todosProdutos();

    @Delete
    void removeProduto(Produto produto);

    @Update
    void editaProduto(Produto produto);
}
