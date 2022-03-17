package com.alphazer0.wmsgestaocomercial.ui.activities.estoque;
//==================================================================================================
import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.CHAVE_PRODUTO_OUTRO;
import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.ID_PASTA;
import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.LINK_MACRO;
import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.PRODUTOS_PLAN;
import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.TITULO_APPBAR_CADASTRO_DE_PRODUTOS;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.alphazer0.wmsgestaocomercial.R;
import com.alphazer0.wmsgestaocomercial.database.ProdutosDatabase;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomProdutoDAO;
import com.alphazer0.wmsgestaocomercial.model.Produto;
import com.alphazer0.wmsgestaocomercial.ui.adapters.ListaEstoqueProdutosAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

//==================================================================================================
public class CadastroProdutoActivity extends AppCompatActivity {
    public CadastroProdutoActivity() throws SQLException {
    }

    @Override
    protected void onCreate(Bundle saveIntanceState) {
        ActionBar bar = getSupportActionBar();
//        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFB0000")));
        super.onCreate(saveIntanceState);
        setTitle(TITULO_APPBAR_CADASTRO_DE_PRODUTOS);
        setContentView(R.layout.activity_formulario_dados_produtos);

        ProdutosDatabase dataBase = ProdutosDatabase.getInstance(this);
        dao = dataBase.getProdutoDAO();

        bindDosCampos();
        carregaProduto();
        configuraBotoes();
        configuraAutoComplete();
    }
//==================================================================================================
    //REFERENCIANDO OS ELEMENTOS
    private Produto produto;
    private RoomProdutoDAO dao;
    private ListaEstoqueProdutosAdapter adapter;
    private String filtroCategoria = "";
    private List<String> categorias = new ArrayList<>();
    private HashSet<String> hashSet = new HashSet<>();
//    private  List<Produto> produtos;

    //CONFIGURACAO SCRIPT E PLANILHA BASE DADOS
    String linkMacro= LINK_MACRO;
    String idPlanilha = ID_PASTA;
    int put = 0;

    private List<Produto> produtos = new ArrayList<>();
    public int id;

    //DADOS PRODUTO
    private EditText campoIdCod;
    private FloatingActionButton btnLerCodigo;
    private MultiAutoCompleteTextView campoCategoria;
    private EditText campoProduto;
    private EditText campoMarca;
    private EditText campoDiscriminacao;
    private EditText campoPrecoCompra;
    private EditText campoPrecoVenda;
    private EditText campoQuantidade;
    private EditText campoUnidadeMedida;

    private Button botao;
//==================================================================================================
    private void bindDosCampos() {
        campoIdCod = findViewById(R.id.edit_codigo_barras_produto);
        campoCategoria = findViewById(R.id.edit_categoria_produto);
        campoProduto = findViewById(R.id.edit_produto);
        campoMarca = findViewById(R.id.edit_marca_produto);
        campoDiscriminacao = findViewById(R.id.edit_discriminacao_produto);
        campoPrecoCompra = findViewById(R.id.edit_preco_compra_produto);
        campoPrecoVenda = findViewById(R.id.edit_preco_venda_produto);
        campoQuantidade = findViewById(R.id.edit_quantidade_produto);
        campoUnidadeMedida = findViewById(R.id.edit_unidade_medida_produto);

        btnLerCodigo = findViewById(R.id.fab_scan_produto);
        botao = findViewById(R.id.botao_salvar_produto);
    }
//==================================================================================================
    //AUTOCOMPLETE
private void pegaCategorias(List<Produto> produto){

    for (Produto filtroGrupo : produto) {
        this.categorias.add(filtroGrupo.getCategoria());
    }
    hashSet.addAll(this.categorias);
    this.categorias.clear();
//    this.categorias.add("Todos");
    this.categorias.addAll(hashSet);
}

    private void configuraAutoComplete() {
        pegaCategorias(dao.todosProdutos());
        ArrayAdapter<String> adapterCategorias = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,categorias);

        campoCategoria.setAdapter(adapterCategorias);
        campoCategoria.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }
