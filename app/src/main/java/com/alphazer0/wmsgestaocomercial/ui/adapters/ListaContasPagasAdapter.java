package com.alphazer0.wmsgestaocomercial.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.alphazer0.wmsgestaocomercial.R;
import com.alphazer0.wmsgestaocomercial.model.ContaAPagar;
import com.alphazer0.wmsgestaocomercial.model.ContaPaga;

import java.util.ArrayList;
import java.util.List;

public class ListaContasPagasAdapter extends BaseAdapter implements Filterable {
    private List<ContaPaga> listaContasPagas;
    private List<ContaPaga> listaContasPagasPesquisa;

    public ListaContasPagasAdapter(List<ContaPaga> contaPaga) {
        this.listaContasPagas = contaPaga;
        this.listaContasPagasPesquisa = contaPaga;
    }
//==================================================================================================
    @Override
    public int getCount() {
        return listaContasPagasPesquisa.size();
    }

    @Override
    public ContaPaga getItem(int position) {
        return listaContasPagasPesquisa.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaContasPagasPesquisa.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup listViewContasPagas) {
        //NAO PRECISA UTILISAR O FALSE QUANDO O LAYOUT E LINEAR
        View viewCriada = criaView(listViewContasPagas);

        ContaPaga contaPagaDevolvida = listaContasPagasPesquisa.get(position);
        vincula(viewCriada, contaPagaDevolvida);

        return viewCriada;
    }
//==================================================================================================
    private void vincula(View viewCriada, ContaPaga contaPagaDevolvida) {
        //BIND
        TextView contaPaga = viewCriada.findViewById(R.id.item_conta_paga);
        TextView dataDePagamento = viewCriada.findViewById(R.id.item_conta_paga_data);
        TextView horaDePagamento = viewCriada.findViewById(R.id.item_conta_paga_hora);
        TextView codigoBarras = viewCriada.findViewById(R.id.item_conta_paga_cod_barras);
        TextView valorConta = viewCriada.findViewById(R.id.item_conta_paga_valor);

        contaPaga.setText(contaPagaDevolvida.getConta());
        dataDePagamento.setText(contaPagaDevolvida.getDataPagamento());
        horaDePagamento.setText(contaPagaDevolvida.getHoraPagamento());
        codigoBarras.setText(contaPagaDevolvida.getCodigoBarras());
        valorConta.setText(contaPagaDevolvida.getVlConta());
    }

    private View criaView(ViewGroup listViewContasPagas) {
        return LayoutInflater.from(listViewContasPagas.getContext()).inflate(R.layout.item_conta_paga, listViewContasPagas, false);
    }
//==================================================================================================
    public void atualizaListaContasPagaS(List<ContaPaga> contaPaga) {
        this.listaContasPagas.clear();
        this.listaContasPagas.addAll(contaPaga);
        notifyDataSetChanged();
    }
//==================================================================================================
    public void remove(ContaPaga contaPaga) {
        listaContasPagas.remove(contaPaga);
        notifyDataSetChanged();
    }
//==================================================================================================
    //INICIANDO CONFIGURACAO DO FILTRO
    @Override
    public Filter getFilter() {
        return filtroContaAPagar;
    }

    //CONFIGURACAO DO FILTRO
    private Filter filtroContaAPagar = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ContaPaga> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(listaContasPagas);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ContaPaga item : listaContasPagas) {
                    if (item.getConta().toLowerCase().contains(filterPattern)) {
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
            listaContasPagasPesquisa.clear();
            listaContasPagasPesquisa.addAll((List) results.values);
            notifyDataSetChanged();
        }

    };
//==================================================================================================
}
