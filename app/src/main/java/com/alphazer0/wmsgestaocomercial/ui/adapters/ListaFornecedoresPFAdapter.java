package com.alphazer0.wmsgestaocomercial.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.alphazer0.wmsgestaocomercial.R;
import com.alphazer0.wmsgestaocomercial.model.FornecedorPF;

import java.util.ArrayList;
import java.util.List;

public class ListaFornecedoresPFAdapter extends BaseAdapter implements Filterable {
//==================================================================================================
    //CRIANDO UMA COPIA DA LISTA PARA O ADAPTER
    private  List<FornecedorPF> fornecedoresPFPesquisa;
    private  List<FornecedorPF> fornecedoresPF;
    //private final Context context;

    public ListaFornecedoresPFAdapter(List<FornecedorPF> fornecedorPFS) {
        //his.context = context;
        this.fornecedoresPFPesquisa = fornecedorPFS;
        this.fornecedoresPF = fornecedorPFS;
    }
//==================================================================================================

    //INDICA A QUANTIDADE DE ELEMENTOS QUE O ADAPTER TERA
    @Override
    public int getCount() {
        return fornecedoresPFPesquisa.size();
    }

    //INDICA O ELEMENTO QUE VC QUER COM BASE NA POSICAO
    @Override
    public FornecedorPF getItem(int position) {
        return fornecedoresPFPesquisa.get(position);
    }

    //ELEMENTO PELO ID, CASO NAO HOUVER ID NA LISTA DEIXE ZERO
    @Override
    public long getItemId(int position) {
        return fornecedoresPFPesquisa.get(position).getId();
    }

    //REPRESENTA AS CONFIGURACOES VIEW QUE SERA APRESENTADA
    @Override
    public View getView(int position, View convertView, ViewGroup listViewFornecedorPF) {
        //NAO PRECISA UTILISAR O FALSE QUANDO O LAYOUT E LINEAR
        View viewCriada = criaView(listViewFornecedorPF);

        FornecedorPF fornecedorPFDevolvido = fornecedoresPFPesquisa.get(position);
        vincula(viewCriada, fornecedorPFDevolvido);

        return viewCriada;
    }
//==================================================================================================
    private void vincula(View viewCriada, FornecedorPF fornecedorPFDevolvido) {
        //BIND
        TextView nome = viewCriada.findViewById(R.id.item_fornecedorpf_nome);
        TextView celular1 = viewCriada.findViewById(R.id.item_fornecedorpf_telefone);
        TextView endereco = viewCriada.findViewById(R.id.item_fornecedorpf_endereco);

        nome.setText(fornecedorPFDevolvido.getNomeCompleto());
        celular1.setText(fornecedorPFDevolvido.getCelular1());
        endereco.setText("R :"+fornecedorPFDevolvido.getRua()+" | "+"Bairro: "+fornecedorPFDevolvido.getBairro());
    }

    private View criaView(ViewGroup listViewFornecedoresPF) {
        return LayoutInflater.from(listViewFornecedoresPF.getContext()).inflate(R.layout.item_fornecedorpf, listViewFornecedoresPF,false);
    }
//==================================================================================================
    public void atualiza(List<FornecedorPF> fornecedoresPF){
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
