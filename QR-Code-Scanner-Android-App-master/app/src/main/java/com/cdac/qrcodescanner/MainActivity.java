package com.cdac.qrcodescanner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();

    }

    private void initComponents(){
        findViewById(R.id.buttonTakePicture).setOnClickListener(this);
        findViewById(R.id.buttonScanBarcode).setOnClickListener(this);
        findViewById(R.id.buttonGoToBarcode).setOnClickListener(this);
        findViewById(R.id.buttonGoToProfilePage).setOnClickListener(this);
        findViewById(R.id.buttonTrackFile).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonScanBarcode:
                startActivity(new Intent(this,ScannerBarcodeActivity.class));
                break;
            case R.id.buttonTakePicture:
                startActivity(new Intent(this,PictureBarcodeActivity.class));
                break;
            case R.id.buttonGoToBarcode:
                startActivity(new Intent(this, GenerateBarcodeActivity.class));
                break;
            case R.id.buttonGoToProfilePage:
                startActivity(new Intent(this, ProfileActivity.class));
                break;
            case R.id.buttonTrackFile:
                startActivity(new Intent(this, TrackfileActivity.class));
                break;
        }
    }

    // TODO: AsyncTask to integrate with DBS
}