//==================================================================================================
    private void recebeDadosDigitadosNosCampos() {
        String codigoBarras = campoIdCod.getText().toString();
        String categoria = campoCategoria.getText().toString();
        String produto = campoProduto.getText().toString();
        String marca = campoMarca.getText().toString();
        String discriminacao = campoDiscriminacao.getText().toString();
        String precoCompra = campoPrecoCompra.getText().toString();
        String precoVenda = campoPrecoVenda.getText().toString();
        String quantidade = campoQuantidade.getText().toString();
        String unidadeMedida = campoUnidadeMedida.getText().toString();

        this.produto.setIdCod(codigoBarras);
        this.produto.setCategoria(categoria);
        this.produto.setProduto(produto);
        this.produto.setMarca(marca);
        this.produto.setDiscriminacao(discriminacao);
        this.produto.setPrecoCompra(precoCompra);
        this.produto.setPrecoVenda(precoVenda);
        this.produto.setQuantidade(quantidade);
        this.produto.setUnidadeMedida(unidadeMedida);
    }
//==================================================================================================
    private void carregaProduto() {
        Intent dados = getIntent();
        if (dados.hasExtra(CHAVE_PRODUTO_OUTRO)) {
            produto = (Produto) dados.getSerializableExtra(CHAVE_PRODUTO_OUTRO);
            preencheCamposParaEdicao();
        } else {
            produto = new Produto();
        }
    }
//==================================================================================================
    private void preencheCamposParaEdicao() {
        campoIdCod.setText(produto.getIdCod());
        campoCategoria.setText(produto.getCategoria());
        campoProduto.setText(produto.getProduto());
        campoMarca.setText(produto.getMarca());
        campoDiscriminacao.setText(produto.getDiscriminacao());
        campoPrecoCompra.setText(produto.getPrecoCompra());
        campoPrecoVenda.setText(produto.getPrecoVenda());
        campoQuantidade.setText(produto.getQuantidade());
        campoUnidadeMedida.setText(produto.getUnidadeMedida());
    }
//==================================================================================================
    private void concluiCadastro()  {
        recebeDadosDigitadosNosCampos();
        if (produto.temIdValido()) {
            put = 2;
            dao.editaProduto(produto);
            produtos = dao.todosProdutos();
            Toast.makeText(CadastroProdutoActivity.this, "Editado com Sucesso!", Toast.LENGTH_SHORT).show();
        } else {
            put = 1;
            dao.salvaProduto(produto);
            produtos = dao.todosProdutos();
            Toast.makeText(CadastroProdutoActivity.this, "Salvo com Sucesso!", Toast.LENGTH_SHORT).show();
        }
    }
//==================================================================================================
    private void realizaVerificacao(){
        String codigo = campoIdCod.getText().toString();
        String categoria = campoCategoria.getText().toString();
        String produto = campoProduto.getText().toString();
        String marca = campoMarca.getText().toString();
        String vlCompra = campoPrecoCompra.getText().toString();
        String vlVenda = campoPrecoVenda.getText().toString();
        String quantidade = campoQuantidade.getText().toString();

        if(codigo.equals(null) || codigo.equals("")){
            Toast.makeText(CadastroProdutoActivity.this, "Preencha o código de barras", Toast.LENGTH_LONG).show();
        }else{
            if(categoria.equals(null) || categoria.equals("")){
                Toast.makeText(CadastroProdutoActivity.this, "Preencha a categoria", Toast.LENGTH_LONG).show();
            }else{
                if(produto.equals(null) || produto.equals("")){
                    Toast.makeText(CadastroProdutoActivity.this, "Preencha o produto", Toast.LENGTH_LONG).show();
                }else{
                    if(marca.equals(null) || marca.equals("")){
                        Toast.makeText(CadastroProdutoActivity.this, "Preencha a marca", Toast.LENGTH_LONG).show();
                    }else{
                        if(vlCompra.equals(null) || vlCompra.equals("")){
                            Toast.makeText(CadastroProdutoActivity.this, "Preencha o valor de compra", Toast.LENGTH_LONG).show();
                        }else{
                            if(vlVenda.equals(null) || vlVenda.equals("")){
                                Toast.makeText(CadastroProdutoActivity.this, "Preencha o valor de venda", Toast.LENGTH_LONG).show();
                            }else{
                                if(quantidade.equals(null) || quantidade.equals("")){
                                    Toast.makeText(CadastroProdutoActivity.this, "Preencha a quantidade", Toast.LENGTH_LONG).show();
                                }else{
                                    concluiCadastro();
                                    new SendRequest().execute();
                                    startActivity(new Intent(CadastroProdutoActivity.this, ListaDeProdutosActivity.class));
                                    finish();
                                }
                            }
                        }
                    }
                }
            }
        }
    }
