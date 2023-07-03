package util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class RegisterPanel extends JPanel{

	float transparency = 0.1f;
	int width, height;
	public RegisterPanel(float transparency,int width,int height) {
		this.transparency = transparency;
		this.width = width;
		this.height = height;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D graphics2d = (Graphics2D) g.create();
		graphics2d.setComposite(AlphaComposite.SrcOver.derive( transparency));
		graphics2d.setColor(Color.white);
		graphics2d.fillRect(0,0,width,height);
		graphics2d.dispose();
	}
}

