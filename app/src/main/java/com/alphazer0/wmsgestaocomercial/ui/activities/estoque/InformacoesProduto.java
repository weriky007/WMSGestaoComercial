package com.alphazer0.wmsgestaocomercial.ui.activities.estoque;

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
import com.alphazer0.wmsgestaocomercial.model.Produto;

public class InformacoesProduto extends AppCompatActivity {
    public static final String INFORMAÇÕES_PRODUTO = "Informações Produto";
    Produto produtoRecebido = new Produto();
    private TextView codBarras;
    private TextView categoria;
    private TextView produto;
    private TextView marca;
    private TextView discriminacao;
    private TextView precoCompra;
    private TextView precoVenda;
    private TextView quantidade;
    private TextView medida;

    //==================================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacoes_estoque_produto);
        setTitle(INFORMAÇÕES_PRODUTO);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);

        insereDadosProduto();
    }

    //==================================================================================================
    private void insereDadosProduto() {
        Intent recebeDados = new Intent();
        recebeDados.hasExtra(CHAVE_PRODUTO);
        Bundle extra = getIntent().getExtras();
        produtoRecebido = (Produto) extra.getSerializable(CHAVE_PRODUTO);

        codBarras = findViewById(R.id.info_produto_txt_codigo_barras);
        categoria = findViewById(R.id.info_produto_txt_categoria);
        produto = findViewById(R.id.info_produto_txt_produto);
        marca = findViewById(R.id.info_produto_txt_marca);
        discriminacao = findViewById(R.id.info_produto_txt_discriminacao);
        precoCompra = findViewById(R.id.info_produto_txt_preco_compra);
        precoVenda = findViewById(R.id.info_produto_txt_preco_venda);
        quantidade = findViewById(R.id.info_produto_txt_quantidade);
        medida = findViewById(R.id.info_produto_txt_medida);

        codBarras.setText(produtoRecebido.getIdCod());
        categoria.setText(produtoRecebido.getCategoria());
        produto.setText(produtoRecebido.getProduto());
        marca.setText(produtoRecebido.getMarca());
        discriminacao.setText(produtoRecebido.getDiscriminacao());
        precoCompra.setText(produtoRecebido.getPrecoCompra());
        precoVenda.setText(produtoRecebido.getPrecoVenda());
        quantidade.setText(produtoRecebido.getQuantidade());
        medida.setText(produtoRecebido.getUnidadeMedida());
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
        Intent enviaDados = new Intent(this, CadastroProdutoActivity.class);
        enviaDados.putExtra(CHAVE_PRODUTO, produtoRecebido);
        startActivity(enviaDados);
    }
//==================================================================================================
}
