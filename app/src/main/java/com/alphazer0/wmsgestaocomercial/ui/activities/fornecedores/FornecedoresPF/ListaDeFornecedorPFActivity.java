package com.alphazer0.wmsgestaocomercial.ui.activities.fornecedores.FornecedoresPF;
//==================================================================================================

import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.CHAVE_FORNECEDORPF;
import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.FORNECEDORESPF_PLAN;
import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.ID_PASTA;
import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.LINK_MACRO;
import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.TITULO_APPBAR_LISTA_DE_FORNECEDORES_PF;

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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alphazer0.wmsgestaocomercial.R;
import com.alphazer0.wmsgestaocomercial.database.FornecedoresPFDatabase;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomFornecedorPFDAO;
import com.alphazer0.wmsgestaocomercial.model.FornecedorPF;
import com.alphazer0.wmsgestaocomercial.model.Produto;
import com.alphazer0.wmsgestaocomercial.ui.activities.estoque.ListaDeProdutosActivity;
import com.alphazer0.wmsgestaocomercial.ui.adapters.ListaFornecedoresPFAdapter;
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
public class ListaDeFornecedorPFActivity extends AppCompatActivity {
    private RecyclerView listaFornecedoresPF;
    private FloatingActionButton btnNovoCliente;
    private ListaFornecedoresPFAdapter adapter;
    private RoomFornecedorPFDAO dao;
    private List<FornecedorPF> fornecedorPFS = new ArrayList<>();
    private final Context context = this;
    //CONFIGURACAO SCRIPT E PLANILHA BASE DADOS
    String linkMacro = LINK_MACRO;
    String idPlanilha = ID_PASTA;
    private int put = 0;
    public int id = 0;

    //==================================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_fornecedorespf);
        setTitle(TITULO_APPBAR_LISTA_DE_FORNECEDORES_PF);

        configuraAdapter();
        configuraLista();
        configuraFabNovoFornecedorPF();
    }
//==================================================================================================
    @Override
    protected void onResume() {
        super.onResume();
        adapter.atualiza(dao.todosFornecedoresPF());
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
        new AlertDialog.Builder(context).setTitle("Removendo Fornecedor").setMessage("Deseja mesmo remover o Fornecedor?").setPositiveButton("Sim", (dialogInterface, i) -> {
            int position = item.getGroupId();
            FornecedorPF fornecedorPF = fornecedorPFS.get(position);
            dao.removeFornecedorPF(fornecedorPF);
            adapter.remove(position);
            put = 3;
            id = fornecedorPF.getId();
            new SendRequest().execute();
        })
                .setNegativeButton("Não", null)
                .show();
    }
//==================================================================================================
    private void configuraAdapter() {
        adapter = new ListaFornecedoresPFAdapter(this, fornecedorPFS);
        //PEGA TODOS OS CLIENTES DO BANCO DE DADOS
        dao = FornecedoresPFDatabase.getInstance(this).getFornecedorPFDAO();
    }

    private void configuraLista() {
        listaFornecedoresPF = findViewById(R.id.list_view_fornecedorespf);
        listaFornecedoresPF.setAdapter(adapter);

        //TIPO DE LAYOUT
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listaFornecedoresPF.setLayoutManager(layoutManager);
        //COLOCA OS MAIS RECENTES PRIMEIRO NA LISTA
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
    }

    private void configuraFabNovoFornecedorPF() {
        btnNovoCliente = findViewById(R.id.fab_novo_fornecedorPF);
        btnNovoCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListaDeFornecedorPFActivity.this, CadastroFornecedorPFActivity.class));
                finish();
            }
        });
    }

    private void configuraClickPorItem(ListView listaFornecedoresPF) {
        listaFornecedoresPF.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                FornecedorPF fornecedorPFEscolhido = (FornecedorPF) adapterView.getItemAtPosition(position);
                abreFormularioModoEdita(fornecedorPFEscolhido);
            }
        });
    }

    private void abreFormularioModoEdita(FornecedorPF fornecedorPF) {
        Intent vaiParaDadosFornecedorPF = new Intent(ListaDeFornecedorPFActivity.this, CadastroFornecedorPFActivity.class);
        vaiParaDadosFornecedorPF.putExtra(CHAVE_FORNECEDORPF, fornecedorPF);
        startActivity(vaiParaDadosFornecedorPF);
    }

    private void abreFormularioModoInsereNew() {
        startActivity(new Intent(ListaDeFornecedorPFActivity.this, CadastroFornecedorPFActivity.class));
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
                } else {
                    adapter.atualiza(dao.todosFornecedoresPF());
                }
                return false;
            }
        });
        return true;
    }

    //==================================================================================================
    //INICIANDO COMUNICACAO WEB
    public class SendRequest extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        protected String doInBackground(String... arg0) {
            try {
                URL url = new URL(linkMacro);
                String idPlan = idPlanilha;

                JSONObject enviaDados = enviaDadosParaPlanilha(idPlan);
                HttpURLConnection connection = executaConeccaoExternalServer(url);
                escreveDadosNaPlanilha(enviaDados, connection);
                return verificaLinhaVazia(connection);
            }//end try

            catch (Exception e) {
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
            String linha = "";

            while ((linha = leValor.readLine()) != null) {
                sb.append(linha);
                break;
            }//end while

            leValor.close();
            return sb.toString();

        }//end if
        else {
            return new String("false : " + codigoWeb);
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

        if (put == 3) {
            action = "deleteFornecedorPF";
        }

        enviaDados.put("pasta", ID_PASTA);
        enviaDados.put("planilha", FORNECEDORESPF_PLAN);
        enviaDados.put("action", action);
        enviaDados.put("idFornecedorPF", id);

        Log.e("params", enviaDados.toString());
        return enviaDados;
    }

    public String configuraDadosParaWeb(JSONObject params) throws Exception {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while (itr.hasNext()) {
            String key = itr.next();
            Object value = params.get(key);

            if (first) {
                first = false;
            } else {
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