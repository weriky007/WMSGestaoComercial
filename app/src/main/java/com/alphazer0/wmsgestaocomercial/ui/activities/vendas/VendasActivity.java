package com.alphazer0.wmsgestaocomercial.ui.activities.vendas;
//==================================================================================================

import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.ID_PASTA;
import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.LINK_MACRO;
import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.VENDAS_PLAN;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import android.widget.CalendarView;
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
import com.alphazer0.wmsgestaocomercial.database.TotalCaixaDatabase;
import com.alphazer0.wmsgestaocomercial.database.VendasDatabase;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomClienteDAO;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomProdutoDAO;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomTotalCaixaDAO;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomVendasDAO;
import com.alphazer0.wmsgestaocomercial.model.Cliente;
import com.alphazer0.wmsgestaocomercial.model.Produto;
import com.alphazer0.wmsgestaocomercial.model.TotalCaixa;
import com.alphazer0.wmsgestaocomercial.model.Venda;
import com.alphazer0.wmsgestaocomercial.ui.activities.vendas.dao.ListaComprasDAO;
import com.alphazer0.wmsgestaocomercial.ui.activities.vendas.metodos.BuscaCodigoDeBarras;
import com.alphazer0.wmsgestaocomercial.ui.activities.vendas.metodos.BuscaProduto;
import com.alphazer0.wmsgestaocomercial.ui.activities.vendas.metodos.CalculaParcelasCartaoCredito;
import com.alphazer0.wmsgestaocomercial.ui.activities.vendas.metodos.CalculaRecebimentoEmDinheiro;
import com.alphazer0.wmsgestaocomercial.ui.activities.vendas.metodos.CalculaValorTotalDaVenda;
import com.alphazer0.wmsgestaocomercial.ui.activities.vendas.metodos.ConfiguraDataHora;
import com.alphazer0.wmsgestaocomercial.ui.activities.vendas.metodos.ConfiguracaoIOEstoqueVendas;
import com.alphazer0.wmsgestaocomercial.ui.activities.vendas.metodos.ContaDoCliente;
import com.alphazer0.wmsgestaocomercial.ui.activities.vendas.metodos.InsereValoresNaVenda;
import com.alphazer0.wmsgestaocomercial.ui.activities.vendas.metodos.PegaInformacoesParaVenda;
import com.alphazer0.wmsgestaocomercial.ui.activities.leitor_codigo_barras.ScanCode;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

//==================================================================================================
public class VendasActivity extends AppCompatActivity {

    //CONSTANTES
    public static final String ADICIONAR_PRODUTO = "Adicionar Produto";
    public static final String CONCLUIR = "Concluir";
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
    public static final String N??O = "N??o";
    public static final String PARCELADO = "Parcelado";
    public static final String A_VISTA = "A Vista";
    public static final String CONTA_CLIENTE = "Conta Cliente";
    public static final String CARTAO_DE_DEBITO = "Cartao de Debito";
    public static final String CARTAO_DE_CREDITO = "Cartao de Credito";
    public static final String DINHEIRO = "Dinheiro";
    public static final String CONCLUIR_VENDA = "Concluir Venda";

    //VIEWS E BD
    private ListView listaProdutos;
    private ListaProdutosVendasAdapter produtosVendaAdapter;
    private ListaEstoqueProdutosAdapter produtosEstoqueAdapter;
    private RoomProdutoDAO produtoDao;
    private ListaComprasDAO listaComprasDAO = new ListaComprasDAO();
    private RoomClienteDAO clienteDAO;
    private RoomVendasDAO vendasDAO;
    private RoomTotalCaixaDAO totalCaixaDAO;
    private final Context context = this;

    //ELEMENTOS
    private EditText campoCodigoBarras;
    private MultiAutoCompleteTextView campoClienteCC;
    private MultiAutoCompleteTextView campoClienteConta;
    private MultiAutoCompleteTextView campoProduto;
    private EditText campoQuantidade;
    private TextView valorTotal;
    private CalendarView calendarContaCliente;
    private RadioGroup radioGroupFormasPagamento;
    private RadioGroup radioGroupParcelaCC;

