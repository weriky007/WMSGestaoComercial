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
import com.alphazer0.wmsgestaocomercial.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class ListaContasAPagarAdapter extends BaseAdapter implements Filterable {
    private List<ContaAPagar> contasAPagar;
    private List<ContaAPagar> contasAPagarPesquisa;

    public ListaContasAPagarAdapter(List<ContaAPagar> contaAPagar) {
        this.contasAPagar = contaAPagar;
        this.contasAPagarPesquisa = contaAPagar;
    }
//==================================================================================================
    @Override
    public int getCount() {
        return contasAPagarPesquisa.size();
    }

    @Override
    public ContaAPagar getItem(int position) {
        return contasAPagarPesquisa.get(position);
    }

    @Override
    public long getItemId(int position) {
        return contasAPagarPesquisa.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup listViewContasAPagar) {
        //NAO PRECISA UTILISAR O FALSE QUANDO O LAYOUT E LINEAR
        View viewCriada = criaView(listViewContasAPagar);

        ContaAPagar contaAPagarDevolvida = contasAPagarPesquisa.get(position);
        vincula(viewCriada, contaAPagarDevolvida);

        return viewCriada;
    }
//==================================================================================================
    private void vincula(View viewCriada, ContaAPagar contaAPagarDevolvida) {
        //BIND
        TextView contaPagar = viewCriada.findViewById(R.id.item_conta_a_pagar);
        TextView dataVencimento = viewCriada.findViewById(R.id.item_conta_a_pagar_data);
        TextView codigoBarras = viewCriada.findViewById(R.id.item_conta_a_pagar_cod_barras);
        TextView valorConta = viewCriada.findViewById(R.id.item_conta_a_pagar_valor);

        contaPagar.setText(contaAPagarDevolvida.getConta());
        dataVencimento.setText(contaAPagarDevolvida.getDataVencimento());
        codigoBarras.setText(contaAPagarDevolvida.getCodigoBarras());
        valorConta.setText(contaAPagarDevolvida.getVlConta());
    }

    private View criaView(ViewGroup listViewContasAPagar) {
        return LayoutInflater.from(listViewContasAPagar.getContext()).inflate(R.layout.item_conta_a_pagar, listViewContasAPagar, false);
    }
//==================================================================================================
    public void atualizaListaContasAPagar(List<ContaAPagar> contaAPagar) {
        this.contasAPagar.clear();
        this.contasAPagar.addAll(contaAPagar);
        notifyDataSetChanged();
    }
//==================================================================================================
    public void remove(ContaAPagar contaAPagar) {
        contasAPagar.remove(contaAPagar);
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
            List<ContaAPagar> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(contasAPagar);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ContaAPagar item : contasAPagar) {
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
            contasAPagarPesquisa.clear();
            contasAPagarPesquisa.addAll((List) results.values);
            notifyDataSetChanged();
        }

    };
//==================================================================================================
}
