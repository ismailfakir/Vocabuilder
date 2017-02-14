package net.cloudcentrik.wordplus;

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

    public static String createPdfWordList(ArrayList<DictionaryWord> words, String fileName) {
        // TODO Auto-generated method stub
        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
        String fPath = "";

        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/wordplus";

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
            Paragraph header = new Paragraph("List of Words in wordplus");
            Font bfBold20 = new Font(Font.FontFamily.COURIER, 20, Font.BOLD, new BaseColor(0, 0, 0));
            header.setAlignment(Paragraph.ALIGN_CENTER);
            header.setFont(bfBold20);
            header.setSpacingAfter(2.0f);

            //add paragraph to document
            document.add(header);

            Paragraph date = new Paragraph("Created on : " + getDateTime());
            Font paraFont2 = new Font(Font.FontFamily.COURIER, 14.0f, 0, CMYKColor.GREEN);
            date.setAlignment(Paragraph.ALIGN_CENTER);
            date.setFont(paraFont2);
            date.setSpacingAfter(2.0f);

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

    private static PdfPTable createWordTable(ArrayList<DictionaryWord> words) {

        //specify column widths
        float[] columnWidths = {2f, 2f, 5f,5f};
        //create PDF table with the given widths
        PdfPTable table = new PdfPTable(columnWidths);
        // set table width a percentage of the page width
        table.setWidthPercentage(96f);


        addTableHeader(table);

        if (words.size() > 0) {
            for (int i = 0; i < words.size(); i++) {
                addTableRow(table, words.get(i));
            }
        } else {
            DictionaryWord w = new DictionaryWord("No Data", "No Data", "No Data", "No Data","no data", "no data");
            addTableRow(table, w);
        }

        return table;

    }

    //adding table header

    private static void addTableHeader(PdfPTable table) {

        Font bfBold14 = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD, new BaseColor(0, 0, 0));

        PdfPCell cellSwedish = new PdfPCell(new Phrase("Swedish", bfBold14));
        PdfPCell cellEnglish = new PdfPCell(new Phrase("English", bfBold14));
        PdfPCell cellSwedishExample = new PdfPCell(new Phrase("Swedish Example", bfBold14));
        PdfPCell cellEnglishExample = new PdfPCell(new Phrase("English Example", bfBold14));

        cellSwedish.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellEnglish.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellSwedishExample.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellEnglishExample.setHorizontalAlignment(Element.ALIGN_LEFT);

        table.addCell(cellSwedish);
        table.addCell(cellEnglish);
        table.addCell(cellSwedishExample);
        table.addCell(cellEnglishExample);

    }

    private static void addTableRow(PdfPTable table, DictionaryWord w) {
        Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 12);
        PdfPCell cellSwedish = new PdfPCell(new Phrase(w.getSwedish(), bf12));
        PdfPCell cellEnglish = new PdfPCell(new Phrase(w.getEnglish(), bf12));
        PdfPCell cellSwedishExample = new PdfPCell(new Phrase(w.getSwedishExample(), bf12));
        PdfPCell cellEnglishExample = new PdfPCell(new Phrase(w.getEnglishExample(), bf12));

        table.addCell(cellSwedish);
        table.addCell(cellEnglish);
        table.addCell(cellSwedishExample);
        table.addCell(cellEnglishExample);

    }

    private static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date).toString();
    }
}