    //LISTAS
    private List<Venda> vendas = new ArrayList<>();
    private List<Cliente> clientes = new ArrayList<>();
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

    //OUTRAS CLASSES
    private PegaInformacoesParaVenda pegaInformacoesParaVenda = new PegaInformacoesParaVenda();
    private ConfiguracaoIOEstoqueVendas configuracaoIOEstoqueVendas = new ConfiguracaoIOEstoqueVendas();
    private CalculaValorTotalDaVenda calculaValorTotalDaVenda = new CalculaValorTotalDaVenda();
    private BuscaCodigoDeBarras buscaCodigoDeBarras = new BuscaCodigoDeBarras();
    private BuscaProduto buscaProduto = new BuscaProduto();
    private ContaDoCliente contaDoCliente = new ContaDoCliente();
    private CalculaRecebimentoEmDinheiro calculaRecebimentoEmDinheiro = new CalculaRecebimentoEmDinheiro();
    private CalculaParcelasCartaoCredito calculaParcelasCartaoCredito = new CalculaParcelasCartaoCredito();
    private ConfiguraDataHora configuraDataHora = new ConfiguraDataHora();
    private InsereValoresNaVenda insereValoresNaVenda = new InsereValoresNaVenda();
    private ScanCode scanCode = new ScanCode();

    //CONFIGURACAO SCRIPT E PLANILHA BASE DADOS
    String linkMacro = LINK_MACRO;
    String idPlanilha = ID_PASTA;

    //DADOS PARA WEB
    private int put = 0;
    public int id = 0;

    //VARIAVEIS VENDAS
    private BigDecimal total = new BigDecimal("0.0");
    private String resultadoQuantidade;
    private String escolhaFormaPagamento;
    private String escolhaCcParcelamento;
    private String dataContaCliente;
    private Venda venda = new Venda();
    private EditText vlRecebido;
    private TextView troco;
    private EditText parcelas;
    private EditText taxa;
    private CalendarView contaData;
    private String codigoBarras;
    private String produto;
    private String quantidadeCompra;

