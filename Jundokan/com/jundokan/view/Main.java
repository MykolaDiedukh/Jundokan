package jundokan.view;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Set "System" - style for openDialog.
				} catch (Exception e1) {
					e1.printStackTrace();
				}
                new GUIQuickStart().setVisible(true);
            }
        });

	}

}
