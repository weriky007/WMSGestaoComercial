package com.alphazer0.wmsgestaocomercial.ui.activities.vendas.metodos;

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

    public void contaCliente(Context context, List<Cliente> clientes, RoomClienteDAO clienteDAO, MultiAutoCompleteTextView campoClienteConta, TextView valorTotal, String dataContaCliente, Venda venda){
        Cliente cli  = new Cliente();
        String sCampoCliente = campoClienteConta.getText().toString().trim();
        clientes = clienteDAO.todosClientes();
        String sDivida ="";
        String sData = "";
        String sDivOld = "";
        String sCliente = "";

        for(int a = 0; a<clientes.size();a++){
            if(sCampoCliente.equals(clientes.get(a).getNomeCompleto().trim())){
                cli = clientes.get(a);
            }
        }
        sCliente = cli.getNomeCompleto();
        venda.setCliente(sCliente);

        try{
        if(cli.getDivida() == null || cli.getDivida().equals("")){
            cli.setDivida("0.00");
        }
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        sDivOld = cli.getDivida();
        sDivida = valorTotal.getText().toString();
        BigDecimal bdivida = new BigDecimal(sDivida);
        BigDecimal bdividaOld = new BigDecimal(sDivOld);
        bdivida = bdivida.add(bdividaOld);

        sData = dataContaCliente;
        cli.setDataVencimento(sData);
        cli.setDivida(bdivida.toString());
        clienteDAO.editaCliente(cli);
    }

}
