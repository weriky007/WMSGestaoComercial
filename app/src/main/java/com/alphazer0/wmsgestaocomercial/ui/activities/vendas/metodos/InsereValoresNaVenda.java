package com.alphazer0.wmsgestaocomercial.ui.activities.vendas.metodos;

import android.widget.TextView;

import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomVendasDAO;
import com.alphazer0.wmsgestaocomercial.model.Produto;
import com.alphazer0.wmsgestaocomercial.model.Venda;

import java.util.List;

public class InsereValoresNaVenda {
    public void insere(TextView valorTotal, Venda venda, String dataFormatada, String horaFormatada, String escolhaFormaPagamento, List<Produto> listaCompras, RoomVendasDAO vendasDAO,String dataContaCliente){
        String total = valorTotal.getText().toString();
        venda.setData(dataFormatada);
        venda.setDataVencimento(dataContaCliente);
        venda.setHora(horaFormatada);
        venda.setVlTotal(total);
        venda.setFormaPagamento(escolhaFormaPagamento);
        venda.setListaCompras(listaCompras);
        vendasDAO.salvaVenda(venda);
    }
}
