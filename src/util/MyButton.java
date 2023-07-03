package util;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MyButton extends JButton implements MouseListener{
	ImageIcon image,image2;
	
	public MyButton(String name,String type) {
		image = new ImageIcon("source/"+name+"."+type);
		image2 = new ImageIcon("source/"+name+"2."+type);
		setIcon(image);
		setBorder(null); 
		setContentAreaFilled(false);
		addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource() == this) {
			setIcon(image2);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource() == this) {
			setIcon(image);
		}
	}
}
