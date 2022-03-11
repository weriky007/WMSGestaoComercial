package com.alphazer0.wmsgestaocomercial.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;

import androidx.appcompat.app.AlertDialog;

import com.alphazer0.wmsgestaocomercial.R;
import com.alphazer0.wmsgestaocomercial.database.ClientesDatabase;
import com.alphazer0.wmsgestaocomercial.database.ProdutosDatabase;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomClienteDAO;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomProdutoDAO;
import com.alphazer0.wmsgestaocomercial.model.Cliente;
import com.alphazer0.wmsgestaocomercial.model.Produto;
import com.alphazer0.wmsgestaocomercial.ui.adapters.ListaEstoqueProdutosAdapter;
import com.alphazer0.wmsgestaocomercial.ui.adapters.ListaProdutosVendasAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class FormularioProdutoDialog extends Activity {

    public FormularioProdutoDialog(String titulo, String tituloBotaoPositivo, Context context) {
        this.titulo = titulo;
        this.tituloBotaoPositivo = tituloBotaoPositivo;
        this.context = this;
    }
//==================================================================================================
    private final String titulo;
    private final String tituloBotaoPositivo;
    private static final String TITULO_BOTAO_NEGATIVO = "Cancelar";

    private ListView listaProdutos;
    private ListaProdutosVendasAdapter produtosVendaAdapter;
    private ListaEstoqueProdutosAdapter produtosEstoqueAdapter;
    private final Context context;

    private EditText campoCodigoBarras;
    private MultiAutoCompleteTextView campoCliente;
    private MultiAutoCompleteTextView campoProduto;
    private EditText campoQuantidade;

    private RoomProdutoDAO produtoDao;
    private RoomClienteDAO clienteDAO;

    private List<Produto> produtos = new ArrayList<>();
    private List<Produto> listaCompras = new ArrayList<>();
    private List<String> filtroTituloProdutos = new ArrayList<>();
    private List<String> filtroCodigo = new ArrayList<>();
    private List<String> filtroClientes = new ArrayList<>();
    private HashSet<Produto> hashSetProdutos = new HashSet<>();
    private HashSet<Produto> hashSetListaCompras = new HashSet<>();
    private HashSet<String> hashSetTituloProdutos = new HashSet<>();
    private HashSet<String> hashSetCodigos = new HashSet<>();
    private HashSet<String> hashSetClientes = new HashSet<>();

//==================================================================================================
    public void mostra() {
        @SuppressLint("InflateParams") View viewCriada = LayoutInflater.from(context)
                .inflate(R.layout.activity_fomulario_adiciona_produto_vendas, null);

        ColorDrawable back = new ColorDrawable(Color.WHITE);
        InsetDrawable inset = new InsetDrawable(back, 0);
        new AlertDialog.Builder(context)
                .setTitle(titulo)
                .setView(viewCriada)
                .setPositiveButton(tituloBotaoPositivo, (dialog, which) -> {
                    //O que acontece quando clica no add
                })
                .setNegativeButton(TITULO_BOTAO_NEGATIVO, null)
                .show().getWindow().setBackgroundDrawable(inset);
    }
//==================================================================================================
    private void bindDosElementos() {
        campoCliente = findViewById(R.id.edit_d_vendas_cliente);
        campoCodigoBarras = findViewById(R.id.edit_d_vendas_codigo_barras);
        campoProduto = findViewById(R.id.edit_d_vendas_produto);
        campoQuantidade = findViewById(R.id.edit_d_vendas_quantidade);
    }

    private void configuraAdapter() {
        produtosVendaAdapter = new ListaProdutosVendasAdapter(listaCompras);
        //PEGA TODOS OS CLIENTES DO BANCO DE DADOS
        produtoDao = ProdutosDatabase.getInstance(context).getProdutoDAO();
        clienteDAO = ClientesDatabase.getInstance(context).getClienteDAO();
    }

    private void configuraLista() {
        listaProdutos = findViewById(R.id.list_view_lista_produtos);
        listaProdutos.setAdapter(produtosVendaAdapter);

        registerForContextMenu(listaProdutos);
    }

    private void configuraAutoComplete() {
    pegaClientes(clienteDAO.todosClientes());
    pegaProduto(produtoDao.todosProdutos());

    ArrayAdapter<String> adapterClientes= new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,filtroClientes);
    ArrayAdapter<String> adapterProdutos = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, filtroTituloProdutos);

    campoCliente.setAdapter(adapterClientes);
    campoCliente.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    campoProduto.setAdapter(adapterProdutos);
    campoProduto.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
}

    //AUTOCOMPLETE
    private void pegaProduto(List<Produto> produto){
        for (Produto filtroProduto : produto) {
            this.filtroTituloProdutos.add(filtroProduto.getProduto());
            this.filtroCodigo.add(filtroProduto.getIdCod());
            this.produtos.add(filtroProduto);
        }
        hashSetTituloProdutos.addAll(this.filtroTituloProdutos);
        filtroTituloProdutos.clear();
        filtroTituloProdutos.addAll(hashSetTituloProdutos);

        hashSetCodigos.addAll(this.filtroCodigo);
        filtroCodigo.clear();
        filtroCodigo.addAll(hashSetCodigos);

        hashSetProdutos.addAll(this.produtos);
        produtos.clear();
        produtos.addAll(hashSetProdutos);
    }

    private void pegaClientes(List<Cliente> cliente){
        for (Cliente filtroCliente : cliente) {
            this.filtroClientes.add(filtroCliente.getNomeCompleto());
        }
        hashSetClientes.addAll(this.filtroClientes);
        filtroClientes.clear();
        filtroClientes.addAll(hashSetClientes);
    }
//==================================================================================================
}
