package com.alphazer0.wmsgestaocomercial.ui.activities.financas;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.alphazer0.wmsgestaocomercial.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FluxoCaixaActivity extends AppCompatActivity {

    private FloatingActionButton fabAddItemFluxo;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedIntanceState){
        super.onCreate(savedIntanceState);
        setContentView(R.layout.activity_lista_fluxo_caixa);
        setTitle("Fluxo de Caixa");

        configuraAddNovoItemFluxo();
        recyclerView = findViewById(R.id.recyclerview_lista_fluxo_caixa);
    }

    private void configuraAddNovoItemFluxo() {
        fabAddItemFluxo = findViewById(R.id.fab_novo_item_fluxo);
        fabAddItemFluxo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
