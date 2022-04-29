package com.alphazer0.wmsgestaocomercial.ui.activities.financas;

import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.MASK_DATA;

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
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alphazer0.wmsgestaocomercial.R;
import com.alphazer0.wmsgestaocomercial.database.ClientesDatabase;
import com.alphazer0.wmsgestaocomercial.database.ContasAReceberDatabase;
import com.alphazer0.wmsgestaocomercial.database.ContasRecebidasDatabase;
import com.alphazer0.wmsgestaocomercial.database.TotalCaixaDatabase;
import com.alphazer0.wmsgestaocomercial.database.TotalContasAReceberDatabase;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomClienteDAO;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomContaAReceberDAO;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomContasRecebidasDAO;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomTotalCaixaDAO;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomTotalContasAReceberDAO;
import com.alphazer0.wmsgestaocomercial.model.Cliente;
import com.alphazer0.wmsgestaocomercial.model.ContaAReceber;
import com.alphazer0.wmsgestaocomercial.model.ContaRecebida;
import com.alphazer0.wmsgestaocomercial.model.MaskText;
import com.alphazer0.wmsgestaocomercial.model.TotalCaixa;
import com.alphazer0.wmsgestaocomercial.model.TotalContasAReceber;
import com.alphazer0.wmsgestaocomercial.ui.activities.leitor_codigo_barras.ScanCode;
import com.alphazer0.wmsgestaocomercial.ui.adapters.ListaContasAReceberAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListaContasAReceberActivity extends AppCompatActivity {

    public static final String CONTAS_A_RECEBER = "Contas a Receber";
    public static final String ADICIONA_CONTA_A_RECEBER = "Adiciona Conta a Receber";
    public static final String CONCLUIR = "Concluir";
    public static final String CANCELAR = "Cancelar";
    private TextView vlTotalContasAReceber;
    private FloatingActionButton fabAddContaAReceber;
    private ListView listViewContasAReceber;
    private List<ContaAReceber> listaContasAReceber = new ArrayList<>();
    private List<Cliente> listaClientes = new ArrayList<>();
    private ListaContasAReceberAdapter contaAReceberAdapter;

    private RoomContaAReceberDAO contaAReceberDAO;
    private RoomContasRecebidasDAO contasRecebidasDAO;
    private RoomTotalContasAReceberDAO totalContasAReceberDAO;
    private RoomClienteDAO clienteDAO;
    private RoomTotalCaixaDAO totalCaixaDAO;

    private EditText campoCodBarras;
    private EditText campoConta;
    private EditText campoValor;
    private EditText campoDataVencimento;
    private FloatingActionButton fabLerCodigo;
    private ScanCode scanCode = new ScanCode();
    private Activity activity = this;
    private Date dataSelect;
    private final Context context = this;
//==================================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(CONTAS_A_RECEBER);
        setContentView(R.layout.activity_lista_contas_a_receber);
        telaEmModoRetrado();

        bind();
        configuraAdapter();
        carregaOsDadosDosBDs();
        configuraLista();
        pegaContasClientes();
        configuraFabAddContaAReceber();
        calculaTotalContasAReceber();
    }

    @Override
    protected void onResume() {
        super.onResume();
        contaAReceberAdapter.atualizaListaContasAReceber(contaAReceberDAO.todasContaAReceber());
        
        //CONFIGURA O TOTAL PARA QUE CARREGUE DE FORMA AUTOMATICA
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
        contaAReceberAdapter = new ListaContasAReceberAdapter(listaContasAReceber);
    }

    private void carregaOsDadosDosBDs() {
        contaAReceberDAO = ContasAReceberDatabase.getInstance(this).getContasAReceberDAO();
        totalContasAReceberDAO = TotalContasAReceberDatabase.getInstance(this).getTotalContasAReceberDAO();
        clienteDAO = ClientesDatabase.getInstance(this).getClienteDAO();
        contasRecebidasDAO = ContasRecebidasDatabase.getInstance(this).getContasRecebidasDAO();
        totalCaixaDAO = TotalCaixaDatabase.getInstance(this).getTotalCaixaDAO();
    }

    private void configuraLista() {
        listViewContasAReceber = findViewById(R.id.list_view_lista_contas_a_receber);
        listViewContasAReceber.setAdapter(contaAReceberAdapter);
        registerForContextMenu(listViewContasAReceber);
    }
//==================================================================================================
    //MENU ITENS LISTA
    @Override
    public void onCreateContextMenu(ContextMenu menu, View view,ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,view,menuInfo);
        getMenuInflater().inflate(R.menu.menu_informar_recebimento_conta,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        int itemID = item.getItemId();
        if(itemID == R.id.item_informar_recebimento_contar){
            confirmaRecebimento(item);
        }
        return super.onContextItemSelected(item);
    }
