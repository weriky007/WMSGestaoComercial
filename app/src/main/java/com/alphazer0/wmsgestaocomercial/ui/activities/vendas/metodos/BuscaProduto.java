package com.alphazer0.wmsgestaocomercial.ui.activities.vendas.metodos;

import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;

import com.alphazer0.wmsgestaocomercial.model.Produto;

import java.util.List;

public class BuscaProduto {
    public void busca(MultiAutoCompleteTextView campoProduto, EditText campoCodigoBarras, List<Produto> produtos){
        String verify = campoProduto.getText().toString();
        if(verify.equals("")){
            String campCodigo = campoCodigoBarras.getText().toString().trim();
            String cod;
            String prod;
            for (int j = 0; j < produtos.size(); j++) {
                cod = produtos.get(j).getIdCod().toString();
                if(campCodigo.equals(cod)){
                    prod = produtos.get(j).getProduto().trim();
                    campoProduto.setText(prod);
                }else if(campCodigo.equals("")){
                    campoProduto.setText("");
                }
            }
        }
    }
}
