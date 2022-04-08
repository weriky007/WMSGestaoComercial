package com.alphazer0.wmsgestaocomercial.ui.activities.contas;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.alphazer0.wmsgestaocomercial.R;
import com.androidplot.pie.PieChart;
import com.androidplot.pie.PieRenderer;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;

public class ContasPrincipalActivity extends AppCompatActivity {

    public static final String CONTAS = "Contas";
    private ImageButton btnContasPagar;
    private ImageButton btnContasReceber;
    private PieChart graficoPizza;
//==================================================================================================
    @Override
    protected void onCreate(Bundle savedIntanceState) {
        super.onCreate(savedIntanceState);
        setTitle(CONTAS);
        setContentView(R.layout.activity_contas_principal);
        mantemAtelaEmModoRetrato();
        configuraBotoes();
        configurandoGraficoPizza();
    }
//==================================================================================================
    private void mantemAtelaEmModoRetrato() {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
    }
//==================================================================================================
    private void configuraBotoes() {
        btnContasPagar = findViewById(R.id.btn_contas_a_pagar);
        btnContasReceber = findViewById(R.id.btn_contas_a_receber);

        btnContasPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ContasPrincipalActivity.this, ListaContasAPagarActivity.class));
            }
        });

        btnContasReceber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ContasPrincipalActivity.this, ListaContasAReceberActivity.class));
            }
        });
    }
//==================================================================================================
    private void configurandoGraficoPizza(){
        //INFLANDO A VIEW
        View viewGrafico = getLayoutInflater().inflate(R.layout.grafico_contas_principal_activity, null);
        LinearLayout linearLayout = findViewById(R.id.linear_grafic_contas);
        linearLayout.addView(viewGrafico);

        //BIND DO ELEMENTO
        graficoPizza = viewGrafico.findViewById(R.id.grafico_contas);

        //LEGENDA E DADOS QUE SERAO INSERIDOS NO GRAFICO
        Segment segment_caixa = new Segment("Caixa",40);
        Segment segment_pagar = new Segment("A Pagar",35);
        Segment segment_receber = new Segment("A Receber",15);

        //SETANDO AS CORES EM RGB
        SegmentFormatter formatterCaixa = new SegmentFormatter(Color.rgb(0,0,205));
        SegmentFormatter formatterPagar = new SegmentFormatter(Color.rgb(255,0,0));
        SegmentFormatter formatterReceber = new SegmentFormatter(Color.rgb(50,205,50));

        //SETANDO AS CONFIGURACOES DE CORES NO GRAFICO
        graficoPizza.addSegment(segment_caixa,formatterCaixa);
        graficoPizza.addSegment(segment_pagar,formatterPagar);
        graficoPizza.addSegment(segment_receber,formatterReceber);

        //MUDANDO ONDE O GRAFICO SE INICIA
//        graficoPizza.getRenderer(PieRenderer.class).setStartDegs(90);

        //CASO QUEIRA CORTAR O CIRCULO
        //graficoPizza.getRenderer(PieRenderer.class).setExtentDegs(190);

        //CASO QUEIRA MUDAR A LARGURA
        //graficoPizza.getRenderer(PieRenderer.class).setDonutSize(0.9f, PieRenderer.DonutMode.PERCENT);
        //graficoPizza.getRenderer(PieRenderer.class).setDonutSize(30, PieRenderer.DonutMode.PIXELS);

        //CASO QUEIRA MUDAR O TAMANHO DAS LETRAS DAS LEGENDAS
        formatterCaixa.getLabelPaint().setTextSize(40f);
        formatterReceber.getLabelPaint().setTextSize(40f);
        formatterPagar.getLabelPaint().setTextSize(40f);
        //MUDAR A COR DA LEGENDA
        formatterCaixa.getLabelPaint().setColor(Color.WHITE);
        formatterReceber.getLabelPaint().setColor(Color.WHITE);
        formatterPagar.getLabelPaint().setColor(Color.WHITE);

        //CASO QUEIRA SEPARAR UMA FATIA
        formatterCaixa.setOffset(30);
//        formatterReceber.setOffset(30);
//        formatterPagar.setOffset(30);
    }
//==================================================================================================
}
