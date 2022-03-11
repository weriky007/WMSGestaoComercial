package com.alphazer0.wmsgestaocomercial.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.alphazer0.wmsgestaocomercial.R;
import com.alphazer0.wmsgestaocomercial.model.FornecedorPJ;

import java.util.ArrayList;
import java.util.List;

public class ListaFornecedoresPJAdapter extends BaseAdapter implements Filterable {
//==================================================================================================
    //CRIANDO UMA COPIA DA LISTA PARA O ADAPTER
    private  List<FornecedorPJ> fornecedoresPJPesquisa;
    private  List<FornecedorPJ> fornecedoresPJ;
    //private final Context context;

    public ListaFornecedoresPJAdapter(List<FornecedorPJ> fornecedorPJS) {
        //his.context = context;
        this.fornecedoresPJPesquisa = fornecedorPJS;
        this.fornecedoresPJ = fornecedorPJS;
    }
//==================================================================================================

    //INDICA A QUANTIDADE DE ELEMENTOS QUE O ADAPTER TERA
    @Override
    public int getCount() {
        return fornecedoresPJPesquisa.size();
    }

    //INDICA O ELEMENTO QUE VC QUER COM BASE NA POSICAO
    @Override
    public FornecedorPJ getItem(int position) {
        return fornecedoresPJPesquisa.get(position);
    }

    //ELEMENTO PELO ID, CASO NAO HOUVER ID NA LISTA DEIXE ZERO
    @Override
    public long getItemId(int position) {
        return fornecedoresPJPesquisa.get(position).getId();
    }

    //REPRESENTA AS CONFIGURACOES VIEW QUE SERA APRESENTADA
    @Override
    public View getView(int position, View convertView, ViewGroup listViewFornecedorPF) {
        //NAO PRECISA UTILISAR O FALSE QUANDO O LAYOUT E LINEAR
        View viewCriada = criaView(listViewFornecedorPF);

        FornecedorPJ fornecedorPJDevolvido = fornecedoresPJPesquisa.get(position);
        vincula(viewCriada, fornecedorPJDevolvido);

        return viewCriada;
    }
    //==================================================================================================
    private void vincula(View viewCriada, FornecedorPJ fornecedorPJDevolvido) {
        //BIND
        TextView nome = viewCriada.findViewById(R.id.item_fornecedorpf_nome);
        TextView celular1 = viewCriada.findViewById(R.id.item_fornecedorpf_telefone);
        TextView endereco = viewCriada.findViewById(R.id.item_fornecedorpf_endereco);

        nome.setText(fornecedorPJDevolvido.getRazaoSocial());
        celular1.setText(fornecedorPJDevolvido.getCelular1());
        endereco.setText("R :"+fornecedorPJDevolvido.getRua()+" | "+"Bairro: "+fornecedorPJDevolvido.getBairro());
    }

    private View criaView(ViewGroup listViewFornecedoresPJ) {
        return LayoutInflater.from(listViewFornecedoresPJ.getContext()).inflate(R.layout.item_fornecedorpf, listViewFornecedoresPJ,false);
    }
    //==================================================================================================
    public void atualiza(List<FornecedorPJ> fornecedoresPJ){
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
