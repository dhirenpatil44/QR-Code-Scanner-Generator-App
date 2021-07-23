package com.example.qrcodescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Point;

import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class GenerateQRActivity extends AppCompatActivity {
    private TextView textView;
    private ImageView QRImage;
    private TextInputEditText editTextInput;
    private Button generateQRCode;
    private QRGEncoder qrgEncoder;
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qractivity);

        textView = findViewById(R.id.textView);
        QRImage = findViewById(R.id.QRImage);
        editTextInput = findViewById(R.id.editTextInput);
        generateQRCode = findViewById(R.id.generateQRCode);



        generateQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String data = editTextInput.getText().toString();

                if (data.isEmpty()) {
                    Toast.makeText(GenerateQRActivity.this, "Please enter the text to generate QR code", Toast.LENGTH_SHORT).show();
                } else {
                    WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                    Display display = manager.getDefaultDisplay();
                    Point point = new Point();
                    display.getSize(point);
                    int width = point.x;
                    int height = point.y;
                    int dimen = Math.min(width, height);
                    dimen = dimen * 3 / 4;

                    qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.TEXT, dimen);

                    try {
                        bitmap = qrgEncoder.encodeAsBitmap();
                        textView.setVisibility(View.GONE);
                        QRImage.setImageBitmap(bitmap);

                    } catch (WriterException e) {
                        e.printStackTrace();

                    }
                }
            }
        });

    }
}