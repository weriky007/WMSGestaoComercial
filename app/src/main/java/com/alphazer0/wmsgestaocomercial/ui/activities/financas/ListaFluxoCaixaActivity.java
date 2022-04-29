package com.alphazer0.wmsgestaocomercial.ui.activities.financas;

import static com.alphazer0.wmsgestaocomercial.ui.activities.financas.ListaContasAPagarActivity.CANCELAR;
import static com.alphazer0.wmsgestaocomercial.ui.activities.financas.ListaContasAPagarActivity.CONCLUIR;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alphazer0.wmsgestaocomercial.R;
import com.alphazer0.wmsgestaocomercial.database.MovimentacoesCaixaDatabase;
import com.alphazer0.wmsgestaocomercial.database.TotalCaixaDatabase;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomMovimentacaoCaixaDAO;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomTotalCaixaDAO;
import com.alphazer0.wmsgestaocomercial.model.MovimentacaoCaixa;
import com.alphazer0.wmsgestaocomercial.model.TotalCaixa;
import com.alphazer0.wmsgestaocomercial.ui.adapters.ListaFluxoCaixaAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class ListaFluxoCaixaActivity extends AppCompatActivity {

    public static final String FLUXO_DE_CAIXA = "Fluxo de Caixa";
    public static final String DEPOSITO = "Depósito";
    public static final String RETIRADA = "Retirada";
    public static final String CANCELADO = "Cancelado!";
    private FloatingActionButton fabAddItemFluxo;
    private RecyclerView recyclerView;
    private RoomMovimentacaoCaixaDAO movimentacaoCaixaDAO;
    private RoomTotalCaixaDAO totalCaixaDAO;
    private ListaFluxoCaixaAdapter fluxoCaixaAdapter;
    private List<MovimentacaoCaixa> listaMovimentacoes = new ArrayList<>();
    private List<String> tipos = new ArrayList<>();
    private HashSet<String> hashSet = new HashSet<>();
    private final Context context = this;

    private RadioGroup grupoTipo;
    private EditText campoDescricao;
    private EditText campoValor;
    private String sescolhaTipoFluxoCaixa ="";
    private TextView textViewSaldoTotal;
    private Spinner sp;
    private String filtroTipo = "";
//==================================================================================================
    @Override
    protected void onCreate(Bundle savedIntanceState) {
        super.onCreate(savedIntanceState);
        setContentView(R.layout.activity_lista_fluxo_caixa);
        setTitle(FLUXO_DE_CAIXA);
        telaEmModoRetrado();

        configuraAdapter();
        pegaDadosDosBDs();
        configuraLista();
        textViewSaldoTotal = findViewById(R.id.text_fluxo_caixa_saldo);
        configuraAddNovoItemFluxo();
        configuraAutoComplete();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fluxoCaixaAdapter.atualiza(movimentacaoCaixaDAO.todasMovimentacoes());
        somaValorAoTotal();
    }

    private void telaEmModoRetrado() {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
    }
//==================================================================================================
    private void configuraAdapter() {
        fluxoCaixaAdapter = new ListaFluxoCaixaAdapter(this,listaMovimentacoes);
    }

    private void pegaDadosDosBDs(){
        movimentacaoCaixaDAO = MovimentacoesCaixaDatabase.getInstance(this).getMovimentacaoCaixaDAO();
        totalCaixaDAO = TotalCaixaDatabase.getInstance(this).getTotalCaixaDAO();
    }

    private void configuraLista() {
        recyclerView = findViewById(R.id.recyclerview_lista_fluxo_caixa);
        recyclerView.setAdapter(fluxoCaixaAdapter);
        //TIPO DE LAYOUT
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //COLOCA OS MAIS RECENTES PRIMEIRO NA LISTA
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
    }

    private void somaValorAoTotal(){
        if(totalCaixaDAO.totalCaixa() != null) {
            textViewSaldoTotal.setText(totalCaixaDAO.totalCaixa().getTotal());
        }else {
            textViewSaldoTotal.setText("0.00");
        }
    }
//==================================================================================================
    private void configuraAddNovoItemFluxo() {
        fabAddItemFluxo = findViewById(R.id.fab_novo_item_fluxo);
        fabAddItemFluxo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abreFormularioAddMovimentacao();
            }
        });
    }
