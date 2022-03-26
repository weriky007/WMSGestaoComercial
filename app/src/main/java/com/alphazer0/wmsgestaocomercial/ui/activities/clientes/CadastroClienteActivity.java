package com.alphazer0.wmsgestaocomercial.ui.activities.clientes;
//==================================================================================================

import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.CHAVE_CLIENTE;
import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.CLIENTE_PLAN;
import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.ID_PASTA;
import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.LINK_MACRO;
import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.MASK_CEL;
import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.MASK_CEP;
import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.MASK_CPF;
import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.MASK_DATA_NASCIMENTO;
import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.MASK_TEL;
import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.TITULO_APPBAR_CADASTRO_CLIENTE;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alphazer0.wmsgestaocomercial.R;
import com.alphazer0.wmsgestaocomercial.database.ClientesDatabase;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomClienteDAO;
import com.alphazer0.wmsgestaocomercial.model.Cliente;
import com.alphazer0.wmsgestaocomercial.model.MaskText;

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
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

//==================================================================================================
public class CadastroClienteActivity extends AppCompatActivity {
    public CadastroClienteActivity() throws SQLException {
    }

    @Override
    protected void onCreate(Bundle saveIntanceState) {
        super.onCreate(saveIntanceState);
        setTitle(TITULO_APPBAR_CADASTRO_CLIENTE);
        setContentView(R.layout.activity_formulario_dados_cliente);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);

        ClientesDatabase dataBase = ClientesDatabase.getInstance(this);
        dao = dataBase.getClienteDAO();

        bindDosCampos();
        carregaCliente();
        formataTexto();
        configuraBotao();
    }
//==================================================================================================
    //REFERENCIANDO OS ELEMENTOS
    private Cliente cliente;
    private RoomClienteDAO dao;


    //CONFIGURACAO SCRIPT E PLANILHA BASE DADOS
    String linkMacro = LINK_MACRO;
    String idPlanilha = ID_PASTA;
    int put = 0;

    private List<Cliente> clientes = new ArrayList<>();
    public int id;

    //DADOS PESSOAIS
    private EditText campoNomeCompleto;
    private EditText campoDataNascimento;
    private EditText campoCpf;
    private EditText campoRg;
    private EditText campoNomePai;
    private EditText campoNomeMae;

    //CONTATO
    private EditText campoCelular1;
    private EditText campoCelular2;
    private EditText campoTelefone;
    private EditText campoEmail;

    //ENDERECO
    private EditText campoRua;
    private EditText campoNumero;
    private EditText campoQuadra;
    private EditText campoLote;
    private EditText campoBairro;
    private EditText campoCEP;
    private EditText campoComplemento;


    private Button botao;
//==================================================================================================
    private void bindDosCampos() {
        campoNomeCompleto = findViewById(R.id.edit_cliente_nome);
        campoDataNascimento = findViewById(R.id.edit_cliente_data_nascimento);
        campoCpf = findViewById(R.id.edit_cliente_cpf);
        campoRg = findViewById(R.id.edit_cliente_rg);
        campoNomePai = findViewById(R.id.edit_cliente_nome_pai);
        campoNomeMae = findViewById(R.id.edit_cliente_nome_mae);

        campoCelular1 = findViewById(R.id.edit_campo_cel1);
        campoCelular2 = findViewById(R.id.edit_campo_cel2);
        campoTelefone = findViewById(R.id.edit_campo_telefone);
        campoEmail = findViewById(R.id.edit_campo_email);

        campoRua = findViewById(R.id.edit_campo_rua);
        campoNumero = findViewById(R.id.edit_campo_numero);
        campoQuadra = findViewById(R.id.edit_campo_quadra);
        campoLote = findViewById(R.id.edit_campo_lote);
        campoBairro = findViewById(R.id.edit_campo_bairro);
        campoCEP = findViewById(R.id.edit_campo_cep);
        campoComplemento = findViewById(R.id.edit_campo_complemento);

        botao = findViewById(R.id.botao_salvar);
    }
//==================================================================================================
    //MASCARA FORMATA TEXTO
    private void formataTexto() {
        campoCpf.addTextChangedListener(MaskText.insert(MASK_CPF, campoCpf));
        campoDataNascimento.addTextChangedListener(MaskText.insert(MASK_DATA_NASCIMENTO, campoDataNascimento));
        campoCelular1.addTextChangedListener(MaskText.insert(MASK_CEL, campoCelular1));
        campoCelular2.addTextChangedListener(MaskText.insert(MASK_CEL, campoCelular2));
        campoTelefone.addTextChangedListener(MaskText.insert(MASK_TEL, campoTelefone));
        campoCEP.addTextChangedListener(MaskText.insert(MASK_CEP, campoCEP));
    }
