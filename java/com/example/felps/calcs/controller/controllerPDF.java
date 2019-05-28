package com.example.felps.calcs.controller;


import android.content.Context;
import android.os.Environment;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Random;

public class controllerPDF {
    private static Context c;
    private File pdfFile;
    private OutputStream output;
    private Document document;
    private PdfWriter pdfWriter;
    private Paragraph paragraph;
    private Font fTitle = new Font(Font.FontFamily.TIMES_ROMAN,20, Font.BOLD);
    private Font fSubTitle = new Font(Font.FontFamily.TIMES_ROMAN,18, Font.BOLD);
    private Font fText = new Font(Font.FontFamily.TIMES_ROMAN,12, Font.BOLD);
    private Font fHighText = new Font(Font.FontFamily.TIMES_ROMAN,15, Font.BOLD, BaseColor.RED);
    public controllerPDF(Context c){
        this.c=c;
    }

    public controllerPDF getTemplatePDF(){
        return controllerPDF.this;
    }

    public void createPdf() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
        }
        Random rand = new Random();
        int versao = rand.nextInt(9999)+1;
        pdfFile = new File(docsFolder.getAbsolutePath(), "laudoV" + versao + ".pdf");
        output = new FileOutputStream(pdfFile);

        openDocument();
    }

    public void openDocument(){
        try {
            document = new Document();
            pdfWriter.getInstance(document, output);
            document.open();

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public void closeDocument (){
        document.close();
    }

    public void addImages(Image i){
        try {
            i.setSpacingAfter(5);
            i.setSpacingBefore(5);
            i.setAlignment(Image.MIDDLE);
            document.add(i);

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public void addTitles(String title, String subTitle, String date){
        try {
            paragraph = new Paragraph();
            addChildP(new Paragraph(title,fTitle));
            addChildP(new Paragraph(subTitle, fSubTitle));
            addChildP(new Paragraph("Gerado "+date, fHighText));
            paragraph.setSpacingAfter(30);
            document.add(paragraph);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private void addChildP(Paragraph childParagraph){
        childParagraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(childParagraph);
    }

    public void addParagraph(String texto){
        try {
            paragraph= new Paragraph(texto, fText);
            paragraph.setSpacingAfter(3);
            paragraph.setSpacingBefore(5);
            document.add(paragraph);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

}


