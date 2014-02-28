package jundokan.view.templates;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

import javax.sound.midi.Patch;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import jundokan.connection.Protocols;
import jundokan.model.DBProtocols;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.jpedal.PdfDecoder;
import org.jpedal.exception.PdfException;
import org.jpedal.fonts.FontMappings;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

public class ConvertToPdf extends JPanel {

	/**
	 * @author Soul
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DataProtocol dataProtocol;
	
	private PdfDecoder pdfDecoder;
	private JTabbedPane tabbedPane;
	private JTextField jtfNameProtocol;
	private JTextField jtfNameGame;
	private JTextField jtfVenue;
	private JTextField jtfDate;
	private JTextField jtfAgeGroup;
	private JTextField jtfCategory;
	private JTextField jtfSex;
	private JTextField jtfMainReferee;
	private JTextField jtfReferee2;
	private JTextField jtfReferee3;
	private JTextField jtfReferee4;
	private JTextField jtfReferee5;
	private JTextField jtfMainJudge;
	private JTextField jtfMainSecretary;
	private JLabel jlbImage;
	private JPanel jPanel2;

	private JLabel [] lblInfo;
	private JTextField [] jtfNumber;
	private JTextPane [] textPaneNameOrganization;
	private JTextField [] pointOne;
	private JTextField [] pointTwo;
	private JTextField [] pointThree;
	private JTextField [] pointFour;
	private JTextField [] pointFive;	
	private JTextField [] jtfKata;
	private JTextField [] jtfAll;	
	private JTextField [] jtfNote;
	private JTextField [] jtfRound;
	private int ProtID;
	
	public int getProtID() {
		return ProtID;
	}
	public ConvertToPdf(ArrayList<String[]> list, int FGID, String ProtName){
		initComponents();
		filling(list);
		dataProtocol = new DataProtocol();
		saveProtocolKata();
		DBProtocols protocol = new DBProtocols();
		protocol.ProtData = dataProtocol.serializationDataProtocol();
		protocol.ProtAge = jtfAgeGroup.getText();
		protocol.ProtSex = true;//potom
		protocol.ProtCategory = jtfCategory.getText();
		protocol.ProtName = ProtName;//potom
		protocol.FGID = FGID;
		ProtID = new Protocols().insert(protocol);
	}
    public void removeNotify() {
    	saveProtocolKata();
    	DBProtocols protocol = new DBProtocols();
		protocol.ProtData = dataProtocol.serializationDataProtocol();
		protocol.ProtAge = jtfAgeGroup.getText();
		protocol.ProtSex = true;//potom
		protocol.ProtCategory = jtfCategory.getText();
		protocol.ProtID = getProtID();
    	new Protocols().update(protocol);
        super.removeNotify();            
        // Remove internal "registered things"  
    }  
	public ConvertToPdf(DBProtocols protocol) {
        try {
            //setExtendedState(MAXIMIZED_BOTH);
        	this.ProtID = protocol.ProtID;
            initComponents();
            dataProtocol = new DataProtocol();
            dataProtocol.unSerializationDataProtocol(protocol.ProtData);
            insertDataProtocol();
           // dataProtocol.unSerializationOutFile();
           // protocol.unSerializationDataProtocol(new ConnectorTest().getProtocol());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new ConvertToPdf(0).setVisible(true);
			}
		});
	}
	*/
	public void filling(ArrayList<String[]> list){		
		Collections.shuffle(list);
		for (int i = 0; i<list.size(); i++){
			textPaneNameOrganization[i].setText(String.format("%s\n(%s)", list.get(i)[1], list.get(i)[0]));
		}		
	}
	
	public void insertDataProtocol(){
		for (int i = 0; i<3; i++)jtfRound[i].setText(dataProtocol.getValue("jtfRound"+i));
		for (int i = 0; i<17; i++)textPaneNameOrganization[i].setText(dataProtocol.getValue("textPaneNameOrganization"+i));
		for (int i = 0; i < 51; i++) pointOne[i].setText(dataProtocol.getValue("pointOne"+i));
		for (int i = 0; i < 51; i++) pointTwo[i].setText(dataProtocol.getValue("pointTwo"+i));
		for (int i = 0; i < 51; i++) pointThree[i].setText(dataProtocol.getValue("pointThree"+i));
		for (int i = 0; i < 51; i++) pointFour[i].setText(dataProtocol.getValue("pointFour"+i));
		for (int i = 0; i < 51; i++) pointFive[i].setText(dataProtocol.getValue("pointFive"+i));
		for (int i = 0; i < 51; i++) jtfAll[i].setText(dataProtocol.getValue("jtfAll"+i));
		for (int i = 0; i < 51; i++) jtfKata[i].setText(dataProtocol.getValue("jtfKata"+i));
		for (int i = 0; i < 51; i++) jtfNote[i].setText(dataProtocol.getValue("jtfNote"+i));
		jtfNameProtocol.setText(dataProtocol.getValue("jtfNameProtocol"));
		jtfNameGame.setText(dataProtocol.getValue("jtfNameGame"));
		jtfVenue.setText(dataProtocol.getValue("jtfVenue"));
		jtfDate.setText(dataProtocol.getValue("jtfDate"));
		jtfAgeGroup.setText(dataProtocol.getValue("jtfAgeGroup"));
		jtfCategory.setText(dataProtocol.getValue("jtfCategory"));
		jtfSex.setText(dataProtocol.getValue("jtfSex"));
		jtfMainReferee.setText(dataProtocol.getValue("jtfMainReferee"));
		jtfReferee2.setText(dataProtocol.getValue("jtfReferee2"));
		jtfReferee3.setText(dataProtocol.getValue("jtfReferee3"));
		jtfReferee4.setText(dataProtocol.getValue("jtfReferee4"));
		jtfReferee5.setText(dataProtocol.getValue("jtfReferee5"));
		jtfMainJudge.setText(dataProtocol.getValue("jtfMainJudge"));
		jtfMainSecretary.setText(dataProtocol.getValue("jtfMainSecretary"));
		
	}
	public void saveProtocolKata(){
		this.dataProtocol = new DataProtocol();
		for (int i = 0; i<17; i++)dataProtocol.setValue("jtfNumber"+i, jtfNumber[i].getText());
		for (int i = 0; i < 3; i++)dataProtocol.setValue("jtfRound"+i, jtfRound[i].getText());
		for (int i = 0; i < 17; i++)dataProtocol.setValue("textPaneNameOrganization"+i, textPaneNameOrganization[i].getText());
		for (int i = 0; i < 51; i++)dataProtocol.setValue("pointOne"+i, pointOne[i].getText());
		for (int i = 0; i < 51; i++)dataProtocol.setValue("pointTwo"+i, pointTwo[i].getText());
		for (int i = 0; i < 51; i++)dataProtocol.setValue("pointThree"+i, pointThree[i].getText()); 		
		for (int i = 0; i < 51; i++)dataProtocol.setValue("pointFour"+i, pointFour[i].getText());
		for (int i = 0; i < 51; i++)dataProtocol.setValue("pointFive"+i, pointFive[i].getText());
		for (int i = 0; i < 51; i++)dataProtocol.setValue("jtfAll"+i, jtfAll[i].getText());
		for (int i = 0; i < 51; i++)dataProtocol.setValue("jtfKata"+i, jtfKata[i].getText());
		for (int i = 0; i < 51; i++)dataProtocol.setValue("jtfNote"+i, jtfNote[i].getText());
		dataProtocol.setValue("jtfNameProtocol", jtfNameProtocol.getText());
		dataProtocol.setValue("jtfNameGame", jtfNameGame.getText());
		dataProtocol.setValue("jtfVenue", jtfVenue.getText());
		dataProtocol.setValue("jtfDate", jtfDate.getText());
		dataProtocol.setValue("jtfAgeGroup", jtfAgeGroup.getText());
		dataProtocol.setValue("jtfCategory", jtfCategory.getText());
		dataProtocol.setValue("jtfSex", jtfSex.getText());
		dataProtocol.setValue("jtfMainReferee", jtfMainReferee.getText());
		dataProtocol.setValue("jtfReferee2", jtfReferee2.getText());
		dataProtocol.setValue("jtfReferee3", jtfReferee3.getText());
		dataProtocol.setValue("jtfReferee4", jtfReferee4.getText());
		dataProtocol.setValue("jtfReferee5", jtfReferee5.getText());
		dataProtocol.setValue("jtfMainJudge", jtfMainJudge.getText());
		dataProtocol.setValue("jtfMainSecretary", jtfMainSecretary.getText());
	}
	public void savePrtocolKataTest(){
		this.dataProtocol = new DataProtocol();
		for (int i = 0; i<17; i++)dataProtocol.setValue("jtfNumber"+i, "jtfNumber"+i);
		for (int i = 0; i < 3; i ++)dataProtocol.setValue("jtfRound"+i, "jtfRound"+i);
		for (int i = 0; i < 17; i++)dataProtocol.setValue("textPaneNameOrganization"+i, "textPaneNameOrganization"+i);
		for (int i = 0; i < 51; i++)dataProtocol.setValue("pointOne"+i, "pointOne"+i);
		for (int i = 0; i < 51; i++)dataProtocol.setValue("pointTwo"+i, "pointTwo"+i);
		for (int i = 0; i < 51; i++)dataProtocol.setValue("pointThree"+i, "pointThree"+i); 		
		for (int i = 0; i < 51; i++)dataProtocol.setValue("pointFour"+i, "pointFour"+i);
		for (int i = 0; i < 51; i++)dataProtocol.setValue("pointFive"+i, "pointFive"+i);
		for (int i = 0; i < 51; i++)dataProtocol.setValue("jtfAll"+i, "jtfAll"+i);
		for (int i = 0; i < 51; i++)dataProtocol.setValue("jtfKata"+i, "jtfKata"+i);
		for (int i = 0; i < 51; i++)dataProtocol.setValue("jtfNote"+i, "jtfNote"+i);
		dataProtocol.setValue("jtfNameProtocol", "jtfNameProtocol");
		dataProtocol.setValue("jtfNameGame", "jtfNameGame");
		dataProtocol.setValue("jtfVenue", "jtfVenue");
		dataProtocol.setValue("jtfDate", "jtfDate");
		dataProtocol.setValue("jtfAgeGroup", "jtfAgeGroup");
		dataProtocol.setValue("jtfCategory", "jtfCategory");
		dataProtocol.setValue("jtfSex", "jtfSex");
		dataProtocol.setValue("jtfMainReferee", "jtfMainReferee");
		dataProtocol.setValue("jtfReferee2", "jtfReferee2");
		dataProtocol.setValue("jtfReferee3", "jtfReferee3");
		dataProtocol.setValue("jtfReferee4", "jtfReferee4");
		dataProtocol.setValue("jtfReferee5", "jtfReferee5");
		dataProtocol.setValue("jtfMainJudge", "jtfMainJudge");
		dataProtocol.setValue("jtfMainSecretary", "jtfMainSecretary");
	}
	private void initComponents() {
		/*setSize(new Dimension(880, 600));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);*/
		contentPane = this;
		JPanel Protocol = new JPanel();
		Protocol.setPreferredSize(new Dimension(830, 970));
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(Protocol);
		scrollPane.getVerticalScrollBar().setUnitIncrement(25);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		//setContentPane(contentPane);
		
		jlbImage = new JLabel();
		jPanel2 = new JPanel();
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, BorderLayout.CENTER);
		tabbedPane.addTab("\u041F\u0440\u043E\u0442\u043E\u043A\u043E\u043B",
				scrollPane);

