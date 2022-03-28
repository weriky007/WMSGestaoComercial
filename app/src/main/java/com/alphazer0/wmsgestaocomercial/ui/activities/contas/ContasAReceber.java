package com.alphazer0.wmsgestaocomercial.ui.activities.contas;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.alphazer0.wmsgestaocomercial.R;

public class ContasAReceber extends AppCompatActivity {

    public static final String CONTAS_A_RECEBER = "Contas a Receber";

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setTitle(CONTAS_A_RECEBER);
        setContentView(R.layout.activity_contas_a_receber);
    }
}