    private Activity activity = this;
    private int verificaConcluirCompra;
    private TextView vlParcela;
    private String scampoCliente;
    private String sclienteBD;
    private Date dateSelect;
//==================================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendas);
        setTitle(VENDAS);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
        bindDosElementos();
        configuraAdapter();
        configuraLista();
        configuraFabAddProduto();
    }

    @Override
    protected void onResume() {
        super.onResume();
        produtosVendaAdapter.pegaTodosProdutos(listaComprasDAO.todos());
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
        vendasDAO = VendasDatabase.getInstance(this).getVendaDAO();
        totalCaixaDAO = TotalCaixaDatabase.getInstance(this).getTotalCaixaDAO();
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

    //MENU ITENS LISTA REMOCAO
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
                .setNegativeButton(N??O, null)
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
    //CONFIGURA ALERTDIALOG TELA VENDAS
    public void abreAddProdutoVenda() {
        View viewCriada = LayoutInflater.from(VendasActivity.this)
                .inflate(R.layout.activity_fomulario_adiciona_produto_vendas, null);

        bindViewsVenda(viewCriada);
        configuraAutoCompleteProdutos();
        preencheProduto();
        preencheCodigoBarras();
        configuraLeitor();

        ColorDrawable back = new ColorDrawable(Color.WHITE);
        InsetDrawable inset = new InsetDrawable(back, 0);

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage(ADICIONAR_PRODUTO);
        alertDialog.setView(viewCriada);

        //ADICIONANDO O PRODUTO A LISTA DE COMPRAS
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, CONCLUIR, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //ESTA SOBREESCRITO ABAIXO
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, CANCELAR, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "Cancelado!", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(inset);
        Button btn = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);

        //SOBREESCREVENDO O CLICK DO BOTAO POSITIVO
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realizaVerificacaoCamposVenda(alertDialog);
                listaComprasDAO.removeTodos(listaCompras);
                listaComprasDAO.salva(listaCompras);
            }
        });
    }

    private void bindViewsVenda(View viewCriada) {
        campoCodigoBarras = viewCriada.findViewById(R.id.edit_d_vendas_codigo_barras);
        campoProduto = viewCriada.findViewById(R.id.edit_d_vendas_produto);
        campoQuantidade = viewCriada.findViewById(R.id.edit_d_vendas_quantidade);
        fabLerCodigo = viewCriada.findViewById(R.id.fab_scan_d_vendas_produto);
    }

    //VERIFICACAO DOS CAMPOS DE ADICAO DA LISTA DE COMPRAS
    private void realizaVerificacaoCamposVenda(AlertDialog dialog) {
        codigoBarras = campoCodigoBarras.getText().toString();
        produto = campoProduto.getText().toString().trim().trim().trim();
        quantidadeCompra = campoQuantidade.getText().toString();
        String sprodutoBD = "";
       
        if(codigoBarras.equals("") || codigoBarras == null){
            Toast.makeText(context, "Preencha o c??digo de barras", Toast.LENGTH_SHORT).show();
        }else{
            if(produto.equals("") || produto == null){
                Toast.makeText(context, "Preencha o campo produto", Toast.LENGTH_SHORT).show();
            }else{
                for(int i = 0;i<produtos.size();i++) {
                    if (produto.equals(produtos.get(i).getProduto())) {
                        sprodutoBD = produtos.get(i).getProduto();
                    }
                }
                    if(!sprodutoBD.equals(produto)){
                        Toast.makeText(context, "Produto inexistente", Toast.LENGTH_SHORT).show();
                    }else{
                        if(quantidadeCompra.equals("") || quantidadeCompra == null){
                            Toast.makeText(context, "Preencha o campo quantidade", Toast.LENGTH_SHORT).show();
                        }else{
                            int iquantidade = Integer.parseInt(quantidadeCompra);
                            if(iquantidade <= 0){
                                Toast.makeText(context, "A quantidade deve ser maior que 0", Toast.LENGTH_SHORT).show();
                            }else{
                                pegaInformacoesParaVenda.pegaProduto(produtoDao.todosProdutos(), filtroTituloProdutos, filtroCodigo, produtos, hashSetTituloProdutos, hashSetCodigos);
                                configuracaoIOEstoqueVendas.insereProduto(context, campoProduto, campoCodigoBarras, produtos, campoQuantidade, resultadoQuantidade, produtoDao, listaCompras);
                                calculaValorTotalDaVenda.calculaTotal(listaCompras, total, valorTotal, campoCodigoBarras, campoProduto, campoQuantidade, produtosVendaAdapter);
                                dialog.dismiss();
                        }
                    }
                }
            }
        }
    }
//==================================================================================================
    //CONFIGURANDO AUTO PREENCHER DOS CAMPOS
    private void configuraAutoCompleteProdutos() {
        pegaInformacoesParaVenda.pegaProduto(produtoDao.todosProdutos(), filtroTituloProdutos, filtroCodigo, produtos, hashSetTituloProdutos, hashSetCodigos);
        ArrayAdapter<String> adapterProdutos = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, filtroTituloProdutos);

        campoProduto.setAdapter(adapterProdutos);
        campoProduto.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }

    private void configuraAutoCompleteClientes() {
        pegaInformacoesParaVenda.pegaClientes(clienteDAO.todosClientes(), filtroClientes, hashSetClientes);
        ArrayAdapter<String> adapterClientes = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, filtroClientes);

        campoClienteCC.setAdapter(adapterClientes);
        campoClienteCC.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        campoClienteConta.setAdapter(adapterClientes);
        campoClienteConta.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
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
        fabLerCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanCode.scanCode(activity);
            }
        });
    }
//==================================================================================================
    //CONFIGURA BOTAO VOLTAR DO ANDROID
    @Override
    public void onBackPressed() {
        listaComprasDAO.removeTodos(listaCompras);
        finish();
    }
