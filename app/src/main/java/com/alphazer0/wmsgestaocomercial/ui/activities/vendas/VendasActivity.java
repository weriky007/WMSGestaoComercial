package com.alphazer0.wmsgestaocomercial.ui.activities.vendas;
//==================================================================================================

import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.ID_PASTA;
import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.LINK_MACRO;
import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.PRODUTOS_PLAN;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alphazer0.wmsgestaocomercial.R;
import com.alphazer0.wmsgestaocomercial.database.ClientesDatabase;
import com.alphazer0.wmsgestaocomercial.database.ProdutosDatabase;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomClienteDAO;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomProdutoDAO;
import com.alphazer0.wmsgestaocomercial.model.Produto;
import com.alphazer0.wmsgestaocomercial.ui.adapters.ListaEstoqueProdutosAdapter;
import com.alphazer0.wmsgestaocomercial.ui.adapters.ListaProdutosVendasAdapter;
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
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

//==================================================================================================
public class VendasActivity extends AppCompatActivity {
    public static final String ADICIONAR_PRODUTO = "Adicionar Produto";
    public static final String OK = "Ok";
    public static final String CANCELAR = "Cancelar";
    public static final String SCAN_CANCELADO = "Scan Cancelado!";
    public static final String VENDAS = "Vendas";
    public static final String REMOVENDO_PRODUTO = "Removendo Produto";
    public static final String DESEJA_MESMO_REMOVER_O_PRODUTO = "Deseja mesmo remover o Produto?";
    public static final String SIM = "Sim";
    public static final String DELETE_PRODUTO = "deleteProduto";
    public static final String PASTA = "pasta";
    public static final String PLANILHA = "planilha";
    public static final String ACTION = "action";
    public static final String ID_PRODUTO = "idProduto";
    public static final String POST = "POST";
    public static final String NÃO = "Não";

    private ListView listaProdutos;
    private ListaProdutosVendasAdapter produtosVendaAdapter;
    private ListaEstoqueProdutosAdapter produtosEstoqueAdapter;
    private RoomProdutoDAO produtoDao;
    private RoomClienteDAO clienteDAO;
    private final Context context = this;

    private EditText campoCodigoBarras;
    private MultiAutoCompleteTextView campoCliente;
    private MultiAutoCompleteTextView campoProduto;
    private EditText campoQuantidade;
    private TextView valorTotal;

    private RadioGroup radioGroupFormasPagamento;
    private RadioGroup radioGroupParcelaCC;

    private List<Produto> produtos = new ArrayList<>();
    private List<Produto> listaCompras = new ArrayList<>();
    private List<String> filtroTituloProdutos = new ArrayList<>();
    private List<String> filtroCodigo = new ArrayList<>();
    private List<String> filtroClientes = new ArrayList<>();
    private HashSet<String> hashSetTituloProdutos = new HashSet<>();
    private HashSet<String> hashSetCodigos = new HashSet<>();
    private HashSet<String> hashSetClientes = new HashSet<>();
    private FloatingActionButton fabAdicionaProduto;
    private FloatingActionButton fabLerCodigo;

    private PegaInformacoesParaVenda pegaInformacoesParaVenda = new PegaInformacoesParaVenda();
    private ConfiguracaoIOEstoqueVendas configuracaoIOEstoqueVendas = new ConfiguracaoIOEstoqueVendas();
    private CalculaValorTotalDaVenda calculaValorTotalDaVenda = new CalculaValorTotalDaVenda();
    private BuscaCodigoDeBarras buscaCodigoDeBarras = new BuscaCodigoDeBarras();
    private BuscaProduto buscaProduto = new BuscaProduto();
    private ConfiguraLeitorCodigoDeBarras configuraLeitorCodigoDeBarras = new ConfiguraLeitorCodigoDeBarras();
    private ContaDoCliente contaDoCliente = new ContaDoCliente();
    private CalculaRecebimentoEmDinheiro calculaRecebimentoEmDinheiro = new CalculaRecebimentoEmDinheiro();
    private CalculaParcelasCartaoCredito calculaParcelasCartaoCredito = new CalculaParcelasCartaoCredito();

