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
		p.add("\nв���������  ��������  ��������  ������ֲ�\n");
		p.add("�ʲ���������  �����-�� ������-��  �  ����  ������\n");
		p.add("���������  �������\n");
		
		document.add(p);
		document.add(new Paragraph("\n",font));
		p = new Paragraph();
		Font f = new Font(baseFont, 14);
		f.setStyle(Font.BOLD);
		p.setFont(f);		
		//p = new Paragraph(dataProtocol.get("jtfNameProtocol")); //����� ���������
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
		p = new Paragraph();// ������������ �������: 
		p.setFont(font);
		p.add("������������ �������: ");
		p.setFont(ff);
		p.add(dataProtocol.get("jtfNameGame"));
		PdfPCell cell = new PdfPCell(p);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setColspan(2);
		table.addCell(cell);
		p = new Paragraph(); // ³���� �����: 
		p.setFont(font);
		p.add("³���� �����: ");
		p.setFont(ff);
		p.add(dataProtocol.get("jtfAgeGroup"));
		table.addCell(p);
		p = new Paragraph();// ̳��� ����������: 
		p.setFont(font);
		p.add("̳��� ����������: ");
		p.setFont(ff);
		p.add(dataProtocol.get("jtfVenue"));
		cell = new PdfPCell(p);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setColspan(2);
		table.addCell(cell);
		p = new Paragraph();// ������ ��������: 
		p.setFont(font);
		p.add("��������: ");
		p.setFont(ff);
		p.add(dataProtocol.get("jtfCategory"));
		table.addCell(p);
		p = new Paragraph();// ���� ����������: 
		p.setFont(font);
		p.add("���� ����������: ");
		p.setFont(ff);
		p.add(dataProtocol.get("jtfDate"));
		//p.add(dataProtocol.get("jtfDate"));
		cell = new PdfPCell(p);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setColspan(2);
		table.addCell(cell);
		p = new Paragraph(); // �����
		p.setFont(font);
		p.add("�����: ");
		p.setFont(ff);
		p.add(dataProtocol.get("jtfSex"));
		table.addCell(p);
		document.add(table);
		//document.add(new Paragraph("\n"));
		
		table = new PdfPTable(4);
		table.setWidthPercentage(100);
		table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		p = new Paragraph();// �������� ����� ����� 1:
		p.setFont(font);
		p.add("�������� ����� ����� 1: ");
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
		p = new Paragraph();// C���� 2: 
		p.setFont(font);
		p.add("C���� 2: ");
		p.setFont(ff);
		p.add(dataProtocol.get("jtfReferee2"));
		table.addCell(p);
		p = new Paragraph();// C���� 3: 
		p.setFont(font);
		p.add("C���� 3: ");
		p.setFont(ff);
		p.add(dataProtocol.get("jtfReferee3"));
		table.addCell(p);
		p = new Paragraph();// C���� 4: 
		p.setFont(font);
		p.add("C���� 4: ");
		p.setFont(ff);
		p.add(dataProtocol.get("jtfReferee4"));
		table.addCell(p);
		p = new Paragraph();// C���� 5: 
		p.setFont(font);
		p.add("C���� 5: ");
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
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfRound0"), font)); //1 ����
		cell.setColspan(5);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote48"),font)); // �������
		//cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		//cell.setVerticalAlignment(Element.ALIGN_CENTER);
		//cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setRotation(90);
		cell.setRowspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfRound1"), font)); // 2 ����
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(5);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote49"),font)); //�������
		//cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setRotation(90);
		cell.setRowspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfRound2"), font)); //3 ����
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(5);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote50"),font)); //�������
		//cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setRotation(90);
		cell.setRowspan(3);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNumber16"),font)); //�����
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("textPaneNameOrganization16"),font)); //������� �� ��'� (����������)
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
		
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata48")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll48")+" ", font)); //������
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata49")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll49")+" ", font)); //������
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata50")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll50")+" ", font)); //������
		cell.setColspan(2);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNumber0")+" ",font)); // Start block  // �����
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("textPaneNameOrganization0")+" ",font)); //������� �� ��'� (����������)
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne0")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo0")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree0")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour0")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive0")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote0")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne16")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo16")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree16")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour16")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive16")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote16"),font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne32")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo32")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree32")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour32")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive32")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote32")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata0")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll0")+" ", font)); //������
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata16")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll16")+" ", font));  //������
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata32")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll32")+" ", font)); //������
		cell.setColspan(2);
		table.addCell(cell); 	// End block
		
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNumber1")+" ",font)); // Start block  // �����
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("textPaneNameOrganization1")+" ",font)); //������� �� ��'� (����������)
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne1")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo1")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree1")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour1")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive1")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote1")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne17")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo17")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree17")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour17")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive17")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote17")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne33")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo33")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree33")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour33")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive33")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote33")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata1")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll1")+" ", font)); //������
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata17")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll17")+" ", font));  //������
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata32")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll32")+" ", font)); //������
		cell.setColspan(2);
		table.addCell(cell); 	// End block
		
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNumber2")+" ",font)); // Start block  // �����
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("textPaneNameOrganization2")+" ",font)); //������� �� ��'� (����������)
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne2")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo2")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree2")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour2")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive2")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote2")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne18")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo18")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree18")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour18")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive18")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote18")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne34")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo34")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree34")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour34")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive34")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote34")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata2")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll2")+" ", font)); //������
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata18")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll18")+" ", font));  //������
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata34")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll34")+" ", font)); //������
		cell.setColspan(2);
		table.addCell(cell); 	// End block
		
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNumber3")+" ",font)); // Start block  // �����
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("textPaneNameOrganization3")+" ",font)); //������� �� ��'� (����������)
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne3")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo3")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree3")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour3")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive3")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote3")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne19")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo19")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree19")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour19")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive19")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote19")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne35")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo35")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree35")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour35")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive35")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote35")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata3")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll3")+" ", font)); //������
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata19")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll19")+" ", font));  //������
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata35")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll35")+" ", font)); //������
		cell.setColspan(2);
		table.addCell(cell); 	// End block
		
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNumber4")+" ",font)); // Start block  // �����
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("textPaneNameOrganization4")+" ",font)); //������� �� ��'� (����������)
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne4")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo4")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree4")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour4")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive4")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote4")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne20")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo20")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree20")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour20")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive20")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote20")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne36")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo36")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree36"), font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour36")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive36")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote36")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata4")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll4")+" ", font)); //������
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata20")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll20")+" ", font));  //������
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata36")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll36")+" ", font)); //������
		cell.setColspan(2);
		table.addCell(cell); 	// End block
		
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNumber5")+" ",font)); // Start block  // �����
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("textPaneNameOrganization5")+" ",font)); //������� �� ��'� (����������)
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne5")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo5")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree5")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour5")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive5")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote5")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne21")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo21")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree21")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour21")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive21")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote21")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne37")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo37")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree37")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour37")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive37")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote37")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata5")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll5")+" ", font)); //������
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata21")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll21")+" ", font));  //������
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata37")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll37")+" ", font)); //������
		cell.setColspan(2);
		table.addCell(cell); 	// End block
		
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNumber6")+" ",font)); // Start block  // �����
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("textPaneNameOrganization6")+" ",font)); //������� �� ��'� (����������)
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne6")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo6")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree6")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour6")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive6")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote6")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne22")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo22")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree22")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour22")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive22")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote22")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne38")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo38")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree38")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour38")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive38")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote38")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata6")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll6")+" ", font)); //������
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata22")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll22")+" ", font));  //������
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata38")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll38")+" ", font)); //������
		cell.setColspan(2);
		table.addCell(cell); 	// End block
		
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNumber7")+" ",font)); // Start block  // �����
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("textPaneNameOrganization7")+" ",font)); //������� �� ��'� (����������)
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne7")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo7")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree7")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour7")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive7")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote7")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne23")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo23")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree23")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour23")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive23")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote23")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne39")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo39")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree39")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour39")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive39")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote39")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata7")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll7")+" ", font)); //������
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata23")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll23")+" ", font));  //������
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata39")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll39")+" ", font)); //������
		cell.setColspan(2);
		table.addCell(cell); 	// End block
		
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNumber8")+" ",font)); // Start block  // �����
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("textPaneNameOrganization8")+" ",font)); //������� �� ��'� (����������)
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne8")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo8")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree8")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour8")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive8")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote8")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne24")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo24")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree24")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour24")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive24")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote24")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne40")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo40")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree40")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour40")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive40")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote40")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata8")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll8")+" ", font)); //������
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata24")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll24")+" ", font));  //������
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata40")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll40")+" ", font)); //������
		cell.setColspan(2);
		table.addCell(cell); 	// End block
		
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNumber9")+" ",font)); // Start block  // �����
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("textPaneNameOrganization9")+" ",font)); //������� �� ��'� (����������)
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne9")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo9")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree9")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour9")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive9")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote9")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne25")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo25")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree25")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour25")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive25")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote25")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne41")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo41")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree41")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour41")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive41")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote41")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata9")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll9")+" ", font)); //������
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata25")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll25")+" ", font));  //������
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata41")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll41")+" ", font)); //������
		cell.setColspan(2);
		table.addCell(cell); 	// End block
		
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNumber10")+" ",font)); // Start block  // �����
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("textPaneNameOrganization10")+" ",font)); //������� �� ��'� (����������)
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne10")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo10")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree10")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour10")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive10")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote10")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne26")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo26")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree26")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour26")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive26")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote26")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne42")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo42")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree42")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour42")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive42")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote42")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata10")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll10")+" ", font)); //������
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata26")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll26")+" ", font));  //������
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata42")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll42")+" ", font)); //������
		cell.setColspan(2);
		table.addCell(cell); 	// End block
		
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNumber11")+" ",font)); // Start block  // �����
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("textPaneNameOrganization11")+" ",font)); //������� �� ��'� (����������)
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne11")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo11")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree11")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour11")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive11")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote11")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne27")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo27")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree27")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour27")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive27")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote27")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne43")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo43")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree43")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour43")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive43")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote43")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata11")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll11")+" ", font)); //������
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata27")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll27")+" ", font));  //������
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata43")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll43")+" ", font)); //������
		cell.setColspan(2);
		table.addCell(cell); 	// End block
		
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNumber12")+" ",font)); // Start block  // �����
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("textPaneNameOrganization12")+" ",font)); //������� �� ��'� (����������)
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne12")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo12")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree12")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour12")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive12")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote12")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne28")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo28")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree28")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour28")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive28")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote28")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne44")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo44")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree44")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour44")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive44")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote44")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata12")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll12")+" ", font)); //������
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata28")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll28")+" ", font));  //������
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata44")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll44")+" ", font)); //������
		cell.setColspan(2);
		table.addCell(cell); 	// End block
		
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNumber13")+" ",font)); // Start block  // �����
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("textPaneNameOrganization13")+" ",font)); //������� �� ��'� (����������)
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne13")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo13")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree13")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour13")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive13")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote13")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne29")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo29")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree29")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour29")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive29")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote29")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne45")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo45")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree45")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour45")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive45")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote45")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata13")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll13")+" ", font)); //������
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata29")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll29")+" ", font));  //������
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata45")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll45")+" ", font)); //������
		cell.setColspan(2);
		table.addCell(cell); 	// End block
		
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNumber14")+" ",font)); // Start block  // �����
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("textPaneNameOrganization14")+" ",font)); //������� �� ��'� (����������)
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne14")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo14")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree14")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour14")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive14")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote14")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne30")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo30")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree30")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour30")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive30")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote30")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne46")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo46")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree46")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour46")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive46")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote46")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata14")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll14")+" ", font)); //������
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata30")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll30")+" ", font));  //������
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata46")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll46")+" ", font)); //������
		cell.setColspan(2);
		table.addCell(cell); 	// End block
		
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNumber15")+" ",font)); // Start block  // �����
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("textPaneNameOrganization15")+" ",font)); //������� �� ��'� (����������)
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne15")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo15")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree15")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour15")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive15")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote15")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne31")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo31")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree31")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour31")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive31")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote31")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Phrase(dataProtocol.get("pointOne47")+" ", font)); //������ 1
		table.addCell(new Phrase(dataProtocol.get("pointTwo47")+" ", font)); //������ 2
		table.addCell(new Phrase(dataProtocol.get("pointThree47")+" ", font)); //������ 3
		table.addCell(new Phrase(dataProtocol.get("pointFour47")+" ", font)); //������ 4
		table.addCell(new Phrase(dataProtocol.get("pointFive47")+" ", font)); //������ 5
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfNote47")+" ",font)); //�������
		cell.setRowspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata15")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll15")+" ", font)); //������
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata31")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll31")+" ", font));  //������
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfKata47")+" ", font)); //����
		cell.setColspan(3);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(dataProtocol.get("jtfAll47")+" ", font)); //������
		cell.setColspan(2);
		table.addCell(cell); 	// End block
		
		document.add(table);
		document.add(new Paragraph("\n"));
		p = new Paragraph();
		p.setFont(ff);
		p.add("�������� ����� �������       ____________________");
		p.add(dataProtocol.get("jtfMainJudge"));
		document.add(p);
		p = new Paragraph();
		font.setSize(8);
		p.setFont(font);
		p.setLeading(5);
		p.add("\n                                                                                                (�����)                           (�������)");
		document.add(p);  
		p = new Paragraph();
		p.setFont(ff);
		p.add("�������� �������� ������� ____________________");
		p.add(dataProtocol.get("jtfMainSecretary"));
		document.add(p);
		p = new Paragraph();
		font.setSize(8);
		p.setFont(font);
		p.setLeading(5);
		p.add("\n                                                                                                (�����)                           (�������)");
		document.add(p);
	}
}