		jtfNameProtocol = new JTextField();
		jtfNameProtocol.setFont(new Font("Times New Roman", Font.BOLD, 12));
		jtfNameProtocol.setBounds(10, 10, 805, 20); //NameProtocol
		jtfNameProtocol.setHorizontalAlignment(SwingConstants.CENTER);
		jtfNameProtocol.setColumns(10);
		jtfNameProtocol
				.setText("\u0417 \u041C \u0410 \u0413 \u0410 \u041D \u041D \u042F    \u0406 \u0417    \u041A \u0410 \u0422 \u0410    \u041A \u0410 \u0420 \u0410 \u0422 \u0415");
		Protocol.add(jtfNameProtocol);

		//Start lblInfo
		lblInfo = new JLabel [13];
		lblInfo[0] = new JLabel(
				"\u0414\u0430\u0442\u0430 \u043F\u0440\u043E\u0432\u0435\u0434\u0435\u043D\u043D\u044F:  ");
		lblInfo[0].setBounds(10, 84, 149, 14);
		lblInfo[1] = new JLabel(
				"\u041D\u0430\u0439\u043C\u0435\u043D\u0443\u0432\u0430\u043D\u043D\u044F \u0437\u043C\u0430\u0433\u0430\u043D\u044C:  ");
		lblInfo[1].setBounds(10, 36, 149, 14);
		lblInfo[2] = new JLabel(
				"\u041C\u0456\u0441\u0446\u0435 \u043F\u0440\u043E\u0432\u0435\u0434\u0435\u043D\u043D\u044F:  ");
		lblInfo[2].setBounds(10, 60, 149, 14);
		lblInfo[3] = new JLabel(
				"\u0412\u0456\u043A\u043E\u0432\u0430 \u0433\u0440\u0443\u043F\u0430: ");
		lblInfo[3].setBounds(543, 36, 80, 14);
		lblInfo[4] = new JLabel(
				"\u041A\u0430\u0442\u0435\u0433\u043E\u0440\u0456\u044F: ");
		lblInfo[4].setBounds(543, 60, 80, 14);

		lblInfo[5] = new JLabel("\u0421\u0442\u0430\u0442\u044C: ");
		lblInfo[5].setBounds(543, 84, 80, 14);
		lblInfo[6] = new JLabel(
				"\u0413\u043E\u043B\u043E\u0432\u043D\u0438\u0439 \u0441\u0443\u0434\u0434\u044F  \u0442\u0430\u0442\u0430\u043C\u0456  1: ");
		lblInfo[6].setBounds(206, 113, 171, 14);
		lblInfo[7] = new JLabel("\u0421\u0443\u0434\u0434\u044F 2:");
		lblInfo[7].setBounds(10, 144, 55, 14);
		lblInfo[8] = new JLabel("\u0421\u0443\u0434\u0434\u044F 3:");
		lblInfo[8].setBounds(206, 144, 55, 14);
		lblInfo[9] = new JLabel("\u0421\u0443\u0434\u0434\u044F 4:");
		lblInfo[9].setBounds(426, 144, 55, 14);
		lblInfo[10] = new JLabel("\u0421\u0443\u0434\u0434\u044F 5:");
		lblInfo[10].setBounds(645, 144, 55, 14);
		lblInfo[11] = new JLabel(
				"\u0413\u043E\u043B\u043E\u0432\u043D\u0438\u0439 \u0441\u0443\u0434\u0434\u044F \u0437\u043C\u0430\u0433\u0430\u043D\u044C");
		lblInfo[11].setBounds(10, 911, 170, 14);
		lblInfo[12] = new JLabel(
				"\u0413\u043E\u043B\u043E\u0432\u043D\u0438\u0439 \u0441\u0435\u043A\u0440\u0435\u0442\u0430\u0440 \u0437\u043C\u0430\u0433\u0430\u043D\u044C ");
		lblInfo[12].setBounds(10, 939, 170, 14);
		for(int i = 0; i<13; i++){
			lblInfo[i].setFont(new Font("Times New Roman", Font.BOLD, 12));
			Protocol.add(lblInfo[i]);
		}
		//End lblInfo
		jtfNameGame = new JTextField();
		jtfNameGame.setBounds(179, 33, 197, 20); //Name game
		jtfNameGame.setColumns(10);
		Protocol.add(jtfNameGame);

