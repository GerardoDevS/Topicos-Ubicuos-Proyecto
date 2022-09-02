package com.lics.proyectou2;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QR {

    public static Bitmap createQR(String content){
        Bitmap bitmap = null;
        try{
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.encodeBitmap(content, BarcodeFormat.QR_CODE, 500, 500);
        }catch (Exception e){
            e.printStackTrace();
        }

        return bitmap;
    }

}