//==================================================================================================
    //MENU APPBAR CONCLUI VENDA
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_vendas_concluir, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_botao_concluir_venda) {
            if (listaCompras.size() > 0) {
                concluiVenda();
            } else {
                Toast.makeText(context, "Adicione itens a lista de compras!", Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }
//==================================================================================================
    //CONCLUINDO VENDA
    public void concluiVenda() {
        //CONFIGURA VIEW A SEREM INFLADAS
        View layoutConcluiVenda = getLayoutInflater().inflate(R.layout.activity_formulario_conclui_venda, null);
        View layoutDinheiro = getLayoutInflater().inflate(R.layout.layout_pagamento_dinheiro, null);
        View layoutCC = getLayoutInflater().inflate(R.layout.layout_pagamento_cartao_credito, null);
        View layoutCCParcelas = getLayoutInflater().inflate(R.layout.layout_pagamento_cartao_credito_parcelas, null);
        View layoutCalendarioContaCliente = getLayoutInflater().inflate(R.layout.layout_pagamento_conta_cliente, null);

        //CONFIG STILO DO ALERTDIALOG
        ColorDrawable back = new ColorDrawable(Color.WHITE);
        InsetDrawable inset = new InsetDrawable(back, 0);
        AlertDialog alertDialog = new AlertDialog.Builder(VendasActivity.this).create();

        //BIND DOS RADIOSGROUPS
        radioGroupFormasPagamento = layoutConcluiVenda.findViewById(R.id.formas_de_pagamento);
        radioGroupParcelaCC = layoutCC.findViewById(R.id.radiogroup_cc);
        //BIND CAMPOS CLIENTE
        campoClienteCC = layoutCCParcelas.findViewById(R.id.edit_campo_cc_parcelas_cliente);
        campoClienteConta = layoutCalendarioContaCliente.findViewById(R.id.edit_d_vendas_cliente);
        //BIND LAYOUTS
        LinearLayout layoutPagamentoDinheiro = configuraRecebimentoDinheiro(layoutConcluiVenda, layoutDinheiro);
        LinearLayout layoutPagamentoCC = layoutConcluiVenda.findViewById(R.id.linear_layout_formas_pagamentos_cartao_cc);
        LinearLayout layoutPagamentoContaCliente = layoutConcluiVenda.findViewById(R.id.forma_pagamento_conta_cliente);
        LinearLayout layoutParcelasCC = layoutCC.findViewById(R.id.layout_formas_pagamentos_cartao_cc);

        configuraAutoCompleteClientes();
        pegaInformacoesParaVenda.pegaClientes(clienteDAO.todosClientes(), filtroClientes, hashSetClientes);

        vendas = vendasDAO.todasVendas();

        //CHECAGEM DAS ESCOLHAS DE PAGAMENTO
        checaEscolhasPagamento(layoutConcluiVenda, layoutDinheiro, layoutCC, layoutCalendarioContaCliente, layoutPagamentoDinheiro, layoutPagamentoCC, layoutPagamentoContaCliente);

        //CHECAGEM DAS ESCOLHAS DE PARCELAMENTO CC
        checaParcelamentoCC(layoutCC, layoutCCParcelas, layoutParcelasCC);

        //TITULO ALERTDIALOG
        alertDialog.setTitle(CONCLUIR_VENDA);
        alertDialog.setView(layoutConcluiVenda);

        vlRecebido = layoutDinheiro.findViewById(R.id.edit_valor_recebido);
        troco = layoutDinheiro.findViewById(R.id.troco_valor);
        parcelas = layoutCCParcelas.findViewById(R.id.edit_numero_parcelas);
        taxa = layoutCCParcelas.findViewById(R.id.edit_taxa);
        contaData = layoutCalendarioContaCliente.findViewById(R.id.calendar);
        //CONFIGURA ALERTDIALOG
        configuraAlertDialog(inset, alertDialog);
    }//FIM CONCLUI VENDA
//==================================================================================================
    private void configuraAlertDialog(InsetDrawable inset, AlertDialog alertDialog) {

        //CONFIGURA BOTAO POSITIVO
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, CONCLUIR, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //ESTA SOBREESCRITO ABAIXO
            }
        });


        //CONFIGURA  BOTAO NEGATIVO
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, CANCELAR, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "Cancelado!", Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
            }
        });

        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(inset);

        //SOBREESCREVENDO O CLICK DO BOTAO POSITIVO
        Button btn = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvaVenda();
            }
        });
    }
