package jundokan.view;

import java.awt.Dimension;
import java.awt.Image;
import javax.swing.ImageIcon;

public abstract class ScalingImage{
	
	public ScalingImage() {		
		/*	image = getScalingImage(ImageIO.read(new FileInputStream("com\\jundokan\\resource\\IFmUel68ihs.jpg")), 
					new Dimension(45, 45));*/
	}
	public static ImageIcon getScalingImage(Image image, Dimension dim){		
		return new ImageIcon(image.getScaledInstance(dim.width, dim.height, Image.SCALE_SMOOTH)); //SCALE_FAST
	}
	
	public static long getNameFoto(){ 
		return  new java.util.Date().getTime();
	}
}
