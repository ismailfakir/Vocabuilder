package net.cloudcentrik.vocabuilder;

import android.os.Environment;
import android.util.Log;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ismail on 2016-02-19.
 */
public class CreatePDF {

   /* public static void createPDFFile(){
        Document document = new Document();
        File file = Environment.getExternalStorageDirectory().getPath() + "/Hello.pdf"
        PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();
        Paragraph p = new Paragraph("Hello PDF");
        document.add(p);
        document.close();
    }*/

    public static String createPdfWordList(ArrayList<Word> words, String fileName) {
        // TODO Auto-generated method stub
        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
        String fPath = "";

        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/vocabuilder";

            File dir = new File(path);
            if (!dir.exists())
                dir.mkdirs();

            Log.d("PDFCreator", "PDF Path: " + path);


            File file = new File(dir, fileName + ".pdf");
            FileOutputStream fOut = new FileOutputStream(file);

            PdfWriter.getInstance(document, fOut);

            //open the document
            document.open();


            //document header
            Paragraph header = new Paragraph("List of Words in vocabuilder");
            Font bfBold20 = new Font(Font.FontFamily.COURIER, 20, Font.BOLD, new BaseColor(0, 0, 0));
            header.setAlignment(Paragraph.ALIGN_CENTER);
            header.setFont(bfBold20);

            //add paragraph to document
            document.add(header);

            Paragraph date = new Paragraph("Created on : " + getDateTime());
            Font paraFont2 = new Font(Font.FontFamily.COURIER, 14.0f, 0, CMYKColor.GREEN);
            date.setAlignment(Paragraph.ALIGN_CENTER);
            date.setFont(paraFont2);

            document.add(date);

            document.add(createWordTable(words));

            fPath = file.getAbsolutePath();

            return file.getAbsolutePath();


        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        } finally {
            document.close();
            return fPath;
        }

    }

    private static PdfPTable createWordTable(ArrayList<Word> words) {

        //specify column widths
        float[] columnWidths = {2f, 2f, 5f};
        //create PDF table with the given widths
        PdfPTable table = new PdfPTable(columnWidths);
        // set table width a percentage of the page width
        table.setWidthPercentage(90f);


        addTableHeader(table);

        if (words.size() > 0) {
            for (int i = 0; i < words.size(); i++) {
                addTableRow(table, words.get(i));
            }
        } else {
            Word w = new Word("No Data", "No Data", "No Data", "No Data", "No Data", "No Data");
            addTableRow(table, w);
        }

        return table;

    }

    //adding table header

    private static void addTableHeader(PdfPTable table) {

        Font bfBold14 = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD, new BaseColor(0, 0, 0));

        PdfPCell cellSwedish = new PdfPCell(new Phrase("Swedish", bfBold14));
        PdfPCell cellEnglish = new PdfPCell(new Phrase("English", bfBold14));
        PdfPCell cellExample = new PdfPCell(new Phrase("Example", bfBold14));

        cellSwedish.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellEnglish.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellExample.setHorizontalAlignment(Element.ALIGN_LEFT);

        table.addCell(cellSwedish);
        table.addCell(cellEnglish);
        table.addCell(cellExample);

    }

    private static void addTableRow(PdfPTable table, Word w) {
        Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 12);
        PdfPCell cellSwedish = new PdfPCell(new Phrase(w.getSwedish(), bf12));
        PdfPCell cellEnglish = new PdfPCell(new Phrase(w.getEnglish(), bf12));
        PdfPCell cellExample = new PdfPCell(new Phrase(w.getPartOfSpeach(), bf12));

        table.addCell(cellSwedish);
        table.addCell(cellEnglish);
        table.addCell(cellExample);

    }

    private static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date).toString();
    }
}
