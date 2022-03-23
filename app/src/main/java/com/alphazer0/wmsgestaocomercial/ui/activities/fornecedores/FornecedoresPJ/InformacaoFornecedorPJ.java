package com.alphazer0.wmsgestaocomercial.ui.activities.fornecedores.FornecedoresPJ;

import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.CHAVE_FORNECEDORPJ;
import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.CHAVE_PRODUTO;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alphazer0.wmsgestaocomercial.R;
import com.alphazer0.wmsgestaocomercial.model.FornecedorPJ;
import com.alphazer0.wmsgestaocomercial.ui.activities.estoque.CadastroProdutoActivity;

public class InformacaoFornecedorPJ extends AppCompatActivity {
    public static final String INFORMAÇÕES_FORNECEDOR_PJ = "Informações FornecedorPJ";
    private FornecedorPJ fornecedorPJRecebido = new FornecedorPJ();
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
        setTitle(INFORMAÇÕES_FORNECEDOR_PJ);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);

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
    //MENU APPBAR EDITA
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edita, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_botao_edita) {
            editar();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

//==================================================================================================
    private void editar() {
        Intent enviaDados = new Intent(this, CadastroFornecedorPJActivity.class);
        enviaDados.putExtra(CHAVE_FORNECEDORPJ, fornecedorPJRecebido);
        startActivity(enviaDados);
    }
//==================================================================================================
}
