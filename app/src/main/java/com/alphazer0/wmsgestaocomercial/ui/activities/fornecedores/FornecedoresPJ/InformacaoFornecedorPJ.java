package com.alphazer0.wmsgestaocomercial.ui.activities.fornecedores.FornecedoresPJ;

import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.CHAVE_FORNECEDORPJ;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alphazer0.wmsgestaocomercial.R;
import com.alphazer0.wmsgestaocomercial.model.FornecedorPJ;

public class InformacaoFornecedorPJ extends AppCompatActivity {
    private FornecedorPJ fornecedorPJRecebido= new FornecedorPJ();
    private TextView razaoSocial;
    private TextView nomeFantasia;
    private TextView cnpj;
    private TextView ie;
    private TextView complemento;
    private TextView cep;
    private TextView bairro;
    private TextView lote;
    private TextView quadra;
    private TextView numero;
    private TextView rua;
    private TextView celular1;
    private TextView celular2;
    private TextView telefoneFixo;
    private TextView email;
//==================================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacoes_fornecedorpj);
        setTitle("Informações FornecedorPJ");

        insereDados();
        insereDadosContato();
        insereDadosEndereco();
    }
//==================================================================================================
    private void insereDados() {
        Intent recebeDados = new Intent();
        recebeDados.hasExtra(CHAVE_FORNECEDORPJ);
        Bundle extra = getIntent().getExtras();
        fornecedorPJRecebido = (FornecedorPJ) extra.getSerializable(CHAVE_FORNECEDORPJ);

        razaoSocial = findViewById(R.id.info_fornecedorpj_txt_razao_social);
        nomeFantasia = findViewById(R.id.info_fornecedorpj_txt_nome_fantasia);
        cnpj = findViewById(R.id.info_fornecedorpj_txt_cnpj);
        ie = findViewById(R.id.info_fornecedorpj_txt_ie);

        razaoSocial.setText(fornecedorPJRecebido.getRazaoSocial());
        nomeFantasia.setText(fornecedorPJRecebido.getNomeFantasia());
        cnpj.setText(fornecedorPJRecebido.getCnpj());
        ie.setText(fornecedorPJRecebido.getIe());
    }
//==================================================================================================
    private void insereDadosContato() {
        Intent recebeDados = new Intent();
        recebeDados.hasExtra(CHAVE_FORNECEDORPJ);
        Bundle extra = getIntent().getExtras();
        fornecedorPJRecebido = (FornecedorPJ) extra.getSerializable(CHAVE_FORNECEDORPJ);

        celular1 = findViewById(R.id.info_fornecedorpj_txt_celular1);
        celular2 = findViewById(R.id.info_fornecedorpj_txt_celular2);
        telefoneFixo = findViewById(R.id.info_fornecedorpj_txt_telefone_fixo);
        email = findViewById(R.id.info_fornecedorpj_txt_email);

        celular1.setText(fornecedorPJRecebido.getCelular1());
        celular2.setText(fornecedorPJRecebido.getCelular2());
        telefoneFixo.setText(fornecedorPJRecebido.getTelefone());
        email.setText(fornecedorPJRecebido.getEmail());
    }
//==================================================================================================
    private void insereDadosEndereco() {
        Intent recebeDados = new Intent();
        recebeDados.hasExtra(CHAVE_FORNECEDORPJ);
        Bundle extra = getIntent().getExtras();
        fornecedorPJRecebido = (FornecedorPJ) extra.getSerializable(CHAVE_FORNECEDORPJ);

        rua = findViewById(R.id.info_fornecedorpj_txt_rua);
        numero = findViewById(R.id.info_fornecedorpj_txt_numero);
        quadra = findViewById(R.id.info_fornecedorpj_txt_quadra);
        lote = findViewById(R.id.info_fornecedorpj_txt_lote);
        bairro = findViewById(R.id.info_fornecedorpj_txt_bairro);
        cep = findViewById(R.id.info_fornecedorpj_txt_cep);
        complemento = findViewById(R.id.info_fornecedorpj_txt_complemento);

        rua.setText(fornecedorPJRecebido.getRua());
        numero.setText(fornecedorPJRecebido.getNumero());
        quadra.setText(fornecedorPJRecebido.getQuadra());
        lote.setText(fornecedorPJRecebido.getLote());
        bairro.setText(fornecedorPJRecebido.getBairro());
        cep.setText(fornecedorPJRecebido.getCep());
        complemento.setText(fornecedorPJRecebido.getComplemento());
    }
//==================================================================================================
}