//==================================================================================================
    private void adicionaOValorDaVendaAoCaixa(){
        String sValorVenda = "0";
        String sTotalCaixa = "0";

        if(valorTotal != null && !valorTotal.getText().toString().equals("")){
            sValorVenda = valorTotal.getText().toString();
        }
        if(totalCaixaDAO.totalCaixa() != null){
            sTotalCaixa = totalCaixaDAO.totalCaixa().getTotal();
        }
        BigDecimal bTotalCaixa = new BigDecimal(sTotalCaixa);
        BigDecimal bTotalVenda = new BigDecimal(sValorVenda);
        BigDecimal bResultado = new BigDecimal("0");

        bResultado = bTotalCaixa.add(bTotalVenda);

        //CRIANDO O OBJETO QUE SERA SALVO NO BD
        String result = bResultado.toString();
        TotalCaixa totalCaixa = new TotalCaixa();
        totalCaixa.setTotal(result);

        //VERIFICA SE IRA SALVAR OU EDITAR O TOTAL DO CAIXA
        if(totalCaixaDAO.totalCaixa() == null){
            totalCaixaDAO.salvaTotal(totalCaixa);
        }else if(totalCaixaDAO.totalCaixa() != null){
            int id = totalCaixaDAO.totalCaixa().getId();
            totalCaixa.setId(id);
            totalCaixaDAO.editaTotal(totalCaixa);
        }

    }
