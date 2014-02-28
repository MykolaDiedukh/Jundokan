package jundokan.view;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;

public class PanelStepThree extends JPanel{
	private static PanelStepThree instance;

	private PanelStepThree() {
		setLayout(new BorderLayout(0, 0));
		
		JLabel lblAnimationviiiiiiiiiiiiiiiiiiiiiiiii = new JLabel("Animation (Viiiiiiiiiiiiiiiiiiiiiiiii)");
		add(lblAnimationviiiiiiiiiiiiiiiiiiiiiiiii, BorderLayout.CENTER);
		
	}
	public static PanelStepThree getInstance(){
		return instance == null ? new PanelStepThree() : instance;
	}
}
