package com.alphazer0.wmsgestaocomercial.ui.activities.vendas;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alphazer0.wmsgestaocomercial.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CalculaRecebimentoEmDinheiro {
    public void calcula(View layoutDinheiro, TextView valorTotal){
        //CONFIGURA RECEBIMENTO EM DINHEIRO
        EditText valorRecebido = layoutDinheiro.findViewById(R.id.edit_valor_recebido);
        TextView valorTroco = layoutDinheiro.findViewById(R.id.troco_valor);
        Button btnConcluiTroco = layoutDinheiro.findViewById(R.id.conclui_calc_troco);
        btnConcluiTroco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String svlRecebido = valorRecebido.getText().toString();
                String svlTotal = valorTotal.getText().toString();
                BigDecimal bvlRecebido = new BigDecimal(svlRecebido);
                BigDecimal bvlTotal = new BigDecimal(svlTotal);
                BigDecimal calc = new BigDecimal("0");
                calc = calc.add(bvlRecebido.subtract(bvlTotal));

                DecimalFormat formatador = new DecimalFormat("0.00");
                String a = formatador.format(calc);
                valorTroco.setText(a);
            }
        });
    }
}