//==================================================================================================
    //CONFIGURA O FILTRO POR TIPO
    private void pegaTipo(List<MovimentacaoCaixa> listaMovimentacaoCaixa){
        for(MovimentacaoCaixa movimentacaoCaixa : listaMovimentacaoCaixa ){
            this.tipos.add(movimentacaoCaixa.getTipo());
        }
        hashSet.addAll(this.tipos);
        this.tipos.clear();
        this.tipos.add("Todos");
        this.tipos.addAll(hashSet);
    }

    private void configuraAutoComplete(){
        pegaTipo(movimentacaoCaixaDAO.todasMovimentacoes());
        ArrayAdapter<String> adapterTipos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,tipos);
        adapterTipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp = findViewById(R.id.spinner);
        sp.setAdapter(adapterTipos);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                filtroTipo = sp.getSelectedItem().toString();
                fluxoCaixaAdapter.atualiza(movimentacaoCaixaDAO.todasMovimentacoes());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
//==================================================================================================
    private void calculaSaldoTotal(){
        if(listaMovimentacoes.size() >0) {

            //SEPARA O VALOR TOTAL POR TIPO
            String sTotal = "";
            BigDecimal bTotalDepositos = new BigDecimal("0");
            BigDecimal bTotalRetiradas = new BigDecimal("0");

            for (int i = 0; i < listaMovimentacoes.size(); i++) {
                //PEGA O VALOR DOS INTENS DA LISTA
                sTotal = listaMovimentacoes.get(i).getValor();
                BigDecimal b = new BigDecimal(sTotal);
                //FAZ A SOMA SEPARADA POR TIPO, DEPOSITO OU RETIRADA
                if(listaMovimentacoes.get(i).getTipo().equals(DEPOSITO)) {
                    bTotalDepositos = bTotalDepositos.add(b);
                }else if(listaMovimentacoes.get(i).getTipo().equals(RETIRADA)){
                    bTotalRetiradas = bTotalRetiradas.add(b);
                }
            }

            //PEGA VALOR DO CAIXA DO BD E INSERE NA STRING
            String sValorCaixa;
            if(totalCaixaDAO.totalCaixa() == null){
                sValorCaixa = "0.00";
            }else if(totalCaixaDAO.totalCaixa() != null) {
                sValorCaixa = totalCaixaDAO.totalCaixa().getTotal();
            }else{
                sValorCaixa = totalCaixaDAO.totalCaixa().getTotal();
            }

            //CALCULANDO O TOTAL DO CAIXA COM O SALDO DAS MOVIMENTACOES INSERIDAS
            //POIS O CAIXA RECEBE OS VALORES DAS VENDAS
            BigDecimal bBD= new BigDecimal(sValorCaixa);
            BigDecimal bSaldoTotal = bTotalDepositos.subtract(bTotalRetiradas);
            BigDecimal bResult = new BigDecimal("0");
            bResult = bSaldoTotal.add(bBD);

            //CRIANDO O OBJETO QUE SERA SALVO NO BD
            String result = bResult.toString();
            TotalCaixa totalCaixa = new TotalCaixa();
            totalCaixa.setTotal(result);

            //VERIFICA SE IRA SALVAR OU EDITAR O TOTAL DO CAIXA
            if(totalCaixaDAO.totalCaixa() == null){
                totalCaixaDAO.salvaTotal(totalCaixa);
            }else if(totalCaixaDAO.totalCaixa() != null){
                int id = totalCaixaDAO.totalCaixa().getId();
                totalCaixa.setId(id);
                totalCaixaDAO.editaTotal(totalCaixa);
            }
            //INSERE O TOTAL DO CALCULO NA STRING
            textViewSaldoTotal.setText(result);
        }
    }
