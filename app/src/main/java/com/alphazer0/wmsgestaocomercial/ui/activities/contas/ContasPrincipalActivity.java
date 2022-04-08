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
        Segment segment_brasil = new Segment("Brasil",50);
        Segment segment_usa = new Segment("USA",30);
        Segment segment_japao = new Segment("Japao",20);

        //SETANDO AS CORES EM RGB
        SegmentFormatter formatterBrasil = new SegmentFormatter(Color.rgb(210,105,30));
        SegmentFormatter formatterUsa = new SegmentFormatter(Color.rgb(128,0,128));
        SegmentFormatter formatterJapao = new SegmentFormatter(Color.rgb(218,165,32));

        //SETANDO AS CONFIGURACOES DE CORES NO GRAFICO
        graficoPizza.addSegment(segment_brasil,formatterBrasil);
        graficoPizza.addSegment(segment_usa,formatterUsa);
        graficoPizza.addSegment(segment_japao,formatterJapao);

        //MUDANDO ONDE O GRAFICO SE INICIA
//        graficoPizza.getRenderer(PieRenderer.class).setStartDegs(90);

        //CASO QUEIRA CORTAR O CIRCULO
        //graficoPizza.getRenderer(PieRenderer.class).setExtentDegs(190);

        //CASO QUEIRA MUDAR A LARGURA
        //graficoPizza.getRenderer(PieRenderer.class).setDonutSize(0.9f, PieRenderer.DonutMode.PERCENT);
        //graficoPizza.getRenderer(PieRenderer.class).setDonutSize(30, PieRenderer.DonutMode.PIXELS);

        //CASO QUEIRA MUDAR O TAMANHO DAS LETRAS DAS LEGENDAS
        formatterBrasil.getLabelPaint().setTextSize(40f);
        formatterJapao.getLabelPaint().setTextSize(40f);
        formatterUsa.getLabelPaint().setTextSize(40f);
        //MUDAR A COR DA LEGENDA
        formatterBrasil.getLabelPaint().setColor(Color.WHITE);
        formatterJapao.getLabelPaint().setColor(Color.WHITE);
        formatterUsa.getLabelPaint().setColor(Color.WHITE);

        //CASO QUEIRA SEPARAR UMA FATIA
        formatterBrasil.setOffset(30);
        formatterJapao.setOffset(30);
        formatterUsa.setOffset(30);
    }
//==================================================================================================
}