//==================================================================================================
//INICIANDO COMUNICACAO WEB
public class SendRequest extends AsyncTask<String, Void, String> {

    protected void onPreExecute(){}

    protected String doInBackground(String... arg0) {
        try{
            URL url = new URL(linkMacro);
            String idPlan= idPlanilha;

            JSONObject enviaDados = enviaDadosParaPlanilha(idPlan);
            HttpURLConnection connection = executaConeccaoExternalServer(url);
            escreveDadosNaPlanilha(enviaDados, connection);
            return verificaLinhaVazia(connection);
        }//end try

        catch(Exception e){
            return new String("Exception: " + e.getMessage());
        }//end catch

    }//end doInBackGround

    @Override
    protected void onPostExecute(String resultado) {
        Toast.makeText(getApplicationContext(),"Salvo Na GSheet!!!",Toast.LENGTH_LONG).show();
//            Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();
    }//end onPostExecute
}//end sendRequest
//==================================================================================================
    private String verificaLinhaVazia(HttpURLConnection connection) throws IOException {
        int codigoWeb = connection.getResponseCode();
        if (codigoWeb == HttpsURLConnection.HTTP_OK) {
            BufferedReader leValor = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer sb = new StringBuffer("");
            String linha="";

            while((linha = leValor.readLine()) != null) {
                sb.append(linha);
                break;
            }//end while

            leValor.close();
            return sb.toString();

        }//end if
        else {
            return new String("false : "+codigoWeb);
        }//end else
    }

    private void escreveDadosNaPlanilha(JSONObject enviaDados, HttpURLConnection connection) throws Exception {
        OutputStream saida = connection.getOutputStream();
        BufferedWriter escrita = new BufferedWriter(new OutputStreamWriter(saida, "UTF-8"));

        escrita.write(configuraDadosParaWeb(enviaDados));
        escrita.flush();
        escrita.close();
        saida.close();
    }

    private HttpURLConnection executaConeccaoExternalServer(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(15000 /* milliseconds */);
        connection.setConnectTimeout(15000 /* milliseconds */);
        connection.setRequestMethod("POST");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        return connection;
    }

    //ENVIA DADOS PARA A PLANILHA GOOGLE
    private JSONObject enviaDadosParaPlanilha(String action) throws JSONException {
        JSONObject enviaDados = new JSONObject();
        if(put == 1) {
            action = "addProduto";
           produto = produtos.get(produtos.size()-1);
           id = produto.getId();
        }
        if(put == 2){
            action ="updateProduto";
            id = produto.getId();
        }

        enviaDados.put("pasta", ID_PASTA);
        enviaDados.put("planilha",PRODUTOS_PLAN);
        enviaDados.put("action",action);

        enviaDados.put("idProduto",produto.getId());
        enviaDados.put("idCod", produto.getIdCod());
        enviaDados.put("categoria", produto.getCategoria());
        enviaDados.put("produto", produto.getProduto());
        enviaDados.put("marca", produto.getMarca());
        enviaDados.put("discriminacao", produto.getDiscriminacao());
        enviaDados.put("precoCompra", produto.getPrecoCompra());
        enviaDados.put("precoVenda", produto.getPrecoVenda());
        enviaDados.put("quantidade", produto.getQuantidade());
        enviaDados.put("unidadeMedida", produto.getUnidadeMedida());

        Log.e("params",enviaDados.toString());
        return enviaDados;
    }

    public String configuraDadosParaWeb(JSONObject params) throws Exception {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){
            String key= itr.next();
            Object value = params.get(key);

            if (first) {
                first = false;
            }else {
                result.append("&");
            }
            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }//end configuraData
//==================================================================================================
    //PEGA O RESULTADO DO SCANNER
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null){
            if(result.getContents() != null){
                campoIdCod.setText(result.getContents());
                alert(result.getContents());
            }else{
                alert("Scan Cancelado!");
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void alert(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }

    //CONFIGURA LEITOR E BOTOES
    private void configuraBotoes() {
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizaVerificacao();
            }
        });


        final Activity activity = this;
        btnLerCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Ativar/Desativar Lanterna: Volume+/-");
                integrator.setCameraId(0);
                integrator.setTorchEnabled(true);
                integrator.setBeepEnabled(true);
                integrator.initiateScan();
            }
        });
    }
//==================================================================================================
}

