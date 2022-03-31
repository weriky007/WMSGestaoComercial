package com.alphazer0.wmsgestaocomercial.ui.activities.menu;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.alphazer0.wmsgestaocomercial.R;

public class Configuracao extends AppCompatActivity {

    public static final String CONFIGURAÇÕES = "Configurações";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);
        setTitle(CONFIGURAÇÕES);
    }
}
