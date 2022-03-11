package com.alphazer0.wmsgestaocomercial.ui.activities.fornecedores;

import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.TITULO_APPBAR_FORNECEDORES;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alphazer0.wmsgestaocomercial.R;
import com.alphazer0.wmsgestaocomercial.database.FornecedoresPFDatabase;
import com.alphazer0.wmsgestaocomercial.database.FornecedoresPJDatabase;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomFornecedorPFDAO;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomFornecedorPJDAO;
import com.alphazer0.wmsgestaocomercial.ui.activities.fornecedores.FornecedoresPF.ListaDeFornecedorPFActivity;
import com.alphazer0.wmsgestaocomercial.ui.activities.fornecedores.FornecedoresPJ.ListaDeFornecedorPJActivity;

public class FornecedoresActivity extends AppCompatActivity {
    private Button btnPessoaFisica;
    private Button getBtnPessoaJuridica;
    private TextView qtdFornecedoresPF;
    private TextView qtdFornecedoresPJ;
    private RoomFornecedorPFDAO pfdao;
    private RoomFornecedorPJDAO pjdao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fornecedores);
        setTitle(TITULO_APPBAR_FORNECEDORES);
        bindDosElementos();
        configuraContadorFornecedores();
        configuraBotoes();
    }

    private void configuraContadorFornecedores() {
        //E NECESSARIO PARA NAO DAR NULLPOINTEREXCEPTION NO DAO
        FornecedoresPFDatabase dataBasePF = FornecedoresPFDatabase.getInstance(this);
        pfdao = dataBasePF.getFornecedorPFDAO();

        FornecedoresPJDatabase dataBasePJ = FornecedoresPJDatabase.getInstance(this);
        pjdao = dataBasePJ.getFornecedorPJDAO();

        String pf;
        String pj;

        pf = Integer.toString(pfdao.todosFornecedoresPF().size());
        pj = Integer.toString(pjdao.todosFornecedoresPJ().size());


        qtdFornecedoresPF.setText(pf);
        qtdFornecedoresPJ.setText(pj);
    }

    private void bindDosElementos(){
        btnPessoaFisica = findViewById(R.id.botao_pessoa_fisica);
        getBtnPessoaJuridica = findViewById(R.id.botao_pessoa_juridica);
        qtdFornecedoresPF = findViewById(R.id.textQtdFornecedoresPF);
        qtdFornecedoresPJ = findViewById(R.id.textQtdFornecedoresPJ);
    }

    private void configuraBotoes(){
        btnPessoaFisica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FornecedoresActivity.this, ListaDeFornecedorPFActivity.class));
                finish();
            }
        });

        getBtnPessoaJuridica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FornecedoresActivity.this, ListaDeFornecedorPJActivity.class));
                finish();
            }
        });
    }
}
