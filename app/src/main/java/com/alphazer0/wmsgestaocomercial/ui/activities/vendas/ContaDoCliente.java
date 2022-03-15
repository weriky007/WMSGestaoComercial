package com.alphazer0.wmsgestaocomercial.ui.activities.vendas;

import android.content.Context;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomClienteDAO;
import com.alphazer0.wmsgestaocomercial.model.Cliente;
import com.alphazer0.wmsgestaocomercial.model.Venda;

import java.math.BigDecimal;
import java.util.List;

public class ContaDoCliente {

    public void contaCliente(List<Cliente> clientes, RoomClienteDAO clienteDAO, MultiAutoCompleteTextView campoClienteConta, TextView valorTotal, String dataContaCliente, Venda venda){
        Cliente cli  = new Cliente();
        String campoCliente = campoClienteConta.getText().toString().trim();
        clientes = clienteDAO.todosClientes();
        String divida ="";
        String data = "";
        String divOld = "";
        String cliente = "";

        for(int a = 0; a<clientes.size();a++){
            if(campoCliente.equals(clientes.get(a).getNomeCompleto().trim())){
                cli = clientes.get(a);
            }
        }
        cliente = cli.getNomeCompleto();
        venda.setCliente(cliente);

        divOld = cli.getDivida();
        divida = valorTotal.getText().toString();
        BigDecimal bdivida = new BigDecimal(divida);
        BigDecimal bdividaOld = new BigDecimal(divOld);
        bdivida = bdivida.add(bdividaOld);

        data = dataContaCliente;
        cli.setDataVencimento(data);
        cli.setDivida(bdivida.toString());
        clienteDAO.editaCliente(cli);
    }

}
