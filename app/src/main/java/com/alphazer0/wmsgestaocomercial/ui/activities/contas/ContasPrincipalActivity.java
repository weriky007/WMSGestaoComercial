package com.alphazer0.wmsgestaocomercial.ui.activities.contas;

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
import com.alphazer0.wmsgestaocomercial.database.TotalContasAPagarDatabase;
import com.alphazer0.wmsgestaocomercial.database.TotalContasAReceberDatabase;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomTotalContasAPagarDAO;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomTotalContasAReceberDAO;
import com.alphazer0.wmsgestaocomercial.model.TotalContasAPagar;
import com.alphazer0.wmsgestaocomercial.model.TotalContasAReceber;
import com.androidplot.pie.PieChart;
import com.androidplot.pie.PieRenderer;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;

import java.util.ArrayList;
import java.util.List;

public class ContasPrincipalActivity extends AppCompatActivity {

    public static final String CONTAS = "Contas";
    private ImageButton btnContasPagar;
    private ImageButton btnContasReceber;
    private PieChart graficoPizza;
    private RoomTotalContasAReceberDAO totalContasAReceberDAO;
    private RoomTotalContasAPagarDAO totalContasAPagarDAO;
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
    private void configurandoGraficoPizza() {
        //INFLANDO A VIEW
        View viewGrafico = getLayoutInflater().inflate(R.layout.grafico_contas_principal_activity, null);
        LinearLayout linearLayout = findViewById(R.id.linear_grafic_contas);
        linearLayout.addView(viewGrafico);

        //BIND DO ELEMENTO
        graficoPizza = viewGrafico.findViewById(R.id.grafico_contas);

        totalContasAReceberDAO = TotalContasAReceberDatabase.getInstance(this).getTotalContasAReceberDAO();
        totalContasAPagarDAO = TotalContasAPagarDatabase.getInstance(this).getTotalContasAPagarDAO();

        if(totalContasAPagarDAO.totalContasAPagar() == null){
            //GRAFICO CAIXA
            Segment segment_caixa = new Segment("Caixa: R$", 40);
            SegmentFormatter formatterCaixa = new SegmentFormatter(Color.rgb(0, 0, 205));
            formatterCaixa.getLabelPaint().setTextSize(40f);//TAMANHO TEXTO LEGENDA
            formatterCaixa.getLabelPaint().setColor(Color.WHITE);//COR DA LEGENDA
            formatterCaixa.setOffset(30);//SEPARA FATIA
            graficoPizza.addSegment(segment_caixa, formatterCaixa);

            //GRAFICO A RECEBER
            double dReceber = Double.parseDouble(totalContasAReceberDAO.totalContasAReceber().getTotal());
            Segment segment_receber = new Segment("A Receber: R$" + dReceber, dReceber);
            SegmentFormatter formatterReceber = new SegmentFormatter(Color.rgb(50, 205, 50));
            formatterReceber.getLabelPaint().setTextSize(40f);
            formatterReceber.getLabelPaint().setColor(Color.BLACK);
            graficoPizza.addSegment(segment_receber, formatterReceber);
        } else if(totalContasAReceberDAO.totalContasAReceber() == null){
            //GRAFICO A PAGAR
            double dPagar = Double.parseDouble(totalContasAPagarDAO.totalContasAPagar().getTotal());
            Segment segment_pagar = new Segment("A Pagar: R$" + dPagar, dPagar);
            SegmentFormatter formatterPagar = new SegmentFormatter(Color.rgb(255, 0, 0));
            graficoPizza.addSegment(segment_pagar, formatterPagar);
            formatterPagar.getLabelPaint().setTextSize(40f);
            formatterPagar.getLabelPaint().setColor(Color.WHITE);

            //GRAFICO CAIXA
            Segment segment_caixa = new Segment("Caixa: R$", 40);
            SegmentFormatter formatterCaixa = new SegmentFormatter(Color.rgb(0, 0, 205));
            formatterCaixa.getLabelPaint().setTextSize(40f);//TAMANHO TEXTO LEGENDA
            formatterCaixa.getLabelPaint().setColor(Color.WHITE);//COR DA LEGENDA
            formatterCaixa.setOffset(30);//SEPARA FATIA
            graficoPizza.addSegment(segment_caixa, formatterCaixa);
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
}
