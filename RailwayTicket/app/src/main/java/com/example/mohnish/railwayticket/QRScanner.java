package com.example.mohnish.railwayticket;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.mohnish.railwayticket.SupportFiles.EncryptionHelper;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.HashMap;

/**
 * Created by Administrator on 11/02/2018.
 */

public class QRScanner extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IntentIntegrator integrator = new IntentIntegrator(QRScanner.this);
        integrator.setPrompt("Scan a QR Code");
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setOrientationLocked(true);
        integrator.setBeepEnabled(true);
        integrator.setCaptureActivity(CaptureActivityPortrait.class);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

            if (result.getContents() == null) {
                Intent intent = new Intent();
                intent.putExtra("hashmap", "0");
                setResult(3, intent);
                finish();//finishing activity


            } else {
                long[] pattern = {0, 50, 50, 50, 50};
                v.vibrate(pattern, -1);


                String recd = result.getContents();
                Log.d("TAG", recd);
                try {

                    String decrypted = EncryptionHelper.decipher(recd.substring(recd.length() - 12, recd.length()), recd.substring(0, recd.length() - 12));
                    String[] qrData = decrypted.split(";");
                    HashMap<String, String> hm = new HashMap<String, String>();

                    hm.put("from", qrData[0]);
                    hm.put("to", qrData[1]);
                    hm.put("class", qrData[2]);
                    hm.put("returnStatus", qrData[3]);
                    hm.put("counterEmployee", qrData[4]);
                    hm.put("dateTime", qrData[5]);
                    hm.put("expiry", qrData[6]);
                    hm.put("flag", qrData[7]);
                    hm.put("passengerId", qrData[8]);
                    Intent intent = new Intent();
                    intent.putExtra("hashmap", hm);
                    setResult(2, intent);
                    finish();//finishing activity
//                    StoreLocally(hm);

                } catch (Exception e) {
                    Toast.makeText(QRScanner.this, String.valueOf(e), Toast.LENGTH_LONG).show();
                }


                // store in local and wait for sync with firebase !!


            }
        }
    }
}
