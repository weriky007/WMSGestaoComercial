package com.alphazer0.wmsgestaocomercial.ui.activities.vendas;

import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomClienteDAO;
import com.alphazer0.wmsgestaocomercial.model.Cliente;

import java.math.BigDecimal;
import java.util.List;

public class ContaDoCliente {

    public void contaCliente(List<Cliente> clientes, RoomClienteDAO clienteDAO, MultiAutoCompleteTextView campoCliente, TextView valorTotal){
        Cliente cliente = new Cliente();
        for(int i =0;i<clientes.size();i++){
            if(campoCliente.getText().equals(clientes.get(i).getNomeCompleto())){
                cliente = clientes.get(i);
            }
        }
        String valorAtual = cliente.getDivida();
        String valorCompra = valorTotal.getText().toString();

        BigDecimal vlAtual = new BigDecimal(valorAtual);
        BigDecimal vlCompra = new BigDecimal(valorCompra);
        BigDecimal calculo  = new BigDecimal("0");
        calculo = calculo.add(vlAtual.add(vlCompra));

        cliente.setDivida(calculo.toString());
        clienteDAO.editaCliente(cliente);
    }

}
