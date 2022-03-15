package com.alphazer0.wmsgestaocomercial.ui.activities.vendas;

import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import com.alphazer0.wmsgestaocomercial.model.Venda;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CalculaParcelasCartaoCredito {
    public void calculoParcelasCC(EditText recebeNumeroParcelas, EditText taxa, TextView vlParcela, TextView valorTotal, Venda venda) {
        //CALCULANDO VALOR PARCELAS
        String svlTotal = valorTotal.getText().toString();
        String snParcelas = recebeNumeroParcelas.getText().toString();
        String sTaxa = taxa.getText().toString();
        venda.setParcelasCC(snParcelas);

        //TRANFORMANDO AS STRINGS EM BIGDECIMAL PARA OS CALCULOS
        BigDecimal bvlTotal = new BigDecimal(svlTotal);
        BigDecimal bnParcelas = new BigDecimal(snParcelas);
        BigDecimal bTaxaIni = new BigDecimal(sTaxa);

        //CONVERTER PERCENTUAL PARA MODO DE PROGRAMACAO
        BigDecimal bTaxaFinal = new BigDecimal("0");
        BigDecimal divisorTaxa = new BigDecimal("100");
        bTaxaFinal = bTaxaIni.divide(divisorTaxa);

        //DIVIDINDO O VALO TOTAL PELO NUMERO DE PARCELAS ESCOLHIDO
        BigDecimal parcelaIni = new BigDecimal("0");
        parcelaIni = bvlTotal.divide(bnParcelas);

        //PEGA O VALOR DA TAXA
        BigDecimal valorDaTaxa = new BigDecimal("0");
        valorDaTaxa = parcelaIni.multiply(bTaxaFinal);
        valorDaTaxa.setScale(2, BigDecimal.ROUND_HALF_EVEN);

        //SOMA O VALOR DA TAXA A PARCELA PARA O RESULTADO FINAL
        BigDecimal resultadoParcela = new BigDecimal("0");
        resultadoParcela = parcelaIni.add(valorDaTaxa);

        DecimalFormat formatador = new DecimalFormat("0.00");
        String a = formatador.format(resultadoParcela);
        venda.setVlParcelas(a);

        //ATRIBUI O RESULTADO AO TEXTVIEW VALOR DAS PARCELAS
        vlParcela.setText(a);
    }

}