//==================================================================================================
    public  void confirmaRecebimento(final  MenuItem item){
        AlertDialog alertRecebimento = new AlertDialog.Builder(this).create();
        alertRecebimento.setTitle("Informar Recebimento de Conta");
        alertRecebimento.setMessage("Se esta conta foi recebida basta clicar em concluir");
        alertRecebimento.setButton(DialogInterface.BUTTON_POSITIVE, "Concluir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ContaAReceber contaAReceber = pegaContaAReceber(item);
                ContaRecebida contaRecebida = new ContaRecebida();

                insereDadosNaContaRecebida(contaAReceber, contaRecebida);
                acaoBDs(contaAReceber, contaRecebida);
                removeContaDoCliente(contaAReceber);

                //SOMANDO VALOR RECEBIDO AO CAIXA
                String sValorConta = contaAReceber.getVlConta();
                String sValorCaixa;

                if(totalCaixaDAO.totalCaixa() == null){
                    sValorCaixa = "0.00";
                }else if(totalCaixaDAO.totalCaixa() != null) {
                    sValorCaixa = totalCaixaDAO.totalCaixa().getTotal();
                }else{
                    sValorCaixa = totalCaixaDAO.totalCaixa().getTotal();
                }


                BigDecimal bVlConta = new BigDecimal(sValorConta);
                BigDecimal bVlCaixa = new BigDecimal(sValorCaixa);
                BigDecimal bResultado = new BigDecimal("0");
                bResultado = bVlCaixa.add(bVlConta);

                String sResult = bResultado.toString();
                TotalCaixa  totalCaixa  = new TotalCaixa();
                totalCaixa.setTotal(sResult);

                //REMOVE DIVIDA CLIENTE
                if(totalCaixaDAO.totalCaixa() == null){
                    totalCaixaDAO.salvaTotal(totalCaixa);
                    Toast.makeText(activity, "Salvando", Toast.LENGTH_SHORT).show();
                }else if(totalCaixaDAO.totalCaixa() != null){
                    int id = totalCaixaDAO.totalCaixa().getId();
                    totalCaixa.setId(id);
                    totalCaixaDAO.editaTotal(totalCaixa);
                    Toast.makeText(activity, "Editando", Toast.LENGTH_SHORT).show();
                }

                atualizaListaAdapter();
                calculaTotalContasAReceber();
            }
        });

        alertRecebimento.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "Cancelado!", Toast.LENGTH_SHORT).show();
                alertRecebimento.dismiss();
            }
        });
        alertRecebimento.show();
    }
//==================================================================================================
    private void removeContaDoCliente(ContaAReceber contaAReceber) {
        Cliente clienteConta = new Cliente();
        String sNome = contaAReceber.getConta();
        String sValor = contaAReceber.getVlConta();

        for(Cliente cliente : listaClientes){
            if(cliente.getNomeCompleto().equals(sNome) && cliente.getDivida().equals(sValor)){
                clienteConta = cliente;
            }
        }
        if(clienteConta.getNomeCompleto() != null){
            clienteConta.setDivida("0");
            clienteDAO.editaCliente(clienteConta);
        }
    }

    private void acaoBDs(ContaAReceber contaAReceber, ContaRecebida contaRecebida) {
        contasRecebidasDAO.salvaContaRecebida(contaRecebida);
        contaAReceberDAO.removeContaAReceber(contaAReceber);
        listaContasAReceber.remove(contaAReceber);
    }

    private void insereDadosNaContaRecebida(ContaAReceber contaAReceber, ContaRecebida contaRecebida) {
        //CONFIGURA DATA E HORA
        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formataHora = new SimpleDateFormat("HH:mm:ss");
        Date dataAtual = new Date();
        Date horaAtual = new Date();
        String dataFormatada = formataData.format(dataAtual);
        String horaFormatada = formataHora.format(horaAtual);

        contaRecebida.setConta(contaAReceber.getConta());
        contaRecebida.setCodigoBarras(contaAReceber.getCodigoBarras());
        contaRecebida.setDataRecebimento(dataFormatada);
        contaRecebida.setHoraRecebimento(horaFormatada);
        contaRecebida.setVlConta(contaAReceber.getVlConta());
    }

    private ContaAReceber pegaContaAReceber(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        return contaAReceberAdapter.getItem(menuInfo.position);
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
        //CRIANDO VIEW  QUE INFLARA O ALERTDIALOG
        View viewAddContaReceber = LayoutInflater.from(ListaContasAReceberActivity.this)
                .inflate(R.layout.activity_formulario_adiciona_conta_a_receber, null);

        bindElementos(viewAddContaReceber);
        configuraScanner(viewAddContaReceber);
        configuraAlertDialog(viewAddContaReceber);
    }
