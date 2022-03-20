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

import java.util.List;

public class ListaInfoProdutosVendaAdapter extends RecyclerView.Adapter {
//==================================================================================================
    //CRIANDO UMA COPIA DA LISTA PARA O ADAPTER
    private List<Produto> produtos;
    private Context context;

    public ListaInfoProdutosVendaAdapter(Context context, List<Produto> produto) {
        this.context = context;
        this.produtos = produto;
    }
//==================================================================================================
    //RECYCLER VIEW
    class produtoInfoViewHolder extends RecyclerView.ViewHolder {
        public produtoInfoViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context).inflate(R.layout.item_produto_lista_compras, parent, false);
        return new ListaInfoProdutosVendaAdapter.produtoInfoViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Produto produtoSelect = produtos.get(position);
        TextView prod = holder.itemView.findViewById(R.id.produto_lista_compras);
        TextView marca = holder.itemView.findViewById(R.id.item_marca_lista_compras);
        TextView qtdValorUnit = holder.itemView.findViewById(R.id.item_quantidade_valor_unitario_lista_compras);


        prod.setText(produtoSelect.getProduto());
        marca.setText("Marca: " + produtoSelect.getMarca());
        qtdValorUnit.setText("Quantidade: " + produtoSelect.getQuantidade() + " | " + "Valor: R$" + produtoSelect.getPrecoVenda());
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }
//==================================================================================================
}
