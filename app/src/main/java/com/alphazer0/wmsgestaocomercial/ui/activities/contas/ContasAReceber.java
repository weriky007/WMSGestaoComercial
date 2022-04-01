package com.alphazer0.wmsgestaocomercial.ui.activities.contas;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alphazer0.wmsgestaocomercial.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ContasAReceber extends AppCompatActivity {

    public static final String CONTAS_A_RECEBER = "Contas a Receber";
    private TextView vlTotalContasAReceber;
    private FloatingActionButton fabAddContaAReceber;
    private ListView listaContasAReceber;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setTitle(CONTAS_A_RECEBER);
        setContentView(R.layout.activity_contas_a_receber);

        vlTotalContasAReceber = findViewById(R.id.valor_contas_a_receber);
        fabAddContaAReceber = findViewById(R.id.fab_adiciona_conta_a_receber);
        listaContasAReceber = findViewById(R.id.list_view_lista_contas_a_receber);
    }
}
