package jkkb.apps.aplikacjakurierska;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRGenerator {
    public Bitmap generate(String data, ImageView bitmap) throws WriterException {
        MultiFormatWriter writer = new MultiFormatWriter();
        //Zapisz podane dane, np. identyfikator przesyłki do kodu QR
        BitMatrix matrix = writer.encode(data, BarcodeFormat.QR_CODE, 400, 400);
        BarcodeEncoder encoder = new BarcodeEncoder();
        Bitmap qr_img = encoder.createBitmap(matrix);
        bitmap.setImageBitmap(qr_img);
        return qr_img;
    }
}
