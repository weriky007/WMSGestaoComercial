package com.alphazer0.wmsgestaocomercial.ui.activities.vendas;
import com.alphazer0.wmsgestaocomercial.model.Cliente;
import com.alphazer0.wmsgestaocomercial.model.Produto;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PegaInformacoesParaVenda {
    public void pegaProduto(List<Produto> produtosDao, List<String> filtroTituloProdutos, List<String> filtroCodigo, List<Produto> produtos, HashSet<String> hashSetTituloProdutos, HashSet<String> hashSetCodigos){

        for (Produto filtroProduto : produtosDao) {
            filtroTituloProdutos.add(filtroProduto.getProduto());
            filtroCodigo.add(filtroProduto.getIdCod());
            produtos.add(filtroProduto);
        }
        hashSetTituloProdutos.addAll(filtroTituloProdutos);
        filtroTituloProdutos.clear();
        filtroTituloProdutos.addAll(hashSetTituloProdutos);

        hashSetCodigos.addAll(filtroCodigo);
        filtroCodigo.clear();
        filtroCodigo.addAll(hashSetCodigos);
    }

    public void pegaClientes(List<Cliente> clientes,List<String> filtroClientes,HashSet<String> hashSetClientes){
        for (Cliente filtroCliente : clientes) {
            filtroClientes.add(filtroCliente.getNomeCompleto().trim());
        }
        hashSetClientes.addAll(filtroClientes);
        filtroClientes.clear();
        filtroClientes.addAll(hashSetClientes);
    }
}
