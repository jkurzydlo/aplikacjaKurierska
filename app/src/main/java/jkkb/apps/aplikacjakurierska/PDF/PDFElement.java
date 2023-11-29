package jkkb.apps.aplikacjakurierska.PDF;

import android.graphics.Paint;
import android.graphics.Point;

//Klasa pomocnicza do reprezentująca element dokumentu PDF
public abstract class PDFElement {
    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    //Kolor tekstu
     private Point position;
     private Paint paint;


    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

     public PDFElement(Point position, Paint paint){
         this.position = position;
         this.paint = paint;
     }
}
