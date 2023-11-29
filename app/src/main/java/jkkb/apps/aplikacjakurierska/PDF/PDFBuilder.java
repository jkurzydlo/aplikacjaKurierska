package jkkb.apps.aplikacjakurierska.PDF;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.util.Log;
import android.util.Pair;

import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import jkkb.apps.aplikacjakurierska.R;

//Klasa generująca potwierdzenie nadania w formacie PDF wykokrzystująca wzorzec Builder
public class PDFBuilder {

    private ArrayList<PDFBitmap> images = new ArrayList<>();
    private ArrayList<PDFText> text = new ArrayList<>();
    private double width=0,height=0;
    public PDFBuilder addImage(PDFBitmap image){
        this.images.add(image);
        return this;
    }

    public PDFBuilder addText(PDFText text){
        this.text.add(text);
        return this;
    }
    //Dodaje wymiary dokumentu
    public PDFBuilder addDimensions(double width, double height){
        this.width = width;
        this.height = height;
        return this;
    }

    public PDFBuilder build(){
        PDFBuilder temp = new PDFBuilder();
        temp.height = this.height;
        temp.width = this.width;
        temp.images = this.images;
        temp.text = this.text;
        return temp;
    }

    public void generate(Context context, String filename){
        PdfDocument doc = new PdfDocument();
        PdfDocument.PageInfo info = new PdfDocument.PageInfo.Builder((int)width,(int)height,1).create();
        PdfDocument.Page page = doc.startPage(info);
        Canvas canvas = page.getCanvas();

        //Dodanie obrazów
        for(PDFBitmap img : images)
            canvas.drawBitmap(img.getImage(),img.getPosition().x, img.getPosition().y,img.getPaint());
        for(PDFText txt : text)
            canvas.drawText(txt.getText(), txt.getPosition().x, txt.getPosition().y,txt.getPaint());
        doc.finishPage(page);

        //Zapis dokumentu do pliku
        File file = new File(Environment.getExternalStorageDirectory().toString(),filename+".pdf");
        try{
            doc.writeTo(Files.newOutputStream(file.toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        doc.close();
        Log.println(Log.INFO,"", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath());
    }

}
