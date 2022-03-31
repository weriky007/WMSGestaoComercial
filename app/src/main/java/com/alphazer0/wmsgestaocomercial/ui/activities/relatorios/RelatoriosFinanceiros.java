package com.alphazer0.wmsgestaocomercial.ui.activities.relatorios;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.alphazer0.wmsgestaocomercial.R;

public class RelatoriosFinanceiros extends AppCompatActivity {

    public static final String RELATÓRIOS_FINANCEIROS = "Relatórios Financeiros";

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorios_financeiros);
        setTitle(RELATÓRIOS_FINANCEIROS);
    }
}
