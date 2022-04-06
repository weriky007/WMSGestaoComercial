package com.alphazer0.wmsgestaocomercial.ui.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alphazer0.wmsgestaocomercial.R;
import com.alphazer0.wmsgestaocomercial.database.VendasDatabase;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomVendasDAO;
import com.alphazer0.wmsgestaocomercial.model.Venda;
import com.alphazer0.wmsgestaocomercial.ui.activities.clientes.ListaDeClientesActivity;
import com.alphazer0.wmsgestaocomercial.ui.activities.contas.ContasPrincipalActivity;
import com.alphazer0.wmsgestaocomercial.ui.activities.estoque.ListaDeProdutosActivity;
import com.alphazer0.wmsgestaocomercial.ui.activities.fornecedores.FornecedoresActivity;
import com.alphazer0.wmsgestaocomercial.ui.activities.relatorios.RelatoriosPrincipal;
import com.alphazer0.wmsgestaocomercial.ui.activities.vendas.VendasActivity;
import com.androidplot.ui.DynamicTableModel;
import com.androidplot.ui.HorizontalPositioning;
import com.androidplot.ui.SeriesRenderer;
import com.androidplot.ui.Size;
import com.androidplot.ui.SizeMode;
import com.androidplot.ui.VerticalPositioning;
import com.androidplot.xy.BarFormatter;
import com.androidplot.xy.BarRenderer;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.StepMode;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class PrincipalActivity extends AppCompatActivity {

    public static final String WMS_GESTÃO_COMERCIAL = "WMS Gestão Comercial";
    private ImageButton btnFornecedores;
    private ImageButton btnClientes;
    private ImageButton btnVendas;
    private ImageButton btnEstoque;
    private ImageButton btnContas;
    private ImageButton btnRelatorios;
    private XYPlot grafico;

//==================================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        setTitle(WMS_GESTÃO_COMERCIAL);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
        bindElementos();
        configuraBotoes();

        try {
            configuraGrafico();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
//==================================================================================================
    private void configuraGrafico() throws ParseException {
        View layoutGrafico = getLayoutInflater().inflate(R.layout.grafico_principal_activity,null);
        LinearLayout layoutGrafic = findViewById(R.id.linear_grafic_principal);
        layoutGrafic.addView(layoutGrafico);
        grafico = layoutGrafico.findViewById(R.id.grafico_linhas);

        //PEGA TODAS AS VENDAS
        RoomVendasDAO dao;
        dao = VendasDatabase.getInstance(this).getVendaDAO();
        List<Venda> vendas = dao.todasVendas();

        //CONFIGURA DATA PARA PEGAR OS MESES
        List<String> sdata = new ArrayList<>();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date data = new Date();
        GregorianCalendar dataCal = new GregorianCalendar();
        int[] imeses = new int[vendas.size()];

        //SEPARA A QUANTIDADE DE VENDAS POR MES
        int jan = 0;
        int fev = 0;
        int mar = 0;
        int abr = 0;
        int mai = 0;
        int jun = 0;
        int jul = 0;
        int ago = 0;
        int set = 0;
        int out = 0;
        int nov = 0;
        int dez = 0;

        //LACO PARA COLETA DOS VALORES
        for(int i =0;i<vendas.size();i++) {
            for (Venda venda : vendas) {

                sdata.add(venda.getData());
                data = formato.parse(sdata.get(i));
                imeses[i] = dataCal.get(Calendar.MONTH)+1;
                dataCal.setTime(data);
            }//END FOREACH

            //CONTA A QUANTIDADE DE VENDAS POR MES
            if(imeses[i] == 1){
                jan++;
            }

            if(imeses[i] == 2){
                fev++;
            }

            if(imeses[i] == 3){
                mar++;
            }

            if(imeses[i] == 4){
                abr++;
            }

            if(imeses[i] == 5){
                mai++;
            }

            if(imeses[i] == 6){
                jun++;
            }

            if(imeses[i] == 7){
                jul++;
            }

            if(imeses[i] == 8){
                ago++;
            }

            if(imeses[i] == 9){
                set++;
            }

            if(imeses[i] == 10){
                out++;
            }

            if(imeses[i] == 11){
                nov++;
            }

            if(imeses[i] == 12){
                dez++;
            }
        }//END FOR

        Number[] A = {0,jan};
        Number[] B  = {0,fev};
        Number[] C  = {0,mar};
        Number[] D  = {0,abr};
        Number[] E  = {0,mai};
        Number[] F  = {0,jun};
        Number[] G  = {0,jul};
        Number[] H  = {0,ago};
        Number[] I  = {0,set};
        Number[] J  = {0,out};
        Number[] K  = {0,nov};
        Number[] L  = {0,dez};

        //Number[] meses = {jan,fev,mar,abr,mai,jun,jul,ago,set,out,nov,dez};
        Number[] meses = {51,28,36,29,55,69,49,42,60,30,27,30};

        XYSeries a = new SimpleXYSeries(Arrays.asList(meses[0]),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"01");
        XYSeries b = new SimpleXYSeries(Arrays.asList(meses[1]),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"02");
        XYSeries c = new SimpleXYSeries(Arrays.asList(meses[2]),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"03");
        XYSeries d = new SimpleXYSeries(Arrays.asList(meses[3]),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"04");
        XYSeries e = new SimpleXYSeries(Arrays.asList(meses[4]),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"05");
        XYSeries f = new SimpleXYSeries(Arrays.asList(meses[5]),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"06");
        XYSeries g = new SimpleXYSeries(Arrays.asList(meses[6]),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"07");
        XYSeries h = new SimpleXYSeries(Arrays.asList(meses[7]),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"08");
        XYSeries i = new SimpleXYSeries(Arrays.asList(meses[8]),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"09");
        XYSeries j = new SimpleXYSeries(Arrays.asList(meses[9]),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"10");
        XYSeries k = new SimpleXYSeries(Arrays.asList(meses[10]),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"11");
        XYSeries l = new SimpleXYSeries(Arrays.asList(meses[11]),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"12");


        MeuGraficoFormater bf1 = new  MeuGraficoFormater ( Color.RED , Color.WHITE );
        MeuGraficoFormater bf2 = new  MeuGraficoFormater ( Color.GREEN , Color.WHITE );
        MeuGraficoFormater bf3 = new  MeuGraficoFormater ( Color.BLUE , Color.WHITE );
        MeuGraficoFormater bf4 = new  MeuGraficoFormater ( Color.YELLOW , Color.WHITE );
        MeuGraficoFormater bf5 = new  MeuGraficoFormater ( Color.MAGENTA , Color.WHITE );
        MeuGraficoFormater bf6 = new  MeuGraficoFormater ( Color.CYAN , Color.WHITE );
        MeuGraficoFormater bf7 = new  MeuGraficoFormater ( Color.CYAN , Color.WHITE );
        MeuGraficoFormater bf8 = new  MeuGraficoFormater ( Color.MAGENTA , Color.WHITE );
        MeuGraficoFormater bf9 = new  MeuGraficoFormater ( Color.YELLOW , Color.WHITE );
        MeuGraficoFormater bf10 = new  MeuGraficoFormater ( Color.BLUE , Color.WHITE );
        MeuGraficoFormater bf11 = new  MeuGraficoFormater ( Color.GREEN , Color.WHITE );
        MeuGraficoFormater bf12 = new  MeuGraficoFormater ( Color.RED , Color.WHITE );

        grafico.addSeries(a,bf1);
        grafico.addSeries(b,bf2);
        grafico.addSeries(c,bf3);
        grafico.addSeries(d,bf4);
        grafico.addSeries(e,bf5);
        grafico.addSeries(f,bf6);
        grafico.addSeries(g,bf7);
        grafico.addSeries(h,bf8);
        grafico.addSeries(i,bf9);
        grafico.addSeries(j,bf10);
        grafico.addSeries(k,bf11);
        grafico.addSeries(l,bf12);

        int ysize = 3;
        int[] intmeses = new int[meses.length];
        for(int m = 0;m < meses.length; m++){
            intmeses[m] = (int) meses[m];
            for(int mes:intmeses){

                if(mes >=100 && mes < 500){
                    ysize = 10;
                }

                if(mes >=500 && mes <1000 ){
                    ysize = 50;
                }

                if(mes >= 1000){
                    ysize = 100;
                }
                if(mes>=2000){
                    ysize = 200;
                }

            }
        }
        //PanZoom.attach(grafico);
        grafico.setRangeStep(StepMode.INCREMENT_BY_VAL,ysize);//QUANTIDADE VALORES VERTICAL Y
        grafico.setDomainStep(StepMode.INCREMENT_BY_VAL, 1);//QUANTIDADE VALORES HORIZONTAL X
        grafico.setDomainBoundaries(-25,24, BoundaryMode.FIXED); // Tamanho fixo horizontal

        MeuGraficoRender meuGraficoRender = grafico.getRenderer(MeuGraficoRender.class);
        meuGraficoRender.setBarOrientation(BarRenderer.BarOrientation.SIDE_BY_SIDE);//ORGANIZA AS COLUNAS
        meuGraficoRender.setBarGroupWidth(BarRenderer.BarGroupWidthMode.FIXED_GAP,10);//LARGURA DAS COLUNAS
        grafico.getLegend().position(-400, HorizontalPositioning.ABSOLUTE_FROM_CENTER,360, VerticalPositioning.ABSOLUTE_FROM_CENTER);
        grafico.getLegend().setTableModel(new DynamicTableModel(6, 2));
        grafico.getLegend().setSize(new Size(100,SizeMode.ABSOLUTE,900,SizeMode.ABSOLUTE));
//        grafico.addSeries(s2,new LineAndPointFormatter(Color.RED,Color.RED,null,null));
//        grafico.addSeries(s3,new LineAndPointFormatter(Color.BLUE,Color.BLUE,null,null));
    }
//==================================================================================================
    private void bindElementos() {
        btnFornecedores = findViewById(R.id.btnPrincipalFornecedoresActivity);
        btnClientes = findViewById(R.id.btnPrincipalClientesActivity);
        btnVendas = findViewById(R.id.btnAdicionaItemNaListaActivity);
        btnEstoque = findViewById(R.id.btnPrincipalEstoqueActivity);
        btnContas = findViewById(R.id.btnPrincipalContasActivity);
        btnRelatorios = findViewById(R.id.btnPrincipalRelatoriosActivity);
    }
//==================================================================================================
    //MENU APPBAR
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.img_botao_manual:
                Toast.makeText(this, ""+item.getItemId(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_botao_config:
                Toast.makeText(this, ""+item.getItemId(), Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
//==================================================================================================
    private void configuraBotoes() {
        btnFornecedores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PrincipalActivity.this, FornecedoresActivity.class));
            }
        });

        btnClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PrincipalActivity.this, ListaDeClientesActivity.class));
            }
        });

        btnVendas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PrincipalActivity.this, VendasActivity.class));
            }
        });

        btnEstoque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PrincipalActivity.this, ListaDeProdutosActivity.class));
            }
        });

        btnContas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PrincipalActivity.this, ContasPrincipalActivity.class));
            }
        });

        btnRelatorios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PrincipalActivity.this, RelatoriosPrincipal.class));
            }
        });
    }
//==================================================================================================
}
//==================================================================================================
class MeuGraficoFormater extends BarFormatter{
    public MeuGraficoFormater(int fillColor, int borderColor) {
        super(fillColor, borderColor);
    }

    @Override
    public Class<? extends SeriesRenderer> getRendererClass() {
        return MeuGraficoRender.class;
    }

    @Override
    public SeriesRenderer doGetRendererInstance(XYPlot plot) {
        return new MeuGraficoRender(plot);
    }
}
//==================================================================================================
class MeuGraficoRender extends BarRenderer<MeuGraficoFormater>{

    public MeuGraficoRender(XYPlot plot) {
        super(plot);
    }
}
//==================================================================================================