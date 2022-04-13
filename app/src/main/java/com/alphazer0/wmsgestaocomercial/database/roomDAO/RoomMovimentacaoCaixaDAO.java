package com.alphazer0.wmsgestaocomercial.database.roomDAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alphazer0.wmsgestaocomercial.model.Cliente;
import com.alphazer0.wmsgestaocomercial.model.MovimentacaoCaixa;

import java.util.List;

@Dao
public interface RoomMovimentacaoCaixaDAO {
    @Insert
    void salvaMovimentacaoCaixa(MovimentacaoCaixa movimentacaoCaixa);

    @Query("SELECT * FROM movimentacaocaixa")
    List<MovimentacaoCaixa> todasMovimentacoes();

    @Delete
    void removeMovimentacao(MovimentacaoCaixa movimentacaoCaixa);

    @Update
    void editaMovimentacaoCaixa(MovimentacaoCaixa movimentacaoCaixa);
}
