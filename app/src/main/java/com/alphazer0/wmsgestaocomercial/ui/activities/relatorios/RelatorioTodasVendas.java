package com.alphazer0.wmsgestaocomercial.ui.activities.relatorios;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alphazer0.wmsgestaocomercial.R;
import com.alphazer0.wmsgestaocomercial.database.VendasDatabase;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomVendasDAO;
import com.alphazer0.wmsgestaocomercial.model.Venda;
import com.alphazer0.wmsgestaocomercial.ui.adapters.ListaVendasAdapter;

import java.util.ArrayList;
import java.util.List;

public class RelatorioTodasVendas extends AppCompatActivity {
    private RecyclerView listaTodasVendas;
    private List<Venda> vendas = new ArrayList<>();
    private RoomVendasDAO dao;
    private ListaVendasAdapter adapter;
//==================================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_relatorio_todas_vendas);
        setTitle("Historico de Vendas");

        configuraAdapter();
        configuraLista();
    }
//==================================================================================================
    @Override
    protected void onResume() {
        super.onResume();
        adapter.atualizaListaVendas(dao.todasVendas());
    }
//==================================================================================================
    private void configuraLista() {
        listaTodasVendas = findViewById(R.id.lista_vendas);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listaTodasVendas.setLayoutManager(layoutManager);
        listaTodasVendas.setItemAnimator(new DefaultItemAnimator());
        listaTodasVendas.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        listaTodasVendas.setAdapter(adapter);
    }
//==================================================================================================
    private void configuraAdapter(){
        adapter = new ListaVendasAdapter(vendas);
        dao = VendasDatabase.getInstance(this).getVendaDAO();
    }
//==================================================================================================
}
