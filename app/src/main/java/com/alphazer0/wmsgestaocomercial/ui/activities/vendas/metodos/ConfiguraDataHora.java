package com.alphazer0.wmsgestaocomercial.ui.activities.vendas.metodos;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConfiguraDataHora {
    public void dataHora(String dataFormatada, String horaFormatada){
        SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formataHora = new SimpleDateFormat("hh:mm:ss");
        Date dataAtual = new Date();
        Date horaAtual = new Date();
        dataFormatada = formataData.format(dataAtual);
        horaFormatada = formataHora.format(horaAtual);
    }
}
