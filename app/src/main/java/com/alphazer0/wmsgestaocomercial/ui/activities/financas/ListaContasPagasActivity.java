package com.alphazer0.wmsgestaocomercial.ui.activities.financas;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.alphazer0.wmsgestaocomercial.R;
public class ListaContasPagasActivity extends AppCompatActivity {

    public static final String CONTAS_PAGAS = "Contas Pagas";
//==================================================================================================
    @Override
    protected void onCreate(Bundle savedIntanceState){
        super.onCreate(savedIntanceState);
        setContentView(R.layout.activity_lista_contas_pagas);
        setTitle(CONTAS_PAGAS);
        mantemAtelaEmModoRetrato();
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
