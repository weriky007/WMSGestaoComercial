package com.alphazer0.wmsgestaocomercial.ui.activities.estoque;

import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.CHAVE_CLIENTE;
import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.CHAVE_PRODUTO_OUTRO;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alphazer0.wmsgestaocomercial.R;
import com.alphazer0.wmsgestaocomercial.model.Cliente;
import com.alphazer0.wmsgestaocomercial.model.Produto;

import org.w3c.dom.Text;

public class InformacoesProduto extends AppCompatActivity {
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
        setContentView(R.layout.activity_informacoes_produto);
        setTitle("Informações Produto");

        insereDadosProduto();
    }
//==================================================================================================
    private void insereDadosProduto(){
        Intent recebeDados = new Intent();
        recebeDados.hasExtra(CHAVE_PRODUTO_OUTRO);
        Bundle extra = getIntent().getExtras();
        produtoRecebido = (Produto) extra.getSerializable(CHAVE_PRODUTO_OUTRO);

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
}
