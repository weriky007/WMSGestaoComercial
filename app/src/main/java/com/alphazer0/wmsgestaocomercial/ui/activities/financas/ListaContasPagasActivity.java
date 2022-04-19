package com.alphazer0.wmsgestaocomercial.ui.activities.financas;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import com.alphazer0.wmsgestaocomercial.R;
import com.alphazer0.wmsgestaocomercial.database.ContasPagasDatabase;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomContasPagasDAO;
import com.alphazer0.wmsgestaocomercial.model.ContaPaga;
import com.alphazer0.wmsgestaocomercial.ui.adapters.ListaContasPagasAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListaContasPagasActivity extends AppCompatActivity {

    public static final String CONTAS_PAGAS = "Contas Pagas";
    private ListView listViewContasPagas;
    private RoomContasPagasDAO contasPagasDAO;
    private ListaContasPagasAdapter listaContasPagasAdapter;
    private List<ContaPaga> listaContasPagas = new ArrayList<>();
//==================================================================================================
    @Override
    protected void onCreate(Bundle savedIntanceState){
        super.onCreate(savedIntanceState);
        setContentView(R.layout.activity_lista_contas_pagas);
        setTitle(CONTAS_PAGAS);
        mantemAtelaEmModoRetrato();

        configuraAdapter();
        pegaDadosBD();
        configuraLista();
    }

    private void mantemAtelaEmModoRetrato() {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
    }

    @Override
    protected void onResume(){
        super.onResume();
        listaContasPagasAdapter.atualizaListaContasPagaS(contasPagasDAO.todasContasPagas());
    }

    private void configuraAdapter(){
        listaContasPagasAdapter = new ListaContasPagasAdapter(listaContasPagas);
    }

    private void pegaDadosBD(){
        contasPagasDAO = ContasPagasDatabase.getInstance(this).getContasPagasDAO();
    }

    private void configuraLista(){
        listViewContasPagas = findViewById(R.id.list_view_lista_contas_a_receber);
        listViewContasPagas.setAdapter(listaContasPagasAdapter);
    }
//==================================================================================================
    @Override
    public void onBackPressed(){
        startActivity(new Intent(ListaContasPagasActivity.this, FinancasPrincipalActivity.class));
        finish();
    }
//==================================================================================================
}