		jtfVenue = new JTextField();
		jtfVenue.setBounds(179, 58, 197, 20); //Venue
		jtfVenue.setColumns(10);
		Protocol.add(jtfVenue);

		jtfDate = new JTextField();
		jtfDate.setBounds(179, 85, 197, 20); //Date
		jtfDate.setColumns(10);
		Protocol.add(jtfDate);

		jtfAgeGroup = new JTextField();
		jtfAgeGroup.setBounds(644, 33, 171, 20); //AgeGroup
		jtfAgeGroup.setColumns(10);
		Protocol.add(jtfAgeGroup);

		jtfCategory = new JTextField();
		jtfCategory.setBounds(644, 60, 171, 20); //Category
		jtfCategory.setColumns(10);
		Protocol.add(jtfCategory);

		jtfSex = new JTextField();
		jtfSex.setBounds(644, 84, 171, 20); //Sex
		jtfSex.setColumns(10);
		Protocol.add(jtfSex);

		jtfMainReferee = new JTextField();
		jtfMainReferee.setBounds(391, 110, 232, 20); //MainReferee
		jtfMainReferee.setColumns(10);
		Protocol.add(jtfMainReferee);

		jtfReferee2 = new JTextField();
		jtfReferee2.setBounds(69, 141, 111, 20); //Referee 2
		jtfReferee2.setColumns(10);
		Protocol.add(jtfReferee2);
		
		jtfReferee3 = new JTextField();
		jtfReferee3.setColumns(10);
		jtfReferee3.setBounds(265, 141, 111, 20); //Referee 3
		Protocol.add(jtfReferee3);
		
		jtfReferee4 = new JTextField();
		jtfReferee4.setColumns(10);
		jtfReferee4.setBounds(485, 141, 111, 20); //Referee 4
		Protocol.add(jtfReferee4);

		jtfReferee5 = new JTextField();
		jtfReferee5.setColumns(10);
		jtfReferee5.setBounds(704, 141, 111, 20); //Referee 5
		Protocol.add(jtfReferee5);

		jtfMainJudge = new JTextField();
		jtfMainJudge.setBounds(192, 908, 181, 20); //MainHudge
		Protocol.add(jtfMainJudge);
		jtfMainJudge.setColumns(10);

		jtfMainSecretary = new JTextField();
		jtfMainSecretary.setColumns(10);
		jtfMainSecretary.setBounds(192, 936, 181, 20); //MainSecretary
		Protocol.add(jtfMainSecretary);
		
