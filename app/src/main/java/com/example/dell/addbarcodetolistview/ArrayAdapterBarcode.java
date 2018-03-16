package com.example.dell.addbarcodetolistview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

/**
 * Created by dell on 3/16/2018.
 */

public class ArrayAdapterBarcode extends ArrayAdapter<Barcode> {

    private Activity activity;
    private List<Barcode> barcodes;

    public ArrayAdapterBarcode(Activity activity, List<Barcode> barcodes) {
        super(activity, R.layout.activity_main, barcodes);
        this.activity = activity;
        this.barcodes = barcodes;


    }

    static class ViewContainer {
        EditText txtNewBarcode;
        Button btnAddBarcode;
    }

    @SuppressLint("WrongViewCast")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewContainer viewContainer;
        View activityMain = convertView;
        if (activityMain == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            activityMain = inflater.inflate(R.layout.activity_main, null);
            viewContainer = new ViewContainer();
            viewContainer.txtNewBarcode = activityMain.findViewById(R.id.txtNewBarcode);
            viewContainer.btnAddBarcode = activityMain.findViewById(R.id.btnAddBarcode);
            activityMain.setTag(viewContainer);
        } else {
            viewContainer = (ViewContainer) activityMain.getTag();
        }

        Barcode barcode = barcodes.get(position);
        viewContainer.txtNewBarcode.setText(barcode.toString());

        return activityMain;
    }
}
