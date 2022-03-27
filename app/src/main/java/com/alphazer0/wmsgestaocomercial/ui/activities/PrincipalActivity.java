package com.alphazer0.wmsgestaocomercial.ui.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alphazer0.wmsgestaocomercial.R;
import com.alphazer0.wmsgestaocomercial.ui.activities.clientes.ListaDeClientesActivity;
import com.alphazer0.wmsgestaocomercial.ui.activities.estoque.ListaDeProdutosActivity;
import com.alphazer0.wmsgestaocomercial.ui.activities.fornecedores.FornecedoresActivity;
import com.alphazer0.wmsgestaocomercial.ui.activities.relatorios.RelatorioTodasVendas;
import com.alphazer0.wmsgestaocomercial.ui.activities.relatorios.RelatoriosPrincipal;
import com.alphazer0.wmsgestaocomercial.ui.activities.vendas.VendasActivity;

public class PrincipalActivity extends AppCompatActivity {

    public static final String WMS_GESTÃO_COMERCIAL = "WMS Gestão Comercial";
    private ImageButton btnFornecedores;
    private ImageButton btnClientes;
    private ImageButton btnVendas;
    private ImageButton btnEstoque;
    private ImageButton btnContas;
    private ImageButton btnRelatorios;

//==================================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        setTitle(WMS_GESTÃO_COMERCIAL);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
        bindElementos();
        configuraBotoes();
    }
//==================================================================================================
    private void bindElementos() {
        btnFornecedores = findViewById(R.id.btnPrincipalFornecedoresActivity);
        btnClientes = findViewById(R.id.btnPrincipalClientesActivity);
        btnVendas = findViewById(R.id.btnAdicionaItemNaListaActivity);
        btnEstoque = findViewById(R.id.btnPrincipalEstoqueActivity);
        btnContas = findViewById(R.id.btnPrincipalContasActivity);
        btnRelatorios = findViewById(R.id.btnPrincipalRelatoriosActivity);
    }
//==================================================================================================
    //MENU APPBAR
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_principal, menu);
//        MenuItem config = menu.add(0,0,0,"Configurações");
//        config.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
//        MenuItem manual = menu.add(0,1,1,"Manual");
//        manual.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case 0:
                Toast.makeText(this, ""+item.getItemId(), Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(this, ""+item.getItemId(), Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
//==================================================================================================
    private void configuraBotoes() {
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

            }
        });

        btnRelatorios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PrincipalActivity.this, RelatoriosPrincipal.class));
            }
        });
    }
//==================================================================================================
}