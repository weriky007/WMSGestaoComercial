package com.alphazer0.wmsgestaocomercial.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alphazer0.wmsgestaocomercial.R;
import com.alphazer0.wmsgestaocomercial.model.FornecedorPF;
import com.alphazer0.wmsgestaocomercial.model.Produto;

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
    class fornecedorPfViewHolder extends RecyclerView.ViewHolder {
        public fornecedorPfViewHolder(@NonNull View itemView) {
            super(itemView);
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
        FornecedorPF fornecedorPF = fornecedoresPF.get(position);
        TextView nome = holder.itemView.findViewById(R.id.item_fornecedorpf_nome);
        TextView telefone = holder.itemView.findViewById(R.id.item_fornecedorpf_telefone);
        TextView endereco = holder.itemView.findViewById(R.id.item_fornecedorpf_endereco);


        nome.setText(fornecedorPF.getNomeCompleto());
        telefone.setText("Telefone: " + fornecedorPF.getTelefone());
        endereco.setText("R: " +fornecedorPF.getRua()+ " | " + "Bairro: " +fornecedorPF.getBairro());
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

    public void remove(FornecedorPF fornecedoresPF) {
        this.fornecedoresPF.remove(fornecedoresPF);
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
