package com.alphazer0.wmsgestaocomercial.ui.activities.financas;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.alphazer0.wmsgestaocomercial.R;
import com.alphazer0.wmsgestaocomercial.database.MovimentacoesCaixaDatabase;
import com.alphazer0.wmsgestaocomercial.database.roomDAO.RoomMovimentacaoCaixaDAO;
import com.alphazer0.wmsgestaocomercial.model.MovimentacaoCaixa;
import com.alphazer0.wmsgestaocomercial.ui.adapters.ListaFluxoCaixaAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ListaFluxoCaixaActivity extends AppCompatActivity {

    public static final String FLUXO_DE_CAIXA = "Fluxo de Caixa";
    private FloatingActionButton fabAddItemFluxo;
    private RecyclerView recyclerView;
    private RoomMovimentacaoCaixaDAO movimentacaoCaixaDAO;
    private ListaFluxoCaixaAdapter fluxoCaixaAdapter;
    private List<MovimentacaoCaixa> listaMovimentacoes = new ArrayList<>();
//==================================================================================================
    @Override
    protected void onCreate(Bundle savedIntanceState) {
        super.onCreate(savedIntanceState);
        setContentView(R.layout.activity_lista_fluxo_caixa);
        setTitle(
                FLUXO_DE_CAIXA);

        configuraAdapter();
        configuraLista();
        configuraAddNovoItemFluxo();
    }

    @Override
    protected void onResume(){
        super.onResume();
        fluxoCaixaAdapter.atualiza(movimentacaoCaixaDAO.todasMovimentacoes());
    }
//==================================================================================================
    private void configuraAdapter() {
        fluxoCaixaAdapter = new ListaFluxoCaixaAdapter(listaMovimentacoes);
        movimentacaoCaixaDAO = MovimentacoesCaixaDatabase.getInstance(this).getMovimentacaoCaixaDAO();
    }

    private void configuraLista() {
        recyclerView = findViewById(R.id.recyclerview_lista_fluxo_caixa);
        recyclerView.setAdapter(fluxoCaixaAdapter);
    }

    private void configuraAddNovoItemFluxo() {
        fabAddItemFluxo = findViewById(R.id.fab_novo_item_fluxo);
        fabAddItemFluxo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
//==================================================================================================
}
