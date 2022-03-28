package com.alphazer0.wmsgestaocomercial.ui.activities.contas;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.alphazer0.wmsgestaocomercial.R;

public class ContasPrincipal extends AppCompatActivity {

    public static final String CONTAS = "Contas";

    @Override
    protected  void onCreate(Bundle savedIntanceState){
        super.onCreate(savedIntanceState);
        setTitle(CONTAS);
        setContentView(R.layout.activity_contas_principal);
    }
}
