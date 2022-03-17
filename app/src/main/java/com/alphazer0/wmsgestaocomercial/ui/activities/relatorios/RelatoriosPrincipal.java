package com.alphazer0.wmsgestaocomercial.ui.activities.relatorios;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.alphazer0.wmsgestaocomercial.R;

public class RelatoriosPrincipal extends AppCompatActivity {
    private ImageButton historicoVendas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_relatorios_principal);
        setTitle("Relatórios");

        historicoVendas = findViewById(R.id.btnPrincipalHistoricoVendasActivity);
        configuraBotoes();
    }

    private void configuraBotoes(){
        historicoVendas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RelatoriosPrincipal.this,RelatorioTodasVendas.class));
            }
        });
    }
}