//==================================================================================================
    private void configuraAlertDialog(View viewAddContaReceber) {
        //CONFIGURA DATA
        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        Date dataAtual = new Date();
        dataSelect = new Date();

        //CONFIGURACOES DE CORES E MARGEM ALERT DIALOG
        ColorDrawable back = new ColorDrawable(Color.WHITE);
        InsetDrawable inset = new InsetDrawable(back, 0);

        //CRIANDO O ALERTDIALOG
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

        //BOTAO NEGATIVO ALERTDIALOG
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, CANCELAR, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "Cancelado!", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });

        //EXECUTANDO O ALERTDIALOG
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(inset);
        Button btn = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);

        //SOBREESCREVENDO O CLICK DO BOTAO POSITIVO
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContaAReceber contaAReceber = new ContaAReceber();
                realizaVerificacao(contaAReceber, formataData, dataAtual, alertDialog);

            }
        });
    }
//==================================================================================================
    private void realizaVerificacao(ContaAReceber contaAReceber, SimpleDateFormat formataData, Date dataAtual, AlertDialog alertDialog) {
        if(campoConta.getText().toString().equals("") || campoConta.getText().toString() == null){
            Toast.makeText(context, "Preencha o campo Conta", Toast.LENGTH_SHORT).show();
        }else{
            if(campoValor.getText().toString().equals("") || campoValor.getText().toString() ==null){
                Toast.makeText(context, "Preencha o campo valor", Toast.LENGTH_SHORT).show();
            }else{
                Double d  = Double.parseDouble(campoValor.getText().toString());
                if(d <= 0){
                    Toast.makeText(context, "O valor tem que ser maior que Zero", Toast.LENGTH_SHORT).show();
                }else{
                    if(campoDataVencimento.getText().toString().equals("") || campoDataVencimento.getText().toString() ==null){
                        Toast.makeText(context, "Preencha a Data", Toast.LENGTH_SHORT).show();
                    }else{
                        configuraDataSelecionada(formataData);
                        if(dataSelect.before(dataAtual)){
                            Toast.makeText(context, "Escolha uma data posterior ao dia de hoje ", Toast.LENGTH_SHORT).show();
                        }else {
                            concluiCadastro(contaAReceber, alertDialog);
                        }
                    }
                }
            }
        }
    }
//==================================================================================================
    private void concluiCadastro(ContaAReceber contaAReceber, AlertDialog alertDialog) {
        pegaDadosDosCampos(contaAReceber);
        salvaContaAReceberNoBancoDeDados(contaAReceber);
        atualizaListaAdapter();
        calculaTotalContasAReceber();
        alertDialog.dismiss();
    }
//==================================================================================================
    private void configuraDataSelecionada(SimpleDateFormat formataData) {
        String sCampoData = campoDataVencimento.getText().toString();
        try {
            dataSelect = formataData.parse(sCampoData);
        }catch (ParseException e){
            e.printStackTrace();
        }
    }
//==================================================================================================
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
        listaContasAReceber = contaAReceberDAO.todasContaAReceber();

        BigDecimal btotal = new BigDecimal("0");
        BigDecimal bvlTotal = new BigDecimal("0");
        String svalorRecebido = "";
        for (int i = 0; i < listaContasAReceber.size(); i++) {
            svalorRecebido = listaContasAReceber.get(i).getVlConta();
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
        contaAReceberAdapter.atualizaListaContasAReceber(contaAReceberDAO.todasContaAReceber());
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

        campoDataVencimento.addTextChangedListener(MaskText.insert(MASK_DATA, campoDataVencimento));
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

    private void pegaContasClientes() {
        ContaAReceber contaCliente = new ContaAReceber();
        listaContasAReceber = contaAReceberDAO.todasContaAReceber();
        listaClientes = clienteDAO.todosClientes();
        List<String> contaList = new ArrayList<>();

        for (Cliente cliente : listaClientes) {
            if (cliente.getDivida() != null) {
                double divCliente = Double.parseDouble(cliente.getDivida());
                if (divCliente > 0) {
                    if (listaContasAReceber.size() == 0) {
                        contaCliente.setConta(cliente.getNomeCompleto());
                        contaCliente.setDataVencimento(cliente.getDataVencimento());
                        contaCliente.setVlConta(cliente.getDivida());
                        contaAReceberDAO.salvaContaAReceber(contaCliente);
                    } else {
                        verificaSeContaClienteJaFoiAdicionada(contaCliente, contaList, cliente);
                    }
                }
            }
        }
    }

    private void verificaSeContaClienteJaFoiAdicionada(ContaAReceber contaCliente, List<String> contaList, Cliente cliente) {
        for (int i = 0; i < listaContasAReceber.size(); i++) {
            contaList.add(listaContasAReceber.get(i).getConta());
        }
        if (!contaList.contains(cliente.getNomeCompleto())) {
            contaCliente.setConta(cliente.getNomeCompleto());
            contaCliente.setDataVencimento(cliente.getDataVencimento());
            contaCliente.setVlConta(cliente.getDivida());
            contaAReceberDAO.salvaContaAReceber(contaCliente);
        }
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
    //SOBREESCREVE O BOTAO VOLTAR
    public void onBackPressed(){
        startActivity(new Intent(ListaContasAReceberActivity.this, FinancasPrincipalActivity.class));
        finish();
    }
//==================================================================================================
}
