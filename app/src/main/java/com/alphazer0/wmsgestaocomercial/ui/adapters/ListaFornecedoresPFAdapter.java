package com.alphazer0.wmsgestaocomercial.ui.adapters;

import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.CHAVE_FORNECEDORPF;

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
import com.alphazer0.wmsgestaocomercial.model.FornecedorPF;
import com.alphazer0.wmsgestaocomercial.ui.activities.fornecedores.FornecedoresPF.InformacaoFornecedorPF;

import java.util.ArrayList;
import java.util.List;

public class ListaFornecedoresPFAdapter extends RecyclerView.Adapter implements Filterable {
//==================================================================================================
    //CRIANDO UMA COPIA DA LISTA PARA O ADAPTER
    private List<FornecedorPF> fornecedoresPFPesquisa;
    private List<FornecedorPF> fornecedoresPF;
    private Context context;

    public ListaFornecedoresPFAdapter(Context context,List<FornecedorPF> fornecedorPFS) {
        this.context = context;
        this.fornecedoresPFPesquisa = fornecedorPFS;
        this.fornecedoresPF = fornecedorPFS;
    }

//==================================================================================================
    //RECYCLER VIEW
    class fornecedorPfViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        public fornecedorPfViewHolder(@NonNull View itemView) {
            super(itemView);
            CardView cardView = itemView.findViewById(R.id.cardViewFornecedorPF);
            cardView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            menu.add(this.getAdapterPosition(),0,0,"Remover Fornecedor");
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context).inflate(R.layout.item_fornecedorpf, parent, false);
        return new ListaFornecedoresPFAdapter.fornecedorPfViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CardView cardView = holder.itemView.findViewById(R.id.cardViewFornecedorPF);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InformacaoFornecedorPF.class);
                intent.putExtra(CHAVE_FORNECEDORPF,fornecedoresPF.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });

        FornecedorPF fornecedorPF = fornecedoresPF.get(position);
        TextView nome = holder.itemView.findViewById(R.id.item_fornecedorpf_nome);
        TextView telefone = holder.itemView.findViewById(R.id.item_fornecedorpf_telefone);
        TextView endereco = holder.itemView.findViewById(R.id.item_fornecedorpf_endereco);


        nome.setText(fornecedorPF.getNomeCompleto());
        telefone.setText("Telefone: " + fornecedorPF.getTelefone());
        endereco.setText("R: " +fornecedorPF.getRua()+ " | " +"Numero: "+fornecedorPF.getNumero()+ "\nBairro: " +fornecedorPF.getBairro());
    }

    @Override
    public int getItemCount() {
        return fornecedoresPF.size();
    }
//==================================================================================================
    public void atualiza(List<FornecedorPF> fornecedoresPF) {
        this.fornecedoresPF.clear();
        this.fornecedoresPF.addAll(fornecedoresPF);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        fornecedoresPF.remove(position);
        notifyDataSetChanged();
    }
//==================================================================================================
    //INICIANDO CONFIGURACAO DO FILTRO
    @Override
    public Filter getFilter() {
        return filtroFornecedorPF;
    }

    //CONFIGURACAO DO FILTRO
    private Filter filtroFornecedorPF = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<FornecedorPF> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(fornecedoresPF);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (FornecedorPF item : fornecedoresPF) {
                    if (item.getNomeCompleto().toLowerCase().contains(filterPattern)) {
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
            fornecedoresPFPesquisa.clear();
            fornecedoresPFPesquisa.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
//==================================================================================================
}
