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

    private Bitmap generateQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
        return bitmap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();


        Button genearate_qr_button = (Button) findViewById(R.id.buttonGenerateQr);
        genearate_qr_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Bitmap img = generateQRCodeImage("Office File Management System", 350, 350);

//                    Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
                    ImageView imgView = findViewById(R.id.imageQr);
                    imgView.setImageBitmap(img);
                } catch (WriterException e) {
                    System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
                } catch (IOException e) {
                    System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
                }
            }
        });


    }

    private void initComponents(){
        findViewById(R.id.buttonTakePicture).setOnClickListener(this);
        findViewById(R.id.buttonScanBarcode).setOnClickListener(this);
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
        }
    }
}