		//Start Number
		jtfNumber = new JTextField[17];
		for (int i = 0; i<17; i++){
			jtfNumber[i] = new JTextField();
			jtfNumber[i].setFont(new Font("Times New Roman", Font.BOLD, 12));
			jtfNumber[i].setHorizontalAlignment(SwingConstants.CENTER);
			jtfNumber[i].setEditable(false);
			jtfNumber[i].setColumns(10);
			Protocol.add(jtfNumber[i]);
		}
		jtfNumber[0].setText("1");
		jtfNumber[0].setBounds(10, 249, 31, 40);
		jtfNumber[1].setText("2");
		jtfNumber[1].setBounds(10, 289, 31, 40);
		jtfNumber[2].setText("3");
		jtfNumber[2].setBounds(10, 329, 31, 40);
		jtfNumber[3].setText("4");
		jtfNumber[3].setBounds(10, 369, 31, 40);
		jtfNumber[4].setText("5");
		jtfNumber[4].setBounds(10, 409, 31, 40);
		jtfNumber[5].setText("6");
		jtfNumber[5].setBounds(10, 449, 31, 40);
		jtfNumber[6].setText("7");
		jtfNumber[6].setBounds(10, 489, 31, 40);
		jtfNumber[7].setText("8");
		jtfNumber[7].setBounds(10, 529, 31, 40);
		jtfNumber[8].setText("9");
		jtfNumber[8].setBounds(10, 569, 31, 40);
		jtfNumber[9].setText("10");
		jtfNumber[9].setBounds(10, 609, 31, 40);
		jtfNumber[10].setText("11");
		jtfNumber[10].setBounds(10, 649, 31, 40);
		jtfNumber[11].setText("12");
		jtfNumber[11].setBounds(10, 689, 31, 40);
		jtfNumber[12].setText("13");
		jtfNumber[12].setBounds(10, 729, 31, 40);
		jtfNumber[13].setText("14");
		jtfNumber[13].setBounds(10, 769, 31, 40);
		jtfNumber[14].setText("15");
		jtfNumber[14].setBounds(10, 809, 31, 40);
		jtfNumber[15].setText("16");
		jtfNumber[15].setBounds(10, 849, 31, 40);
		jtfNumber[16].setText("\u2116");
		jtfNumber[16].setBounds(10, 209, 31, 40);
		//End Number
		//Start Round
		jtfRound = new JTextField[3];
		for (int i = 0; i<3; i++) {
			jtfRound[i] = new JTextField();
			jtfRound[i].setFont(new Font("Times New Roman", Font.BOLD, 12));
			jtfRound[i].setEditable(false);
			jtfRound[i].setHorizontalAlignment(SwingConstants.CENTER);
			jtfRound[i].setColumns(10);
			Protocol.add(jtfRound[i]);
		}
		jtfRound[0].setText("1 \u043A\u043E\u043B\u043E (5,0 \u2013 7,0)");
		jtfRound[0].setBounds(248, 189, 155, 20);
		jtfRound[1].setText("2 \u043A\u043E\u043B\u043E (6,0 \u2013 8,0)");
		jtfRound[1].setBounds(438, 189, 155, 20);
		jtfRound[2].setText("3 \u043A\u043E\u043B\u043E (7,0 \u2013 9,0)");
		jtfRound[2].setBounds(628, 189, 155, 20);
		//End Round
		//Start pointOne
		pointOne = new JTextField[51];
		for(int i=0; i<51; i++){
			pointOne[i] = new JTextField();
			pointOne[i].setColumns(10);
			pointOne[i].setHorizontalAlignment(SwingConstants.CENTER);
			Protocol.add(pointOne[i]);
		}
		pointOne[0].setBounds(248, 249, 31, 20);
		pointOne[1].setBounds(248, 289, 31, 20);
		pointOne[2].setBounds(248, 329, 31, 20);
		pointOne[3].setBounds(248, 369, 31, 20);		
		pointOne[4].setBounds(248, 409, 31, 20);
		pointOne[5].setBounds(248, 449, 31, 20);
		pointOne[6].setBounds(248, 489, 31, 20);
		pointOne[7].setBounds(248, 529, 31, 20);
		pointOne[8].setBounds(248, 569, 31, 20);
		pointOne[9].setBounds(248, 609, 31, 20);
		pointOne[10].setBounds(248, 649, 31, 20);
		pointOne[11].setBounds(248, 689, 31, 20);
		pointOne[12].setBounds(248, 729, 31, 20);
		pointOne[13].setBounds(248, 769, 31, 20);
		pointOne[14].setBounds(248, 809, 31, 20);
		pointOne[15].setBounds(248, 849, 31, 20);
		pointOne[16].setBounds(438, 249, 31, 20);
		pointOne[17].setBounds(438, 289, 31, 20);
		pointOne[18].setBounds(438, 329, 31, 20);
		pointOne[19].setBounds(438, 369, 31, 20);
		pointOne[20].setBounds(438, 409, 31, 20);
		pointOne[21].setBounds(438, 449, 31, 20);
		pointOne[22].setBounds(438, 489, 31, 20);
		pointOne[23].setBounds(438, 529, 31, 20);
		pointOne[24].setBounds(438, 569, 31, 20);
		pointOne[25].setBounds(438, 609, 31, 20);
		pointOne[26].setBounds(438, 649, 31, 20);
		pointOne[27].setBounds(438, 689, 31, 20);
		pointOne[28].setBounds(438, 729, 31, 20);
		pointOne[29].setBounds(438, 769, 31, 20);
		pointOne[30].setBounds(438, 809, 31, 20);
		pointOne[31].setBounds(438, 849, 31, 20);
		pointOne[32].setBounds(628, 249, 31, 20);
		pointOne[33].setBounds(628, 289, 31, 20);
		pointOne[34].setBounds(628, 329, 31, 20);
		pointOne[35].setBounds(628, 369, 31, 20);
		pointOne[36].setBounds(628, 409, 31, 20);
		pointOne[37].setBounds(628, 449, 31, 20);
		pointOne[38].setBounds(628, 489, 31, 20);
		pointOne[39].setBounds(628, 529, 31, 20);
		pointOne[40].setBounds(628, 569, 31, 20);
		pointOne[41].setBounds(628, 609, 31, 20);
		pointOne[42].setBounds(628, 649, 31, 20);
		pointOne[43].setBounds(628, 689, 31, 20);
		pointOne[44].setBounds(628, 729, 31, 20);
		pointOne[45].setBounds(628, 769, 31, 20);
		pointOne[46].setBounds(628, 809, 31, 20);
		pointOne[47].setBounds(628, 849, 31, 20);
		pointOne[48].setFont(new Font("Times New Roman", Font.BOLD, 12));
		pointOne[48].setText("1");
		pointOne[48].setEditable(false);
		pointOne[48].setBounds(248, 209, 31, 20);
		pointOne[49].setFont(new Font("Times New Roman", Font.BOLD, 12));
		pointOne[49].setText("1");
		pointOne[49].setEditable(false);
		pointOne[49].setBounds(438, 209, 31, 20);
		pointOne[50].setFont(new Font("Times New Roman", Font.BOLD, 12));
		pointOne[50].setText("1");
		pointOne[50].setEditable(false);
		pointOne[50].setBounds(628, 209, 31, 20);
		//End pointOne
		//Start pointTwo
		pointTwo = new JTextField[51];
		for (int i = 0; i<51; i++){
			pointTwo[i] = new JTextField();
			pointTwo[i].setColumns(10);
			pointTwo[i].setHorizontalAlignment(SwingConstants.CENTER);
			Protocol.add(pointTwo[i]);
		}
		pointTwo[0].setBounds(279, 249, 31, 20);
		pointTwo[1].setBounds(279, 289, 31, 20);
		pointTwo[2].setBounds(279, 329, 31, 20);
		pointTwo[3].setBounds(279, 369, 31, 20);
		pointTwo[4].setBounds(279, 409, 31, 20);
		pointTwo[5].setBounds(279, 449, 31, 20);
		pointTwo[6].setBounds(279, 489, 31, 20);
		pointTwo[7].setBounds(279, 529, 31, 20);
		pointTwo[8].setBounds(279, 569, 31, 20);
		pointTwo[9].setBounds(279, 609, 31, 20);
		pointTwo[10].setBounds(279, 649, 31, 20);
		pointTwo[11].setBounds(279, 689, 31, 20);
		pointTwo[12].setBounds(279, 729, 31, 20);
		pointTwo[13].setBounds(279, 769, 31, 20);
		pointTwo[14].setBounds(279, 809, 31, 20);
		pointTwo[15].setBounds(279, 849, 31, 20);
		pointTwo[16].setBounds(469, 249, 31, 20);
		pointTwo[17].setBounds(469, 289, 31, 20);
		pointTwo[18].setBounds(469, 329, 31, 20);
		pointTwo[19].setBounds(469, 369, 31, 20);
		pointTwo[20].setBounds(469, 409, 31, 20);
		pointTwo[21].setBounds(469, 449, 31, 20);
		pointTwo[22].setBounds(469, 489, 31, 20);
		pointTwo[23].setBounds(469, 529, 31, 20);
		pointTwo[24].setBounds(469, 569, 31, 20);
		pointTwo[25].setBounds(469, 609, 31, 20);
		pointTwo[26].setBounds(469, 649, 31, 20);
		pointTwo[27].setBounds(469, 689, 31, 20);
		pointTwo[28].setBounds(469, 729, 31, 20);
		pointTwo[29].setBounds(469, 769, 31, 20);
		pointTwo[30].setBounds(469, 809, 31, 20);
		pointTwo[31].setBounds(469, 849, 31, 20);
		pointTwo[32].setBounds(659, 249, 31, 20);
		pointTwo[33].setBounds(659, 289, 31, 20);
		pointTwo[34].setBounds(659, 329, 31, 20);
		pointTwo[35].setBounds(659, 369, 31, 20);
		pointTwo[36].setBounds(659, 409, 31, 20);
		pointTwo[37].setBounds(659, 449, 31, 20);
		pointTwo[38].setBounds(659, 489, 31, 20);
		pointTwo[39].setBounds(659, 529, 31, 20);
		pointTwo[40].setBounds(659, 569, 31, 20);
		pointTwo[41].setBounds(659, 609, 31, 20);
		pointTwo[42].setBounds(659, 649, 31, 20);
		pointTwo[43].setBounds(659, 689, 31, 20);
		pointTwo[44].setBounds(659, 729, 31, 20);
		pointTwo[45].setBounds(659, 769, 31, 20);
		pointTwo[46].setBounds(659, 809, 31, 20);
		pointTwo[47].setBounds(659, 849, 31, 20);
		pointTwo[48].setFont(new Font("Times New Roman", Font.BOLD, 12));
		pointTwo[48].setText("2");
		pointTwo[48].setEditable(false);
		pointTwo[48].setBounds(279, 209, 31, 20);
		pointTwo[49].setFont(new Font("Times New Roman", Font.BOLD, 12));
		pointTwo[49].setText("2");
		pointTwo[49].setEditable(false);
		pointTwo[49].setBounds(469, 209, 31, 20);
		pointTwo[50].setFont(new Font("Times New Roman", Font.BOLD, 12));
		pointTwo[50].setText("2");
		pointTwo[50].setEditable(false);
		pointTwo[50].setBounds(659, 209, 31, 20);
		//End pointTwo
		//Start pointThree
		pointThree = new JTextField[51];
		for(int i = 0; i<51; i++){
			pointThree[i] = new JTextField();
			pointThree[i].setColumns(10);
			pointThree[i].setHorizontalAlignment(SwingConstants.CENTER);
			Protocol.add(pointThree[i]);
		}
		pointThree[0].setBounds(310, 249, 31, 20);
		pointThree[1].setBounds(310, 289, 31, 20);
		pointThree[2].setBounds(310, 329, 31, 20);
		pointThree[3].setBounds(310, 369, 31, 20);
		pointThree[4].setBounds(310, 409, 31, 20);
		pointThree[5].setBounds(310, 449, 31, 20);
		pointThree[6].setBounds(310, 489, 31, 20);
		pointThree[7].setBounds(310, 529, 31, 20);
		pointThree[8].setBounds(310, 569, 31, 20);
		pointThree[9].setBounds(310, 609, 31, 20);
		pointThree[10].setBounds(310, 649, 31, 20);
		pointThree[11].setBounds(310, 689, 31, 20);
		pointThree[12].setBounds(310, 729, 31, 20);
		pointThree[13].setBounds(310, 769, 31, 20);
		pointThree[14].setBounds(310, 809, 31, 20);
		pointThree[15].setBounds(310, 849, 31, 20);
		pointThree[16].setBounds(500, 249, 31, 20);
		pointThree[17].setBounds(500, 289, 31, 20);
		pointThree[18].setBounds(500, 329, 31, 20);
		pointThree[19].setBounds(500, 369, 31, 20);
		pointThree[20].setBounds(500, 409, 31, 20);
		pointThree[21].setBounds(500, 449, 31, 20);
		pointThree[22].setBounds(500, 489, 31, 20);
		pointThree[23].setBounds(500, 529, 31, 20);
		pointThree[24].setBounds(500, 569, 31, 20);
		pointThree[25].setBounds(500, 609, 31, 20);
		pointThree[26].setBounds(500, 649, 31, 20);
		pointThree[27].setBounds(500, 689, 31, 20);
		pointThree[28].setBounds(500, 729, 31, 20);
		pointThree[29].setBounds(500, 769, 31, 20);
		pointThree[30].setBounds(500, 809, 31, 20);
		pointThree[31].setBounds(500, 849, 31, 20);
		pointThree[32].setBounds(690, 249, 31, 20);
		pointThree[33].setBounds(690, 289, 31, 20);
		pointThree[34].setBounds(690, 329, 31, 20);
		pointThree[35].setBounds(690, 369, 31, 20);
		pointThree[36].setBounds(690, 409, 31, 20);
		pointThree[37].setBounds(690, 449, 31, 20);
		pointThree[38].setBounds(690, 489, 31, 20);
		pointThree[39].setBounds(690, 529, 31, 20);
		pointThree[40].setBounds(690, 569, 31, 20);
		pointThree[41].setBounds(690, 609, 31, 20);
		pointThree[42].setBounds(690, 649, 31, 20);
		pointThree[43].setBounds(690, 689, 31, 20);
		pointThree[44].setBounds(690, 729, 31, 20);
		pointThree[45].setBounds(690, 769, 31, 20);
		pointThree[46].setBounds(690, 809, 31, 20);
		pointThree[47].setBounds(690, 849, 31, 20);
		pointThree[48].setFont(new Font("Times New Roman", Font.BOLD, 12));
		pointThree[48].setText("3");
		pointThree[48].setEditable(false);
		pointThree[48].setBounds(310, 209, 31, 20);
		pointThree[49].setFont(new Font("Times New Roman", Font.BOLD, 12));
		pointThree[49].setText("3");
		pointThree[49].setEditable(false);
		pointThree[49].setBounds(500, 209, 31, 20);
		pointThree[50].setFont(new Font("Times New Roman", Font.BOLD, 12));
		pointThree[50].setText("3");
		pointThree[50].setEditable(false);
		pointThree[50].setBounds(690, 209, 31, 20);
		//End pointThree
		//Start pointFour
		pointFour = new JTextField[51];
		for(int i = 0; i<51; i++){
			pointFour[i] = new JTextField();
			pointFour[i].setColumns(10);
			pointFour[i].setHorizontalAlignment(SwingConstants.CENTER);
			Protocol.add(pointFour[i]);
		}
		pointFour[0].setBounds(341, 249, 31, 20);
		pointFour[1].setBounds(341, 289, 31, 20);
		pointFour[2].setBounds(341, 329, 31, 20);
		pointFour[3].setBounds(341, 369, 31, 20);
		pointFour[4].setBounds(341, 409, 31, 20);
		pointFour[5].setBounds(341, 449, 31, 20);
		pointFour[6].setBounds(341, 489, 31, 20);
		pointFour[7].setBounds(341, 529, 31, 20);
		pointFour[8].setBounds(341, 569, 31, 20);
		pointFour[9].setBounds(341, 609, 31, 20);
		pointFour[10].setBounds(341, 649, 31, 20);
		pointFour[11].setBounds(341, 689, 31, 20);
		pointFour[12].setBounds(341, 729, 31, 20);
		pointFour[13].setBounds(341, 769, 31, 20);
		pointFour[14].setBounds(341, 809, 31, 20);
		pointFour[15].setBounds(341, 849, 31, 20);
		pointFour[16].setBounds(531, 249, 31, 20);
		pointFour[17].setBounds(531, 289, 31, 20);
		pointFour[18].setBounds(531, 329, 31, 20);
		pointFour[19].setBounds(531, 369, 31, 20);
		pointFour[20].setBounds(531, 409, 31, 20);
		pointFour[21].setBounds(531, 449, 31, 20);
		pointFour[22].setBounds(531, 489, 31, 20);
		pointFour[23].setBounds(531, 529, 31, 20);
		pointFour[24].setBounds(531, 569, 31, 20);
		pointFour[25].setBounds(531, 609, 31, 20);
		pointFour[26].setBounds(531, 649, 31, 20);
		pointFour[27].setBounds(531, 689, 31, 20);
		pointFour[28].setBounds(531, 729, 31, 20);
		pointFour[29].setBounds(531, 769, 31, 20);
		pointFour[30].setBounds(531, 809, 31, 20);
		pointFour[31].setBounds(531, 849, 31, 20);
		pointFour[32].setBounds(721, 249, 31, 20);
		pointFour[33].setBounds(721, 289, 31, 20);
		pointFour[34].setBounds(721, 329, 31, 20);
		pointFour[35].setBounds(721, 369, 31, 20);
		pointFour[36].setBounds(721, 409, 31, 20);
		pointFour[37].setBounds(721, 449, 31, 20);
		pointFour[38].setBounds(721, 489, 31, 20);
		pointFour[39].setBounds(721, 529, 31, 20);
		pointFour[40].setBounds(721, 569, 31, 20);
		pointFour[41].setBounds(721, 609, 31, 20);
		pointFour[42].setBounds(721, 649, 31, 20);
		pointFour[43].setBounds(721, 689, 31, 20);
		pointFour[44].setBounds(721, 729, 31, 20);
		pointFour[45].setBounds(721, 769, 31, 20);
		pointFour[46].setBounds(721, 809, 31, 20);
		pointFour[47].setBounds(721, 849, 31, 20);
		pointFour[48].setFont(new Font("Times New Roman", Font.BOLD, 12));
		pointFour[48].setText("4");
		pointFour[48].setEditable(false);
		pointFour[48].setBounds(341, 209, 31, 20);
		pointFour[49].setFont(new Font("Times New Roman", Font.BOLD, 12));
		pointFour[49].setText("4");
		pointFour[49].setEditable(false);
		pointFour[49].setBounds(531, 209, 31, 20);
		pointFour[50].setFont(new Font("Times New Roman", Font.BOLD, 12));
		pointFour[50].setText("4");
		pointFour[50].setEditable(false);
		pointFour[50].setBounds(721, 209, 31, 20);
		//End pointFour
		//Start pointFive
		pointFive = new JTextField[51];
		for (int i = 0; i<51; i++){
			pointFive[i] = new JTextField();
			pointFive[i].setColumns(10);
			pointFive[i].setHorizontalAlignment(SwingConstants.CENTER);
			Protocol.add(pointFive[i]);
		}
		pointFive[0].setBounds(372, 249, 31, 20);
		pointFive[1].setBounds(372, 289, 31, 20);
		pointFive[2].setBounds(372, 329, 31, 20);
		pointFive[3].setBounds(372, 369, 31, 20);
		pointFive[4].setBounds(372, 409, 31, 20);
		pointFive[5].setBounds(372, 449, 31, 20);
		pointFive[6].setBounds(372, 489, 31, 20);
		pointFive[7].setBounds(372, 529, 31, 20);
		pointFive[8].setBounds(372, 569, 31, 20);
		pointFive[9].setBounds(372, 609, 31, 20);
		pointFive[10].setBounds(372, 649, 31, 20);
		pointFive[11].setBounds(372, 689, 31, 20);
		pointFive[12].setBounds(372, 729, 31, 20);
		pointFive[13].setBounds(372, 769, 31, 20);
		pointFive[14].setBounds(372, 809, 31, 20);
		pointFive[15].setBounds(372, 849, 31, 20);
		pointFive[16].setBounds(562, 249, 31, 20);
		pointFive[17].setBounds(562, 289, 31, 20);
		pointFive[18].setBounds(562, 329, 31, 20);
		pointFive[19].setBounds(562, 369, 31, 20);
		pointFive[20].setBounds(562, 409, 31, 20);
		pointFive[21].setBounds(562, 449, 31, 20);
		pointFive[22].setBounds(562, 489, 31, 20);
		pointFive[23].setBounds(562, 529, 31, 20);
		pointFive[24].setBounds(562, 569, 31, 20);
		pointFive[25].setBounds(562, 609, 31, 20);
		pointFive[26].setBounds(562, 649, 31, 20);
		pointFive[27].setBounds(562, 689, 31, 20);
		pointFive[28].setBounds(562, 729, 31, 20);
		pointFive[29].setBounds(562, 769, 31, 20);
		pointFive[30].setBounds(562, 809, 31, 20);
		pointFive[31].setBounds(562, 849, 31, 20);
		pointFive[32].setBounds(752, 249, 31, 20);
		pointFive[33].setBounds(752, 289, 31, 20);
		pointFive[34].setBounds(752, 329, 31, 20);
		pointFive[35].setBounds(752, 369, 31, 20);
		pointFive[36].setBounds(752, 409, 31, 20);
		pointFive[37].setBounds(752, 449, 31, 20);
		pointFive[38].setBounds(752, 489, 31, 20);
		pointFive[39].setBounds(752, 529, 31, 20);
		pointFive[40].setBounds(752, 569, 31, 20);
		pointFive[41].setBounds(752, 609, 31, 20);
		pointFive[42].setBounds(752, 649, 31, 20);
		pointFive[43].setBounds(752, 689, 31, 20);
		pointFive[44].setBounds(752, 729, 31, 20);
		pointFive[45].setBounds(752, 769, 31, 20);
		pointFive[46].setBounds(752, 809, 31, 20);
		pointFive[47].setBounds(752, 849, 31, 20);
		pointFive[48].setFont(new Font("Times New Roman", Font.BOLD, 12));
		pointFive[48].setText("5");
		pointFive[48].setEditable(false);
		pointFive[48].setBounds(372, 209, 31, 20);
		pointFive[49].setFont(new Font("Times New Roman", Font.BOLD, 12));
		pointFive[49].setText("5");
		pointFive[49].setEditable(false);
		pointFive[49].setBounds(562, 209, 31, 20);
		pointFive[50].setFont(new Font("Times New Roman", Font.BOLD, 12));
		pointFive[50].setText("5");
		pointFive[50].setEditable(false);
		pointFive[50].setBounds(752, 209, 31, 20);
		//End pointFive
		//Start Kata
		jtfKata = new JTextField[51];
		for (int i = 0; i<51; i++){
			jtfKata[i] = new JTextField();
			jtfKata[i].setColumns(10);
			jtfKata[i].setHorizontalAlignment(SwingConstants.CENTER);
			Protocol.add(jtfKata[i]);
		}
		jtfKata[0].setBounds(248, 269, 93, 20);
		jtfKata[1].setBounds(248, 309, 93, 20);
		jtfKata[2].setBounds(248, 349, 93, 20);
		jtfKata[3].setBounds(248, 389, 93, 20);
		jtfKata[4].setBounds(248, 429, 93, 20);
		jtfKata[5].setBounds(248, 469, 93, 20);
		jtfKata[6].setBounds(248, 509, 93, 20);
		jtfKata[7].setBounds(248, 549, 93, 20);
		jtfKata[8].setBounds(248, 589, 93, 20);
		jtfKata[9].setBounds(248, 629, 93, 20);
		jtfKata[10].setBounds(248, 669, 93, 20);
		jtfKata[11].setBounds(248, 709, 93, 20);
		jtfKata[12].setBounds(248, 749, 93, 20);
		jtfKata[13].setBounds(248, 789, 93, 20);
		jtfKata[14].setBounds(248, 829, 93, 20);
		jtfKata[15].setBounds(248, 869, 93, 20);
		jtfKata[16].setBounds(438, 269, 93, 20);
		jtfKata[17].setBounds(438, 309, 93, 20);
		jtfKata[18].setBounds(438, 349, 93, 20);
		jtfKata[19].setBounds(438, 389, 93, 20);
		jtfKata[20].setBounds(438, 429, 93, 20);
		jtfKata[21].setBounds(438, 469, 93, 20);
		jtfKata[22].setBounds(438, 509, 93, 20);
		jtfKata[23].setBounds(438, 549, 93, 20);
		jtfKata[24].setBounds(438, 589, 93, 20);
		jtfKata[25].setBounds(438, 629, 93, 20);
		jtfKata[26].setBounds(438, 669, 93, 20);
		jtfKata[27].setBounds(438, 709, 93, 20);
		jtfKata[28].setBounds(438, 749, 93, 20);
		jtfKata[29].setBounds(438, 789, 93, 20);
		jtfKata[30].setBounds(438, 829, 93, 20);
		jtfKata[31].setBounds(438, 869, 93, 20);
		jtfKata[32].setBounds(628, 269, 93, 20);
		jtfKata[33].setBounds(628, 309, 93, 20);
		jtfKata[34].setBounds(628, 349, 93, 20);
		jtfKata[35].setBounds(628, 389, 93, 20);
		jtfKata[36].setBounds(628, 429, 93, 20);
		jtfKata[37].setBounds(628, 469, 93, 20);
		jtfKata[38].setBounds(628, 509, 93, 20);
		jtfKata[39].setBounds(628, 549, 93, 20);
		jtfKata[40].setBounds(628, 589, 93, 20);
		jtfKata[41].setBounds(628, 629, 93, 20);
		jtfKata[42].setBounds(628, 669, 93, 20);
		jtfKata[43].setBounds(628, 709, 93, 20);
		jtfKata[44].setBounds(628, 749, 93, 20);
		jtfKata[45].setBounds(628, 789, 93, 20);
		jtfKata[46].setBounds(628, 829, 93, 20);
		jtfKata[47].setBounds(628, 869, 93, 20);
		jtfKata[48].setFont(new Font("Times New Roman", Font.BOLD, 12));
		jtfKata[48].setText("\u041A\u0430\u0442\u0430");
		jtfKata[48].setEditable(false);
		jtfKata[48].setBounds(248, 229, 93, 20);
		jtfKata[49].setFont(new Font("Times New Roman", Font.BOLD, 12));
		jtfKata[49].setText("\u041A\u0430\u0442\u0430");
		jtfKata[49].setEditable(false);
		jtfKata[49].setBounds(438, 229, 93, 20);
		jtfKata[50].setFont(new Font("Times New Roman", Font.BOLD, 12));
		jtfKata[50].setText("\u041A\u0430\u0442\u0430");
		jtfKata[50].setEditable(false);
		jtfKata[50].setBounds(628, 229, 93, 20);
		//End Kata
		//Start All
		jtfAll = new JTextField[51];
		for (int i = 0; i<51; i++){
			jtfAll[i] = new JTextField();		
			jtfAll[i].setColumns(10);
			jtfAll[i].setHorizontalAlignment(SwingConstants.CENTER);
			Protocol.add(jtfAll[i]);
		}
		jtfAll[0].setBounds(341, 269, 62, 20);
		jtfAll[1].setBounds(341, 309, 62, 20);
		jtfAll[2].setBounds(341, 349, 62, 20);
		jtfAll[3].setBounds(341, 389, 62, 20);
		jtfAll[4].setBounds(341, 429, 62, 20);
		jtfAll[5].setBounds(341, 469, 62, 20);
		jtfAll[6].setBounds(341, 509, 62, 20);
		jtfAll[7].setBounds(341, 549, 62, 20);
		jtfAll[8].setBounds(341, 589, 62, 20);
		jtfAll[9].setBounds(341, 629, 62, 20);
		jtfAll[10].setBounds(341, 669, 62, 20);
		jtfAll[11].setBounds(341, 709, 62, 20);
		jtfAll[12].setBounds(341, 749, 62, 20);
		jtfAll[13].setBounds(341, 789, 62, 20);
		jtfAll[14].setBounds(341, 829, 62, 20);
		jtfAll[15].setBounds(341, 869, 62, 20);
		jtfAll[16].setBounds(531, 269, 62, 20);
		jtfAll[17].setBounds(531, 309, 62, 20);
		jtfAll[18].setBounds(531, 349, 62, 20);
		jtfAll[19].setBounds(531, 389, 62, 20);
		jtfAll[20].setBounds(531, 429, 62, 20);
		jtfAll[21].setBounds(531, 469, 62, 20);
		jtfAll[22].setBounds(531, 509, 62, 20);
		jtfAll[23].setBounds(531, 549, 62, 20);
		jtfAll[24].setBounds(531, 589, 62, 20);
		jtfAll[25].setBounds(531, 669, 62, 20);
		jtfAll[26].setBounds(531, 629, 62, 20);
		jtfAll[27].setBounds(531, 709, 62, 20);
		jtfAll[28].setBounds(531, 749, 62, 20);
		jtfAll[29].setBounds(531, 789, 62, 20);
		jtfAll[30].setBounds(531, 829, 62, 20);
		jtfAll[31].setBounds(531, 869, 62, 20);
		jtfAll[32].setBounds(721, 269, 62, 20);
		jtfAll[33].setBounds(721, 309, 62, 20);
		jtfAll[34].setBounds(721, 349, 62, 20);
		jtfAll[35].setBounds(721, 389, 62, 20);
		jtfAll[36].setBounds(721, 429, 62, 20);
		jtfAll[37].setBounds(721, 469, 62, 20);
		jtfAll[38].setBounds(721, 509, 62, 20);
		jtfAll[39].setBounds(721, 549, 62, 20);
		jtfAll[40].setBounds(721, 589, 62, 20);
		jtfAll[41].setBounds(721, 629, 62, 20);
		jtfAll[42].setBounds(721, 669, 62, 20);
		jtfAll[43].setBounds(721, 709, 62, 20);
		jtfAll[44].setBounds(721, 749, 62, 20);
		jtfAll[45].setBounds(721, 789, 62, 20);
		jtfAll[46].setBounds(721, 829, 62, 20);
		jtfAll[47].setBounds(721, 869, 62, 20);
		jtfAll[48].setFont(new Font("Times New Roman", Font.BOLD, 12));
		jtfAll[48].setText("\u0412\u0441\u044C\u043E\u0433\u043E");
		jtfAll[48].setEditable(false);
		jtfAll[48].setBounds(341, 229, 62, 20);
		jtfAll[49].setFont(new Font("Times New Roman", Font.BOLD, 12));
		jtfAll[49].setText("\u0412\u0441\u044C\u043E\u0433\u043E");
		jtfAll[49].setEditable(false);
		jtfAll[49].setBounds(531, 229, 62, 20);
		jtfAll[50].setFont(new Font("Times New Roman", Font.BOLD, 12));
		jtfAll[50].setText("\u0412\u0441\u044C\u043E\u0433\u043E");
		jtfAll[50].setEditable(false);
		jtfAll[50].setBounds(721, 229, 62, 20);
		//End All
		//Start Note
		jtfNote = new JTextField[51];
		for (int i = 0; i<51; i++){
			jtfNote[i] = new JTextField();
			jtfNote[i].setColumns(10);
			jtfNote[i].setHorizontalAlignment(SwingConstants.CENTER);
			Protocol.add(jtfNote[i]);
		}
		jtfNote[0].setBounds(402, 249, 36, 40);
		jtfNote[1].setBounds(402, 289, 36, 40);
		jtfNote[2].setBounds(402, 329, 36, 40);
		jtfNote[3].setBounds(402, 369, 36, 40);
		jtfNote[4].setBounds(402, 409, 36, 40);
		jtfNote[5].setBounds(402, 449, 36, 40);
		jtfNote[6].setBounds(402, 489, 36, 40);
		jtfNote[7].setBounds(402, 529, 36, 40);
		jtfNote[8].setBounds(402, 569, 36, 40);
		jtfNote[9].setBounds(402, 609, 36, 40);
		jtfNote[10].setBounds(402, 649, 36, 40);
		jtfNote[11].setBounds(402, 689, 36, 40);
		jtfNote[12].setBounds(402, 729, 36, 40);
		jtfNote[13].setBounds(402, 769, 36, 40);
		jtfNote[14].setBounds(402, 809, 36, 40);
		jtfNote[15].setBounds(402, 849, 36, 40);
		jtfNote[16].setBounds(592, 249, 36, 40);
		jtfNote[17].setBounds(592, 289, 36, 40);
		jtfNote[18].setBounds(592, 329, 36, 40);
		jtfNote[19].setBounds(592, 369, 36, 40);
		jtfNote[20].setBounds(592, 409, 36, 40);
		jtfNote[21].setBounds(592, 449, 36, 40);
		jtfNote[22].setBounds(592, 489, 36, 40);
		jtfNote[23].setBounds(592, 529, 36, 40);
		jtfNote[24].setBounds(592, 569, 36, 40);
		jtfNote[25].setBounds(592, 609, 36, 40);
		jtfNote[26].setBounds(592, 649, 36, 40);
		jtfNote[27].setBounds(592, 689, 36, 40);
		jtfNote[28].setBounds(592, 729, 36, 40);
		jtfNote[29].setBounds(592, 769, 36, 40);
		jtfNote[30].setBounds(592, 809, 36, 40);
		jtfNote[31].setBounds(592, 849, 36, 40);
		jtfNote[32].setBounds(782, 249, 36, 40);
		jtfNote[33].setBounds(782, 289, 36, 40);
		jtfNote[34].setBounds(782, 329, 36, 40);
		jtfNote[35].setBounds(782, 369, 36, 40);
		jtfNote[36].setBounds(782, 409, 36, 40);
		jtfNote[37].setBounds(782, 449, 36, 40);
		jtfNote[38].setBounds(782, 489, 36, 40);
		jtfNote[39].setBounds(782, 529, 36, 40);
		jtfNote[40].setBounds(782, 569, 36, 40);
		jtfNote[41].setBounds(782, 609, 36, 40);
		jtfNote[42].setBounds(782, 649, 36, 40);
		jtfNote[43].setBounds(782, 689, 36, 40);
		jtfNote[44].setBounds(782, 729, 36, 40);
		jtfNote[45].setBounds(782, 769, 36, 40);
		jtfNote[46].setBounds(782, 809, 36, 40);
		jtfNote[47].setBounds(782, 849, 36, 40);
		jtfNote[48].setToolTipText("\u041F\u0440\u0438\u043C\u0456\u0442\u043A\u0430");
		jtfNote[48].setText("\u041F\u0440\u0438\u043C\u0456\u0442\u043A\u0430");
		jtfNote[48].setFont(new Font("Times New Roman", Font.BOLD, 12));
		jtfNote[48].setEditable(false);
		jtfNote[48].setBounds(402, 189, 36, 60);
		jtfNote[49].setToolTipText("\u041F\u0440\u0438\u043C\u0456\u0442\u043A\u0430");
		jtfNote[49].setFont(new Font("Times New Roman", Font.BOLD, 12));
		jtfNote[49].setText("\u041F\u0440\u0438\u043C\u0456\u0442\u043A\u0430");
		jtfNote[49].setEditable(false);
		jtfNote[49].setBounds(592, 189, 36, 60);
		jtfNote[50].setToolTipText("\u041F\u0440\u0438\u043C\u0456\u0442\u043A\u0430");
		jtfNote[50].setText("\u041F\u0440\u0438\u043C\u0456\u0442\u043A\u0430");
		jtfNote[50].setFont(new Font("Times New Roman", Font.BOLD, 12));
		jtfNote[50].setEditable(false);
		jtfNote[50].setBounds(782, 189, 36, 60);
		//End Note
		Protocol.setLayout(null);		
		//Start Name and Organization
		textPaneNameOrganization = new JTextPane[17];
		
