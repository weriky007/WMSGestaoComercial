package com.alphazer0.wmsgestaocomercial.ui.activities.financas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import androidx.appcompat.app.AppCompatActivity;

import com.alphazer0.wmsgestaocomercial.R;
import com.alphazer0.wmsgestaocomercial.database.ClientesDatabase;
import com.alphazer0.wmsgestaocomercial.database.ContasAReceberDatabase;
import com.alphazer0.wmsgestaocomercial.database.TotalContasAReceberDatabase;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomClienteDAO;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomContaAReceberDAO;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomTotalContasAReceberDAO;
import com.alphazer0.wmsgestaocomercial.model.Cliente;
import com.alphazer0.wmsgestaocomercial.model.ContaAReceber;
import com.alphazer0.wmsgestaocomercial.model.TotalContasAReceber;
import com.alphazer0.wmsgestaocomercial.ui.activities.leitor_codigo_barras.ScanCode;
import com.alphazer0.wmsgestaocomercial.ui.adapters.ListaContasAReceberAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ListaContasAReceberActivity extends AppCompatActivity {

    public static final String CONTAS_A_RECEBER = "Contas a Receber";
    public static final String ADICIONA_CONTA_A_RECEBER = "Adiciona Conta a Receber";
    public static final String CONCLUIR = "Concluir";
    public static final String CANCELAR = "Cancelar";
    private TextView vlTotalContasAReceber;
    private FloatingActionButton fabAddContaAReceber;
    private ListView listaContasAReceber;
    private List<ContaAReceber> contasAReceber = new ArrayList<>();
    private List<Cliente> listaClientes = new ArrayList<>();
    private ListaContasAReceberAdapter adapter;
    private RoomContaAReceberDAO contaAReceberDAO;
    private RoomTotalContasAReceberDAO totalContasAReceberDAO;
    private RoomClienteDAO clienteDAO;
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
        setContentView(R.layout.activity_lista_contas_a_receber);
        telaEmModoRetrado();

        bind();
        configuraAdapter();
        configuraLista();
        configuraFabAddContaAReceber();
        pegaContasClientes();
        calculaTotalContasAReceber();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.atualizaListaContasAReceber(contaAReceberDAO.todasContaAReceber());
        if (totalContasAReceberDAO.totalContasAReceber() != null) {
            vlTotalContasAReceber.setText(totalContasAReceberDAO.totalContasAReceber().getTotal());
        } else {
            vlTotalContasAReceber.setText("0.00");
        }
    }
//==================================================================================================
    private void telaEmModoRetrado() {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
    }

    private void bind() {
        vlTotalContasAReceber = findViewById(R.id.valor_contas_a_receber);
    }

    private void configuraAdapter() {
        adapter = new ListaContasAReceberAdapter(contasAReceber);
        contaAReceberDAO = ContasAReceberDatabase.getInstance(this).getContasAReceberDAO();
        totalContasAReceberDAO = TotalContasAReceberDatabase.getInstance(this).getTotalContasAReceberDAO();
        clienteDAO = ClientesDatabase.getInstance(this).getClienteDAO();
    }

    private void configuraLista() {
        listaContasAReceber = findViewById(R.id.list_view_lista_contas_a_receber);
        listaContasAReceber.setAdapter(adapter);
    }
//==================================================================================================
    private void configuraFabAddContaAReceber() {
        fabAddContaAReceber = findViewById(R.id.fab_adiciona_conta_a_receber);
        fabAddContaAReceber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abreFormularioContaAReceber();
            }
        });
    }
