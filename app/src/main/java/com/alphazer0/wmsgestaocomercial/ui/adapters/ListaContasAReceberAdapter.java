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
import com.alphazer0.wmsgestaocomercial.model.ContaAReceber;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ListaContasAReceberAdapter extends BaseAdapter implements Filterable {
    private List<ContaAReceber> contasAReceber;
    private List<ContaAReceber> contasAReceberPesquisa;

    public ListaContasAReceberAdapter(List<ContaAReceber> contaAReceber) {
        this.contasAReceber = contaAReceber;
        this.contasAReceberPesquisa = contaAReceber;
    }
//==================================================================================================
    @Override
    public int getCount() {
        return contasAReceberPesquisa.size();
    }

    @Override
    public ContaAReceber getItem(int position) {
        return contasAReceberPesquisa.get(position);
    }

    @Override
    public long getItemId(int position) {
        return contasAReceberPesquisa.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup listViewContasAReceber) {
        //NAO PRECISA UTILISAR O FALSE QUANDO O LAYOUT E LINEAR
        View viewCriada = criaView(listViewContasAReceber);

        ContaAReceber contaAReceberDevolvida = contasAReceberPesquisa.get(position);
        vincula(viewCriada, contaAReceberDevolvida);

        return viewCriada;
    }
//==================================================================================================
    private void vincula(View viewCriada, ContaAReceber contaAReceberDevolvida) {
        //BIND
        TextView contaReceber = viewCriada.findViewById(R.id.item_conta_a_receber);
        TextView dataVencimento = viewCriada.findViewById(R.id.item_conta_a_receber_data);
        TextView codigoBarras = viewCriada.findViewById(R.id.item_conta_a_receber_cod_barras);
        TextView valorConta = viewCriada.findViewById(R.id.item_conta_a_receber_valor);

        contaReceber.setText(contaAReceberDevolvida.getConta());
        dataVencimento.setText(contaAReceberDevolvida.getDataVencimento());
        codigoBarras.setText(contaAReceberDevolvida.getCodigoBarras());
        valorConta.setText(contaAReceberDevolvida.getVlConta());
    }

    private View criaView(ViewGroup listViewContasAReceber) {
        return LayoutInflater.from(listViewContasAReceber.getContext()).inflate(R.layout.item_conta_a_receber, listViewContasAReceber, false);
    }
//==================================================================================================
    public void atualizaListaContasAPagar(List<ContaAReceber> contaAReceber) {
        this.contasAReceber.clear();
        this.contasAReceber.addAll(contaAReceber);
        notifyDataSetChanged();
    }
//==================================================================================================
    public void remove(ContaAReceber contaAReceber) {
        contasAReceber.remove(contaAReceber);
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
            List<ContaAReceber> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(contasAReceber);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ContaAReceber item : contasAReceber) {
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
            contasAReceberPesquisa.clear();
            contasAReceberPesquisa.addAll((List) results.values);
            notifyDataSetChanged();
        }

    };
//==================================================================================================
}
