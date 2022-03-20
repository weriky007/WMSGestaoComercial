package com.alphazer0.wmsgestaocomercial.ui.adapters;

import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.CHAVE_CLIENTE;
import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.CHAVE_VENDA;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.alphazer0.wmsgestaocomercial.R;
import com.alphazer0.wmsgestaocomercial.model.Produto;
import com.alphazer0.wmsgestaocomercial.model.Venda;
import com.alphazer0.wmsgestaocomercial.ui.activities.clientes.InformacoesCliente;
import com.alphazer0.wmsgestaocomercial.ui.activities.relatorios.VendasInfoActivity;

import java.util.List;

public class ListaTodasVendasAdapter extends RecyclerView.Adapter {
    private List<Venda> vendas;
    private Context context;
//==================================================================================================
    public ListaTodasVendasAdapter(Context context, List<Venda> vendas) {
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
        CardView cardView = holder.itemView.findViewById(R.id.cardViewTodasVendas);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, VendasInfoActivity.class);
                intent.putExtra(CHAVE_VENDA,vendas.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });

         Venda venda = vendas.get(position);
         TextView dataHora = holder.itemView.findViewById(R.id.item_list_vendas_data_hora);
         TextView cliente = holder.itemView.findViewById(R.id.item_list_vendas_cliente);
         TextView vlTotal = holder.itemView.findViewById(R.id.item_list_vendas_valor_total);
         TextView formaPagamento = holder.itemView.findViewById(R.id.item_list_vendas_tipo_pagamento);

         dataHora.setText("Data: "+venda.getData()+" | "+"Hora: "+venda.getHora());
         String cli = "";
         vlTotal.setText("Total: R$"+venda.getVlTotal());
         try {
             if (!venda.getCliente().equals(null) || !venda.getCliente().equals("")) {
                 cli = venda.getCliente();
             }
         }catch (NullPointerException n){
             n.printStackTrace();
         }

        cliente.setText("Cliente: "+cli);

         //CONFIGURA PAGAMENTO CC
         pagamentoCC(venda,formaPagamento);

         //CONFIGURA PAGAMENTO CONTA CLIENTE
         pagamentoContaCliente(venda,formaPagamento);

         //CONFIGURA PAGAMENTO DINHEIRO
        pagamentoDinheiro(venda,formaPagamento);

        //CONFIGURA PAGAMENTO DEBITO
        pagamentoDebito(venda,formaPagamento);
    }

    @Override
    public int getItemCount() {
        return vendas.size();
    }
//==================================================================================================
    private void pagamentoCC(Venda venda, TextView formaPagamento){
        String parcelasCC = "";
        try{
            if(venda.getParcelasCC().equals("") || venda.getParcelasCC().equals(null)){
                parcelasCC = "";
            }else {
                parcelasCC = venda.getParcelasCC();
            }
        }catch (NullPointerException n){
            n.printStackTrace();
        }
        if(venda.getFormaPagamento().equals("Cartao de Credito")){
            if(!parcelasCC.equals("")) {
                formaPagamento.setText("Forma pagamento: " + venda.getFormaPagamento() + " | " + "\nParcelas: " + venda.getParcelasCC() +" | "+ "Valor: R$" + venda.getVlParcelas());
            }else {
                formaPagamento.setText("Forma pagamento: " + venda.getFormaPagamento());
            }}
    }

    private void pagamentoContaCliente(Venda venda, TextView formaPagamento){
        if(venda.getFormaPagamento().equals("Conta Cliente")){
            formaPagamento.setText("Forma pagamento: " + venda.getFormaPagamento()+"\n"+"Data de Vencimento: "+venda.getDataVencimento());
        }
    }

    private void pagamentoDinheiro(Venda venda, TextView formaPagamento){
        if(venda.getFormaPagamento().equals("Dinheiro")){
            formaPagamento.setText("Forma pagamento: " + venda.getFormaPagamento());
        }
    }

    private void pagamentoDebito(Venda venda, TextView formaPagamento){
        if(venda.getFormaPagamento().equals("Cartao de Debito")){
            formaPagamento.setText("Forma pagamento: " + venda.getFormaPagamento());
        }
    }
//==================================================================================================
  public void atualizaListaVendas(List<Venda> venda){
    this.vendas.clear();
    this.vendas.addAll(venda);
    notifyDataSetChanged();
  }
//==================================================================================================
}
