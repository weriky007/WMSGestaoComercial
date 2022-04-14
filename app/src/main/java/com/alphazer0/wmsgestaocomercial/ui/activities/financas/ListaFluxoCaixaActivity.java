package com.alphazer0.wmsgestaocomercial.ui.activities.financas;

import static com.alphazer0.wmsgestaocomercial.ui.activities.financas.ListaContasAPagarActivity.CANCELAR;
import static com.alphazer0.wmsgestaocomercial.ui.activities.financas.ListaContasAPagarActivity.CONCLUIR;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.alphazer0.wmsgestaocomercial.R;
import com.alphazer0.wmsgestaocomercial.database.MovimentacoesCaixaDatabase;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomMovimentacaoCaixaDAO;
import com.alphazer0.wmsgestaocomercial.model.MovimentacaoCaixa;
import com.alphazer0.wmsgestaocomercial.ui.adapters.ListaFluxoCaixaAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListaFluxoCaixaActivity extends AppCompatActivity {

    public static final String FLUXO_DE_CAIXA = "Fluxo de Caixa";
    private FloatingActionButton fabAddItemFluxo;
    private RecyclerView recyclerView;
    private RoomMovimentacaoCaixaDAO movimentacaoCaixaDAO;
    private ListaFluxoCaixaAdapter fluxoCaixaAdapter;
    private List<MovimentacaoCaixa> listaMovimentacoes = new ArrayList<>();
    private final Context context = this;

    private RadioGroup grupoTipo;
    private EditText campoDescricao;
    private EditText campoValor;
    private String sescolhaTipoFluxoCaixa;

    //==================================================================================================
    @Override
    protected void onCreate(Bundle savedIntanceState) {
        super.onCreate(savedIntanceState);
        setContentView(R.layout.activity_lista_fluxo_caixa);
        setTitle(FLUXO_DE_CAIXA);

        configuraAdapter();
        configuraLista();
        configuraAddNovoItemFluxo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fluxoCaixaAdapter.atualiza(movimentacaoCaixaDAO.todasMovimentacoes());
    }

    //==================================================================================================
    private void configuraAdapter() {
        fluxoCaixaAdapter = new ListaFluxoCaixaAdapter(listaMovimentacoes);
        movimentacaoCaixaDAO = MovimentacoesCaixaDatabase.getInstance(this).getMovimentacaoCaixaDAO();
    }

    private void configuraLista() {
        recyclerView = findViewById(R.id.recyclerview_lista_fluxo_caixa);
        recyclerView.setAdapter(fluxoCaixaAdapter);
    }

    private void configuraAddNovoItemFluxo() {
        fabAddItemFluxo = findViewById(R.id.fab_novo_item_fluxo);
        fabAddItemFluxo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abreFormularioAddMovimentacao();
            }
        });
    }

    private void abreFormularioAddMovimentacao() {
        View viewAddMovimentacao = LayoutInflater.from(ListaFluxoCaixaActivity.this)
                .inflate(R.layout.activity_formulario_fluxo_caixa, null);

        grupoTipo = viewAddMovimentacao.findViewById(R.id.radio_escolha_tipo_fluxo_caixa);
        campoDescricao = viewAddMovimentacao.findViewById(R.id.edit_descricao_fluxo_caixa);
        campoValor = viewAddMovimentacao.findViewById(R.id.edit_valor_fluxo_caixa);

        //PEGANDO ESCOLHA DO TIPO
        grupoTipo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int item = radioGroup.getCheckedRadioButtonId();
                RadioButton rescolhaTipoFluxoCaixa = viewAddMovimentacao.findViewById(item);
                sescolhaTipoFluxoCaixa = rescolhaTipoFluxoCaixa.getText().toString();

                switch (sescolhaTipoFluxoCaixa) {
                    case "Depósito":
                        Toast.makeText(context, "Depósito", Toast.LENGTH_SHORT).show();
                        break;

                    case "Retirada":
                        Toast.makeText(context, "Retirada", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        ColorDrawable back = new ColorDrawable(Color.WHITE);
        InsetDrawable inset = new InsetDrawable(back, 0);

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage("Adicionar Movimentação");
        alertDialog.setView(viewAddMovimentacao);

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
                alertDialog.dismiss();
            }
        });
    }
//==================================================================================================
}
