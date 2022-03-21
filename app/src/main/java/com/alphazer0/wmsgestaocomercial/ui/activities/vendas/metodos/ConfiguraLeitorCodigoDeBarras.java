package com.alphazer0.wmsgestaocomercial.ui.activities.vendas.metodos;

import android.app.Activity;

import com.google.zxing.integration.android.IntentIntegrator;

public class ConfiguraLeitorCodigoDeBarras {
    public void configuraLeitor(Activity activity){
        IntentIntegrator integrator = new IntentIntegrator(activity);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Ativar/Desativar Lanterna: Volume+/-");
        integrator.setCameraId(0);
        integrator.setTorchEnabled(true);
        integrator.setBeepEnabled(true);
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
    }
}
