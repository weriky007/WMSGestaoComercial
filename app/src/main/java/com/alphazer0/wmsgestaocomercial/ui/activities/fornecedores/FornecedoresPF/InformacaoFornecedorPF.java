package com.alphazer0.wmsgestaocomercial.ui.activities.fornecedores.FornecedoresPF;

import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.CHAVE_CLIENTE;
import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.CHAVE_FORNECEDORPF;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alphazer0.wmsgestaocomercial.R;
import com.alphazer0.wmsgestaocomercial.model.Cliente;
import com.alphazer0.wmsgestaocomercial.model.FornecedorPF;

public class InformacaoFornecedorPF extends AppCompatActivity {
    FornecedorPF fornecedorPFRecebido = new FornecedorPF();
    private TextView nome;
    private TextView dataNascimento;
    private TextView cpf;
    private TextView rg;
    private TextView nomePai;
    private TextView nomeMae;
    private TextView celular1;
    private TextView celular2;
    private TextView telefoneFixo;
    private TextView email;
    private TextView rua;
    private TextView numero;
    private TextView quadra;
    private TextView lote;
    private TextView bairro;
    private TextView cep;
    private TextView complemento;
//==================================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacoes_fornecedorpf);
        setTitle("Informações FornecedorPF");

        insereDadosPessoais();
        insereDadosContato();
        insereDadosEndereco();
    }
//==================================================================================================
    private void insereDadosPessoais() {
        Intent recebeDados = new Intent();
        recebeDados.hasExtra(CHAVE_FORNECEDORPF);
        Bundle extra = getIntent().getExtras();
        fornecedorPFRecebido = (FornecedorPF) extra.getSerializable(CHAVE_FORNECEDORPF);

        nome = findViewById(R.id.info_fornecedorpf_txt_nome_completo);
        dataNascimento = findViewById(R.id.info_fornecedorpf_txt_data_nascimento);
        cpf = findViewById(R.id.info_fornecedorpf_txt_cpf);
        rg = findViewById(R.id.info_fornecedorpf_txt_rg);
        nomePai = findViewById(R.id.info_fornecedorpf_txt_nome_pai);
        nomeMae = findViewById(R.id.info_fornecedorpf_txt_nome_mae);

        nome.setText(fornecedorPFRecebido.getNomeCompleto());
        dataNascimento.setText(fornecedorPFRecebido.getDataNascimento());
        cpf.setText(fornecedorPFRecebido.getCpf());
        rg.setText(fornecedorPFRecebido.getRg());
        nomePai.setText(fornecedorPFRecebido.getNomePai());
        nomeMae.setText(fornecedorPFRecebido.getNomeMae());
    }
//==================================================================================================
    private void insereDadosContato() {
        Intent recebeDados = new Intent();
        recebeDados.hasExtra(CHAVE_FORNECEDORPF);
        Bundle extra = getIntent().getExtras();
        fornecedorPFRecebido = (FornecedorPF) extra.getSerializable(CHAVE_FORNECEDORPF);

        celular1 = findViewById(R.id.info_fornecedorpf_txt_celular1);
        celular2 = findViewById(R.id.info_fornecedorpf_txt_celular2);
        telefoneFixo = findViewById(R.id.info_fornecedorpf_txt_telefone_fixo);
        email = findViewById(R.id.info_fornecedorpf_txt_email);

        celular1.setText(fornecedorPFRecebido.getCelular1());
        celular2.setText(fornecedorPFRecebido.getCelular2());
        telefoneFixo.setText(fornecedorPFRecebido.getTelefone());
        email.setText(fornecedorPFRecebido.getEmail());
    }
//==================================================================================================
    private void insereDadosEndereco() {
        Intent recebeDados = new Intent();
        recebeDados.hasExtra(CHAVE_FORNECEDORPF);
        Bundle extra = getIntent().getExtras();
        fornecedorPFRecebido = (FornecedorPF) extra.getSerializable(CHAVE_FORNECEDORPF);

        rua = findViewById(R.id.info_fornecedorpf_txt_rua);
        numero = findViewById(R.id.info_fornecedorpf_txt_numero);
        quadra = findViewById(R.id.info_fornecedorpf_txt_quadra);
        lote = findViewById(R.id.info_fornecedorpf_txt_lote);
        bairro = findViewById(R.id.info_fornecedorpf_txt_bairro);
        cep = findViewById(R.id.info_fornecedorpf_txt_cep);
        complemento = findViewById(R.id.info_fornecedorpf_txt_complemento);

        rua.setText(fornecedorPFRecebido.getRua());
        numero.setText(fornecedorPFRecebido.getNumero());
        quadra.setText(fornecedorPFRecebido.getQuadra());
        lote.setText(fornecedorPFRecebido.getLote());
        bairro.setText(fornecedorPFRecebido.getBairro());
        cep.setText(fornecedorPFRecebido.getCep());
        complemento.setText(fornecedorPFRecebido.getComplemento());
    }
//==================================================================================================
}
