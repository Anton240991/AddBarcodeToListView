package com.example.dell.addbarcodetolistview;

import android.content.Context;
import android.view.View;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Kfear Oshri on 17/03/2018.
 */

class BarcodeScanner {

    String result = "0000";
    private ZXingScannerView scannerView;

    public String scanBarcode(Context context) {
        scannerView = new ZXingScannerView(context);
        scannerView.setResultHandler(new BarcodeScanner.ZXingScanneResultHandler());
        scannerView.startCamera();
        return result;
    }

    class ZXingScanneResultHandler implements ZXingScannerView.ResultHandler {
        @Override
        public void handleResult(Result barCodeResult) {
            result = barCodeResult.getText();
            scannerView.stopCamera();
        }
    }
}
