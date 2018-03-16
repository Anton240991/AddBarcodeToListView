package com.example.dell.addbarcodetolistview;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class MainActivity extends AppCompatActivity {


    private SurfaceView surfaceView;
    private BarcodeDetector detector;
    private CameraSource cmSource;
    TextView txtNewBarcode;
    ListView lstBarcode;
    ArrayList<String> barcode;
    List<Barcode> barcodes;
    ArrayAdapterBarcode adapterBarcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        barcode = new ArrayList<>();

        surfaceView = findViewById(R.id.surfView);
        txtNewBarcode = findViewById(R.id.txtNewBarcode);
        lstBarcode = findViewById(R.id.lstBarcode);
        barcodes = new ArrayList<>();

        final ListView lstBarcode = findViewById(R.id.lstBarcode);
        adapterBarcode = new ArrayAdapterBarcode(this, barcodes);

        lstBarcode.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Barcode barcode = barcodes.get(i);
                Toast.makeText(MainActivity.this, "barcode: " + barcode.getClass(), Toast.LENGTH_SHORT).show();
                adapterBarcode.notifyDataSetChanged();


            }
        });

        detector = new BarcodeDetector
                .Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cmSource = new CameraSource
                .Builder(this, detector)
                .setRequestedPreviewSize(640, 480)
                .setAutoFocusEnabled(true)
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 10);
                    return;
                }
                try {
                    cmSource.start(holder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cmSource.stop();
            }
        });

        detector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                if (detections != null) {
                    SparseArray<Barcode> codes = detections.getDetectedItems();
                    if (codes.size() != 0) {
                        Barcode bc = codes.valueAt(0);
                        final String text = bc.displayValue;
                        surfaceView.post(new Runnable() {
                            @Override
                            public void run() {
                                new AlertDialog.Builder(MainActivity.this)
                                        .setTitle(text).setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        btnAddBarcode(this);
                                    }
                                })
                                        .show();
                                barcode.add(text);
                                lstBarcode.setAdapter(adapterBarcode);

                            }
                        });
                    }
                }
            }
        });

    }


    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 10 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            try {
                cmSource.start(surfaceView.getHolder());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void btnAddBarcode(DialogInterface.OnClickListener view) {
        TextView txtNewBarcode = findViewById(R.id.txtNewBarcode);
    }

}
