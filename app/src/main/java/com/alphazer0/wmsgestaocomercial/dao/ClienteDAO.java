package com.alphazer0.wmsgestaocomercial.dao;
//==================================================================================================

import com.alphazer0.wmsgestaocomercial.model.Cliente;

import java.util.ArrayList;
import java.util.List;

//==================================================================================================
public class ClienteDAO {
//==================================================================================================
    private final static List<Cliente> clientes = new ArrayList<>();
    private static int contadorIds = 1;
//==================================================================================================
    //COPIA DA LISTA
    public List<Cliente> todos (){
        return new ArrayList<>(clientes);
    }
//==================================================================================================
    public void salva (Cliente cliente){
        cliente.setId(contadorIds);
        contadorIds++;
    }
//==================================================================================================
    private void atualizaId(){
        contadorIds++;
    }
//==================================================================================================
    public void edita(Cliente cliente){
        Cliente clienteEncontrado = buscaClientePeloId(cliente);
        if (clienteEncontrado != null){
            int posicaoDoCliente = clientes.indexOf(clienteEncontrado);
            clientes.set(posicaoDoCliente, cliente);
        }
    }

    private Cliente buscaClientePeloId (Cliente cliente){
        for (Cliente c: clientes) {
            if(c.getId() == cliente.getId()){
                return c;
            }
        }
        return null;
    }
//==================================================================================================
    public void remove(Cliente cliente){
        Cliente clienteDevolvido = buscaClientePeloId(cliente);
        if(clienteDevolvido != null){
            clientes.remove(clienteDevolvido);
        }
    }
//==================================================================================================
}