    //CONFIGURACAO SCRIPT E PLANILHA BASE DADOS
    String linkMacro = LINK_MACRO;
    String idPlanilha = ID_PASTA;
    private int put = 0;
    public int id = 0;
    private BigDecimal total = new BigDecimal("0.0");
    private String resultadoQuantidade;
    private String escolhaFormaPagamento;
    private String escolhaCcParcelamento;

    //==================================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendas);
        setTitle(VENDAS);
        bindDosElementos();
        configuraAdapter();
        configuraLista();
        configuraFabAddProduto();
    }

    //==================================================================================================
    @Override
    protected void onResume() {
        super.onResume();
        produtosVendaAdapter.pegaTodosProdutos(listaCompras);
    }

    //==================================================================================================
    private void bindDosElementos() {
        valorTotal = findViewById(R.id.valor);
        fabAdicionaProduto = findViewById(R.id.fab_adiciona_produto_venda);
    }

    //==================================================================================================
    private void configuraAdapter() {
        produtosVendaAdapter = new ListaProdutosVendasAdapter(listaCompras);
        //PEGA TODOS OS CLIENTES DO BANCO DE DADOS
        produtoDao = ProdutosDatabase.getInstance(this).getProdutoDAO();
        clienteDAO = ClientesDatabase.getInstance(this).getClienteDAO();
    }

    private void configuraLista() {
        listaProdutos = findViewById(R.id.list_view_lista_produtos);
        listaProdutos.setAdapter(produtosVendaAdapter);

        configuraClickPorItem(listaProdutos);
        registerForContextMenu(listaProdutos);
    }

    private void configuraClickPorItem(ListView listaProdutos) {
        listaProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Produto produtoEscolhido = (Produto) adapterView.getItemAtPosition(position);

            }
        });
    }

    //==================================================================================================
    //CONFIGURA ALERTDIALOG TELA VENDAS
    public void mostra() {
        View viewCriada = LayoutInflater.from(VendasActivity.this)
                .inflate(R.layout.activity_fomulario_adiciona_produto_vendas, null);

        bindViewsVenda(viewCriada);
        configuraAutoComplete();
        preencheProduto();
        preencheCodigoBarras();
        configuraLeitor();

        ColorDrawable back = new ColorDrawable(Color.WHITE);
        InsetDrawable inset = new InsetDrawable(back, 0);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(VendasActivity.this);

        alertDialog.setMessage(ADICIONAR_PRODUTO).setView(viewCriada)
                .setPositiveButton(OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        pegaInformacoesParaVenda.pegaProduto(produtoDao.todosProdutos(), filtroTituloProdutos, filtroCodigo, produtos, hashSetTituloProdutos, hashSetCodigos);
                        configuracaoIOEstoqueVendas.diminuiItemDoEstoque(campoProduto, campoCodigoBarras, produtos, campoQuantidade, resultadoQuantidade, produtoDao, listaCompras);
                        calculaValorTotalDaVenda.calculaTotal(listaCompras, total, valorTotal, campoCodigoBarras, campoProduto, campoQuantidade, produtosVendaAdapter);
                        pegaInformacoesParaVenda.pegaClientes(clienteDAO.todosClientes(), filtroClientes, hashSetClientes);
                        contaDoCliente.contaCliente(clienteDAO.todosClientes(), clienteDAO, campoCliente, valorTotal);
                    }
                }).setNegativeButton(CANCELAR, null).show().getWindow().setBackgroundDrawable(inset);
    }

    private void bindViewsVenda(View viewCriada) {
        campoCodigoBarras = viewCriada.findViewById(R.id.edit_d_vendas_codigo_barras);
        campoProduto = viewCriada.findViewById(R.id.edit_d_vendas_produto);
        campoCliente = viewCriada.findViewById(R.id.edit_d_vendas_cliente);
        campoQuantidade = viewCriada.findViewById(R.id.edit_d_vendas_quantidade);
        fabLerCodigo = viewCriada.findViewById(R.id.fab_scan_d_vendas_produto);
    }

    //==================================================================================================
    private void configuraAutoComplete() {
        pegaInformacoesParaVenda.pegaClientes(clienteDAO.todosClientes(), filtroClientes, hashSetClientes);
        pegaInformacoesParaVenda.pegaProduto(produtoDao.todosProdutos(), filtroTituloProdutos, filtroCodigo, produtos, hashSetTituloProdutos, hashSetCodigos);

        ArrayAdapter<String> adapterClientes = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, filtroClientes);
        ArrayAdapter<String> adapterProdutos = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, filtroTituloProdutos);

        campoCliente.setAdapter(adapterClientes);
        campoCliente.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        campoProduto.setAdapter(adapterProdutos);
        campoProduto.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }

    private void preencheCodigoBarras() {
        pegaInformacoesParaVenda.pegaProduto(produtoDao.todosProdutos(), filtroTituloProdutos, filtroCodigo, produtos, hashSetTituloProdutos, hashSetCodigos);
        campoProduto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                buscaCodigoDeBarras.busca(campoCodigoBarras, campoProduto, produtos);
            }
        });
    }

    private void preencheProduto() {
        pegaInformacoesParaVenda.pegaProduto(produtoDao.todosProdutos(), filtroTituloProdutos, filtroCodigo, produtos, hashSetTituloProdutos, hashSetCodigos);
        campoCodigoBarras.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                buscaProduto.busca(campoProduto, campoCodigoBarras, produtos);
            }
        });
    }

    //==================================================================================================
    //CONFIGURA LEITOR DE  CODIGO
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                campoCodigoBarras.setText(result.getContents());
                alert(result.getContents());
            } else {
                alert(SCAN_CANCELADO);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void alert(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    private void configuraLeitor() {
        final Activity activity = this;
        fabLerCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configuraLeitorCodigoDeBarras.configuraLeitor(activity);
            }
        });
    }

    //==================================================================================================
    //MENU ITENS LISTA
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_listas_activity, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_remover_listas_activity) {
            confirmaRemocao(item);
        }
        return super.onContextItemSelected(item);
    }

    //CONFIGURACOES DE REMOCAO
    public void confirmaRemocao(final MenuItem item) {
        new AlertDialog.Builder(context).setTitle(REMOVENDO_PRODUTO).setMessage(DESEJA_MESMO_REMOVER_O_PRODUTO).setPositiveButton(SIM, (dialogInterface, i) -> {
            Produto produto = pegaProdutoItem(item);
            put = 3;
            id = produto.getId();
//            new SendRequest().execute();
            remove(produto);
        })
                .setNegativeButton(NÃO, null)
                .show();
    }


    private Produto pegaProdutoItem(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        return produtosVendaAdapter.getItem(menuInfo.position);
    }

    private void remove(Produto produto) {
        configuraRemocaoProdutoDoCarrinhoCompras(produto);
    }