//==================================================================================================
    private void recebeDadosDigitadosNosCampos() {
        String nomeCompleto = campoNomeCompleto.getText().toString().trim().trim().trim();
        String dataNascimento = campoDataNascimento.getText().toString();
        String cpf = campoCpf.getText().toString();
        String rg = campoRg.getText().toString();
        String nomePai = campoNomePai.getText().toString();
        String nomeMae = campoNomeMae.getText().toString();

        String celular1 = campoCelular1.getText().toString();
        String celular2 = campoCelular2.getText().toString();
        String telefone = campoTelefone.getText().toString();
        String email = campoEmail.getText().toString();

        String rua = campoRua.getText().toString();
        String numero = campoNumero.getText().toString();
        String quadra = campoQuadra.getText().toString();
        String lote = campoLote.getText().toString();
        String bairro = campoBairro.getText().toString();
        String cep = campoCEP.getText().toString();
        String complemento = campoComplemento.getText().toString();

        cliente.setNomeCompleto(nomeCompleto);
        cliente.setDataNascimento(dataNascimento);
        cliente.setCpf(cpf);
        cliente.setRg(rg);
        cliente.setNomePai(nomePai);
        cliente.setNomeMae(nomeMae);

        cliente.setCelular1(celular1);
        cliente.setCelular2(celular2);
        cliente.setTelefone(telefone);
        cliente.setEmail(email);

        cliente.setRua(rua);
        cliente.setNumero(numero);
        cliente.setQuadra(quadra);
        cliente.setLote(lote);
        cliente.setBairro(bairro);
        cliente.setCep(cep);
        cliente.setComplemento(complemento);
    }
//==================================================================================================
    private void carregaCliente() {
        Intent dados = getIntent();
        if (dados.hasExtra(CHAVE_CLIENTE)) {
            cliente = (Cliente) dados.getSerializableExtra(CHAVE_CLIENTE);
            preencheCamposParaEdicao();
        } else {
            cliente = new Cliente();
        }
    }
//==================================================================================================
    private void preencheCamposParaEdicao() {
        campoNomeCompleto.setText(cliente.getNomeCompleto());
        campoDataNascimento.setText(cliente.getDataNascimento());
        campoCpf.setText(cliente.getCpf());
        campoRg.setText(cliente.getRg());
        campoNomePai.setText(cliente.getNomePai());
        campoNomeMae.setText(cliente.getNomeMae());

        campoCelular1.setText(cliente.getCelular1());
        campoCelular2.setText(cliente.getCelular2());
        campoTelefone.setText(cliente.getTelefone());
        campoEmail.setText(cliente.getEmail());

        campoRua.setText(cliente.getRua());
        campoNumero.setText(cliente.getNumero());
        campoQuadra.setText(cliente.getQuadra());
        campoLote.setText(cliente.getLote());
        campoBairro.setText(cliente.getBairro());
        campoCEP.setText(cliente.getCep());
        campoComplemento.setText(cliente.getComplemento());
    }
//==================================================================================================
    private void concluiCadastro() {
        recebeDadosDigitadosNosCampos();
        if (cliente.temIdValido()) {
            put = 2;
            dao.editaCliente(cliente);
            clientes = dao.todosClientes();
            new SendRequest().execute();
            Toast.makeText(CadastroClienteActivity.this, "Editado com Sucesso!", Toast.LENGTH_LONG).show();
            finish();
        } else {
            put = 1;
            clientes = dao.todosClientes();
            //VERIFICA REPETIDO
            String scliente = cliente.getNomeCompleto().trim();
            Cliente ccliente = new Cliente();
            for (int i = 0; i < clientes.size(); i++) {
                if (scliente.equals(clientes.get(i).getNomeCompleto().trim())) {
                    ccliente = clientes.get(i);
                }
            }
            if (ccliente.getNomeCompleto() == null || ccliente.getNomeCompleto().equals("")) {
                dao.salvaCliente(cliente);
                clientes = dao.todosClientes();
                new SendRequest().execute();
                startActivity(new Intent(CadastroClienteActivity.this, ListaDeClientesActivity.class));
                Toast.makeText(CadastroClienteActivity.this, "Salvo com Sucesso!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Esse cliente já está cadastrado!", Toast.LENGTH_LONG).show();
            }
        }
    }
