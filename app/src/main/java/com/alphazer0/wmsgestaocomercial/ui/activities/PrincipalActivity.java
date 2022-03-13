package com.alphazer0.wmsgestaocomercial.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.alphazer0.wmsgestaocomercial.R;
import com.alphazer0.wmsgestaocomercial.ui.activities.clientes.ListaDeClientesActivity;
import com.alphazer0.wmsgestaocomercial.ui.activities.estoque.ListaDeProdutosActivity;
import com.alphazer0.wmsgestaocomercial.ui.activities.fornecedores.FornecedoresActivity;
import com.alphazer0.wmsgestaocomercial.ui.activities.vendas.VendasActivity;

public class PrincipalActivity extends AppCompatActivity {

    private ImageButton btnFornecedores;
    private ImageButton btnClientes;
    private ImageButton btnVendas;
    private ImageButton btnEstoque;
    private ImageButton btnContas;
    private ImageButton btnRelatorios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        bindElementos();
        configuraBotoes();
    }

    private void bindElementos(){
        btnFornecedores = findViewById(R.id.btnPrincipalFornecedoresActivity);
        btnClientes = findViewById(R.id.btnPrincipalClientesActivity);
        btnVendas = findViewById(R.id.btnAdicionaItemNaListaActivity);
        btnEstoque = findViewById(R.id.btnPrincipalEstoqueActivity);
        btnContas = findViewById(R.id.btnPrincipalContasActivity);
        btnRelatorios = findViewById(R.id.btnPrincipalRelatoriosActivity);
    }

    private void configuraBotoes(){
        btnFornecedores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PrincipalActivity.this, FornecedoresActivity.class));
            }
        });

        btnClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PrincipalActivity.this, ListaDeClientesActivity.class));
            }
        });

        btnVendas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PrincipalActivity.this, VendasActivity.class));
            }
        });

        btnEstoque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PrincipalActivity.this, ListaDeProdutosActivity.class));
            }
        });

        btnContas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(PrincipalActivity.this, ListaDeClientesActivity.class));
            }
        });

        btnRelatorios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(PrincipalActivity.this, ListaDeClientesActivity.class));
            }
        });
    }
}