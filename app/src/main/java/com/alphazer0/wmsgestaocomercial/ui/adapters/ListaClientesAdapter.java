package com.alphazer0.wmsgestaocomercial.ui.adapters;

import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.CHAVE_CLIENTE;
import static com.alphazer0.wmsgestaocomercial.ui.activities.ConstantesActivities.CHAVE_PRODUTO_OUTRO;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.alphazer0.wmsgestaocomercial.R;
import com.alphazer0.wmsgestaocomercial.model.Cliente;
import com.alphazer0.wmsgestaocomercial.model.Venda;
import com.alphazer0.wmsgestaocomercial.ui.activities.clientes.CadastroClienteActivity;
import com.alphazer0.wmsgestaocomercial.ui.activities.clientes.InformacoesCliente;
import com.alphazer0.wmsgestaocomercial.ui.activities.estoque.CadastroProdutoActivity;

import java.util.ArrayList;
import java.util.List;

public class ListaClientesAdapter extends RecyclerView.Adapter implements Filterable {
//==================================================================================================
    //CRIANDO UMA COPIA DA LISTA PARA O ADAPTER
    private List<Cliente> clientePesquisa;
    private List<Cliente> clientes;
    private Context context;

    //CONSTRUCTOR
    public ListaClientesAdapter(Context context, List<Cliente> cliente) {
        this.context = context;
        this.clientePesquisa = cliente;
        this.clientes = cliente;
    }
//==================================================================================================
    //RECYCLER VIEW
    class clienteViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        public clienteViewHolder(@NonNull View itemView) {
            super(itemView);
            CardView cardView = itemView.findViewById(R.id.cardViewCliente);
            cardView.setOnCreateContextMenuListener(this);
        }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.add(this.getAdapterPosition(),0,0,"Remover Cliente");
    }
}

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context).inflate(R.layout.item_cliente, parent, false);
        return new ListaClientesAdapter.clienteViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CardView cardView = holder.itemView.findViewById(R.id.cardViewCliente);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InformacoesCliente.class);
                intent.putExtra(CHAVE_CLIENTE,clientes.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });

        Cliente cliente = clientes.get(position);
        TextView nome = holder.itemView.findViewById(R.id.item_cliente_nome);
        TextView telefone = holder.itemView.findViewById(R.id.item_cliente_telefone);
        TextView endereco = holder.itemView.findViewById(R.id.item_cliente_endereco);
        TextView divida = holder.itemView.findViewById(R.id.item_cliente_divida);

        nome.setText(cliente.getNomeCompleto());
        telefone.setText("Telefone: "+cliente.getCelular1());
        endereco.setText("R: " + cliente.getRua()+" | "+"Número: "+cliente.getNumero()+"\nBairro: "+cliente.getBairro());

        try {
            if (!cliente.getDivida().equals(null) || !cliente.getDivida().equals("0")) {
                divida.setText("Pendente: R$" + cliente.getDivida() + "\n" + "Data vencimento: " + cliente.getDataVencimento());
            }
        }catch (NullPointerException n){
            n.printStackTrace();
        }

        if (cliente.getDivida().equals(null) || cliente.getDivida().equals("0")){
            divida.setText("");
        }

    }

    @Override
    public int getItemCount() {
        return clientes.size();
    }
//==================================================================================================
    public void atualiza(List<Cliente> clientes) {
        this.clientes.clear();
        this.clientes.addAll(clientes);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        clientes.remove(position);
        notifyDataSetChanged();
    }
//==================================================================================================
    //INICIANDO CONFIGURACAO DO FILTRO
    @Override
    public Filter getFilter() {
        return filtroCliente;
    }

    //CONFIGURACAO DO FILTRO
    private Filter filtroCliente = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Cliente> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(clientes);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Cliente item : clientes) {
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
            clientePesquisa.clear();
            clientePesquisa.addAll((List) results.values);
            notifyDataSetChanged();
        }

    };
//==================================================================================================
}
