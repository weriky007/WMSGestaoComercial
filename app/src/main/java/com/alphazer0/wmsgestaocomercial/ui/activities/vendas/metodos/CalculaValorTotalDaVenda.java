package com.alphazer0.wmsgestaocomercial.ui.activities.vendas.metodos;

import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import com.alphazer0.wmsgestaocomercial.model.Produto;
import com.alphazer0.wmsgestaocomercial.ui.adapters.ListaProdutosVendasAdapter;

import java.math.BigDecimal;
import java.util.List;

public class CalculaValorTotalDaVenda {

    public void calculaTotal(List<Produto> listaCompras, BigDecimal total, TextView valorTotal, EditText campoCodigoBarras, MultiAutoCompleteTextView campoProduto, EditText campoQuantidade, ListaProdutosVendasAdapter produtosVendaAdapter){

        String sqtd = "";
        String svl = "";

        BigDecimal vlTotal = new BigDecimal("0");
        for(int a = 0;a<listaCompras.size();a++){
         sqtd = listaCompras.get(a).getQuantidade();
         svl = listaCompras.get(a).getPrecoVenda();
            BigDecimal vl = new BigDecimal(svl);
            BigDecimal qtd = new BigDecimal(sqtd);
            vlTotal = vlTotal.add(qtd.multiply(vl));
        }

        total = total.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        total = total.add(vlTotal);

        valorTotal.setText(total.toString());

        campoCodigoBarras.setText("");
        campoProduto.setText("");
        campoQuantidade.setText("");
        produtosVendaAdapter.notifyDataSetChanged();
    }
}
