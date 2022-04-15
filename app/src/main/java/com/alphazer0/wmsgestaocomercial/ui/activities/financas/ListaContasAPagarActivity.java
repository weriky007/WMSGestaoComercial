package com.alphazer0.wmsgestaocomercial.ui.activities.financas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alphazer0.wmsgestaocomercial.R;
import com.alphazer0.wmsgestaocomercial.database.ContasAPagarDatabase;
import com.alphazer0.wmsgestaocomercial.database.TotalContasAPagarDatabase;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomContaAPagarDAO;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomTotalContasAPagarDAO;
import com.alphazer0.wmsgestaocomercial.model.ContaAPagar;
import com.alphazer0.wmsgestaocomercial.model.TotalContasAPagar;
import com.alphazer0.wmsgestaocomercial.ui.activities.leitor_codigo_barras.ScanCode;
import com.alphazer0.wmsgestaocomercial.ui.adapters.ListaContasAPagarAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ListaContasAPagarActivity extends AppCompatActivity {

    public static final String CONTAS_A_PAGAR = "Contas a Pagar";
    public static final String ADICIONAR_CONTA_A_PAGAR = "Adicionar Conta a Pagar";
    public static final String CONCLUIR = "Concluir";
    public static final String CANCELAR = "Cancelar";
    private TextView vlTotalContasAPagar;
    private FloatingActionButton fabAddContaAPagar;
    private ListView listViewContasAPagar;
    private List<ContaAPagar> listaContasAPagar = new ArrayList<>();
    private ListaContasAPagarAdapter adapter;
    private RoomContaAPagarDAO contaAPagarDAO;
    private RoomTotalContasAPagarDAO totalContasAPagarDAO;
    private final Context context = this;

    private EditText campoCodBarras;
    private EditText campoConta;
    private EditText campoValor;
    private EditText campoDataVencimento;
    private FloatingActionButton fabLerCodigo;
    private ScanCode scanCode = new ScanCode();
    private Activity activity = this;
    private List<TotalContasAPagar> listTotal = new ArrayList<>();

    //==================================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(CONTAS_A_PAGAR);
        setContentView(R.layout.activity_lista_contas_a_pagar);
        mantemAtelaEmModoRetrato();

        bind();
        configuraAdapter();
        configuraLista();
        configuraFabAddContaAPagar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.atualizaListaContasAPagar(contaAPagarDAO.todasContasAPagar());
        if (totalContasAPagarDAO.totalContasAPagar() != null) {
            vlTotalContasAPagar.setText(totalContasAPagarDAO.totalContasAPagar().getTotal());
        } else {
            vlTotalContasAPagar.setText("0.00");
        }
    }

    //==================================================================================================
    private void mantemAtelaEmModoRetrato() {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
    }

    private void bind() {
        vlTotalContasAPagar = findViewById(R.id.valor_contas_a_pagar);
    }

    private void configuraAdapter() {
        adapter = new ListaContasAPagarAdapter(listaContasAPagar);
        contaAPagarDAO = ContasAPagarDatabase.getInstance(this).getContasAPagarDAO();
        totalContasAPagarDAO = TotalContasAPagarDatabase.getInstance(this).getTotalContasAPagarDAO();
    }

    private void configuraLista() {
        listViewContasAPagar = findViewById(R.id.list_view_lista_contas_a_pagar);
        listViewContasAPagar.setAdapter(adapter);
    }

    //==================================================================================================
    private void configuraFabAddContaAPagar() {
        fabAddContaAPagar = findViewById(R.id.fab_adiciona_conta_a_pagar);
        fabAddContaAPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abreFormularioContaAPagar();
            }
        });
    }
//==================================================================================================
    private void abreFormularioContaAPagar() {
        View viewAddContaPagar = LayoutInflater.from(ListaContasAPagarActivity.this)
                .inflate(R.layout.activity_formulario_adiciona_conta_a_pagar, null);

        bindElementos(viewAddContaPagar);
        configuraScanner(viewAddContaPagar);

        ColorDrawable back = new ColorDrawable(Color.WHITE);
        InsetDrawable inset = new InsetDrawable(back, 0);

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage(ADICIONAR_CONTA_A_PAGAR);
        alertDialog.setView(viewAddContaPagar);

        //ADICIONANDO O PRODUTO A LISTA DE COMPRAS
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, CONCLUIR, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //ESTA SOBREESCRITO ABAIXO
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, CANCELAR, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "Cancelado!", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(inset);
        Button btn = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);

        //SOBREESCREVENDO O CLICK DO BOTAO POSITIVO
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContaAPagar contaAPagar = new ContaAPagar();
                contaAPagar.setConta(campoConta.getText().toString());
                contaAPagar.setCodigoBarras(campoCodBarras.getText().toString());
                contaAPagar.setDataVencimento(campoDataVencimento.getText().toString());
                contaAPagar.setVlConta(campoValor.getText().toString());
                contaAPagarDAO.salvaContaAPagar(contaAPagar);
                adapter.atualizaListaContasAPagar(contaAPagarDAO.todasContasAPagar());

                //CALCULA TOTAL DE CONTAS A PAGAR
                listaContasAPagar = contaAPagarDAO.todasContasAPagar();
                BigDecimal btotal = new BigDecimal("0");
                BigDecimal bvlTotal = new BigDecimal("0");
                String svalorRecebido = "";
                for (int i = 0; i < listaContasAPagar.size(); i++) {
                    svalorRecebido = listaContasAPagar.get(i).getVlConta();
                    BigDecimal bvalorRecebido = new BigDecimal(svalorRecebido);
                    bvlTotal = bvlTotal.add(bvalorRecebido);
                }
                btotal = btotal.setScale(2, BigDecimal.ROUND_HALF_EVEN);
                btotal = btotal.add(bvlTotal);
                String stotal = btotal.toString();

                //SALVANDO O TOTAL
                TotalContasAPagar totalContasAPagar = new TotalContasAPagar();
                totalContasAPagar.setTotal(stotal);

                if (totalContasAPagarDAO.totalContasAPagar() == null) {
                    totalContasAPagarDAO.salvaTotal(totalContasAPagar);
                    vlTotalContasAPagar.setText(totalContasAPagar.getTotal());
                } else {
                    int a = totalContasAPagarDAO.totalContasAPagar().getId();
                    totalContasAPagar.setId(a);
                    totalContasAPagarDAO.editaTotal(totalContasAPagar);
                    vlTotalContasAPagar.setText(totalContasAPagar.getTotal());
                }

                alertDialog.dismiss();
            }
        });
    }


    private void bindElementos(View viewAddContaPagar) {
        campoCodBarras = viewAddContaPagar.findViewById(R.id.edit_d_conta_a_pagar_codigo_barras);
        campoConta = viewAddContaPagar.findViewById(R.id.edit_conta_a_pagar);
        campoValor = viewAddContaPagar.findViewById(R.id.edit_valor_conta_a_pagar);
        campoDataVencimento = viewAddContaPagar.findViewById(R.id.edit_data_conta_a_pagar);
    }

    private void configuraScanner(View viewAddContaPagar) {
        fabLerCodigo = viewAddContaPagar.findViewById(R.id.fab_scan_d_conta_pagar);
        fabLerCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanCode.scanCode(activity);
            }
        });
    }
//==================================================================================================
    //PEGA O RESULTADO DO SCANNER
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                campoCodBarras.setText(result.getContents());
                alert(result.getContents());
            } else {
                alert("Scan Cancelado!");
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void alert(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
//==================================================================================================
}
