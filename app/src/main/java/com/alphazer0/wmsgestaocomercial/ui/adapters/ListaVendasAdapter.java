package com.alphazer0.wmsgestaocomercial.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alphazer0.wmsgestaocomercial.R;
import com.alphazer0.wmsgestaocomercial.model.Produto;
import com.alphazer0.wmsgestaocomercial.model.Venda;
import java.util.List;

public class ListaVendasAdapter extends RecyclerView.Adapter<ListaVendasAdapter.MyViewHolder> {
    private List<Venda> vendas;
//==================================================================================================
    public ListaVendasAdapter(List<Venda> vendas) {
        this.vendas = vendas;
    }
//==================================================================================================
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView dataHora;
        private TextView cliente;
        private TextView vlTotal;
        private TextView formaPagamento;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            dataHora = itemView.findViewById(R.id.item_list_vendas_data_hora);
            cliente = itemView.findViewById(R.id.item_list_vendas_cliente);
            vlTotal = itemView.findViewById(R.id.item_list_vendas_valor_total);
            formaPagamento = itemView.findViewById(R.id.item_list_vendas_tipo_pagamento);
        }
    }
//==================================================================================================
    @NonNull
    @Override
    public ListaVendasAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vendas,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListaVendasAdapter.MyViewHolder holder, int position) {
        String data = vendas.get(position).getData();
        String hora = vendas.get(position).getHora();
        String cliente = vendas.get(position).getCliente();
        String vlTotal = vendas.get(position).getVlTotal();
        String tipoPagamento = vendas.get(position).getFormaPagamento();

        holder.dataHora.setText("Data: "+data+" | "+"Hora: "+hora);
        holder.cliente.setText("Cliente: "+cliente);
        holder.vlTotal.setText("Total: R$"+vlTotal);
        holder.formaPagamento.setText("Forma pagamento: "+tipoPagamento);
    }

    @Override
    public int getItemCount() {
        return vendas.size();
    }
//==================================================================================================
  public void atualizaListaVendas(List<Venda> venda){
    this.vendas.clear();
    this.vendas.addAll(venda);
    notifyDataSetChanged();
  }
//==================================================================================================
}
