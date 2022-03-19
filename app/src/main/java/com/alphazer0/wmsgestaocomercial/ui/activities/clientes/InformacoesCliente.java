package com.alphazer0.wmsgestaocomercial.ui.activities.clientes;

import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.CHAVE_CLIENTE;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alphazer0.wmsgestaocomercial.R;
import com.alphazer0.wmsgestaocomercial.model.Cliente;

public class InformacoesCliente extends AppCompatActivity {

    private Cliente clienteRecebido = new Cliente();
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
        setContentView(R.layout.activity_informacoes_cliente);
        setTitle("Informações Cliente");

        insereDadosPessoais();
        insereDadosContato();
        insereDadosEndereco();
    }
//==================================================================================================
    private void insereDadosPessoais() {
        Intent recebeDados = new Intent();
        recebeDados.hasExtra(CHAVE_CLIENTE);
        Bundle extra = getIntent().getExtras();
        clienteRecebido = (Cliente) extra.getSerializable(CHAVE_CLIENTE);

        nome = findViewById(R.id.info_cliente_txt_nome_completo);
        dataNascimento = findViewById(R.id.info_cliente_txt_data_nascimento);
        cpf = findViewById(R.id.info_cliente_txt_cpf);
        rg = findViewById(R.id.info_cliente_txt_rg);
        nomePai = findViewById(R.id.info_cliente_txt_nome_pai);
        nomeMae = findViewById(R.id.info_cliente_txt_nome_mae);

        nome.setText(clienteRecebido.getNomeCompleto());
        dataNascimento.setText(clienteRecebido.getDataNascimento());
        cpf.setText(clienteRecebido.getCpf());
        rg.setText(clienteRecebido.getRg());
        nomePai.setText(clienteRecebido.getNomePai());
        nomeMae.setText(clienteRecebido.getNomeMae());
    }
//==================================================================================================
    private void insereDadosContato() {
        Intent recebeDados = new Intent();
        recebeDados.hasExtra(CHAVE_CLIENTE);
        Bundle extra = getIntent().getExtras();
        clienteRecebido = (Cliente) extra.getSerializable(CHAVE_CLIENTE);

        celular1 = findViewById(R.id.info_cliente_txt_celular1);
        celular2 = findViewById(R.id.info_cliente_txt_celular2);
        telefoneFixo = findViewById(R.id.info_cliente_txt_telefone_fixo);
        email = findViewById(R.id.info_cliente_txt_email);

        celular1.setText(clienteRecebido.getCelular1());
        celular2.setText(clienteRecebido.getCelular2());
        telefoneFixo.setText(clienteRecebido.getTelefone());
        email.setText(clienteRecebido.getEmail());
    }
//==================================================================================================
    private void insereDadosEndereco() {
        Intent recebeDados = new Intent();
        recebeDados.hasExtra(CHAVE_CLIENTE);
        Bundle extra = getIntent().getExtras();
        clienteRecebido = (Cliente) extra.getSerializable(CHAVE_CLIENTE);

        rua = findViewById(R.id.info_cliente_txt_rua);
        numero = findViewById(R.id.info_cliente_txt_numero);
        quadra = findViewById(R.id.info_cliente_txt_quadra);
        lote = findViewById(R.id.info_cliente_txt_lote);
        bairro = findViewById(R.id.info_cliente_txt_bairro);
        cep = findViewById(R.id.info_cliente_txt_cep);
        complemento = findViewById(R.id.info_cliente_txt_complemento);

        rua.setText(clienteRecebido.getRua());
        numero.setText(clienteRecebido.getNumero());
        quadra.setText(clienteRecebido.getQuadra());
        lote.setText(clienteRecebido.getLote());
        bairro.setText(clienteRecebido.getBairro());
        cep.setText(clienteRecebido.getCep());
        complemento.setText(clienteRecebido.getComplemento());
    }
//==================================================================================================
}