//==================================================================================================
    private void abreFormularioContaAReceber() {
        View viewAddContaReceber = LayoutInflater.from(ListaContasAReceberActivity.this)
                .inflate(R.layout.activity_formulario_adiciona_conta_a_receber, null);

        bindElementos(viewAddContaReceber);
        configuraScanner(viewAddContaReceber);

        ColorDrawable back = new ColorDrawable(Color.WHITE);
        InsetDrawable inset = new InsetDrawable(back, 0);

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage(ADICIONA_CONTA_A_RECEBER);
        alertDialog.setView(viewAddContaReceber);

        //ADICIONANDO A CONTA A LISTA
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
                ContaAReceber contaAReceber = new ContaAReceber();
                pegaDadosDosCampos(contaAReceber);
                salvaContaAReceberNoBancoDeDados(contaAReceber);
                atualizaListaAdapter();
                calculaTotalContasAReceber();
                alertDialog.dismiss();
            }
        });
    }

    //METODOS ALERTDIALOG POSITIVE
    private void salvaTotalNoBD(TotalContasAReceber totalContasAReceber) {
        if (totalContasAReceberDAO.totalContasAReceber() == null) {
            totalContasAReceberDAO.salvaTotal(totalContasAReceber);
            vlTotalContasAReceber.setText(totalContasAReceber.getTotal());
        } else {
            int a = totalContasAReceberDAO.totalContasAReceber().getId();
            totalContasAReceber.setId(a);
            totalContasAReceberDAO.editaTotal(totalContasAReceber);
            vlTotalContasAReceber.setText(totalContasAReceber.getTotal());
        }
    }

    private void calculaTotalContasAReceber() {
        contasAReceber = contaAReceberDAO.todasContaAReceber();

        BigDecimal btotal = new BigDecimal("0");
        BigDecimal bvlTotal = new BigDecimal("0");
        String svalorRecebido = "";
        for (int i = 0; i < contasAReceber.size(); i++) {
            svalorRecebido = contasAReceber.get(i).getVlConta();
            BigDecimal bvalorRecebido = new BigDecimal(svalorRecebido);
            bvlTotal = bvlTotal.add(bvalorRecebido);
        }
        btotal = btotal.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        btotal = btotal.add(bvlTotal);
        String stotal = btotal.toString();

        TotalContasAReceber totalContasAReceber = new TotalContasAReceber();
        totalContasAReceber.setTotal(stotal);
        salvaTotalNoBD(totalContasAReceber);
    }

    private void atualizaListaAdapter() {
        adapter.atualizaListaContasAReceber(contaAReceberDAO.todasContaAReceber());
    }

    private void salvaContaAReceberNoBancoDeDados(ContaAReceber contaAReceber) {
        contaAReceberDAO.salvaContaAReceber(contaAReceber);
    }

    private void pegaDadosDosCampos(ContaAReceber contaAReceber) {
        contaAReceber.setConta(campoConta.getText().toString());
        contaAReceber.setCodigoBarras(campoCodBarras.getText().toString());
        contaAReceber.setDataVencimento(campoDataVencimento.getText().toString());
        contaAReceber.setVlConta(campoValor.getText().toString());
    }


    private void bindElementos(View viewAddContaReceber) {
        campoCodBarras = viewAddContaReceber.findViewById(R.id.edit_d_conta_a_receber_codigo_barras);
        campoConta = viewAddContaReceber.findViewById(R.id.edit_conta_a_receber);
        campoValor = viewAddContaReceber.findViewById(R.id.edit_valor_conta_a_receber);
        campoDataVencimento = viewAddContaReceber.findViewById(R.id.edit_data_conta_a_receber);
    }

    private void configuraScanner(View viewAddContaReceber) {
        fabLerCodigo = viewAddContaReceber.findViewById(R.id.fab_scan_d_conta_receber);
        fabLerCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanCode.scanCode(activity);
            }
        });
    }

    private void pegaContasClientes(){
        ContaAReceber contaCliente = new ContaAReceber();
        contasAReceber = contaAReceberDAO.todasContaAReceber();
        listaClientes = clienteDAO.todosClientes();
        List<String> contaList = new ArrayList<>();

        for(Cliente cliente : listaClientes){
            double divCliente = Double.parseDouble(cliente.getDivida());
            if(divCliente > 0){
                if(contasAReceber.size() ==0){
                    contaCliente.setConta(cliente.getNomeCompleto());
                    contaCliente.setDataVencimento(cliente.getDataVencimento());
                    contaCliente.setVlConta(cliente.getDivida());
                    contaAReceberDAO.salvaContaAReceber(contaCliente);
                }else{
                    verificaSeContaClienteJaFoiAdicionada(contaCliente, contaList, cliente);
                }
            }
        }
    }

    private void verificaSeContaClienteJaFoiAdicionada(ContaAReceber contaCliente, List<String> contaList, Cliente cliente) {
        for (int i =0; i<contasAReceber.size();i++){
            contaList.add(contasAReceber.get(i).getConta());
        }
        if(!contaList.contains(cliente.getNomeCompleto())){
            contaCliente.setConta(cliente.getNomeCompleto());
            contaCliente.setDataVencimento(cliente.getDataVencimento());
            contaCliente.setVlConta(cliente.getDivida());
            contaAReceberDAO.salvaContaAReceber(contaCliente);
        }
    }
//==================================================================================================
}