//==================================================================================================
    private void abreFormularioAddMovimentacao() {
        //CRIA VIEW QUE IRA INFLAR O ALERTDIALOG
        View viewAddMovimentacao = LayoutInflater.from(ListaFluxoCaixaActivity.this)
                .inflate(R.layout.activity_formulario_fluxo_caixa, null);

        //BIND DOS ELEMENTOS
        grupoTipo = viewAddMovimentacao.findViewById(R.id.radio_escolha_tipo_fluxo_caixa);
        campoDescricao = viewAddMovimentacao.findViewById(R.id.edit_descricao_fluxo_caixa);
        campoValor = viewAddMovimentacao.findViewById(R.id.edit_valor_fluxo_caixa);

        //PEGANDO ESCOLHA DO TIPO
        grupoTipo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int item = radioGroup.getCheckedRadioButtonId();
                RadioButton rescolhaTipoFluxoCaixa = viewAddMovimentacao.findViewById(item);
                if(item > 0) {
                    sescolhaTipoFluxoCaixa = rescolhaTipoFluxoCaixa.getText().toString();
                    switch (sescolhaTipoFluxoCaixa) {
                        case DEPOSITO:
                            Toast.makeText(context, DEPOSITO, Toast.LENGTH_SHORT).show();
                            break;

                        case RETIRADA:
                            Toast.makeText(context, RETIRADA, Toast.LENGTH_SHORT).show();
                            break;
                    }
                }else{
                    Toast.makeText(context, "Escolha um tipo", Toast.LENGTH_SHORT).show();
                }

            }
        });
        configuraAlertDialog(viewAddMovimentacao);
    }
//==================================================================================================
    private void configuraAlertDialog(View viewAddMovimentacao) {
        //CONFIGURA COR E MARGEM ALERT DIALOG
        ColorDrawable back = new ColorDrawable(Color.WHITE);
        InsetDrawable inset = new InsetDrawable(back, 0);

        //CRIA ALERTDIALOG
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage("Adicionar Movimentação");
        alertDialog.setView(viewAddMovimentacao);

        //BOTAO POSITIVO
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, CONCLUIR, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //ESTA SOBREESCRITO ABAIXO
            }
        });

        //BOTAO NEGATIVO
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, CANCELAR, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, CANCELADO, Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });

        //EXECUTANDO ALERTDIALOG
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(inset);
        Button btn = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CONFIGURA DATA E HORA
                SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat formataHora = new SimpleDateFormat("HH:mm:ss");
                Date dataAtual = new Date();
                Date horaAtual = new Date();
                String dataFormatada = formataData.format(dataAtual);
                String horaFormatada = formataHora.format(horaAtual);

                realizaVerificacao(dataFormatada, horaFormatada, alertDialog);

            }
        });
    }
