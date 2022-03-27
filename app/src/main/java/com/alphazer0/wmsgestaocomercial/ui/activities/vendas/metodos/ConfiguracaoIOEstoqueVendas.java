package com.alphazer0.wmsgestaocomercial.ui.activities.vendas.metodos;

import android.app.AlertDialog;
import android.content.Context;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.alphazer0.wmsgestaocomercial.database.ProdutosDatabase;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomProdutoDAO;
import com.alphazer0.wmsgestaocomercial.model.Produto;
import com.alphazer0.wmsgestaocomercial.ui.adapters.ListaEstoqueProdutosAdapter;
import com.alphazer0.wmsgestaocomercial.ui.adapters.ListaProdutosVendasAdapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ConfiguracaoIOEstoqueVendas {
//==================================================================================================
    public void devolveItemAoEstoque(RoomProdutoDAO produtoDao, Produto produto, List<Produto> produtos, BigDecimal total, TextView valorTotal, ListaProdutosVendasAdapter produtosVendaAdapter, List<Produto> listaCompras) {
        List<Produto> p = produtoDao.todosProdutos();
        int qtdProduto = 0;
        int qtdProdutos = 0;

        //fazer ele devolver o item ao estoque
        Produto produtoLocalizado = new Produto();
        Produto produtoLocalizado2 = new Produto();
        for (int i = 0; i < p.size(); i++) {
            if (produto.getIdCod().equals(produtos.get(i).getIdCod())) {
                qtdProduto = Integer.parseInt(produto.getQuantidade());
                qtdProdutos = Integer.parseInt(produtos.get(i).getQuantidade());
                produtoLocalizado = produtos.get(i);
            }
        }
        qtdProdutos = qtdProdutos - qtdProduto;
        int result = qtdProduto + qtdProdutos;
        String res = Integer.toString(result);
        produtoLocalizado.setQuantidade(res);
        produtoDao.editaProduto(produtoLocalizado);

        subtraiValorDoTotal(listaCompras, produto, valorTotal);
        produtosVendaAdapter.remove(produto);
        listaCompras.remove(produto);
        produtosVendaAdapter.notifyDataSetChanged();
    }
//==================================================================================================
    public void subtraiValorDoTotal(List<Produto> listaCompras, Produto produto, TextView valorTotal) {
        String sqtd = "";
        String svl = "";
        String total = valorTotal.getText().toString();
        Produto produtoF = new Produto();
        for (int i = 0; i < listaCompras.size(); i++) {
            if (produto.equals(listaCompras.get(i))) {
                produtoF = listaCompras.get(i);
            }
        }
        sqtd = produtoF.getQuantidade();
        svl = produtoF.getPrecoVenda();
        BigDecimal bqtd = new BigDecimal(sqtd);
        BigDecimal bvl = new BigDecimal(svl);
        BigDecimal calculo = new BigDecimal("0");
        calculo = bqtd.multiply(bvl);
        BigDecimal vl = new BigDecimal(total);
        vl = vl.subtract(calculo);

        vl = vl.setScale(2, BigDecimal.ROUND_HALF_EVEN);

        valorTotal.setText(vl.toString());
    }
//==================================================================================================
    public void diminuiItemDoEstoque(Context context,List<Produto> produtos, RoomProdutoDAO produtoDao, List<Produto> listaCompras) {
      List<Produto> produtosBD = produtoDao.todosProdutos();
      Produto produto = new Produto();
      int qtdBD = 0;
      int qtdListaCompras = 0;
      int resultado = 0;
      String sresultado = "";

      for(int i = 0;i<listaCompras.size(); i++){
          for(Produto prod : produtosBD){
              if(listaCompras.get(i).getProduto().equals(prod.getProduto())){
                  qtdBD = Integer.parseInt(prod.getQuantidade());
                  qtdListaCompras = Integer.parseInt(listaCompras.get(i).getQuantidade());
                  resultado = qtdBD - qtdListaCompras;
                  sresultado = Integer.toString(resultado);

                  produto =  prod;
                  produto.setQuantidade(sresultado);
                  produtoDao.editaProduto(produto);
              }
          }
      }
    }
//==================================================================================================
    public void insereProduto(Context context, MultiAutoCompleteTextView campoProduto, EditText campoCodigoBarras, List<Produto> produtos, EditText campoQuantidade, String resultadoQuantidade, RoomProdutoDAO produtoDao, List<Produto> listaCompras) {
        String tituloProduto = campoProduto.getText().toString().trim().trim().trim();
        String codigoBarras = campoCodigoBarras.getText().toString().trim();
        Produto produtoLocalizado = new Produto();
        Produto produtoBD = new Produto();

        for (int a = 0; a < produtos.size(); a++) {
            if (tituloProduto.equals(produtos.get(a).getProduto()) || codigoBarras.equals(produtos.get(a).getIdCod())) {
                produtoLocalizado = produtos.get(a);
                produtoBD = produtos.get(a);
            }
        }

        int qtdBD = Integer.parseInt(produtoBD.getQuantidade());
        int qtdVenda = Integer.parseInt(campoQuantidade.getText().toString());
        if (qtdVenda > qtdBD) {
            Toast.makeText(context, "A quantidade não pode ser maior que o estoque" + " | " + "Qantidade Estoque: " + qtdBD, Toast.LENGTH_LONG).show();
        } else {
            produtoLocalizado.setQuantidade(campoQuantidade.getText().toString());
            listaCompras.add(produtoLocalizado);
        }
    }
//==================================================================================================
}
