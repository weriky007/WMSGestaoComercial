package com.alphazer0.wmsgestaocomercial.ui.activities.relatorios;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.alphazer0.wmsgestaocomercial.R;

public class RelatoriosPrincipal extends AppCompatActivity {
    public static final String RELATÓRIOS = "Relatórios";
    private ImageButton historicoVendas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_relatorios_principal);
        setTitle(RELATÓRIOS);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);

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
