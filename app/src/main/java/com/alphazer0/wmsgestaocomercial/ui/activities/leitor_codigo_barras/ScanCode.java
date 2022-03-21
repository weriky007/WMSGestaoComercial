package com.alphazer0.wmsgestaocomercial.ui.activities.leitor_codigo_barras;

import android.app.Activity;

import com.google.zxing.integration.android.IntentIntegrator;

public class ScanCode {
    public void scanCode(Activity activity){
        IntentIntegrator integrator = new IntentIntegrator(activity);
        integrator.setCaptureActivity(CaptureCodeActivity.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(integrator.ALL_CODE_TYPES);
        integrator.setPrompt("Ligar|Desligar Lanterna:Vol+|Vol-");
        integrator.initiateScan();
    }
}
