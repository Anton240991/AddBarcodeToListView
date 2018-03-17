package com.example.dell.addbarcodetolistview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity {

    private ZXingScannerView scannerView;
    TextView txtBarcode;
    EditText editBarcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void scanBarcode(View view) {
        scannerView = new ZXingScannerView(this);
        scannerView.setResultHandler(new ZXingScanneResultHandler());

        setContentView(scannerView);
        scannerView.startCamera();
    }

    @Override
    public void onPause() {
        scannerView.stopCamera();
        super.onPause();
    }

    class ZXingScanneResultHandler implements ZXingScannerView.ResultHandler {
        @Override
        public void handleResult(Result result) {
            setContentView(R.layout.activity_main);

            String resultCode = result.getText().toString();
            txtBarcode = findViewById(R.id.txtBarcode);
            editBarcode = findViewById(R.id.editBarcode);

            editBarcode.setText(resultCode);
            txtBarcode.setText("The barcode number:");
            Toast.makeText(MainActivity.this, resultCode, Toast.LENGTH_LONG).show();
            scannerView.stopCamera();
        }
    }
}