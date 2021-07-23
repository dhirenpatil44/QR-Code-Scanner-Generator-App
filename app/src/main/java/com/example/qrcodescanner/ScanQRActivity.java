package com.example.qrcodescanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import eu.livotov.labs.android.camview.ScannerLiveView;
import eu.livotov.labs.android.camview.scanner.decoder.zxing.ZXDecoder;

import static android.Manifest.permission.VIBRATE;
import static android.Manifest.permission.CAMERA;

public class ScanQRActivity extends AppCompatActivity {
    private ScannerLiveView scannerLiveView;
    private TextView scanText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qractivity);

        scannerLiveView = findViewById(R.id.scannerLiveView);
        scanText = findViewById(R.id.scanText);

        if (checkPermission()){
//            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }
        else {
            requestPermission();
        }

        scannerLiveView.setScannerViewEventListener(new ScannerLiveView.ScannerViewEventListener() {
            @Override
            public void onScannerStarted(ScannerLiveView scanner) {

            }

            @Override
            public void onScannerStopped(ScannerLiveView scanner) {

            }

            @Override
            public void onScannerError(Throwable err) {

            }

            @Override
            public void onCodeScanned(String data) {
                scanText.setText(data);

            }
        });

    }

    private boolean checkPermission(){
        int camera = ContextCompat.checkSelfPermission(getApplicationContext(),CAMERA);
        int vibrate = ContextCompat.checkSelfPermission(getApplicationContext(),VIBRATE);

        return camera == PackageManager.PERMISSION_GRANTED && vibrate == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission(){
        int code = 200;
        ActivityCompat.requestPermissions(this,new String[]{CAMERA,VIBRATE},code);
    }

    @Override
    protected void onPause() {
        scannerLiveView.startScanner();
        super.onPause();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        ZXDecoder decoder = new ZXDecoder();
        decoder.setScanAreaPercent(0.8);
        scannerLiveView.setDecoder(decoder);
        scannerLiveView.startScanner();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0){
            boolean cameraAcc = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            boolean vibrateAcc = grantResults[1] == PackageManager.PERMISSION_GRANTED;
            
            if (cameraAcc && vibrateAcc){
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}