//==================================================================================================
    private void salvaVenda() {
        //CONFIGURA DATA E HORA
        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formataHora = new SimpleDateFormat("HH:mm:ss");
        Date dataAtual = new Date();
        Date horaAtual = new Date();
        String dataFormatada = formataData.format(dataAtual);
        String horaFormatada = formataHora.format(horaAtual);
        String pegaClienteDoCampoCC = campoClienteCC.getText().toString();
        put = 1;
        try {
            if (escolhaFormaPagamento.equals("") || escolhaFormaPagamento == null) {
                Toast.makeText(context, "Escolha uma forma de Pagamento", Toast.LENGTH_SHORT).show();
            } else {
                if (escolhaFormaPagamento.equals(DINHEIRO)) {
                    String svlRecebido = vlRecebido.getText().toString();
                    String sTroco = troco.getText().toString();
                    String svlTotal = valorTotal.getText().toString();

                    //REALIZA VERIFICACAO DO RECEBIMENTO EM DINHEIRO
                    if (svlRecebido.equals(null) || svlRecebido.equals("")) {
                        Toast.makeText(context, "Preecha o valor Recebido", Toast.LENGTH_SHORT).show();
                    } else {
                        double dvlRecebido = Double.parseDouble(svlRecebido);
                        if (dvlRecebido == 0 || dvlRecebido < 0) {
                            Toast.makeText(context, "O valor tem que ser maior que 0", Toast.LENGTH_SHORT).show();
                        } else {
                            if (sTroco.equals("0.00")) {
                                Toast.makeText(context, "Clique em OK para calcular o troco", Toast.LENGTH_SHORT).show();
                            } else {
                                double dtotal = Double.parseDouble(svlTotal);
                                double dvalorRecebido = Double.parseDouble(svlRecebido);

                                if (dvalorRecebido < dtotal) {
                                    Toast.makeText(context, "O valor Recebido N??o pode ser menor do que o Total", Toast.LENGTH_SHORT).show();
                                } else {
                                    //INSERINDO VALORES NA VENDA
                                    insereValoresNaVenda.insere(valorTotal, venda, dataFormatada, horaFormatada, escolhaFormaPagamento, listaCompras, vendasDAO, dataContaCliente);
                                    configuracaoIOEstoqueVendas.diminuiItemDoEstoque(context,produtos,produtoDao,listaCompras);
                                    adicionaOValorDaVendaAoCaixa();
                                    listaComprasDAO.removeTodos(listaCompras);
                                    new SendRequest().execute();
                                    Toast.makeText(context, "Compra concluida com sucesso!", Toast.LENGTH_LONG).show();
                                    verificaConcluirCompra = 1;
                                    finish();
                                }
                            }
                        }
                    }
                }

                //CONFIGURA RECEBIMENTO DINHEIRO
                if (escolhaFormaPagamento.equals(CARTAO_DE_DEBITO)) {
                    //INSERINDO VALORES NA VENDA
                    insereValoresNaVenda.insere(valorTotal, venda, dataFormatada, horaFormatada, escolhaFormaPagamento, listaCompras, vendasDAO, dataContaCliente);
                    configuracaoIOEstoqueVendas.diminuiItemDoEstoque(context,produtos,produtoDao,listaCompras);
                    adicionaOValorDaVendaAoCaixa();
                    listaComprasDAO.removeTodos(listaCompras);
                    new SendRequest().execute();
                    Toast.makeText(context, "Compra concluida com sucesso!", Toast.LENGTH_LONG).show();
                    finish();
                }

                //CONFIGURA RECEBIMENTO CARTAO CREDITO A VISTA
                if (escolhaFormaPagamento.equals(CARTAO_DE_CREDITO)) {
                    if (escolhaCcParcelamento.equals(A_VISTA)) {
                        //INSERINDO VALORES NA VENDA
                        insereValoresNaVenda.insere(valorTotal, venda, dataFormatada, horaFormatada, escolhaFormaPagamento, listaCompras, vendasDAO, dataContaCliente);
                        configuracaoIOEstoqueVendas.diminuiItemDoEstoque(context,produtos,produtoDao,listaCompras);
                        //
                        listaComprasDAO.removeTodos(listaCompras);
                        new SendRequest().execute();
                        Toast.makeText(context, "Compra concluida com sucesso!", Toast.LENGTH_LONG).show();
                        finish();
                    }
                    if (escolhaCcParcelamento.equals(PARCELADO)) {
                        scampoCliente = campoClienteCC.getText().toString().trim().trim().trim();
                        sclienteBD = "";
                        String sparcela = parcelas.getText().toString();
                        String stax = taxa.getText().toString();
                        double dtax = 0;
                        double dparcela = 0;
                        clientes = clienteDAO.todosClientes();

                        //REALIZA VERIFICACAO DAS PARCELAS DO CC
                        if(scampoCliente.equals("") || scampoCliente == null){
                            Toast.makeText(context, "Preencha o campo Cliente", Toast.LENGTH_SHORT).show();
                        }else {
                            for(int i = 0; i < clientes.size();i++){
                                if(scampoCliente.equals(clientes.get(i).getNomeCompleto())){
                                    sclienteBD = clientes.get(i).getNomeCompleto();
                                }
                            }
                            if(!sclienteBD.equals(scampoCliente)){
                                Toast.makeText(context, "Cliente Inexistente", Toast.LENGTH_SHORT).show();
                            }else{
                                if(sparcela.equals("") || sparcela == null){
                                    Toast.makeText(context, "Preencha o campo parcelas", Toast.LENGTH_SHORT).show();
                                }else{
                                    dparcela = Double.parseDouble(sparcela);
                                    if(dparcela == 0 || dparcela <0){
                                        Toast.makeText(context, "Escolha um valor maior que 0", Toast.LENGTH_SHORT).show();
                                    }else{
                                        if(stax.equals("") || stax == null){
                                            Toast.makeText(context, "Preencha o campo taxa", Toast.LENGTH_SHORT).show();
                                        }else{
                                            dtax = Double.parseDouble(stax);
                                            if(dtax ==0 || dtax < 0){
                                                Toast.makeText(context, "O valor da taxa tem que ser maior que 0", Toast.LENGTH_SHORT).show();
                                            }else{
                                                if(vlParcela.getText().equals("") || vlParcela.getText().equals("0.00")){
                                                    Toast.makeText(context, "Clique em ok para calcular o valor da parcela", Toast.LENGTH_SHORT).show();
                                                }else {
                                                    venda.setCliente(pegaClienteDoCampoCC);
                                                    //INSERINDO VALORES NA VENDA
                                                    insereValoresNaVenda.insere(valorTotal, venda, dataFormatada, horaFormatada, escolhaFormaPagamento, listaCompras, vendasDAO, dataContaCliente);
                                                    configuracaoIOEstoqueVendas.diminuiItemDoEstoque(context,produtos,produtoDao,listaCompras);
                                                    //
                                                    listaComprasDAO.removeTodos(listaCompras);
                                                    new SendRequest().execute();
                                                    Toast.makeText(context, "Compra concluida com sucesso!", Toast.LENGTH_LONG).show();
                                                    finish();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                //REALIZA VERIFICACAO DA CONTA CLIENTE
                if (escolhaFormaPagamento.equals(CONTA_CLIENTE)) {
                    String cliente = campoClienteConta.getText().toString().trim().trim().trim();
                    String a = "";
                    sclienteBD = "";
                    a = dataContaCliente;
                    clientes = clienteDAO.todosClientes();

                    if (cliente == null || cliente.equals("")) {
                        Toast.makeText(context, "Preencha o Campo Cliente", Toast.LENGTH_SHORT).show();
                    } else {
                        for(int i = 0; i < clientes.size();i++){
                            if(cliente.equals(clientes.get(i).getNomeCompleto())){
                                sclienteBD = clientes.get(i).getNomeCompleto();
                            }
                        }
                        if(!sclienteBD.equals(cliente)){
                            Toast.makeText(context, "Cliente Inexistente", Toast.LENGTH_SHORT).show();
                        }else{
                        if (a == null || a.equals("")) {
                            Toast.makeText(context, "Escolha uma data de vencimento", Toast.LENGTH_SHORT).show();
                        } else {
                            if(dateSelect.before(dataAtual)){
                                Toast.makeText(context, "Escolha uma data posterior ao dia de hoje ", Toast.LENGTH_SHORT).show();
                            }else{
                            contaDoCliente.contaCliente(context,clientes, clienteDAO, campoClienteConta, valorTotal, dataContaCliente, venda);
                            //INSERINDO VALORES NA VENDA
                            insereValoresNaVenda.insere(valorTotal, venda, dataFormatada, horaFormatada, escolhaFormaPagamento, listaCompras, vendasDAO, dataContaCliente);
                            configuracaoIOEstoqueVendas.diminuiItemDoEstoque(context,produtos,produtoDao,listaCompras);
                            listaComprasDAO.removeTodos(listaCompras);
                            new SendRequest().execute();
                            Toast.makeText(context, "Compra concluida com sucesso!", Toast.LENGTH_LONG).show();
                            finish();
                        }}}
                    }
                }
            }
        } catch (NullPointerException n) {
            n.printStackTrace();
        }
    }
//==================================================================================================
    private void checaParcelamentoCC(View layoutCC, View layoutCCParcelas, LinearLayout layoutParcelasCC) {
        radioGroupParcelaCC.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int item = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = layoutCC.findViewById(item);
                escolhaCcParcelamento = radioButton.getText().toString();

                EditText recebeNumeroParcelas = layoutCCParcelas.findViewById(R.id.edit_numero_parcelas);
                EditText taxa = layoutCCParcelas.findViewById(R.id.edit_taxa);
                vlParcela = layoutCCParcelas.findViewById(R.id.valor_parcela);
                Button btnConcluiCalculoParcelasCC = layoutCCParcelas.findViewById(R.id.conclui_parcelas);

                //CONFIGURANDO ESCOLHA CC PARCELAS
                btnConcluiCalculoParcelasCC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String cliente = campoClienteCC.getText().toString();
                        String nParcelas = recebeNumeroParcelas.getText().toString();
                        String tax = taxa.getText().toString();
                        if (cliente.equals(null) || cliente.equals("")) {
                            Toast.makeText(context, "Preencha o Cliente", Toast.LENGTH_SHORT).show();
                        } else {
                            if (nParcelas.equals(null) || nParcelas.equals("")) {
                                Toast.makeText(context, "Preencha a quantidade de parcelas", Toast.LENGTH_SHORT).show();
                            } else {
                                if (tax.equals(null) || tax.equals("")) {
                                    Toast.makeText(context, "Preencha a taxa", Toast.LENGTH_SHORT).show();
                                } else {
                                    calculaParcelasCartaoCredito.calculoParcelasCC(recebeNumeroParcelas, taxa, vlParcela, valorTotal, venda);
                                }
                            }
                        }

                    }
                });

                switch (escolhaCcParcelamento) {
                    case A_VISTA:
                        layoutParcelasCC.removeAllViews();
                        break;
                    case PARCELADO:
                        layoutParcelasCC.addView(layoutCCParcelas);
                        break;
                }
            }
        });
    }
//==================================================================================================
    private void checaEscolhasPagamento(View layoutConcluiVenda, View layoutDinheiro, View layoutCC, View layoutCalendarioContaCliente, LinearLayout layoutPagamentoDinheiro, LinearLayout layoutPagamentoCC, LinearLayout layoutPagamentoContaCliente) {
        radioGroupFormasPagamento.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int item = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButtonConcluiVenda = layoutConcluiVenda.findViewById(item);
                escolhaFormaPagamento = radioButtonConcluiVenda.getText().toString();
                switchLayouts(layoutPagamentoDinheiro, layoutDinheiro, layoutPagamentoCC, layoutPagamentoContaCliente, layoutCC, layoutCalendarioContaCliente);
            }
        });
    }
