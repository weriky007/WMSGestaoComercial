package com.alphazer0.wmsgestaocomercial.ui.activities.vendas;

import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;

import com.alphazer0.wmsgestaocomercial.model.Produto;

import java.util.List;

public class BuscaCodigoDeBarras {

    public void busca(EditText campoCodigoBarras, MultiAutoCompleteTextView campoProduto, List<Produto> produtos){
        String verify = campoCodigoBarras.getText().toString().trim();
        if(verify.equals("")) {
            String campProduto = campoProduto.getText().toString().trim();
            String prod;
            String cod;

            for (int j = 0; j < produtos.size(); j++) {
                prod = produtos.get(j).getProduto();
                if (campProduto.equals(prod)) {
                    cod = produtos.get(j).getIdCod();
                    campoCodigoBarras.setText(cod);
                }else if(campoProduto.equals("")){
                    campoCodigoBarras.setText("");
                }
            }
        }
    }
}
