package com.cdac.qrcodescanner;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.IOException;

public class GenerateBarcodeActivity extends AppCompatActivity {
    String text_for_barcode = "Write Something";
    EditText edit_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_barcode);

        edit_text = (EditText) findViewById(R.id.textForBarcode);

        Button genearate_qr_button = (Button) findViewById(R.id.buttonGenerateBarcode);
        genearate_qr_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    text_for_barcode = edit_text.getText().toString();
                    if(text_for_barcode.matches("")){
                        Toast.makeText(getApplicationContext(), "Please Enter Something to convert to barcode", Toast.LENGTH_SHORT).show();
                    }else{
                        System.out.println(text_for_barcode);
                        Bitmap img = generateQRCodeImage(text_for_barcode, 350, 350);
                        ImageView imgView = findViewById(R.id.imageQr);
                        imgView.setImageBitmap(img);
                    }
                } catch (WriterException e) {
                    System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
                } catch (IOException e) {
                    System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
                }
            }
        });

    }
    private Bitmap generateQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
        return bitmap;
    }


}
