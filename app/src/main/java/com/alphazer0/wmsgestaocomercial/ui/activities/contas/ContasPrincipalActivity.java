package com.alphazer0.wmsgestaocomercial.ui.activities.contas;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.alphazer0.wmsgestaocomercial.R;

public class ContasPrincipalActivity extends AppCompatActivity {

    public static final String CONTAS = "Contas";
    private ImageButton btnContasPagar;
    private ImageButton btnContasReceber;
//==================================================================================================
    @Override
    protected void onCreate(Bundle savedIntanceState) {
        super.onCreate(savedIntanceState);
        setTitle(CONTAS);
        setContentView(R.layout.activity_contas_principal);
        mantemAtelaEmModoRetrato();
        configuraBotoes();
    }

//==================================================================================================
    private void mantemAtelaEmModoRetrato() {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
    }

    private void configuraBotoes() {
        btnContasPagar = findViewById(R.id.btn_contas_a_pagar);
        btnContasReceber = findViewById(R.id.btn_contas_a_receber);

        btnContasPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ContasPrincipalActivity.this, ListaContasAPagarActivity.class));
            }
        });

        btnContasReceber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ContasPrincipalActivity.this, ListaContasAReceberActivity.class));
            }
        });
    }
//==================================================================================================
}
