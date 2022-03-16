package com.alphazer0.wmsgestaocomercial.ui.adapters;

import android.content.Context;
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

public class ListaVendasAdapter extends RecyclerView.Adapter {
    private List<Venda> vendas;
    private Context context;
//==================================================================================================
    public ListaVendasAdapter(Context context, List<Venda> vendas) {
        this.context = context;
        this.vendas = vendas;
    }
//==================================================================================================
    //RECYCLER VIEW
    class vendaViewHolder extends RecyclerView.ViewHolder{
    public vendaViewHolder(@NonNull View itemView) {
        super(itemView);
    }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context).inflate(R.layout.item_vendas, parent, false);
        return new vendaViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
       Venda venda = vendas.get(position);
         TextView dataHora = holder.itemView.findViewById(R.id.item_list_vendas_data_hora);
         TextView cliente = holder.itemView.findViewById(R.id.item_list_vendas_cliente);
         TextView vlTotal = holder.itemView.findViewById(R.id.item_list_vendas_valor_total);
         TextView formaPagamento = holder.itemView.findViewById(R.id.item_list_vendas_tipo_pagamento);

         dataHora.setText("Data: "+venda.getData()+" | "+"Hora: "+venda.getHora());
         cliente.setText("Cliente: "+venda.getCliente());
         vlTotal.setText("Total: R$"+venda.getVlTotal());
         formaPagamento.setText("Forma pagamento: "+venda.getFormaPagamento());
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
