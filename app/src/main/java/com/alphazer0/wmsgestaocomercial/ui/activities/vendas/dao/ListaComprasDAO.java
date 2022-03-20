package com.alphazer0.wmsgestaocomercial.ui.activities.vendas.dao;
//==================================================================================================

import com.alphazer0.wmsgestaocomercial.model.Cliente;
import com.alphazer0.wmsgestaocomercial.model.Produto;

import java.util.ArrayList;
import java.util.List;

//==================================================================================================
public class ListaComprasDAO {
//==================================================================================================
    private final static List<Produto> produtos = new ArrayList<>();
    private static int contadorIds = 1;
//==================================================================================================
    //COPIA DA LISTA
    public List<Produto> todos (){
        return new ArrayList<>(produtos);
    }
//==================================================================================================
    public void salva (List<Produto> produto){
        produtos.addAll(produto);
    }
//==================================================================================================
    public void removeTodos(List<Produto> produto){
        produtos.removeAll(produto);
    }
//==================================================================================================
}

