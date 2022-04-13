package com.alphazer0.wmsgestaocomercial.ui.adapters;

import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.CHAVE_CLIENTE;

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
import com.alphazer0.wmsgestaocomercial.model.Cliente;
import com.alphazer0.wmsgestaocomercial.model.MovimentacaoCaixa;
import com.alphazer0.wmsgestaocomercial.ui.activities.clientes.InformacoesCliente;

import java.util.ArrayList;
import java.util.List;

public class ListaFluxoCaixaAdapter extends RecyclerView.Adapter implements Filterable {
//==================================================================================================
    //CRIANDO UMA COPIA DA LISTA PARA O ADAPTER
    private List<MovimentacaoCaixa> listaMovimentacaoPesquisa;
    private List<MovimentacaoCaixa> listaMovimentacao;
    private Context context;

    //CONSTRUCTOR
    public ListaFluxoCaixaAdapter(Context context, List<MovimentacaoCaixa> movimentacao) {
        this.context = context;
        this.listaMovimentacaoPesquisa = movimentacao;
        this.listaMovimentacao = movimentacao;
    }
//==================================================================================================
    //CRIANDO O MENU DE CONTEXTO
    class FluxoCaixaViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        public FluxoCaixaViewHolder(@NonNull View itemView) {
            super(itemView);
            CardView cardView = itemView.findViewById(R.id.cardViewMovimentacaoCaixa);
            cardView.setOnCreateContextMenuListener(this);
        }

    //PRESSAO DO ITEM
    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.add(this.getAdapterPosition(),0,0,"Remover item?");
    }
}

    //INFLANDO O ITEM NA LISTA
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context).inflate(R.layout.item_movimentacao_caixa, parent, false);
        return new FluxoCaixaViewHolder(viewCriada);
    }

    //CLICK DO ITEM
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CardView cardView = holder.itemView.findViewById(R.id.cardViewMovimentacaoCaixa);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InformacoesCliente.class);
                intent.putExtra(CHAVE_CLIENTE, listaMovimentacao.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });

        //PEGANDO ELEMENTOS DO ITEM
        MovimentacaoCaixa itemMovimentacao = listaMovimentacao.get(position);
        TextView tipo = holder.itemView.findViewById(R.id.item_tipo);
        TextView data = holder.itemView.findViewById(R.id.item_data);
        TextView descricao = holder.itemView.findViewById(R.id.item_descricao);
        TextView valor = holder.itemView.findViewById(R.id.item_valor);

        tipo.setText(itemMovimentacao.getTipo());
        data.setText(itemMovimentacao.getData());
        descricao.setText(itemMovimentacao.getDescricao());
        valor.setText(itemMovimentacao.getDescricao());
    }

    //PEGA O TAMANHO DA LISTA
    @Override
    public int getItemCount() {
        return listaMovimentacao.size();
    }
//==================================================================================================
    public void atualiza(List<MovimentacaoCaixa> movimentacaoCaixa) {
        this.listaMovimentacao.clear();
        this.listaMovimentacao.addAll(movimentacaoCaixa);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        listaMovimentacao.remove(position);
        notifyDataSetChanged();
    }
//==================================================================================================
    //INICIANDO CONFIGURACAO DO FILTRO
    @Override
    public Filter getFilter() {
        return filtroMovimentacao;
    }

    //CONFIGURACAO DO FILTRO
    private Filter filtroMovimentacao = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<MovimentacaoCaixa> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(listaMovimentacao);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (MovimentacaoCaixa item : listaMovimentacao) {
                    if (item.getTipo().toLowerCase().contains(filterPattern)) {
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
            listaMovimentacaoPesquisa.clear();
            listaMovimentacaoPesquisa.addAll((List) results.values);
            notifyDataSetChanged();
        }

    };
//==================================================================================================
}
