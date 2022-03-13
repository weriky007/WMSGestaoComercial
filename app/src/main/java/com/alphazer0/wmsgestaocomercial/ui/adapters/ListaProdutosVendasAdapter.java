package com.alphazer0.wmsgestaocomercial.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.alphazer0.wmsgestaocomercial.R;
import com.alphazer0.wmsgestaocomercial.model.Produto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ListaProdutosVendasAdapter extends BaseAdapter implements Filterable {
//==================================================================================================
    //CRIANDO UMA COPIA DA LISTA PARA O ADAPTER
    private  List<Produto> produtoPesquisa;
    private  List<Produto> produtos;
    public ListaProdutosVendasAdapter(List<Produto> produto) {
        //his.context = context;
        this.produtoPesquisa = produto;
        this.produtos = produto;
    }
//==================================================================================================
      //INDICA A QUANTIDADE DE ELEMENTOS QUE O ADAPTER TERA
     @Override
     public int getCount() { return produtoPesquisa.size();
     }

    //INDICA O ELEMENTO QUE VC QUER COM BASE NA POSICAO
    @Override
    public Produto getItem(int position) {
        return produtoPesquisa.get(position);
    }

    //ELEMENTO PELO ID, CASO NAO HOUVER ID NA LISTA DEIXE ZERO
    @Override
    public long getItemId(int position) {
        return produtoPesquisa.get(position).getId();
    }

    //REPRESENTA AS CONFIGURACOES VIEW QUE SERA APRESENTADA
    @Override
    public View getView(int position, View convertView, ViewGroup listViewProdutos) {
        //NAO PRECISA UTILISAR O FALSE QUANDO O LAYOUT E LINEAR
        View viewCriada = criaView(listViewProdutos);

        Produto produtoDevolvido = produtoPesquisa.get(position);
        vincula(viewCriada, produtoDevolvido);

        return viewCriada;
    }
//==================================================================================================
    private void vincula(View viewCriada, Produto produtoDevolvido) {
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
        quantidadeValorUnitario.setText("Quantidade:"+qtd+" | "+"Total: R$"+newPv);
    }

    private View criaView(ViewGroup listViewProdutos) {
        return LayoutInflater.from(listViewProdutos.getContext()).inflate(R.layout.item_produto_vendas, listViewProdutos,false);
    }
//==================================================================================================
    public void atualizaListaProdutos(List<Produto> produto, String categoria){
        this.produtos.clear();
        if(categoria.equals("Todos") || categoria.equals(null)) {
          this.produtos.addAll(produto);
          notifyDataSetChanged();
        }else {
            for (Produto filtroGrupo : produto) {
                if (filtroGrupo.getCategoria().equals(categoria)) {
                    this.produtos.add(filtroGrupo);
                    notifyDataSetChanged();
                }
            }
        }
    }

    public void pegaTodosProdutos(List<Produto> produto){
        this.produtos.clear();
        this.produtos.addAll(produto);
    }
//==================================================================================================
    public void remove(Produto produto) {
        produtos.remove(produto);
        notifyDataSetChanged();
    }
//==================================================================================================
    //INICIANDO CONFIGURACAO DO FILTRO
    @Override
    public Filter getFilter() {
    return filtroProduto;
    }

    //CONFIGURACAO DO FILTRO
    private Filter filtroProduto = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Produto> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(produtos);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Produto item : produtos) {
                    if (item.getProduto().toLowerCase().contains(filterPattern)) {
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
            produtoPesquisa.clear();
            produtoPesquisa.addAll((List) results.values);
            notifyDataSetChanged();
        }

    };
//==================================================================================================
}
