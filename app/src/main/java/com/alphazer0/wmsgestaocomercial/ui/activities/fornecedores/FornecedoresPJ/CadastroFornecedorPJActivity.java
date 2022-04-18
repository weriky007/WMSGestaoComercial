package com.alphazer0.wmsgestaocomercial.ui.activities.fornecedores.FornecedoresPJ;

import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.CHAVE_FORNECEDORPJ;
import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.FORNECEDORESPJ_PLAN;
import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.ID_PASTA;
import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.LINK_MACRO;
import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.MASK_CEL;
import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.MASK_CEP;
import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.MASK_CNPJ;
import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.MASK_TEL;
import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.TITULO_APPBAR_CADASTRO_FORNECEDOR_PJ;

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
import com.alphazer0.wmsgestaocomercial.database.FornecedoresPJDatabase;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomFornecedorPJDAO;
import com.alphazer0.wmsgestaocomercial.model.FornecedorPJ;
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

public class CadastroFornecedorPJActivity extends AppCompatActivity {
    public CadastroFornecedorPJActivity() throws SQLException {
    }

    @Override
    protected void onCreate(Bundle saveIntanceState) {
        super.onCreate(saveIntanceState);
        setTitle(TITULO_APPBAR_CADASTRO_FORNECEDOR_PJ);
        setContentView(R.layout.activity_formulario_dados_fornecedorpj);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);

        FornecedoresPJDatabase dataBase = FornecedoresPJDatabase.getInstance(this);
        dao = dataBase.getFornecedorPJDAO();

        bindDosCampos();
        carregaCliente();
        formataTexto();
        configuraBotao();
    }
//==================================================================================================
    //REFERENCIANDO OS ELEMENTOS
    private FornecedorPJ fornecedorPJ;
    private RoomFornecedorPJDAO dao;


    //CONFIGURACAO SCRIPT E PLANILHA BASE DADOS
    String linkMacro = LINK_MACRO;
    String idPlanilha = ID_PASTA;
    int put = 0;

    private List<FornecedorPJ> fornecedoresPJ = new ArrayList<>();
    public int id;

    //DADOS PESSOAIS
    private EditText campoRazaoSocial;
    private EditText campoNomeFantasia;
    private EditText campoCNPJ;
    private EditText campoIE;

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
        campoRazaoSocial = findViewById(R.id.edit_razao_social);
        campoNomeFantasia = findViewById(R.id.edit_nome_fantasia);
        campoCNPJ = findViewById(R.id.edit_cnpj);
        campoIE = findViewById(R.id.edit_ie);

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
        campoCNPJ.addTextChangedListener(MaskText.insert(MASK_CNPJ, campoCNPJ));
        campoCelular1.addTextChangedListener(MaskText.insert(MASK_CEL, campoCelular1));
        campoCelular2.addTextChangedListener(MaskText.insert(MASK_CEL, campoCelular2));
        campoTelefone.addTextChangedListener(MaskText.insert(MASK_TEL, campoTelefone));
        campoCEP.addTextChangedListener(MaskText.insert(MASK_CEP, campoCEP));
    }
//==================================================================================================
    private void recebeDadosDigitadosNosCampos() {
        String razaoSocial = campoRazaoSocial.getText().toString().trim().trim().trim();
        String nomeFantasia = campoNomeFantasia.getText().toString().trim().trim().trim();
        String cnpj = campoCNPJ.getText().toString().trim().trim().trim();
        String ie = campoIE.getText().toString();

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

        fornecedorPJ.setRazaoSocial(razaoSocial);
        fornecedorPJ.setNomeFantasia(nomeFantasia);
        fornecedorPJ.setCnpj(cnpj);
        fornecedorPJ.setIe(ie);

        fornecedorPJ.setCelular1(celular1);
        fornecedorPJ.setCelular2(celular2);
        fornecedorPJ.setTelefone(telefone);
        fornecedorPJ.setEmail(email);

        fornecedorPJ.setRua(rua);
        fornecedorPJ.setNumero(numero);
        fornecedorPJ.setQuadra(quadra);
        fornecedorPJ.setLote(lote);
        fornecedorPJ.setBairro(bairro);
        fornecedorPJ.setCep(cep);
        fornecedorPJ.setComplemento(complemento);
    }
