package jkkb.apps.aplikacjakurierska.PDF;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Point;

public class PDFBitmap extends PDFElement {
    private Bitmap image;

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public PDFBitmap(Bitmap image, Paint paint, Point position) {
        super(position,paint);
        this.image = image;
    }
}