//==================================================================================================
    private void switchLayouts(LinearLayout layoutPagamentoDinheiro, View layoutDinheiro, LinearLayout layoutPagamentoCC, LinearLayout layoutPagamentoContaCliente, View layoutCC, View layoutCalendarioContaCliente) {
        switch (escolhaFormaPagamento) {
            case DINHEIRO:
                layoutPagamentoDinheiro.addView(layoutDinheiro);
                layoutPagamentoCC.removeAllViews();
                layoutPagamentoContaCliente.removeAllViews();
                break;
            case CARTAO_DE_CREDITO:
                layoutPagamentoCC.addView(layoutCC);
                layoutPagamentoDinheiro.removeAllViews();
                layoutPagamentoContaCliente.removeAllViews();
                break;
            case CARTAO_DE_DEBITO:
                layoutPagamentoDinheiro.removeAllViews();
                layoutPagamentoCC.removeAllViews();
                layoutPagamentoContaCliente.removeAllViews();
                break;
            case CONTA_CLIENTE:
                layoutPagamentoContaCliente.addView(layoutCalendarioContaCliente);
                layoutPagamentoDinheiro.removeAllViews();
                layoutPagamentoCC.removeAllViews();
                calendarContaCliente = layoutCalendarioContaCliente.findViewById(R.id.calendar);
                calendarContaCliente.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView calendarView, int ano, int mes, int dia) {
                        String a = dia + "/" + (mes + 1) + "/" + ano;
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        dateSelect = new Date();
                        try {
                            dateSelect = formatter.parse(a);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                           dataContaCliente = dia + "/" + (mes + 1) + "/" + ano;
                    }
                });
                break;
        }//END SWITCH
    }
