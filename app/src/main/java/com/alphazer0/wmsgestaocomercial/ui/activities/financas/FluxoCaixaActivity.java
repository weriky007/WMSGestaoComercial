package com.alphazer0.wmsgestaocomercial.ui.activities.financas;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.alphazer0.wmsgestaocomercial.R;

public class FluxoCaixaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedIntanceState){
        super.onCreate(savedIntanceState);
        setContentView(R.layout.activity_lista_fluxo_caixa);
        setTitle("Fluxo de Caixa");
    }
}
