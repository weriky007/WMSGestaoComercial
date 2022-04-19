package com.alphazer0.wmsgestaocomercial.ui.activities.financas;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.alphazer0.wmsgestaocomercial.R;
import com.alphazer0.wmsgestaocomercial.database.ContasRecebidasDatabase;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomContasRecebidasDAO;
import com.alphazer0.wmsgestaocomercial.model.ContaRecebida;
import com.alphazer0.wmsgestaocomercial.ui.adapters.ListaContasARecebidasAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListaContasRecebidasActivity extends AppCompatActivity {

    public static final String CONTAS_RECEBIDAS = "Contas Recebidas";
    private ListView listViewContasRecebidas;
    private ListaContasARecebidasAdapter listaContasARecebidasAdapter;
    private RoomContasRecebidasDAO contasRecebidasDAO;
    private List<ContaRecebida> listaContasRecebidas = new ArrayList<>();
//==================================================================================================
    @Override
    protected void onCreate(Bundle savedIntanceState){
        super.onCreate(savedIntanceState);
        setContentView(R.layout.activity_lista_contas_recebidas);
        setTitle(CONTAS_RECEBIDAS);
        mantemAtelaEmModoRetrato();

        configuraAdapter();
        pegaDadosBd();
        configuraLista();
    }

    private void mantemAtelaEmModoRetrato() {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    private void configuraAdapter(){
        listaContasARecebidasAdapter = new ListaContasARecebidasAdapter(listaContasRecebidas);
    }

    private void pegaDadosBd(){
        contasRecebidasDAO = ContasRecebidasDatabase.getInstance(this).getContasRecebidasDAO();
    }

    private void configuraLista(){
        listViewContasRecebidas = findViewById(R.id.list_view_lista_contas_recebidas);
        listViewContasRecebidas.setAdapter(listaContasARecebidasAdapter);
    }
//==================================================================================================
    @Override
    public void onBackPressed(){
      startActivity(new Intent(ListaContasRecebidasActivity.this, FinancasPrincipalActivity.class));
      finish();
    }
//==================================================================================================
}
