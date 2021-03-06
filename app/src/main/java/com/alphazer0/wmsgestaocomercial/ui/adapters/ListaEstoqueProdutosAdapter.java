package com.alphazer0.wmsgestaocomercial.ui.adapters;

import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.CHAVE_PRODUTO;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.alphazer0.wmsgestaocomercial.R;
import com.alphazer0.wmsgestaocomercial.model.Produto;
import com.alphazer0.wmsgestaocomercial.ui.activities.estoque.InformacoesProduto;

import java.util.ArrayList;
import java.util.List;

public class ListaEstoqueProdutosAdapter extends RecyclerView.Adapter implements Filterable{
//==================================================================================================
    //CRIANDO UMA COPIA DA LISTA PARA O ADAPTER
    private List<Produto> produtoPesquisa;
    private List<Produto> produtos;
    private Context context;

    public ListaEstoqueProdutosAdapter(Context context, List<Produto> produto) {
        this.context = context;
        this.produtoPesquisa = produto;
        this.produtos = produto;
    }

//==================================================================================================
    //RECYCLER VIEW
    class produtoViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        public produtoViewHolder(@NonNull View itemView) {
            super(itemView);
            CardView cardView = itemView.findViewById(R.id.cardViewProdutos);
            cardView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
             menu.add(this.getAdapterPosition(),0,0,"Remover Produto");
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context).inflate(R.layout.item_produto, parent, false);
        return new ListaEstoqueProdutosAdapter.produtoViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CardView cardView = holder.itemView.findViewById(R.id.cardViewProdutos);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InformacoesProduto.class);
                intent.putExtra(CHAVE_PRODUTO,produtos.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });

        Produto produto = produtos.get(position);
        TextView prod = holder.itemView.findViewById(R.id.item_produto);
        TextView marca = holder.itemView.findViewById(R.id.item_marca);
        TextView qtdValorUnit = holder.itemView.findViewById(R.id.item_quantidade_valor_unitario);


        prod.setText(produto.getProduto());
        marca.setText("Marca: " + produto.getMarca());
        qtdValorUnit.setText("Quantidade: " + produto.getQuantidade() + " | " + "Valor: R$" + produto.getPrecoVenda());
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }
//==================================================================================================
    public void atualizaListaProdutos(List<Produto> produto, String categoria) {
        this.produtos.clear();
        if (categoria.equals("Todos") || categoria.equals(null)) {
            this.produtos.addAll(produto);
            notifyDataSetChanged();
        } else {
            for (Produto filtroGrupo : produto) {
                if (filtroGrupo.getCategoria().equals(categoria)) {
                    this.produtos.add(filtroGrupo);
                    notifyDataSetChanged();
                }
            }
        }
    }

    public void pegaTodosProdutos(List<Produto> produto) {
        this.produtos.clear();
        this.produtos.addAll(produto);
    }

//==================================================================================================
    public void remove(int position) {
        produtos.remove(position);
        notifyDataSetChanged();
    }
    public void pegaProduto(int position, Produto produto){
         produto = produtos.get(position);
    }
//==================================================================================================
    //INICIANDO CONFIGURACAO DO FILTRO
    @Override
    public Filter getFilter() {
        return filtroProduto;
    }

    //CONFIGURACAO DO FILTRO
    private Filter filtroProduto = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Produto> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(produtos);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Produto item : produtos) {
                    if (item.getProduto().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            produtoPesquisa.clear();
            produtoPesquisa.addAll((List) results.values);
            notifyDataSetChanged();
        }

    };
//==================================================================================================
}
