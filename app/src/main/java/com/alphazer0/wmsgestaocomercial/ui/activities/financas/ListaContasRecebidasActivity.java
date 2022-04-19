package com.alphazer0.wmsgestaocomercial.ui.activities.financas;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.alphazer0.wmsgestaocomercial.R;

public class ListaContasRecebidasActivity extends AppCompatActivity {

    public static final String CONTAS_RECEBIDAS = "Contas Recebidas";
    private ListView listViewContasRecebidas;
//==================================================================================================
    @Override
    protected void onCreate(Bundle savedIntanceState){
        super.onCreate(savedIntanceState);
        setContentView(R.layout.activity_lista_contas_recebidas);
        setTitle(CONTAS_RECEBIDAS);
        mantemAtelaEmModoRetrato();

        //CONFIGURANDO LISTA
        listViewContasRecebidas = findViewById(R.id.list_view_lista_contas_recebidas);
    }

    private void mantemAtelaEmModoRetrato() {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
    }

    @Override
    protected void onResume(){
        super.onResume();
    }
//==================================================================================================
}
