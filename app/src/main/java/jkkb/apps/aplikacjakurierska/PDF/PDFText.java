package jkkb.apps.aplikacjakurierska.PDF;

import android.graphics.Paint;
import android.graphics.Point;

public class PDFText extends PDFElement{
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public PDFText(String text, Paint paint, Point position) {
        super(position,paint);
        this.text = text;
    }
}