//==================================================================================================
    private void carregaCliente() {
        Intent dados = getIntent();
        if (dados.hasExtra(CHAVE_FORNECEDORPJ)) {
            fornecedorPJ = (FornecedorPJ) dados.getSerializableExtra(CHAVE_FORNECEDORPJ);
            preencheCamposParaEdicao();
        } else {
            fornecedorPJ = new FornecedorPJ();
        }
    }
//==================================================================================================
    private void preencheCamposParaEdicao() {
        campoRazaoSocial.setText(fornecedorPJ.getRazaoSocial());
        campoNomeFantasia.setText(fornecedorPJ.getNomeFantasia());
        campoCNPJ.setText(fornecedorPJ.getCnpj());
        campoIE.setText(fornecedorPJ.getIe());

        campoCelular1.setText(fornecedorPJ.getCelular1());
        campoCelular2.setText(fornecedorPJ.getCelular2());
        campoTelefone.setText(fornecedorPJ.getTelefone());
        campoEmail.setText(fornecedorPJ.getEmail());

        campoRua.setText(fornecedorPJ.getRua());
        campoNumero.setText(fornecedorPJ.getNumero());
        campoQuadra.setText(fornecedorPJ.getQuadra());
        campoLote.setText(fornecedorPJ.getLote());
        campoBairro.setText(fornecedorPJ.getBairro());
        campoCEP.setText(fornecedorPJ.getCep());
        campoComplemento.setText(fornecedorPJ.getComplemento());
    }
//==================================================================================================
    private void concluiCadastro() {
        recebeDadosDigitadosNosCampos();
        if (fornecedorPJ.temIdValido()) {
            put = 2;
            dao.editaFornecedorPJ(fornecedorPJ);
            fornecedoresPJ = dao.todosFornecedoresPJ();
            Toast.makeText(CadastroFornecedorPJActivity.this, "Editado com Sucesso!", Toast.LENGTH_LONG).show();
            new SendRequest().execute();
            finish();
        } else {
            put = 1;
            fornecedoresPJ = dao.todosFornecedoresPJ();
            //VERIFICA REPETIDO
            String sfornecedorPJ = fornecedorPJ.getRazaoSocial().trim();
            FornecedorPJ ffornecedorPJ = new FornecedorPJ();
            for (int i = 0; i < fornecedoresPJ.size(); i++) {
                if (sfornecedorPJ.equals(fornecedoresPJ.get(i).getRazaoSocial().trim())) {
                    ffornecedorPJ = fornecedoresPJ.get(i);
                }
            }
            if (ffornecedorPJ.getRazaoSocial() == null || ffornecedorPJ.getRazaoSocial().equals("")) {
                dao.salvaFornecedorPJ(fornecedorPJ);
                fornecedoresPJ = dao.todosFornecedoresPJ();
                new SendRequest().execute();
                startActivity(new Intent(CadastroFornecedorPJActivity.this, ListaDeFornecedorPJActivity.class));
                Toast.makeText(CadastroFornecedorPJActivity.this, "Salvo com Sucesso!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Esse fornecedor já está cadastrado!", Toast.LENGTH_LONG).show();
            }
        }
    }