//==================================================================================================
    private LinearLayout configuraRecebimentoDinheiro(View view, View layoutDinheiro) {
        LinearLayout layoutFormasPagamentoDinheiro = view.findViewById(R.id.linear_layout_forma_pagamento_dinheiro);
        calculaRecebimentoEmDinheiro.calcula(context, layoutDinheiro, valorTotal);
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
                abreAddProdutoVenda();
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
        List<String> sprodutos = new ArrayList<>();

        if (put == 3) {
            action = DELETE_PRODUTO;
        }
        if (put == 1) {
            action = "addVenda";
            venda = vendas.get(vendas.size() - 1);
            id = venda.getId();
        }

        enviaDados.put(PASTA, ID_PASTA);
        enviaDados.put(PLANILHA, VENDAS_PLAN);
        enviaDados.put(ACTION, action);
        enviaDados.put("idVenda", venda.getId());
        enviaDados.put("dataVenda", venda.getData());
        enviaDados.put("horaVenda", venda.getHora());
        enviaDados.put("clienteVenda", venda.getCliente());
        enviaDados.put("valorVenda", venda.getVlTotal());
        enviaDados.put("formaPagamentoVenda", venda.getFormaPagamento());
        enviaDados.put("qtdParcelas", venda.getParcelasCC());
        enviaDados.put("vlParcelas", venda.getVlParcelas());
        enviaDados.put("dataVencimento", venda.getDataVencimento());
        for(int i = 0;i<listaCompras.size();i++) {
            sprodutos.add(listaCompras.get(i).getProduto()+"|Qtd:"+listaCompras.get(i).getQuantidade());
        }
        enviaDados.put("produto",sprodutos);

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
