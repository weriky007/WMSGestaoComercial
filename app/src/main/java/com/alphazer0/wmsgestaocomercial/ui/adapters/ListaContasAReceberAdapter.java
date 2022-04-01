package com.alphazer0.wmsgestaocomercial.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alphazer0.wmsgestaocomercial.R;
import com.alphazer0.wmsgestaocomercial.model.ContaAPagar;
import com.alphazer0.wmsgestaocomercial.model.ContaAReceber;

import java.math.BigDecimal;
import java.util.List;

public class ListaContasAReceberAdapter extends BaseAdapter {
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
    private void vincula(View viewCriada, ContaAPagar contaAPagarDevolvida) {
        //BIND
        TextView produto = viewCriada.findViewById(R.id.item_produto_vendas_produto);
        TextView marca = viewCriada.findViewById(R.id.item_produto_vendas_marca);
        TextView quantidadeValorUnitario = viewCriada.findViewById(R.id.item_produto_vendas_quantidade_valor_unitario);

        produto.setText(produtoDevolvido.getProduto());
        marca.setText(produtoDevolvido.getMarca());

        String q = produtoDevolvido.getQuantidade();
        String v = produtoDevolvido.getPrecoVenda();

        BigDecimal qtd = new BigDecimal(q);
        BigDecimal vd = new BigDecimal(v);
        BigDecimal result = qtd.multiply(vd);

        String newPv = result.toString();
        quantidadeValorUnitario.setText("Quantidade:" + qtd + " | " + "Total: R$" + newPv);
    }

    private View criaView(ViewGroup listViewProdutos) {
        return LayoutInflater.from(listViewProdutos.getContext()).inflate(R.layout.item_produto_vendas, listViewProdutos, false);
    }
//==================================================================================================
}
