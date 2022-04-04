package com.alphazer0.wmsgestaocomercial.ui.activities.contas;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alphazer0.wmsgestaocomercial.R;
import com.alphazer0.wmsgestaocomercial.database.ContasAPagarDatabase;
import com.alphazer0.wmsgestaocomercial.database.ContasAReceberDatabase;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomContaAPagarDAO;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomContaAReceberDAO;
import com.alphazer0.wmsgestaocomercial.model.ContaAPagar;
import com.alphazer0.wmsgestaocomercial.model.ContaAReceber;
import com.alphazer0.wmsgestaocomercial.ui.activities.leitor_codigo_barras.ScanCode;
import com.alphazer0.wmsgestaocomercial.ui.adapters.ListaContasAPagarAdapter;
import com.alphazer0.wmsgestaocomercial.ui.adapters.ListaContasAReceberAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ListaContasAReceberActivity extends AppCompatActivity {

    public static final String CONTAS_A_RECEBER = "Contas a Receber";
    private TextView vlTotalContasAReceber;
    private FloatingActionButton fabAddContaAReceber;
    private ListView listaContasAReceber;
    private List<ContaAReceber> contasAReceber = new ArrayList<>();
    private ListaContasAReceberAdapter adapter;
    private RoomContaAReceberDAO dao;
    private final Context context = this;

    private EditText campoCodBarras;
    private EditText campoConta;
    private EditText campoValor;
    private EditText campoDataVencimento;
    private FloatingActionButton fabLerCodigo;
    private ScanCode scanCode = new ScanCode();
    private Activity activity = this;
//==================================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(CONTAS_A_RECEBER);
        setContentView(R.layout.activity_contas_a_receber);
        telaEmModoRetrado();

        vlTotalContasAReceber = findViewById(R.id.valor_contas_a_receber);

        configuraAdapter();
        configuraLista();
        configuraFabAddContaAReceber();
    }
//==================================================================================================
    private void telaEmModoRetrado() {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
    }

    private void configuraAdapter() {
        adapter = new ListaContasAReceberAdapter(contasAReceber);
        dao = ContasAReceberDatabase.getInstance(this).getContasAReceberDAO();
    }

    private void configuraLista(){
        listaContasAReceber = findViewById(R.id.list_view_lista_contas_a_receber);
        listaContasAReceber.setAdapter(adapter);
    }

    private void configuraFabAddContaAReceber() {
        fabAddContaAReceber = findViewById(R.id.fab_adiciona_conta_a_receber);
        fabAddContaAReceber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
//==================================================================================================
}