		for(int i=0; i<17; i++){
			textPaneNameOrganization[i] = new JTextPane();
			StyledDocument doc = textPaneNameOrganization[i].getStyledDocument();
			SimpleAttributeSet center = new SimpleAttributeSet();
			StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
			doc.setParagraphAttributes(0, doc.getLength(), center, false);
			textPaneNameOrganization[i].setBorder(new LineBorder(Color.LIGHT_GRAY));
			Protocol.add(textPaneNameOrganization[i]);
		}
		textPaneNameOrganization[0].setBounds(41, 249, 207, 40);
		textPaneNameOrganization[1].setBounds(41, 289, 207, 40);
		textPaneNameOrganization[2].setBounds(41, 329, 207, 40);
		textPaneNameOrganization[3].setBounds(41, 369, 207, 40);
		textPaneNameOrganization[4].setBounds(41, 409, 207, 40);
		textPaneNameOrganization[5].setBounds(41, 449, 207, 40);
		textPaneNameOrganization[6].setBounds(41, 489, 207, 40);
		textPaneNameOrganization[7].setBounds(41, 529, 207, 40);
		textPaneNameOrganization[8].setBounds(41, 569, 207, 40);
		textPaneNameOrganization[9].setBounds(41, 609, 207, 40);
		textPaneNameOrganization[10].setBounds(41, 649, 207, 40);
		textPaneNameOrganization[11].setBounds(41, 689, 207, 40);
		textPaneNameOrganization[12].setBounds(41, 729, 207, 40);
		textPaneNameOrganization[13].setBounds(41, 769, 207, 40);
		textPaneNameOrganization[14].setBounds(41, 809, 207, 40);
		textPaneNameOrganization[15].setBounds(41, 849, 207, 40);
		textPaneNameOrganization[16].setFont(new Font("Times New Roman", Font.BOLD,12));
		textPaneNameOrganization[16].setEditable(false);
		textPaneNameOrganization[16]
				.setText("\u041F\u0440\u0456\u0437\u0432\u0438\u0449\u0435 \u0442\u0430 \u0456\u043C'\u044F \r\n(\u043E\u0440\u0433\u0430\u043D\u0456\u0437\u0430\u0446\u0456\u044F)");
		textPaneNameOrganization[16].setBorder(new LineBorder(Color.LIGHT_GRAY));
		textPaneNameOrganization[16].setBounds(41, 209, 207, 40);
		//End Name and Organization