//==================================================================================================
    private void realizaVerificacao(String dataFormatada, String horaFormatada, AlertDialog alertDialog) {
        //VERIFICACAO DOS CAMPOS
        if(sescolhaTipoFluxoCaixa.equals("") || sescolhaTipoFluxoCaixa == null){
            Toast.makeText(context, "Escolha um tipo", Toast.LENGTH_SHORT).show();
        }else{
            if(campoDescricao.getText().toString().equals("") || campoDescricao.getText().toString() == null){
                Toast.makeText(context, "Preencha o campo descrição", Toast.LENGTH_SHORT).show();
            }else{
                if(campoValor.getText().toString().equals("") || campoValor.getText().toString() == null){
                    Toast.makeText(context, "Preencha o valor", Toast.LENGTH_SHORT).show();
                }else{
                    double d = Double.parseDouble(campoValor.getText().toString());
                    if(d <=0){
                        Toast.makeText(context, "O valor deve ser maior que Zero", Toast.LENGTH_SHORT).show();
                    }else{
                        //PEGA OS VALORES DOS CAMPOS
                        String tipo = sescolhaTipoFluxoCaixa.trim().trim().trim();
                        String descricao = campoDescricao.getText().toString();
                        String valorDigitado = campoValor.getText().toString();


                        //VERIFICA O TIPO
                        if (tipo.equals(DEPOSITO)) {
                            concluiAddMovimentacao(dataFormatada, horaFormatada, tipo, descricao, valorDigitado, alertDialog);
                            somaValorAoTotal(valorDigitado);
                        }

                        if (tipo.equals(RETIRADA)) {
                            if (totalCaixaDAO.totalCaixa() == null) {
                                Toast.makeText(context, "Não é possível realizar uma Retirada sem Saldo", Toast.LENGTH_SHORT).show();
                            } else {
                                double dtotal = Double.parseDouble(totalCaixaDAO.totalCaixa().getTotal());
                                double dvalor = Double.parseDouble(valorDigitado);
                                if (dvalor > 0 && dvalor <= dtotal) {
                                    concluiAddMovimentacao(dataFormatada, horaFormatada, tipo, descricao, valorDigitado, alertDialog);
                                    subtraiValorDoTotal(valorDigitado);
                                } else {
                                    Toast.makeText(context, "O valorDigitado da Retirada não pode ser maior do que o Saldo: R$" + dtotal, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        configuraAutoComplete();
                    }
                }
            }
        }
    }
//==================================================================================================
    private void subtraiValorDoTotal(String valorDigitado) {
        BigDecimal bValor = new BigDecimal(valorDigitado);
        BigDecimal bTotalCaixa;
        BigDecimal resultado = new BigDecimal("0");
        TotalCaixa totalCaixa = new TotalCaixa();

        if (totalCaixaDAO.totalCaixa() != null) {
            bTotalCaixa = new BigDecimal(totalCaixaDAO.totalCaixa().getTotal());
            resultado = bTotalCaixa.subtract(bValor);
            totalCaixa.setId(totalCaixaDAO.totalCaixa().getId());
            totalCaixa.setTotal(resultado.toString());
            totalCaixaDAO.editaTotal(totalCaixa);
            textViewSaldoTotal.setText(totalCaixaDAO.totalCaixa().getTotal());
        }else {
            bTotalCaixa = new BigDecimal("0");
            resultado = bTotalCaixa.add(bValor);
            totalCaixa.setTotal(resultado.toString());
            totalCaixaDAO.salvaTotal(totalCaixa);
            textViewSaldoTotal.setText(totalCaixaDAO.totalCaixa().getTotal());
        }
    }

    private void somaValorAoTotal(String valorDigitado) {
        BigDecimal bValor = new BigDecimal(valorDigitado);
        BigDecimal bTotalCaixa;
        BigDecimal resultado = new BigDecimal("0");
        TotalCaixa totalCaixa = new TotalCaixa();
        if (totalCaixaDAO.totalCaixa() != null) {
            bTotalCaixa = new BigDecimal(totalCaixaDAO.totalCaixa().getTotal());
            resultado = bTotalCaixa.add(bValor);
            totalCaixa.setId(totalCaixaDAO.totalCaixa().getId());
            totalCaixa.setTotal(resultado.toString());
            totalCaixaDAO.editaTotal(totalCaixa);
            textViewSaldoTotal.setText(totalCaixaDAO.totalCaixa().getTotal());
        }else {
            bTotalCaixa = new BigDecimal("0");
            resultado = bTotalCaixa.add(bValor);
            totalCaixa.setTotal(resultado.toString());
            totalCaixaDAO.salvaTotal(totalCaixa);
            textViewSaldoTotal.setText(totalCaixaDAO.totalCaixa().getTotal());
        }
    }
//==================================================================================================
    private void concluiAddMovimentacao(String dataFormatada, String horaFormatada, String tipo, String descricao, String valor, AlertDialog alertDialog) {
        //INSERE VALORES DO ITEM QUE ESTA SENDO CRIADO
        MovimentacaoCaixa movimentacaoCaixa = new MovimentacaoCaixa();
        movimentacaoCaixa.setData(dataFormatada);
        movimentacaoCaixa.setHora(horaFormatada);
        movimentacaoCaixa.setTipo(tipo);
        movimentacaoCaixa.setDescricao(descricao);
        movimentacaoCaixa.setValor(valor);

        //SALVANDO O ITEM CRIADO E ATUALIZANDO A LISTA
        movimentacaoCaixaDAO.salvaMovimentacaoCaixa(movimentacaoCaixa);
        listaMovimentacoes = movimentacaoCaixaDAO.todasMovimentacoes();
        fluxoCaixaAdapter.atualiza(listaMovimentacoes);

        alertDialog.dismiss();
    }
//==================================================================================================
    //SOBREESCREVE O BOTAO VOLTAR
    @Override
    public void onBackPressed(){
        startActivity(new Intent(ListaFluxoCaixaActivity.this,FinancasPrincipalActivity.class));
        finish();
    }
//==================================================================================================
}
