package com.alphazer0.wmsgestaocomercial.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.alphazer0.wmsgestaocomercial.R;
import com.alphazer0.wmsgestaocomercial.model.ContaAReceber;
import com.alphazer0.wmsgestaocomercial.model.ContaRecebida;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListaContasARecebidasAdapter extends BaseAdapter implements Filterable {
    private List<ContaRecebida> listaContasRecebidas;
    private List<ContaRecebida> listaContasRecebidasPesquisa;

    public ListaContasARecebidasAdapter(List<ContaRecebida> contaRecebida) {
        this.listaContasRecebidas = contaRecebida;
        this.listaContasRecebidasPesquisa = contaRecebida;
    }
//==================================================================================================
    @Override
    public int getCount() {
        return listaContasRecebidasPesquisa.size();
    }

    @Override
    public ContaRecebida getItem(int position) {
        return listaContasRecebidasPesquisa.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaContasRecebidasPesquisa.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup listViewContasRecebidas) {
        //NAO PRECISA UTILISAR O FALSE QUANDO O LAYOUT E LINEAR
        View viewCriada = criaView(listViewContasRecebidas);

        ContaRecebida contaRecebidaDevolvida = listaContasRecebidasPesquisa.get(position);
        vincula(viewCriada, contaRecebidaDevolvida);

        return viewCriada;
    }
//==================================================================================================
    private void vincula(View viewCriada, ContaRecebida contaRecebidaDevolvida) {
        //BIND
        TextView contaRecebida = viewCriada.findViewById(R.id.item_conta_recebida);
        TextView dataRecebimento = viewCriada.findViewById(R.id.item_conta_recebida_data);
        TextView horaRecebimento = viewCriada.findViewById(R.id.item_txt_conta_recebida_hora);
        TextView codigoBarras = viewCriada.findViewById(R.id.item_conta_recebida_cod_barras);
        TextView valorConta = viewCriada.findViewById(R.id.item_conta_recebida_valor);

        contaRecebida.setText(contaRecebidaDevolvida.getConta());
        dataRecebimento.setText(contaRecebidaDevolvida.getDataRecebimento());
        horaRecebimento.setText(contaRecebidaDevolvida.getHoraRecebimento());
        codigoBarras.setText(contaRecebidaDevolvida.getCodigoBarras());
        valorConta.setText(contaRecebidaDevolvida.getVlConta());
    }

    private View criaView(ViewGroup listViewContasAReceber) {
        return LayoutInflater.from(listViewContasAReceber.getContext()).inflate(R.layout.item_conta_recebida, listViewContasAReceber, false);
    }
//==================================================================================================
    public void atualizaListaContasAReceber(List<ContaRecebida> contasRecebidas) {
        this.listaContasRecebidas.clear();
        this.listaContasRecebidas.addAll(contasRecebidas);
        Collections.reverse(listaContasRecebidas);
        notifyDataSetChanged();
    }
//==================================================================================================
    public void remove(ContaAReceber contaAReceber) {
        listaContasRecebidas.remove(contaAReceber);
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
            List<ContaRecebida> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(listaContasRecebidas);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ContaRecebida item : listaContasRecebidas) {
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
            listaContasRecebidasPesquisa.clear();
            listaContasRecebidasPesquisa.addAll((List) results.values);
            notifyDataSetChanged();
        }

    };
//==================================================================================================
}
