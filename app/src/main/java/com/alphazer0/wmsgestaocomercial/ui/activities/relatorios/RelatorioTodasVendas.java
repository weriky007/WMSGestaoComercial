package com.alphazer0.wmsgestaocomercial.ui.activities.relatorios;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alphazer0.wmsgestaocomercial.R;
import com.alphazer0.wmsgestaocomercial.database.VendasDatabase;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomVendasDAO;
import com.alphazer0.wmsgestaocomercial.model.Venda;
import com.alphazer0.wmsgestaocomercial.ui.adapters.ListaTodasVendasAdapter;

import java.util.ArrayList;
import java.util.List;

public class RelatorioTodasVendas extends AppCompatActivity {
    public static final String HISTÓRICO_DE_VENDAS = "Histórico de Vendas";
    private RecyclerView listaTodasVendas;
    private List<Venda> vendas = new ArrayList<>();
    private RoomVendasDAO dao;
    private ListaTodasVendasAdapter adapter;
//==================================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_relatorio_todas_vendas);
        setTitle(HISTÓRICO_DE_VENDAS);

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
        vendas = dao.todasVendas();
        listaTodasVendas.setAdapter(adapter);
        //TIPO DE LAYOUT
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listaTodasVendas.setLayoutManager(layoutManager);
        //COLOCA OS MAIS RECENTES PRIMEIRO NA LISTA
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
    }
//==================================================================================================
    private void configuraAdapter(){
        adapter = new ListaTodasVendasAdapter(this,vendas);
        dao = VendasDatabase.getInstance(this).getVendaDAO();
    }
//==================================================================================================
    //MENU CLICK ITEM


//==================================================================================================
}
