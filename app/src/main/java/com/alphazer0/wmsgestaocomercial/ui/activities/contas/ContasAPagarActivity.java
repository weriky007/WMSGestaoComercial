package com.alphazer0.wmsgestaocomercial.ui.activities.contas;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alphazer0.wmsgestaocomercial.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ContasAPagarActivity extends AppCompatActivity {

    public static final String CONTAS_A_PAGAR = "Contas a Pagar";
    private TextView vlContasAPagar;
    private FloatingActionButton fabAddContaAPagar;
    private ListView listaContasAPagar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setTitle(CONTAS_A_PAGAR);
        setContentView(R.layout.activity_contas_a_pagar);

        vlContasAPagar = findViewById(R.id.valor_contas_a_pagar);
        fabAddContaAPagar = findViewById(R.id.fab_adiciona_conta_a_pagar);
        listaContasAPagar = findViewById(R.id.list_view_lista_contas_a_pagar);
    }
}
