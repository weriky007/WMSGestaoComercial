package com.alphazer0.wmsgestaocomercial.ui.activities.estoque;
//==================================================================================================

import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.ID_PASTA;
import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.LINK_MACRO;
import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.PRODUTOS_PLAN;
import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.TITULO_APPBAR_LISTA_DE_PRODUTOS;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alphazer0.wmsgestaocomercial.R;
import com.alphazer0.wmsgestaocomercial.database.ProdutosDatabase;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomProdutoDAO;
import com.alphazer0.wmsgestaocomercial.model.Produto;
import com.alphazer0.wmsgestaocomercial.ui.adapters.ListaEstoqueProdutosAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

//==================================================================================================
public class ListaDeProdutosActivity extends AppCompatActivity {
    private RecyclerView listaProdutos;
    private FloatingActionButton btnNovoProduto;
    private ListaEstoqueProdutosAdapter adapter;
    private RoomProdutoDAO dao;
    private List<Produto> produtos = new ArrayList<>();
    private String filtroCategoria = "";
    private List<String> categorias = new ArrayList<>();
    private HashSet<String> hashSet = new HashSet<>();
    private final Context context = this;
    //CONFIGURACAO SCRIPT E PLANILHA BASE DADOS
    String linkMacro= LINK_MACRO;
    String idPlanilha = ID_PASTA;
    private int put =0;
    public int id =0;
    private Spinner sp;
//==================================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produtos);
        setTitle(TITULO_APPBAR_LISTA_DE_PRODUTOS);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);

        configuraAdapter();
        configuraLista();
        configuraFabNovoProduto();
        configuraAutoComplete();
    }
//==================================================================================================
    @Override
    protected void onResume() {
        super.onResume();
        adapter.atualizaListaProdutos(dao.todosProdutos(),filtroCategoria);
    }
//==================================================================================================
   //CONFIGURA LISTA SUSPENSA DE FILTRO
    private void pegaCategorias(List<Produto> produto){

        for (Produto filtroGrupo : produto) {
            this.categorias.add(filtroGrupo.getCategoria());
        }
        hashSet.addAll(this.categorias);
        this.categorias.clear();
        this.categorias.add("Todos");
        this.categorias.addAll(hashSet);
    }

    private void configuraAutoComplete() {
        pegaCategorias(dao.todosProdutos());
        ArrayAdapter<String> adapterCategorias = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,categorias);
        adapterCategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp = findViewById(R.id.spinner);
        sp.setAdapter(adapterCategorias);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
               filtroCategoria = sp.getSelectedItem().toString();
               adapter.atualizaListaProdutos(dao.todosProdutos(),filtroCategoria);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
//==================================================================================================
    //MENU ITENS LISTA
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == 0) {
            confirmaRemocao(item);
        }
        return super.onContextItemSelected(item);
    }

    public void confirmaRemocao(final MenuItem item) {
    new AlertDialog.Builder(context).setTitle("Removendo Produto").setMessage("Deseja mesmo remover o Produto?").setPositiveButton("Sim", (dialogInterface, i) -> {
        int position = item.getGroupId();
        Produto produto = produtos.get(position);
        dao.removeProduto(produto);
        adapter.remove(position);
        put = 3;
        id = produto.getId();
        new SendRequest().execute();
    })
            .setNegativeButton("Não",null)
            .show();
    }
//==================================================================================================
    private void configuraAdapter() {
        adapter = new ListaEstoqueProdutosAdapter(this,produtos);
        //PEGA TODOS OS CLIENTES DO BANCO DE DADOS
        dao = ProdutosDatabase.getInstance(this).getProdutoDAO();
    }

    private void configuraLista() {
        listaProdutos = findViewById(R.id.list_view_produtos_outros);
        listaProdutos.setAdapter(adapter);

        //TIPO DE LAYOUT
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listaProdutos.setLayoutManager(layoutManager);
        //COLOCA OS MAIS RECENTES PRIMEIRO NA LISTA
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
    }

    private void configuraFabNovoProduto() {
        btnNovoProduto = findViewById(R.id.fab_novo_produto_outro);
        btnNovoProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListaDeProdutosActivity.this, CadastroProdutoActivity.class));
                finish();
            }
        });
    }
//==================================================================================================
    //CONFIGURACOES DO CAMPO PESQUISA
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_search, menu);

    MenuItem searchItem = menu.findItem(R.id.action_search);
    SearchView campoBusca = (SearchView) searchItem.getActionView();

    campoBusca.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
    campoBusca.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            if (newText.toString() != null && !newText.toString().equals("")) {
                adapter.getFilter().filter(newText);
            }else {
                adapter.atualizaListaProdutos(dao.todosProdutos(),filtroCategoria);
            }
            return false;
        }
    });
    return true;
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
//            Toast.makeText(getApplicationContext(),"Salvo Na GSheet!!!",Toast.LENGTH_LONG).show();
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

        if(put == 3){
            action ="deleteProduto";
        }

        enviaDados.put("pasta",ID_PASTA);
        enviaDados.put("planilha",PRODUTOS_PLAN);
        enviaDados.put("action",action);
        enviaDados.put("idProduto",id);

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
}