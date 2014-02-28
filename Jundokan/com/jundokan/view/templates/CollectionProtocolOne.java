package jundokan.view.templates;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

public class CollectionProtocolOne {
    public CollectionProtocolOne(HashMap<String, String> dataProtocol) {
        try {
            initialization();
            collectionPDFDocument(dataProtocol);
            document.close();
            pdfWriter.close();
            bytes.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Byte [] getByteArray(HashMap<String, String> dataProtocol) {
        //collectionPDFDocument(dataProtocol);
        return null;
    }
    public CollectionProtocolOne(ArrayList<HashMap<String, String>> dataProtocol) {
        try {
            initialization();
            for (HashMap<String, String> protocol: dataProtocol){
                collectionPDFDocument(protocol);
                this.document.newPage();
            }
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void initialization() throws DocumentException, IOException {
        this.document = new Document(PageSize.A4, 10, 10, 10, 10);
        bytes = new ByteArrayOutputStream();
        this.pdfWriter = PdfWriter.getInstance(document, bytes);
        this.baseFont = BaseFont.createFont("c:/windows/fonts/times.ttf","cp1251",BaseFont.EMBEDDED);
        this.document.open();
    }
    private void collectionPDFDocument(final HashMap<String, String> dataProtocol) throws DocumentException, MalformedURLException, IOException {
    	Image img = Image.getInstance(System.getProperty("user.dir") +"/resource/gui/logo_1.jpg");
		img.scaleAbsolute(67, 80);
		img.setAbsolutePosition(40,740);
		img.setAlignment(Image.LEFT | Image.TEXTWRAP);
		document.add(img);
       /* Image img = Image.getInstance("C:/Users/Soul/workspace/PrintPage/logo_1.jpg");
        img.scaleAbsolute(67, 80);
        img.setAbsolutePosition(40,740);
        img.setAlignment(Image.LEFT | Image.TEXTWRAP);
        document.add(img);*/
        Paragraph p=new Paragraph();
        FontFactory.registerDirectories();
        Font font = new Font(baseFont,12);
        font.setFamily(FontFactory.getFont("Times New Roman").getFamilyname());
        p.setFont(font);
        p.setAlignment(Element.ALIGN_CENTER);
        p.add("\nРІВНЕНСЬКИЙ  ОБЛАСНИЙ  ОСЕРЕДОК  ФЕДЕРАЦІЇ\n");
        p.add("ОКІНАВСЬКОГО  ГОДЗЮ-РЮ КАРАТЕ-ДО  І  РЮКЮ  КОБУДО\n");
        p.add("ДЖУНДОКАН  УКРАЇНИ\n");

        document.add(p);
        p = new Paragraph();
        Font f = new Font(baseFont, 14);
        f.setStyle(Font.BOLD);
        p.setFont(f);
        p.add("\n\n"+dataProtocol.get("jtfNameProtocol"));
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);
        document.add(new Paragraph("\n"));
        /////
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        p = new Paragraph();
        p.setFont(font);
        p.add("Найменування змагань: ");
        Font ff = new Font(baseFont,12);
        ff.setStyle(Font.BOLD | Font.UNDERLINE);
        ff.setFamily(FontFactory.TIMES_ROMAN);
        p.setFont(ff);
        p.add(dataProtocol.get("jtfNameGame"));
        PdfPCell cell = new PdfPCell(p);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(2);
        table.addCell(cell);
        //
        p = new Paragraph();
        p.setFont(font);
        p.add("Місце проведення: ");
        ff = new Font(baseFont,12);
        ff.setStyle(Font.BOLD | Font.UNDERLINE);
        ff.setFamily(FontFactory.TIMES_ROMAN);
        p.setFont(ff);
        p.add(dataProtocol.get("jtfVenue"));
        table.addCell(p);
        //
        p = new Paragraph();
        p.setFont(font);
        p.add("Дата проведення: ");
        ff = new Font(baseFont,12);
        ff.setStyle(Font.BOLD | Font.UNDERLINE);
        ff.setFamily(FontFactory.TIMES_ROMAN);
        p.setFont(ff);
        p.add(dataProtocol.get("jtfDate"));
        cell = new PdfPCell(p);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(2);
        table.addCell(cell);

        p = new Paragraph();
        p.setFont(font);
        p.add("Вид змагань: ");
        p.setFont(ff);
        p.add(dataProtocol.get("jtfTypeGame"));
        table.addCell(p);

        p = new Paragraph();
        p.setFont(font);
        p.add("Вікова група: ");
        p.setFont(ff);
        p.add(dataProtocol.get("jtfAgeGroup"));
        table.addCell(p);

        p = new Paragraph();
        p.setFont(font);
        p.add("Категорія: ");
        p.setFont(ff);
        p.add(dataProtocol.get("jtfCategory"));
        table.addCell(p);

        p = new Paragraph();
        p.setFont(font);
        p.add("Стать: ");
        p.setFont(ff);
        p.add(dataProtocol.get("jtfSex"));
        table.addCell(p);
        document.add(table);

        document.add(new Paragraph("\n"));
        table = new PdfPTable(8);
        font.setSize(10);
        table.setWidthPercentage(100);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(new PdfPCell(new Phrase("Жереб", font))).setRotation(90);
        table.addCell(new Phrase("Організація", font));
        table.addCell(new Phrase("Прізвище та ім'я",font));
        table.addCell(new PdfPCell(new Phrase("Бали", font))).setRotation(90);
        table.addCell("\n");
        table.addCell(new PdfPCell(new Phrase("Місце", font))).setRotation(90);
        table.addCell(new Phrase("Прізвище та ім'я",font));
        table.addCell(new Phrase("Організація",font));
        table.setWidths(new float []{1.3f,4.3f,6.4f,1.3f,17f,1.3f,6.2f,4.5f});
        cell = new PdfPCell(new Phrase("\n", font));
        cell.setColspan(5);
        cell.setRowspan(3);
        table.addCell(cell);
        font.setSize(8);
        table.addCell(new Phrase(dataProtocol.get("jtfPlace0")+" ", font));
        table.addCell(new Phrase(dataProtocol.get("jcbName18"), font));
        table.addCell(new Phrase(dataProtocol.get("jcbOrganization0"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfPlace1")+" ", font));
        table.addCell(new Phrase(dataProtocol.get("jcbName19"), font));
        table.addCell(new Phrase(dataProtocol.get("jcbOrganization1"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfPlace2")+" ", font));
        table.addCell(new Phrase(dataProtocol.get("jcbName20"), font));
        table.addCell(new Phrase(dataProtocol.get("jcbOrganization2"), font));
        document.add(table);

        document.add(new Paragraph("\n"));

        table = new PdfPTable(11);
        table.setWidthPercentage(100);
        font.setSize(8);
        ff = new Font(baseFont, 10);
        ff.setStyle(Font.BOLD);

        cell = new PdfPCell(new Phrase("Група",ff));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(4);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("1/8 фіналу",ff));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("1/4 фіналу",ff));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("1/2 фіналу",ff));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Фінал",ff));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        //Row one
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.getDefaultCell().setFixedHeight(15f);
        table.addCell(new Phrase(dataProtocol.get("jtfLot0"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfOrganization0"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfName0"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfScore0"), font));
        cell = new PdfPCell(new Phrase("\n",font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(7);
        table.addCell(cell);
        table.setWidths(new float []{1.1f,4.5f,6.2f,1.3f,6.2f,1.3f,6.2f,1.3f,6.2f,1.3f,6.2f});
        //Row two
        table.addCell(new Phrase(dataProtocol.get("jtfLot1"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfOrganization1"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfName1"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfScore1"), font));
        table.addCell(new Phrase(dataProtocol.get("jcbName0"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfScore16"), font));
        cell = new PdfPCell(new Phrase("\n"));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(5);
        table.addCell(cell);
        //Row three
        cell = new PdfPCell(new Phrase("\n"));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(6);
        table.addCell(cell);
        table.addCell(new Phrase(dataProtocol.get("jcbName8"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfScore24"), font));
        cell = new PdfPCell(new Phrase("\n"));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(3);
        table.addCell(cell);
        //Row four
        table.addCell(new Phrase(dataProtocol.get("jtfLot2"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfOrganization2"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfName2"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfScore2"), font));
        table.addCell(new Phrase(dataProtocol.get("jcbName1"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfScore17"), font));
        cell = new PdfPCell(new Phrase("\n"));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(5);
        table.addCell(cell);
        //Row five
        table.addCell(new Phrase(dataProtocol.get("jtfLot3"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfOrganization3"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfName3"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfScore3"), font));
        cell = new PdfPCell(new Phrase("\n",font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(7);
        table.addCell(cell);
        //Row six
        cell = new PdfPCell(new Phrase("\n",font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(8);
        table.addCell(cell);
        table.addCell(new Phrase(dataProtocol.get("jcbName12"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfScore28"), font));
        cell = new PdfPCell(new Phrase("\n",font));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        //Row seven
        table.addCell(new Phrase(dataProtocol.get("jtfLot4"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfOrganization4"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfName4"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfScore4"), font));
        cell = new PdfPCell(new Phrase("\n",font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(7);
        table.addCell(cell);
        //Row eight
        table.addCell(new Phrase(dataProtocol.get("jtfLot5"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfOrganization5"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfName5"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfScore5"), font));
        table.addCell(new Phrase(dataProtocol.get("jcbName2"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfScore18"), font));
        cell = new PdfPCell(new Phrase("\n"));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(5);
        table.addCell(cell);
        //Row nine
        cell = new PdfPCell(new Phrase("\n"));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(6);
        table.addCell(cell);
        table.addCell(new Phrase(dataProtocol.get("jcbName9"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfScore25"), font));
        cell = new PdfPCell(new Phrase("\n"));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(3);
        table.addCell(cell);
        //Row ten
        table.addCell(new Phrase(dataProtocol.get("jtfLot6"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfOrganization6"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfName6"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfScore6"), font));
        table.addCell(new Phrase(dataProtocol.get("jcbName3"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfScore19"), font));
        cell = new PdfPCell(new Phrase("\n"));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(5);
        table.addCell(cell);
        //Row eleven
        table.addCell(new Phrase(dataProtocol.get("jtfLot7"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfOrganization7"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfName7"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfScore7"), font));
        cell = new PdfPCell(new Phrase("\n",font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(7);
        table.addCell(cell);
        //Row twelve
        cell = new PdfPCell(new Phrase("\n",font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(10);
        table.addCell(cell);
        table.addCell(new Phrase(dataProtocol.get("jcbName14"), font));
        //Row 13
        table.addCell(new Phrase(dataProtocol.get("jtfLot8"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfOrganization8"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfName8"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfScore8"), font));
        cell = new PdfPCell(new Phrase("\n",font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(7);
        table.addCell(cell);
        //Row 14
        table.addCell(new Phrase(dataProtocol.get("jtfLot9"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfOrganization9"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfName9"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfScore9"), font));
        table.addCell(new Phrase(dataProtocol.get("jcbName4"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfScore20"), font));
        cell = new PdfPCell(new Phrase("\n"));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(5);
        table.addCell(cell);
        //Row 15
        cell = new PdfPCell(new Phrase("\n"));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(6);
        table.addCell(cell);
        table.addCell(new Phrase(dataProtocol.get("jcbName10"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfScore26"), font));
        cell = new PdfPCell(new Phrase("\n"));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(3);
        table.addCell(cell);
        //Row 16
        table.addCell(new Phrase(dataProtocol.get("jtfLot10"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfOrganization10"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfName10"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfScore10"), font));
        table.addCell(new Phrase(dataProtocol.get("jcbName5"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfScore21"), font));
        cell = new PdfPCell(new Phrase("\n"));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(5);
        table.addCell(cell);
        //Row 17
        table.addCell(new Phrase(dataProtocol.get("jtfLot11"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfOrganization11"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfName11"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfScore11"), font));
        cell = new PdfPCell(new Phrase("\n",font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(7);
        table.addCell(cell);
        //Row 18
        cell = new PdfPCell(new Phrase("\n",font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(8);
        table.addCell(cell);
        table.addCell(new Phrase(dataProtocol.get("jcbName13"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfScore29"), font));
        cell = new PdfPCell(new Phrase("\n",font));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        //Row 19
        table.addCell(new Phrase(dataProtocol.get("jtfLot12"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfOrganization12"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfName12"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfScore12"), font));
        cell = new PdfPCell(new Phrase("\n",font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(7);
        table.addCell(cell);
        //Row 20
        table.addCell(new Phrase(dataProtocol.get("jtfLot13"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfOrganization13"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfName13"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfScore13"), font));
        table.addCell(new Phrase(dataProtocol.get("jcbName6"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfScore22"), font));
        cell = new PdfPCell(new Phrase("\n"));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(5);
        table.addCell(cell);
        //Row 21
        cell = new PdfPCell(new Phrase("\n"));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(6);
        table.addCell(cell);
        table.addCell(new Phrase(dataProtocol.get("jcbName11"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfScore27"), font));
        cell = new PdfPCell(new Phrase("\n"));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(3);
        table.addCell(cell);
        //Row 22
        table.addCell(new Phrase(dataProtocol.get("jtfLot14"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfOrganization14"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfName14"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfScore14"), font));
        table.addCell(new Phrase(dataProtocol.get("jcbName7"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfScore23"), font));
        cell = new PdfPCell(new Phrase("\n"));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(5);
        table.addCell(cell);
        //Row 23
        table.addCell(new Phrase(dataProtocol.get("jtfLot15"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfOrganization15"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfName15"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfScore15"), font));
        cell = new PdfPCell(new Phrase("\n",font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(7);
        table.addCell(cell);
        ////////
        cell = new PdfPCell(new Phrase("\n",ff));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(11);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Додаткова зустріч за 3 місце",ff));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(11);
        table.addCell(cell);
        //Row one
        table.addCell(new Phrase(dataProtocol.get("jtfLot16"), font));
        table.addCell(new Phrase(dataProtocol.get("jcbOrganization3"), font));
        table.addCell(new Phrase(dataProtocol.get("jcbName15"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfScore30"), font));
        cell = new PdfPCell(new Phrase("\n",font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(7);
        table.addCell(cell);
        //Row two
        table.addCell(new Phrase(dataProtocol.get("jtfLot17"), font));
        table.addCell(new Phrase(dataProtocol.get("jcbOrganization4"), font));
        table.addCell(new Phrase(dataProtocol.get("jcbName16"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfScore31"), font));
        table.addCell(new Phrase(dataProtocol.get("jcbName17"), font));
        table.addCell(new Phrase(dataProtocol.get("jtfScore32"), font));
        cell = new PdfPCell(new Phrase("\n"));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(5);
        table.addCell(cell);
        document.add(table);

        document.add(new Paragraph("\n"));

        p = new Paragraph();
        ff = new Font(baseFont,12);
        ff.setStyle(Font.BOLD);
        p.setFont(ff);
        p.add("Головний суддя змагань      ____________________");
        p.add("/ " + dataProtocol.get("jtfMainJudge") + " /");
        document.add(p);
        p = new Paragraph();
        font.setSize(8);
        p.setFont(font);
        p.setLeading(5);
        p.add("\n                                                                                                (підпис)                           (прізвище)");
        document.add(p);
        p = new Paragraph();
        p.setFont(ff);
        p.add("Головний секретар змагань ____________________");
        p.add("/ " + dataProtocol.get("jtfMainSecretary") + " /");
        document.add(p);
        p = new Paragraph();
        font.setSize(8);
        p.setFont(font);
        p.setLeading(5);
        p.add("\n                                                                                                (підпис)                           (прізвище)");
        document.add(p);
    }

    public byte [] getByteArrayPDF() {
        return bytes.toByteArray();
    }

    private Document document;
    private PdfWriter pdfWriter;
    private BaseFont baseFont;
    private ByteArrayOutputStream bytes;
}