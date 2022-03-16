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
import com.alphazer0.wmsgestaocomercial.model.FornecedorPJ;

import java.util.ArrayList;
import java.util.List;

public class ListaFornecedoresPJAdapter extends RecyclerView.Adapter implements Filterable {
    //==================================================================================================
    //CRIANDO UMA COPIA DA LISTA PARA O ADAPTER
    private List<FornecedorPJ> fornecedoresPJPesquisa;
    private List<FornecedorPJ> fornecedoresPJ;
    private Context context;

    public ListaFornecedoresPJAdapter(Context context, List<FornecedorPJ> fornecedorPJS) {
        this.context = context;
        this.fornecedoresPJPesquisa = fornecedorPJS;
        this.fornecedoresPJ = fornecedorPJS;
    }

    //==================================================================================================
    //RECYCLER VIEW
    class fornecedorPJViewHolder extends RecyclerView.ViewHolder {
        public fornecedorPJViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context).inflate(R.layout.item_fornecedorpj, parent, false);
        return new ListaFornecedoresPJAdapter.fornecedorPJViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FornecedorPJ fornecedorPJ = fornecedoresPJ.get(position);

        TextView razaoSocial = holder.itemView.findViewById(R.id.item_fornecedorpj_razao_social);
        TextView nomeFantasia = holder.itemView.findViewById(R.id.item_fornecedorpj_nome_fantasia);
        TextView cel1 = holder.itemView.findViewById(R.id.item_fornecedorpj_celular1);
        TextView telefone = holder.itemView.findViewById(R.id.item_fornecedorpj_telefone);
        TextView endereco = holder.itemView.findViewById(R.id.item_fornecedorpj_endereco);


        razaoSocial.setText(fornecedorPJ.getRazaoSocial());
        nomeFantasia.setText("Nome Fantasia: " +fornecedorPJ.getNomeFantasia());
        cel1.setText("Celular: " +fornecedorPJ.getCelular1());
        telefone.setText("Telefone Fixo: " +fornecedorPJ.getTelefone());
        endereco.setText("R: " + fornecedorPJ.getRua() + " | " + "Bairro: " + fornecedorPJ.getBairro());
    }

    @Override
    public int getItemCount() {
        return fornecedoresPJ.size();
    }

    //==================================================================================================
    public void atualiza(List<FornecedorPJ> fornecedoresPJ) {
        this.fornecedoresPJ.clear();
        this.fornecedoresPJ.addAll(fornecedoresPJ);
        notifyDataSetChanged();
    }

    public void remove(FornecedorPJ fornecedoresPJ) {
        this.fornecedoresPJ.remove(fornecedoresPJ);
        notifyDataSetChanged();
    }

    //==================================================================================================
    //INICIANDO CONFIGURACAO DO FILTRO
    @Override
    public Filter getFilter() {
        return filtroFornecedorPJ;
    }

    //CONFIGURACAO DO FILTRO
    private Filter filtroFornecedorPJ = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<FornecedorPJ> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(fornecedoresPJ);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (FornecedorPJ item : fornecedoresPJ) {
                    if (item.getRazaoSocial().toLowerCase().contains(filterPattern)) {
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
            fornecedoresPJPesquisa.clear();
            fornecedoresPJPesquisa.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
//==================================================================================================
}
