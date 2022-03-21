package com.alphazer0.wmsgestaocomercial.ui.activities.relatorios;

import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.CHAVE_VENDA;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alphazer0.wmsgestaocomercial.R;
import com.alphazer0.wmsgestaocomercial.database.VendasDatabase;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomVendasDAO;
import com.alphazer0.wmsgestaocomercial.model.Produto;
import com.alphazer0.wmsgestaocomercial.model.Venda;
import com.alphazer0.wmsgestaocomercial.ui.adapters.ListaInfoProdutosVendaAdapter;

import java.util.ArrayList;
import java.util.List;

public class VendasInfoActivity extends AppCompatActivity {

    public static final String INFORMAÇÕES_VENDA = "Informações Venda";
    private Venda vendaRecebida = new Venda();
    private ListaInfoProdutosVendaAdapter adapter;
    private RoomVendasDAO dao;

    private List<Produto> produtos = new ArrayList<>();
    private RecyclerView listaProdutos;
    private TextView data;
    private TextView hora;
    private TextView cliente;
    private TextView total;
    private TextView tipoPagamento;
    private TextView condicional;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacoes_venda);
        setTitle(INFORMAÇÕES_VENDA);

        insereDados();
        configuraAdapter();
        configuraLista();
    }
    private void insereDados() {
        Intent recebeDados = new Intent();
        recebeDados.hasExtra(CHAVE_VENDA);
        Bundle extra = getIntent().getExtras();
        vendaRecebida = (Venda) extra.getSerializable(CHAVE_VENDA);

        produtos = vendaRecebida.getListaCompras();

        data = findViewById(R.id.info_txt_list_venda_data);
        hora = findViewById(R.id.info_txt_list_venda_hora);
        cliente = findViewById(R.id.info_txt_list_venda_cliente);
        total = findViewById(R.id.info_txt_list_valor_total);
        tipoPagamento = findViewById(R.id.info_txt_list_vendas_tipo_pagamento);
        condicional = findViewById(R.id.info_txt_list_vendas_nparcelas);

        data.setText(vendaRecebida.getData());
        hora.setText(vendaRecebida.getHora());
        cliente.setText(vendaRecebida.getCliente());
        total.setText(vendaRecebida.getVlTotal());
        tipoPagamento.setText(vendaRecebida.getFormaPagamento());

        String snParcelas = vendaRecebida.getParcelasCC();
        try{
        if(snParcelas != null || !snParcelas.equals("")) {
            condicional.setText("Parcelas: "+vendaRecebida.getParcelasCC()+" | "+"Valor: R$"+vendaRecebida.getVlParcelas());
        }}catch (NullPointerException n){
            n.printStackTrace();
        }
        if (tipoPagamento.getText().equals("Conta Cliente")){
            condicional.setText("Data vencimento: "+vendaRecebida.getDataVencimento());
        }
    }

    private void configuraAdapter(){
        adapter = new ListaInfoProdutosVendaAdapter(this,produtos);
        dao = VendasDatabase.getInstance(this).getVendaDAO();
    }

    private void configuraLista(){
        listaProdutos = findViewById(R.id.info_list_produtos_venda);
        listaProdutos.setAdapter(adapter);
        //TIPO DE LAYOUT
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listaProdutos.setLayoutManager(layoutManager);
    }
}
