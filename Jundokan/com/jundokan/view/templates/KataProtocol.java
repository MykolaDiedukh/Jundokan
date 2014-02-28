package jundokan.view.templates;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
/**
 * @Autor Soul
 */
public class KataProtocol {
    private Document document;
    private PdfWriter pdfWriter;
    private BaseFont baseFont;
    private ByteArrayOutputStream bytes;
    
    public byte [] getByteArrayPDF() {
        return bytes.toByteArray();
    }
    public void Add(ArrayList<HashMap<String, String>> hashMapKata) {
    	for (HashMap<String, String> protocol: hashMapKata){
            try {
				collectionPDFDocument(protocol);
				this.document.newPage();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        document.close();
    }
    public KataProtocol(HashMap<String, String> hashMapKata) {
        try {
            initialization();
            collectionPDFDocument(hashMapKata);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public KataProtocol(ArrayList<HashMap<String, String>> hashMapKata) {
        try {
            initialization();
            for (HashMap<String, String> protocol: hashMapKata){
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
	/*	Document document = new Document(PageSize.A4, 10, 10, 10, 10);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("KataProtocol.pdf"));
		BaseFont times = BaseFont.createFont("c:/windows/fonts/times.ttf","cp1251",BaseFont.EMBEDDED);
		
		document.open();*/
    	
		Image img = Image.getInstance(System.getProperty("user.dir") +"/resource/gui/logo_1.jpg");
		img.scaleAbsolute(67, 80);
		img.setAbsolutePosition(40,740);
		img.setAlignment(Image.LEFT | Image.TEXTWRAP);
		document.add(img);
		
		Paragraph p = new Paragraph();
		//p.add(new Chunk(img,0,-15, true));
		FontFactory.registerDirectories();
		Font font = new Font(baseFont,12);
		font.setFamily(FontFactory.getFont("Times New Roman").getFamilyname());
		p.setFont(font);
		document.add(new Paragraph("\n",font));
		p.setAlignment(Element.ALIGN_CENTER);
		p.add("\nРІВНЕНСЬКИЙ  ОБЛАСНИЙ  ОСЕРЕДОК  ФЕДЕРАЦІЇ\n");
		p.add("ОКІНАВСЬКОГО  ГОДЗЮ-РЮ КАРАТЕ-ДО  І  РЮКЮ  КОБУДО\n");
		p.add("ДЖУНДОКАН  УКРАЇНИ\n");
		
		document.add(p);
		document.add(new Paragraph("\n",font));
		p = new Paragraph();
		Font f = new Font(baseFont, 14);
		f.setStyle(Font.BOLD);
		p.setFont(f);		
		//p = new Paragraph(dataProtocol.get("jtfNameProtocol")); //Назва протоколу
		//p = new Paragraph();
		p.setAlignment(Element.ALIGN_CENTER);
		//p.setFont(f);
		p.add(dataProtocol.get("jtfNameProtocol"));
		document.add(p);
		document.add(new Paragraph("\n",font));
		PdfPTable table  = new PdfPTable(3);
		table.setWidthPercentage(100);
		//table.setWidths(new float [] {40f,25f,35f});
		table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		Font ff = new Font(baseFont,12);
		ff.setStyle(Font.BOLD | Font.UNDERLINE);
		ff.setFamily(FontFactory.TIMES_ROMAN);
		p.setFont(ff);
		p = new Paragraph();// Найменування змагань: 
		p.setFont(font);
		p.add("Найменування змагань: ");
		p.setFont(ff);
		p.add(dataProtocol.get("jtfNameGame"));
		PdfPCell cell = new PdfPCell(p);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setColspan(2);
		table.addCell(cell);
		p = new Paragraph(); // Вікова група: 
		p.setFont(font);
		p.add("Вікова група: ");
		p.setFont(ff);
		p.add(dataProtocol.get("jtfAgeGroup"));
		table.addCell(p);
		p = new Paragraph();// Місце проведення: 
		p.setFont(font);
		p.add("Місце проведення: ");
		p.setFont(ff);
		p.add(dataProtocol.get("jtfVenue"));
		cell = new PdfPCell(p);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setColspan(2);
		table.addCell(cell);
		p = new Paragraph();// Вагова категорія: 
		p.setFont(font);
		p.add("Категорія: ");
		p.setFont(ff);
		p.add(dataProtocol.get("jtfCategory"));
		table.addCell(p);
		p = new Paragraph();// Дата проведення: 
		p.setFont(font);
		p.add("Дата проведення: ");
		p.setFont(ff);
		p.add(dataProtocol.get("jtfDate"));
		//p.add(dataProtocol.get("jtfDate"));
		cell = new PdfPCell(p);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setColspan(2);
		table.addCell(cell);
		p = new Paragraph(); // Стать
		p.setFont(font);
		p.add("Стать: ");
		p.setFont(ff);
		p.add(dataProtocol.get("jtfSex"));
		table.addCell(p);
		document.add(table);
		//document.add(new Paragraph("\n"));
		
		table = new PdfPTable(4);
		table.setWidthPercentage(100);
		table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		p = new Paragraph();// Головний суддя татамі 1:
		p.setFont(font);
		p.add("Головний суддя татамі 1: ");
		p.setFont(ff);
		p.add(dataProtocol.get("jtfMainReferee"));
		cell = new PdfPCell(p);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(4);
		table.addCell(cell);
		p = new Paragraph();
		p.setFont(ff);
		p.add("\n");
		cell = new PdfPCell();
		cell.setFixedHeight(5);
		cell.setColspan(4);
		cell.setBorder(Rectangle.NO_BORDER);
		table.addCell(cell);
		p = new Paragraph();// Cуддя 2: 
		p.setFont(font);
		p.add("Cуддя 2: ");
		p.setFont(ff);
		p.add(dataProtocol.get("jtfReferee2"));
		table.addCell(p);
		p = new Paragraph();// Cуддя 3: 
		p.setFont(font);
		p.add("Cуддя 3: ");
		p.setFont(ff);
		p.add(dataProtocol.get("jtfReferee3"));
		table.addCell(p);
		p = new Paragraph();// Cуддя 4: 
		p.setFont(font);
		p.add("Cуддя 4: ");
		p.setFont(ff);
		p.add(dataProtocol.get("jtfReferee4"));
		table.addCell(p);
		p = new Paragraph();// Cуддя 5: 
		p.setFont(font);
		p.add("Cуддя 5: ");
		p.setFont(ff);
		p.add(dataProtocol.get("jtfReferee5"));
		table.addCell(p);
		table.addCell("\n");
		document.add(table);
		document.add(new Paragraph("\n",font));
		
		table = new PdfPTable(20);
		table.setWidths(new float []{1.3f,7f,1.3f,1.3f,1.3f,1.3f,1.3f,1.1f,1.3f,1.3f,1.3f,1.3f,1.3f,1.1f,1.3f,1.3f,1.3f,1.3f,1.3f,1.1f});
		table.setWidthPercentage(100);
		font.setSize(10);
		ff = new Font(baseFont, 12);
		ff.setStyle(Font.BOLD);
		
		cell = new PdfPCell(new Phrase());
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfRound0"), font)); //1 коло
		cell.setColspan(5);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote48"),font)); // Примітка
		//cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		//cell.setVerticalAlignment(Element.ALIGN_CENTER);
		//cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setRotation(90);
		cell.setRowspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfRound1"), font)); // 2 коло
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(5);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote49"),font)); //Примітка
		//cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setRotation(90);
		cell.setRowspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfRound2"), font)); //3 коло
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(5);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote50"),font)); //Примітка
		//cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setRotation(90);
		cell.setRowspan(3);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNumber16"),font)); //Номер
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("textPaneNameOrganization16"),font)); //Прізвище та ім'я (організація)
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne48"), font)); 
		table.addCell(new Phrase(dataProtocol.get("pointTwo48"), font));
		table.addCell(new Phrase(dataProtocol.get("pointThree48"), font));
		table.addCell(new Phrase(dataProtocol.get("pointFour48"), font));
		table.addCell(new Phrase(dataProtocol.get("pointFive48"), font));
		table.addCell(new Phrase(dataProtocol.get("pointOne49"), font)); 
		table.addCell(new Phrase(dataProtocol.get("pointTwo49"), font));
		table.addCell(new Phrase(dataProtocol.get("pointThree49"), font));
		table.addCell(new Phrase(dataProtocol.get("pointFour49"), font));
		table.addCell(new Phrase(dataProtocol.get("pointFive49"), font));
		table.addCell(new Phrase(dataProtocol.get("pointOne50"), font)); 
		table.addCell(new Phrase(dataProtocol.get("pointTwo50"), font));
		table.addCell(new Phrase(dataProtocol.get("pointThree50"), font));
		table.addCell(new Phrase(dataProtocol.get("pointFour50"), font));
		table.addCell(new Phrase(dataProtocol.get("pointFive50"), font));
		
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata48")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll48")+" ", font)); //Всього
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata49")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll49")+" ", font)); //Всього
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata50")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll50")+" ", font)); //Всього
		cell.setColspan(2);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNumber0")+" ",font)); // Start block  // Номер
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("textPaneNameOrganization0")+" ",font)); //Прізвище та ім'я (організація)
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne0")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo0")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree0")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour0")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive0")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote0")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne16")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo16")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree16")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour16")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive16")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote16"),font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne32")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo32")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree32")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour32")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive32")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote32")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata0")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll0")+" ", font)); //Всього
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata16")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll16")+" ", font));  //Всього
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata32")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll32")+" ", font)); //Всього
		cell.setColspan(2);
		table.addCell(cell); 	// End block
		
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNumber1")+" ",font)); // Start block  // Номер
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("textPaneNameOrganization1")+" ",font)); //Прізвище та ім'я (організація)
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne1")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo1")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree1")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour1")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive1")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote1")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne17")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo17")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree17")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour17")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive17")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote17")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne33")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo33")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree33")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour33")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive33")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote33")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata1")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll1")+" ", font)); //Всього
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata17")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll17")+" ", font));  //Всього
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata32")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll32")+" ", font)); //Всього
		cell.setColspan(2);
		table.addCell(cell); 	// End block
		
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNumber2")+" ",font)); // Start block  // Номер
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("textPaneNameOrganization2")+" ",font)); //Прізвище та ім'я (організація)
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne2")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo2")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree2")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour2")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive2")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote2")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne18")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo18")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree18")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour18")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive18")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote18")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne34")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo34")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree34")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour34")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive34")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote34")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata2")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll2")+" ", font)); //Всього
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata18")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll18")+" ", font));  //Всього
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata34")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll34")+" ", font)); //Всього
		cell.setColspan(2);
		table.addCell(cell); 	// End block
		
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNumber3")+" ",font)); // Start block  // Номер
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("textPaneNameOrganization3")+" ",font)); //Прізвище та ім'я (організація)
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne3")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo3")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree3")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour3")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive3")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote3")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne19")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo19")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree19")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour19")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive19")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote19")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne35")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo35")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree35")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour35")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive35")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote35")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata3")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll3")+" ", font)); //Всього
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata19")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll19")+" ", font));  //Всього
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata35")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll35")+" ", font)); //Всього
		cell.setColspan(2);
		table.addCell(cell); 	// End block
		
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNumber4")+" ",font)); // Start block  // Номер
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("textPaneNameOrganization4")+" ",font)); //Прізвище та ім'я (організація)
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne4")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo4")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree4")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour4")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive4")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote4")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne20")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo20")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree20")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour20")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive20")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote20")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne36")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo36")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree36"), font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour36")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive36")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote36")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata4")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll4")+" ", font)); //Всього
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata20")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll20")+" ", font));  //Всього
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata36")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll36")+" ", font)); //Всього
		cell.setColspan(2);
		table.addCell(cell); 	// End block
		
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNumber5")+" ",font)); // Start block  // Номер
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("textPaneNameOrganization5")+" ",font)); //Прізвище та ім'я (організація)
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne5")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo5")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree5")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour5")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive5")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote5")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne21")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo21")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree21")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour21")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive21")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote21")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne37")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo37")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree37")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour37")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive37")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote37")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata5")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll5")+" ", font)); //Всього
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata21")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll21")+" ", font));  //Всього
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata37")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll37")+" ", font)); //Всього
		cell.setColspan(2);
		table.addCell(cell); 	// End block
		
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNumber6")+" ",font)); // Start block  // Номер
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("textPaneNameOrganization6")+" ",font)); //Прізвище та ім'я (організація)
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne6")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo6")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree6")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour6")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive6")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote6")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne22")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo22")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree22")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour22")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive22")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote22")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne38")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo38")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree38")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour38")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive38")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote38")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata6")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll6")+" ", font)); //Всього
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata22")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll22")+" ", font));  //Всього
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata38")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll38")+" ", font)); //Всього
		cell.setColspan(2);
		table.addCell(cell); 	// End block
		
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNumber7")+" ",font)); // Start block  // Номер
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("textPaneNameOrganization7")+" ",font)); //Прізвище та ім'я (організація)
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne7")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo7")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree7")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour7")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive7")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote7")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne23")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo23")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree23")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour23")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive23")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote23")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne39")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo39")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree39")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour39")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive39")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote39")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata7")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll7")+" ", font)); //Всього
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata23")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll23")+" ", font));  //Всього
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata39")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll39")+" ", font)); //Всього
		cell.setColspan(2);
		table.addCell(cell); 	// End block
		
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNumber8")+" ",font)); // Start block  // Номер
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("textPaneNameOrganization8")+" ",font)); //Прізвище та ім'я (організація)
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne8")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo8")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree8")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour8")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive8")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote8")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne24")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo24")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree24")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour24")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive24")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote24")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne40")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo40")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree40")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour40")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive40")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote40")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata8")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll8")+" ", font)); //Всього
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata24")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll24")+" ", font));  //Всього
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata40")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll40")+" ", font)); //Всього
		cell.setColspan(2);
		table.addCell(cell); 	// End block
		
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNumber9")+" ",font)); // Start block  // Номер
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("textPaneNameOrganization9")+" ",font)); //Прізвище та ім'я (організація)
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne9")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo9")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree9")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour9")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive9")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote9")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne25")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo25")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree25")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour25")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive25")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote25")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne41")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo41")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree41")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour41")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive41")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote41")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata9")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll9")+" ", font)); //Всього
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata25")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll25")+" ", font));  //Всього
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata41")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll41")+" ", font)); //Всього
		cell.setColspan(2);
		table.addCell(cell); 	// End block
		
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNumber10")+" ",font)); // Start block  // Номер
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("textPaneNameOrganization10")+" ",font)); //Прізвище та ім'я (організація)
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne10")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo10")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree10")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour10")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive10")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote10")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne26")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo26")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree26")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour26")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive26")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote26")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne42")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo42")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree42")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour42")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive42")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote42")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata10")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll10")+" ", font)); //Всього
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata26")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll26")+" ", font));  //Всього
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata42")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll42")+" ", font)); //Всього
		cell.setColspan(2);
		table.addCell(cell); 	// End block
		
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNumber11")+" ",font)); // Start block  // Номер
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("textPaneNameOrganization11")+" ",font)); //Прізвище та ім'я (організація)
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne11")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo11")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree11")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour11")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive11")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote11")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne27")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo27")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree27")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour27")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive27")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote27")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne43")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo43")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree43")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour43")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive43")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote43")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata11")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll11")+" ", font)); //Всього
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata27")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll27")+" ", font));  //Всього
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata43")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll43")+" ", font)); //Всього
		cell.setColspan(2);
		table.addCell(cell); 	// End block
		
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNumber12")+" ",font)); // Start block  // Номер
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("textPaneNameOrganization12")+" ",font)); //Прізвище та ім'я (організація)
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne12")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo12")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree12")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour12")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive12")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote12")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne28")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo28")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree28")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour28")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive28")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote28")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne44")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo44")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree44")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour44")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive44")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote44")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata12")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll12")+" ", font)); //Всього
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata28")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll28")+" ", font));  //Всього
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata44")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll44")+" ", font)); //Всього
		cell.setColspan(2);
		table.addCell(cell); 	// End block
		
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNumber13")+" ",font)); // Start block  // Номер
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("textPaneNameOrganization13")+" ",font)); //Прізвище та ім'я (організація)
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne13")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo13")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree13")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour13")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive13")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote13")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne29")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo29")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree29")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour29")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive29")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote29")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne45")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo45")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree45")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour45")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive45")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote45")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata13")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll13")+" ", font)); //Всього
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata29")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll29")+" ", font));  //Всього
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata45")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll45")+" ", font)); //Всього
		cell.setColspan(2);
		table.addCell(cell); 	// End block
		
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNumber14")+" ",font)); // Start block  // Номер
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("textPaneNameOrganization14")+" ",font)); //Прізвище та ім'я (організація)
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne14")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo14")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree14")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour14")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive14")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote14")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne30")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo30")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree30")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour30")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive30")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote30")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne46")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo46")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree46")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour46")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive46")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote46")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata14")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll14")+" ", font)); //Всього
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata30")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll30")+" ", font));  //Всього
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata46")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll46")+" ", font)); //Всього
		cell.setColspan(2);
		table.addCell(cell); 	// End block
		
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNumber15")+" ",font)); // Start block  // Номер
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("textPaneNameOrganization15")+" ",font)); //Прізвище та ім'я (організація)
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne15")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo15")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree15")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour15")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive15")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote15")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne31")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo31")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree31")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour31")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive31")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote31")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne47")+" ", font)); //оцінка 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo47")+" ", font)); //оцінка 2
		table.addCell(new Phrase(dataProtocol.get("pointThree47")+" ", font)); //оцінка 3
		table.addCell(new Phrase(dataProtocol.get("pointFour47")+" ", font)); //оцінка 4
		table.addCell(new Phrase(dataProtocol.get("pointFive47")+" ", font)); //оцінка 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote47")+" ",font)); //Примітка
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata15")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll15")+" ", font)); //Всього
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata31")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll31")+" ", font));  //Всього
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata47")+" ", font)); //Ката
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll47")+" ", font)); //Всього
		cell.setColspan(2);
		table.addCell(cell); 	// End block
		
		document.add(table);
		document.add(new Paragraph("\n"));
		p = new Paragraph();
		p.setFont(ff);
		p.add("Головний суддя змагань       ____________________");
		p.add(dataProtocol.get("jtfMainJudge"));
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
		p.add(dataProtocol.get("jtfMainSecretary"));
		document.add(p);
		p = new Paragraph();
		font.setSize(8);
		p.setFont(font);
		p.setLeading(5);
		p.add("\n                                                                                                (підпис)                           (прізвище)");
		document.add(p);
	}
}