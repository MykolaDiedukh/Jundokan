package jundokan.view;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.text.JTextComponent;

import com.toedter.calendar.JDateChooser;

public abstract class ValidationComponents {
	public static boolean isEmpty(JTextComponent text) {
		if (text.getText().equals("")) {
			text.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.RED));
			return false;
		}
		text.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GREEN));
		return true;
	}

	public static boolean isEmpty(JComboBox combo) {
		if (combo.getSelectedItem() == null || combo.getSelectedItem().equals("")) {
			combo.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.RED));
			return false;
		}
		combo.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GREEN));
		return true;
	}

	public static boolean isEmpty(JDateChooser date) {
		if (date.getDate() == null) {
			date.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.RED));
			return false;
		}
		date.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GREEN));
		return true;
	}
}