//==================================================================================================
    private void realizaVerificacao() {
        String srazaoSocial = campoRazaoSocial.getText().toString().trim().trim().trim();
        String snomeFantasia = campoNomeFantasia.getText().toString().trim().trim().trim();
        String scnpj = campoCNPJ.getText().toString().trim().trim().trim();
        String ie = campoIE.getText().toString().trim().trim().trim();

        String stelefone = campoTelefone.getText().toString().trim().trim().trim();
        String semail = campoEmail.getText().toString().trim().trim().trim();
        String srua = campoRua.getText().toString().trim().trim().trim();
        String snumero = campoNumero.getText().toString().trim().trim().trim();
        String squadra = campoQuadra.getText().toString().trim().trim().trim();
        String slote = campoLote.getText().toString().trim().trim().trim();
        String sbairro = campoBairro.getText().toString().trim().trim().trim();
        String scep = campoCEP.getText().toString().trim().trim().trim();
        String scomplemento = campoComplemento.getText().toString().trim().trim().trim();

        if(srazaoSocial.equals("") || srazaoSocial == null){
            Toast.makeText(this, "Preencha o campo Razão Social", Toast.LENGTH_SHORT).show();
        }else{
            if(snomeFantasia.equals("") || snomeFantasia == null){
                Toast.makeText(this, "Preencha o campo Nome Fantasia", Toast.LENGTH_SHORT).show();
            }else{
                if(scnpj.equals("") || scnpj == null){
                    Toast.makeText(this, "Preencha o campo CNPJ", Toast.LENGTH_SHORT).show();
                }else{
                    if(ie.equals("") || ie == null){
                        Toast.makeText(this, "Preencha o campo Inscrição Estadual", Toast.LENGTH_SHORT).show();
                    }else{
                        if(stelefone.equals("") || stelefone  == null){
                            Toast.makeText(this, "Preencha o campo Telefone Fixo", Toast.LENGTH_SHORT).show();
                        }else{
                            if(semail.equals("") || semail == null){
                                Toast.makeText(this, "Preencha o campo E-mail", Toast.LENGTH_SHORT).show();
                            }else{
                                if(srua.equals("") || srua == null){
                                    Toast.makeText(this, "Preencha o campo Rua", Toast.LENGTH_SHORT).show();
                                }else{
                                    if(snumero.equals("")||snumero == null){
                                        Toast.makeText(this, "Preencha o campo Rua", Toast.LENGTH_SHORT).show();
                                    }else{
                                        if(squadra.equals("")||squadra == null){
                                            Toast.makeText(this, "Preencha o campo Quadra", Toast.LENGTH_SHORT).show();
                                        }else{
                                            if(slote.equals("")||slote == null){
                                                Toast.makeText(this, "Preencha o campo Lote", Toast.LENGTH_SHORT).show();
                                            }else{
                                                if(sbairro.equals("")||sbairro == null){
                                                    Toast.makeText(this, "Preencha o campo Bairro", Toast.LENGTH_SHORT).show();
                                                }else{
                                                    if(scep.equals("") || scep == null){
                                                        Toast.makeText(this, "Preencha o campo CEP", Toast.LENGTH_SHORT).show();
                                                    }else{
                                                        if(scomplemento.equals("") || scomplemento == null){
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
            Toast.makeText(getApplicationContext(), "Salvo Na GSheet!", Toast.LENGTH_LONG).show();
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
            action = "addFornecedorPJ";
            fornecedorPJ = fornecedoresPJ.get(fornecedoresPJ.size() - 1);
            id = fornecedorPJ.getId();
        }
        if (put == 2) {
            action = "updateFornecedorPJ";
            id = fornecedorPJ.getId();
        }


        enviaDados.put("pasta", ID_PASTA);
        enviaDados.put("planilha", FORNECEDORESPJ_PLAN);
        enviaDados.put("action", action);
        enviaDados.put("idFornecedorPJ", id);
        enviaDados.put("razaoSocial", fornecedorPJ.getRazaoSocial());
        enviaDados.put("nomeFantasia", fornecedorPJ.getNomeFantasia());
        enviaDados.put("cnpj", fornecedorPJ.getCnpj());
        enviaDados.put("ie", fornecedorPJ.getIe());

        enviaDados.put("celular1", fornecedorPJ.getCelular1());
        enviaDados.put("celular2", fornecedorPJ.getCelular2());
        enviaDados.put("telefone", fornecedorPJ.getTelefone());
        enviaDados.put("email", fornecedorPJ.getEmail());

        enviaDados.put("cep", fornecedorPJ.getCep());
        enviaDados.put("rua", fornecedorPJ.getRua());
        enviaDados.put("numero", fornecedorPJ.getNumero());
        enviaDados.put("quadra", fornecedorPJ.getQuadra());
        enviaDados.put("lote", fornecedorPJ.getLote());
        enviaDados.put("bairro", fornecedorPJ.getBairro());
        enviaDados.put("complemento", fornecedorPJ.getComplemento());

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