		JScrollPane scrollPane_1 = new JScrollPane();	
		pdfDecoder = new PdfDecoder(true);
		scrollPane_1.getVerticalScrollBar().setUnitIncrement(20);
		tabbedPane.addTab("Перегляд протоколу", scrollPane_1);
		jPanel2.setLayout(new FlowLayout());
        jPanel2.add(jlbImage);
        jPanel2.setComponentPopupMenu(PopupMenu());
        scrollPane_1.setViewportView(jPanel2);
        tabbedPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            	 if (tabbedPane.getSelectedIndex() == 1) {
                     new Thread(new Runnable() {
                         @Override
                         public void run() {
                        	 tabbedPane.setEnabledAt(0, false);
                        	 saveProtocolKata();
                             viewProtocol(new KataProtocol(dataProtocol.getDataProtocol()).getByteArrayPDF());
                             tabbedPane.setEnabledAt(0, true);
                         }
                     }).start();
                 }
            }
        });
	}
	private void viewProtocol(byte[] bytes) {
        FontMappings.setFontReplacements(); //ensure non-embedded font map to sensible replacements
        try {
            pdfDecoder.openPdfArray(bytes);
            pdfDecoder.decodePage(1);
            pdfDecoder.setPageParameters(1.5f, 1); //values scaling (1=100%). page number
            jlbImage.setIcon(new ImageIcon(pdfDecoder.getPageAsImage(1)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	 private JPopupMenu PopupMenu () {
			JPopupMenu popupMenu = new JPopupMenu();
			Action zoom50 = new AbstractAction("75 %") {
				@Override
				public void actionPerformed(ActionEvent e) {
					viewProtocol(new KataProtocol(dataProtocol.getDataProtocol()).getByteArrayPDF());
					pdfDecoder.setPageParameters(0.75f,1);
					try {
						jlbImage.setIcon(new ImageIcon(pdfDecoder.getPageAsImage(1)));
					} catch (PdfException e1) {
						e1.printStackTrace();
					}
				}
			};
			Action zoom100 = new AbstractAction("100 %") {
				@Override
				public void actionPerformed(ActionEvent e) {
					viewProtocol(new KataProtocol(dataProtocol.getDataProtocol()).getByteArrayPDF());
					pdfDecoder.setPageParameters(1.0f,1);
					try {
						jlbImage.setIcon(new ImageIcon(pdfDecoder.getPageAsImage(1)));
					} catch (PdfException e1) {
						e1.printStackTrace();
					}
				}
			};
			Action zoom150 = new AbstractAction("150 %") {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					viewProtocol(new KataProtocol(dataProtocol.getDataProtocol()).getByteArrayPDF());
					pdfDecoder.setPageParameters(1.5f,1);
					try {
						jlbImage.setIcon(new ImageIcon(pdfDecoder.getPageAsImage(1)));
					} catch (PdfException e1) {
						e1.printStackTrace();
					}
				}
			};
			Action zoom200 = new AbstractAction("200 %") {
				@Override
			
			public void actionPerformed(ActionEvent arg0) {
					viewProtocol(new KataProtocol(dataProtocol.getDataProtocol()).getByteArrayPDF());
					pdfDecoder.setPageParameters(2.0f,1);
					try {
						jlbImage.setIcon(new ImageIcon(pdfDecoder.getPageAsImage(1)));
					} catch (PdfException e1) {
						e1.printStackTrace();
					}
				}
			};
			Action saveProtocol = new AbstractAction("Зберегти протокол"){

				@Override
				public void actionPerformed(ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setAcceptAllFileFilterUsed(false);
					int chooser = fileChooser.showSaveDialog(contentPane);
					if(!(chooser == JFileChooser.CANCEL_OPTION))
					try {
						String path = Paths.get(fileChooser.getCurrentDirectory().getAbsolutePath(), String.format("%s.pdf",fileChooser.getSelectedFile().getName())).toString();
						FileOutputStream fos = new FileOutputStream(path);
						fos.write(new KataProtocol(dataProtocol.getDataProtocol()).getByteArrayPDF());
						fos.close();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			};
			Action printProtocol = new AbstractAction("Роздрукувати"){
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						PDDocument.load(new ByteArrayInputStream(new KataProtocol(dataProtocol.getDataProtocol()).getByteArrayPDF())).print();
					} catch (PrinterException | IOException ex) {
						ex.printStackTrace();
					}
				}
			};
			popupMenu.add(zoom50);
			popupMenu.add(zoom100);
			popupMenu.add(zoom150);
			popupMenu.add(zoom200);
			popupMenu.add(saveProtocol);
			popupMenu.add(printProtocol);
			return popupMenu;
	 }
}