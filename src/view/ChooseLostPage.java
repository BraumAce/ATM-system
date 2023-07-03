package view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import util.MyButton;

public class ChooseLostPage extends ATMManage implements MouseListener{
	static ChooseLostPage chooseLostPage;
	private MyButton back,loss1,loss2;
	
	public ChooseLostPage() {
		
		loss1 = new MyButton("loss1","gif");
		loss1.setBounds(350,250,170,60);
		panel.add(loss1);
		loss1.addMouseListener(this);
		
		loss2 = new MyButton("loss2","gif");
		loss2.setBounds(350,400,170,60);
		panel.add(loss2);
		loss2.addMouseListener(this);
		
		//Ìí¼Ó·µ»Ø°´Å¥
		back = new MyButton("back","gif");
		back.setBounds(30,600,170,60);
		panel.add(back);
		back.addMouseListener(this);
		
		setVisible(true);
	} 
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == loss1) {
			chooseLostPage.dispose();
			LostPage1.lostPage = new LostPage1();
		}
		else if (e.getSource() == loss2) {
			chooseLostPage.dispose();
			LostPage2.lostPage = new LostPage2();
		}
		else if (e.getSource() == back) {
			chooseLostPage.dispose();
			InitialPage.initialPage.setVisible(true);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
}
