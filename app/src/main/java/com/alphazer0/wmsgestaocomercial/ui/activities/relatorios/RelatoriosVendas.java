package com.alphazer0.wmsgestaocomercial.ui.activities.relatorios;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.alphazer0.wmsgestaocomercial.R;

public class RelatoriosVendas extends AppCompatActivity {

    public static final String RELATÓRIOS_VENDAS = "Relatórios Vendas";

    @Override
    protected void onCreate(Bundle savedIntanceState){
        super.onCreate(savedIntanceState);
        setContentView(R.layout.activity_relatorios_vendas);
        setTitle(RELATÓRIOS_VENDAS);
    }
}