//==================================================================================================
    //MENU CONCLUI VENDA
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_vendas_concluir, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_botao_concluir_venda) {
            concluiVenda();
        }
        return super.onOptionsItemSelected(item);
    }
//==================================================================================================
    public void concluiVenda() {
        //CONFIGURA VIEW A SEREM INFLADAS
        View layoutConcluiVenda = getLayoutInflater().inflate(R.layout.activity_formulario_conclui_venda, null);
        View layoutDinheiro = getLayoutInflater().inflate(R.layout.layout_pagamento_dinheiro, null);
        View layoutCC = getLayoutInflater().inflate(R.layout.layout_pagamento_cartao_credito,null);
        View layoutCCParcelas = getLayoutInflater().inflate(R.layout.layout_pagamento_cartao_credito_parcelas,null);

        //CONFIG STILO DO ALERTDIALOG
        ColorDrawable back = new ColorDrawable(Color.WHITE);
        InsetDrawable inset = new InsetDrawable(back, 0);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(VendasActivity.this);

        //CONFIG DOS RADIOSGROUPS
        radioGroupFormasPagamento = layoutConcluiVenda.findViewById(R.id.formas_de_pagamento);
        radioGroupParcelaCC = layoutCC.findViewById(R.id.radiogroup_cc);

        //CHECAGEM DAS ESCOLHAS DE PAGAMENTO
        radioGroupFormasPagamento.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int item = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButtonConcluiVenda = layoutConcluiVenda.findViewById(item);
                escolhaFormaPagamento = radioButtonConcluiVenda.getText().toString();

                LinearLayout layoutPagamentoDinheiro = configuraRecebimentoDinheiro(layoutConcluiVenda, layoutDinheiro);
                LinearLayout layoutPagamentoCC = layoutConcluiVenda.findViewById(R.id.linear_layout_formas_pagamentos_cartao_cc);

                switch (escolhaFormaPagamento) {
                    case "Dinheiro":
                        layoutPagamentoDinheiro.addView(layoutDinheiro);
                        layoutPagamentoCC.removeAllViews();
                        break;
                    case "Cartao de Credito":
                        layoutPagamentoCC.addView(layoutCC);
                        layoutPagamentoDinheiro.removeAllViews();
                        break;
                    case "Cartao de Debito":
                        layoutPagamentoDinheiro.removeAllViews();
                        layoutPagamentoCC.removeAllViews();
                        break;
                    case "Conta Cliente":
                        layoutPagamentoDinheiro.removeAllViews();
                        layoutPagamentoCC.removeAllViews();
                        break;
                }
            }
        });

        //CHECAGEM DAS ESCOLHAS DE PARCELAMENTO CC
        radioGroupParcelaCC.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int item = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = layoutCC.findViewById(item);
                escolhaCcParcelamento = radioButton.getText().toString();
                LinearLayout layoutParcelasCC = layoutCC.findViewById(R.id.layout_formas_pagamentos_cartao_cc);

                EditText recebeNumeroParcelas = layoutCCParcelas.findViewById(R.id.edit_numero_parcelas);
                EditText taxa = layoutCCParcelas.findViewById(R.id.edit_taxa);
                TextView vlParcela = layoutCCParcelas.findViewById(R.id.valor_parcela);
                Button btnConcluiCalculoParcelasCC = layoutCCParcelas.findViewById(R.id.conclui_parcelas);

                btnConcluiCalculoParcelasCC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        calculaParcelasCartaoCredito.calculoParcelasCC(recebeNumeroParcelas, taxa, vlParcela,valorTotal);
                    }
                });

                switch (escolhaCcParcelamento){
                    case "A Vista":
                        layoutParcelasCC.removeAllViews();
                        break;
                    case "Parcelado":
                        layoutParcelasCC.addView(layoutCCParcelas);
                        break;
                }
            }
        });

        //CONFIGURANDO ALERTDIALOG
        alertDialog.setTitle("Concluir Venda")
                .setView(layoutConcluiVenda);

        alertDialog.setPositiveButton(OK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        alertDialog.setNegativeButton(CANCELAR, null)
                .show()
                .getWindow()
                .setBackgroundDrawable(inset);
    }
 //==================================================================================================
    private LinearLayout configuraRecebimentoDinheiro(View view, View layoutDinheiro) {
        LinearLayout layoutFormasPagamentoDinheiro = view.findViewById(R.id.linear_layout_forma_pagamento_dinheiro);
        calculaRecebimentoEmDinheiro.calcula(layoutDinheiro, valorTotal);
        return layoutFormasPagamentoDinheiro;
    }
//==================================================================================================
    private void configuraRemocaoProdutoDoCarrinhoCompras(Produto produto) {
        configuracaoIOEstoqueVendas.devolveItemAoEstoque(produtoDao, produto, produtos, total, valorTotal, produtosVendaAdapter, listaCompras);
    }

//==================================================================================================
    //JANELA ADICIONA PRODUTO AO CARRINHO
    private void configuraFabAddProduto() {
        fabAdicionaProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostra();
            }
        });
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
        connection.setRequestMethod(POST);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        return connection;
    }

    //ENVIA DADOS PARA A PLANILHA GOOGLE
    private JSONObject enviaDadosParaPlanilha(String action) throws JSONException {
        JSONObject enviaDados = new JSONObject();

        if (put == 3) {
            action = DELETE_PRODUTO;
        }

        enviaDados.put(PASTA, ID_PASTA);
        enviaDados.put(PLANILHA, PRODUTOS_PLAN);
        enviaDados.put(ACTION, action);
        enviaDados.put(ID_PRODUTO, id);

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