//==================================================================================================
    private void realizaVerificacao() {
        String nome = campoNomeCompleto.getText().toString().trim().trim().trim();
        String dataNascimento = campoDataNascimento.getText().toString().trim().trim().trim();
        String cpf = campoCpf.getText().toString().trim().trim().trim();
        String rg = campoRg.getText().toString().trim().trim().trim();
        String nomePai = campoNomePai.getText().toString().trim().trim().trim();
        String nomeMae = campoNomeMae.getText().toString().trim().trim().trim();

        String celular = campoCelular1.getText().toString().trim().trim().trim();
        String rua = campoRua.getText().toString().trim().trim().trim();
        String numero = campoNumero.getText().toString().trim().trim().trim();
        String quadra = campoQuadra.getText().toString().trim().trim().trim();
        String lote = campoLote.getText().toString().trim().trim().trim();
        String bairro = campoBairro.getText().toString().trim().trim().trim();
        String cep = campoCEP.getText().toString().trim().trim().trim();
        String complemento = campoComplemento.getText().toString().trim().trim().trim();

        if(nome.equals("") || nome == null){
            Toast.makeText(this, "Preencha o campo Nome", Toast.LENGTH_SHORT).show();
        }else{
            if (dataNascimento.equals("") || dataNascimento == null){
                Toast.makeText(this, "Preencha o campo Data Nascimento", Toast.LENGTH_SHORT).show();
            }else{
                if(cpf.equals("") || cpf == null){
                    Toast.makeText(this, "Preencha o campo CPF", Toast.LENGTH_SHORT).show();
                }else{
                    if(rg.equals("") || rg == null){
                        Toast.makeText(this, "Preencha o campo RG", Toast.LENGTH_SHORT).show();
                    }else{
                        if(nomePai.equals("") || nomePai == null){
                            Toast.makeText(this, "Preencha o campo Nome Pai", Toast.LENGTH_SHORT).show();
                        }else{
                            if(nomeMae.equals("") || nomeMae == null){
                                Toast.makeText(this, "Preencha o campo Nome Mãe", Toast.LENGTH_SHORT).show();
                            }else{
                                if(celular.equals("") || celular == null){
                                    Toast.makeText(this, "Preencha o campo Celular", Toast.LENGTH_SHORT).show();
                                }else{
                                    if(rua.equals("") || rua == null){
                                        Toast.makeText(this, "Preencha o campo Rua", Toast.LENGTH_SHORT).show();
                                    }else{
                                        if(numero.equals("") || numero == null){
                                            Toast.makeText(this, "Preencha o campo Número", Toast.LENGTH_SHORT).show();
                                        }else{
                                            if(quadra.equals("") || quadra == null){
                                                Toast.makeText(this, "Preencha o campo Quadra", Toast.LENGTH_SHORT).show();
                                            }else{
                                                if(lote.equals("") || lote == null){
                                                    Toast.makeText(this, "Preencha o campo Lote", Toast.LENGTH_SHORT).show();
                                                }else{
                                                    if(bairro.equals("") || bairro == null){
                                                        Toast.makeText(this, "Preencha o campo Bairro", Toast.LENGTH_SHORT).show();
                                                    }else{
                                                        if(cep.equals("") || cep == null){
                                                            Toast.makeText(this, "Preencha o campo CEP", Toast.LENGTH_SHORT).show();
                                                        }else{
                                                            if(complemento.equals("") || complemento == null){
                                                                Toast.makeText(this, "Preencha o campo Complemento", Toast.LENGTH_SHORT).show();
                                                            }else{
                                                                concluiCadastro();
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
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
            Toast.makeText(getApplicationContext(), "Salvo Na GSheet!!!", Toast.LENGTH_LONG).show();
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
        if (put == 1) {
            action = "addCliente";
            cliente = clientes.get(clientes.size() - 1);
            id = cliente.getId();
        }
        if (put == 2) {
            action = "updateCliente";
            id = cliente.getId();
        }

        enviaDados.put("pasta", ID_PASTA);
        enviaDados.put("planilha", CLIENTE_PLAN);
        enviaDados.put("action", action);
        enviaDados.put("idCliente", id);
        enviaDados.put("nomeCompleto", cliente.getNomeCompleto());
        enviaDados.put("dataNascimento", cliente.getDataNascimento());
        enviaDados.put("cpf", cliente.getCpf());
        enviaDados.put("rg", cliente.getRg());
        enviaDados.put("nomePai", cliente.getNomePai());
        enviaDados.put("nomeMae", cliente.getNomeMae());

        enviaDados.put("celular1", cliente.getCelular1());
        enviaDados.put("celular2", cliente.getCelular2());
        enviaDados.put("telefone", cliente.getTelefone());
        enviaDados.put("email", cliente.getEmail());

        enviaDados.put("cep", cliente.getCep());
        enviaDados.put("rua", cliente.getRua());
        enviaDados.put("numero", cliente.getNumero());
        enviaDados.put("quadra", cliente.getQuadra());
        enviaDados.put("lote", cliente.getLote());
        enviaDados.put("bairro", cliente.getBairro());
        enviaDados.put("complemento", cliente.getComplemento());

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
    private void configuraBotao() {
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizaVerificacao();
            }
        });
    }
//==================================================================================================
}

