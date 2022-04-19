package com.alphazer0.wmsgestaocomercial.ui.activities.financas;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alphazer0.wmsgestaocomercial.R;
import com.alphazer0.wmsgestaocomercial.database.TotalCaixaDatabase;
import com.alphazer0.wmsgestaocomercial.database.TotalContasAPagarDatabase;
import com.alphazer0.wmsgestaocomercial.database.TotalContasAReceberDatabase;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomTotalCaixaDAO;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomTotalContasAPagarDAO;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomTotalContasAReceberDAO;
import com.androidplot.pie.PieChart;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;

public class FinancasPrincipalActivity extends AppCompatActivity {

    public static final String CONTAS = "Finanças";
    private ImageButton btnContasPagar;
    private ImageButton btnContasReceber;
    private ImageButton btnContasRecebidas;
    private ImageButton btnContasPagas;
    private ImageButton btnFluxoCaixa;

    private PieChart graficoPizza;
    private RoomTotalContasAReceberDAO totalContasAReceberDAO;
    private RoomTotalContasAPagarDAO totalContasAPagarDAO;
    private RoomTotalCaixaDAO totalCaixaDAO;
//==================================================================================================
    @Override
    protected void onCreate(Bundle savedIntanceState) {
        super.onCreate(savedIntanceState);
        setTitle(CONTAS);
        setContentView(R.layout.activity_financas_principal);
        mantemAtelaEmModoRetrato();
        pegaDadosBds();
        configuraBotoes();
        configurandoGraficoPizza();
    }
//==================================================================================================
    private void mantemAtelaEmModoRetrato() {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
    }
//==================================================================================================
    private void pegaDadosBds(){
        totalContasAReceberDAO = TotalContasAReceberDatabase.getInstance(this).getTotalContasAReceberDAO();
        totalContasAPagarDAO = TotalContasAPagarDatabase.getInstance(this).getTotalContasAPagarDAO();
        totalCaixaDAO  = TotalCaixaDatabase.getInstance(this).getTotalCaixaDAO();
    }
//==================================================================================================
    private void configuraBotoes() {
        //BIND DOS BOTOES
        btnContasPagar = findViewById(R.id.btn_contas_a_pagar);
        btnContasReceber = findViewById(R.id.btn_contas_a_receber);
        btnFluxoCaixa = findViewById(R.id.btn_caixa);

        //VAI PARA CONTAS A PAGAR
        btnContasPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FinancasPrincipalActivity.this, ListaContasAPagarActivity.class));
                finish();
            }
        });

        //VAI PARA CONTAS A RECEBER
        btnContasReceber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FinancasPrincipalActivity.this, ListaContasAReceberActivity.class));
                finish();
            }
        });

        //VAI PARA CONTROLE DE CAIXA
        btnFluxoCaixa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FinancasPrincipalActivity.this, ListaFluxoCaixaActivity.class));
                finish();
            }
        });
    }
//==================================================================================================
    private void configurandoGraficoPizza() {
        //INFLANDO A VIEW DO GRAFICO NO CARDVIEW
        View viewGrafico = getLayoutInflater().inflate(R.layout.grafico_contas_principal_activity, null);
        LinearLayout linearLayout = findViewById(R.id.linear_grafic_contas);

        //BIND DO ELEMENTO
        graficoPizza = viewGrafico.findViewById(R.id.grafico_contas);

        //VERIFICA SE EXISTEM VALORES PARA O GRAFICO SER RENDERIZADO
        if(totalContasAReceberDAO.totalContasAReceber() != null && !totalContasAReceberDAO.totalContasAReceber().getTotal().equals("0.00") || totalContasAPagarDAO.totalContasAPagar() != null || totalCaixaDAO.totalCaixa() != null){
            linearLayout.addView(viewGrafico);
        }

        //VERIFICA QUAIS GRAFICOS SERAO CONFIGURADOS
        if(totalCaixaDAO.totalCaixa() != null) {
            if (totalContasAPagarDAO.totalContasAPagar() == null && totalContasAReceberDAO.totalContasAReceber() != null) {
                configuraGraficoCaixa();
                configuraGraficoReceber();
            } else if (totalContasAReceberDAO.totalContasAReceber() == null && totalContasAPagarDAO.totalContasAPagar() != null) {
                configuraGraficoPagar();
                configuraGraficoCaixa();
            } else if (totalContasAReceberDAO.totalContasAReceber() != null && totalContasAPagarDAO.totalContasAPagar() != null) {
                configuraGraficoCaixa();
                configuraGraficoPagar();
                configuraGraficoReceber();
            }
        }else{
            //NAO INSERE NADA NO GRAFICO
        }

        //MUDANDO ONDE O GRAFICO SE INICIA
        //graficoPizza.getRenderer(PieRenderer.class).setStartDegs(90);

        //CASO QUEIRA CORTAR O CIRCULO
        //graficoPizza.getRenderer(PieRenderer.class).setExtentDegs(190);

        //CASO QUEIRA MUDAR A LARGURA
        //graficoPizza.getRenderer(PieRenderer.class).setDonutSize(0.9f, PieRenderer.DonutMode.PERCENT);
        //graficoPizza.getRenderer(PieRenderer.class).setDonutSize(30, PieRenderer.DonutMode.PIXELS);
    }
//==================================================================================================
    //CONFIGURACOES DOS DADOS A SEREM APRESENTADOS NO GRAFICO
    private void configuraGraficoReceber(){
        double dReceber = Double.parseDouble(totalContasAReceberDAO.totalContasAReceber().getTotal());
        Segment segment_receber = new Segment("A Receber: R$" + dReceber, dReceber);
        SegmentFormatter formatterReceber = new SegmentFormatter(Color.rgb(50, 205, 50));
        formatterReceber.getLabelPaint().setTextSize(40f);
        formatterReceber.getLabelPaint().setColor(Color.WHITE);
        graficoPizza.addSegment(segment_receber, formatterReceber);
    }

    private void configuraGraficoPagar(){
        double dPagar = Double.parseDouble(totalContasAPagarDAO.totalContasAPagar().getTotal());
        Segment segment_pagar = new Segment("A Pagar: R$" + dPagar, dPagar);
        SegmentFormatter formatterPagar = new SegmentFormatter(Color.rgb(255, 0, 0));
        graficoPizza.addSegment(segment_pagar, formatterPagar);
        formatterPagar.getLabelPaint().setTextSize(40f);
        formatterPagar.getLabelPaint().setColor(Color.WHITE);
    }

    private void configuraGraficoCaixa(){
        double dCaixa = Double.parseDouble(totalCaixaDAO.totalCaixa().getTotal());
        Segment segment_caixa = new Segment("Caixa: R$"+dCaixa, dCaixa);
        SegmentFormatter formatterCaixa = new SegmentFormatter(Color.rgb(30,144,255));
        formatterCaixa.getLabelPaint().setTextSize(40f);//TAMANHO TEXTO LEGENDA
        formatterCaixa.getLabelPaint().setColor(Color.WHITE);//COR DA LEGENDA
        formatterCaixa.setOffset(30);//SEPARA FATIA
        graficoPizza.addSegment(segment_caixa, formatterCaixa);
    }
//==================================================================================================
}
