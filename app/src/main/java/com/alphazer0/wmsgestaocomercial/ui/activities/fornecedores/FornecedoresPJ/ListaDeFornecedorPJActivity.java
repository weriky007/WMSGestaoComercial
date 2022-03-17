package com.alphazer0.wmsgestaocomercial.ui.activities.fornecedores.FornecedoresPJ;
//==================================================================================================
import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.CHAVE_FORNECEDORPJ;
import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.FORNECEDORESPJ_PLAN;
import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.ID_PASTA;
import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.LINK_MACRO;
import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.TITULO_APPBAR_LISTA_DE_FORNECEDORES_PJ;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alphazer0.wmsgestaocomercial.R;
import com.alphazer0.wmsgestaocomercial.database.FornecedoresPJDatabase;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomFornecedorPJDAO;
import com.alphazer0.wmsgestaocomercial.model.FornecedorPJ;
import com.alphazer0.wmsgestaocomercial.ui.adapters.ListaFornecedoresPJAdapter;
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
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

//==================================================================================================
public class ListaDeFornecedorPJActivity extends AppCompatActivity {
    private RecyclerView listaFornecedoresPJ;
    private FloatingActionButton btnNovoFornecedorPJ;
    private ListaFornecedoresPJAdapter adapter;
    private RoomFornecedorPJDAO dao;
    private List<FornecedorPJ> fornecedorPJS = new ArrayList<>();
    private final Context context = this;
    //CONFIGURACAO SCRIPT E PLANILHA BASE DADOS
    String linkMacro= LINK_MACRO;
    String idPlanilha = ID_PASTA;
    private int put =0;
    public int id =0;
//==================================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_fornecedorespj);
        setTitle(TITULO_APPBAR_LISTA_DE_FORNECEDORES_PJ);

        configuraAdapter();
        configuraLista();
        configuraFabNovoFornecedorPJ();
    }
//==================================================================================================
    @Override
    protected void onResume() {
        super.onResume();
        adapter.atualiza(dao.todosFornecedoresPJ());
    }
//==================================================================================================
//    //MENU ITENS LISTA
//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//    super.onCreateContextMenu(menu, v, menuInfo);
//    getMenuInflater().inflate(R.menu.menu_listas_activity, menu);
//}
//
//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        int itemId = item.getItemId();
//        if (itemId == R.id.menu_remover_listas_activity) {
//            confirmaRemocao(item);
//        }
//        return super.onContextItemSelected(item);
//    }
//
//    public void confirmaRemocao(final MenuItem item) {
//    new AlertDialog.Builder(context).setTitle("Removendo Fornecedor PJ").setMessage("Deseja mesmo remover o FornecedorPJ?").setPositiveButton("Sim", (dialogInterface, i) -> {
//        FornecedorPJ fornecedorPJ = pegaFornecedorPJ(item);
//        put = 3;
//        id = fornecedorPJ.getId();
//        new SendRequest().execute();
//         remove(fornecedorPJ);
//    })
//            .setNegativeButton("Não",null)
//            .show();
//    }
//
//
//    private FornecedorPJ pegaFornecedorPJ(MenuItem item) {
//        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//        return adapter.getItem(menuInfo.position);
//    }
//==================================================================================================
    private void remove(FornecedorPJ fornecedorPJ){
    adapter.remove(fornecedorPJ);
    dao.removeFornecedorPJ(fornecedorPJ);
    }
//==================================================================================================
    private void configuraAdapter() {
        adapter = new ListaFornecedoresPJAdapter(this,fornecedorPJS);
        //PEGA TODOS OS CLIENTES DO BANCO DE DADOS
        dao = FornecedoresPJDatabase.getInstance(this).getFornecedorPJDAO();
    }

    private void configuraLista() {
        listaFornecedoresPJ = findViewById(R.id.list_view_fornecedorespj);
        listaFornecedoresPJ.setAdapter(adapter);

        //TIPO DE LAYOUT
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listaFornecedoresPJ.setLayoutManager(layoutManager);

        //COLOCA OS MAIS RECENTES PRIMEIRO NA LISTA
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

//        configuraClickPorItem(listaFornecedoresPJ);
//        registerForContextMenu(listaFornecedoresPJ);
    }

    private void configuraFabNovoFornecedorPJ() {
        btnNovoFornecedorPJ = findViewById(R.id.fab_novo_fornecedorPJ);
        btnNovoFornecedorPJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListaDeFornecedorPJActivity.this, CadastroFornecedorPJActivity.class));
                finish();
            }
        });
    }

    private void configuraClickPorItem(ListView listaFornecedoresPJ) {
        listaFornecedoresPJ.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                FornecedorPJ fornecedorPJEscolhido = (FornecedorPJ) adapterView.getItemAtPosition(position);
                abreFormularioModoEdita(fornecedorPJEscolhido);
            }
        });
    }

    private void abreFormularioModoEdita(FornecedorPJ fornecedorPJ) {
        Intent vaiParaDadosFornecedorPJ = new Intent(ListaDeFornecedorPJActivity.this, CadastroFornecedorPJActivity.class);
        vaiParaDadosFornecedorPJ.putExtra(CHAVE_FORNECEDORPJ, fornecedorPJ);
        startActivity(vaiParaDadosFornecedorPJ);
    }

    private void abreFormularioModoInsereNew() {
        startActivity(new Intent(ListaDeFornecedorPJActivity.this, CadastroFornecedorPJActivity.class));
    }
//==================================================================================================
    //CONFIGURACOES DO FILTRO PESQUISA
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
                adapter.atualiza(dao.todosFornecedoresPJ());
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
            action ="deleteFornecedorPJ";
        }

        enviaDados.put("pasta",ID_PASTA);
        enviaDados.put("planilha",FORNECEDORESPJ_PLAN);
        enviaDados.put("action",action);
        enviaDados.put("idFornecedorPJ",id);

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