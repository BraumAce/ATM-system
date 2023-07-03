package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import util.MyButton;
import util.RandomID;

public class InitialPage extends ATMManage implements MouseListener{
	
	static InitialPage initialPage;
	MyButton lost,login,register,exit;
	JLabel welcome,exitLabel;
	
	public InitialPage() {
		
		//��ӵ�¼��ť
		login = new MyButton("login","gif");
		login.setBounds(700,300,170,60);
		panel.add(login);
		login.addMouseListener(this);
	
		
		//���ע�ᰴť
		register = new MyButton("register","gif");
		register.setBounds(700,400,170,60);
		panel.add(register);
		register.addMouseListener(this);	
		
		//��ӹ�ʧ��ť
		lost = new MyButton("lost","gif");
		lost.setBounds(700,500,170,60);
		panel.add(lost);
		lost.addMouseListener(this);	
		
		//����˳���ť
		exit = new MyButton("exit","gif");
		exit.setBounds(700,600,170,60);
		panel.add(exit);
		exit.addMouseListener(this);
		
		welcome = new JLabel("���ã���ѡ������Ҫ�ķ���");
		welcome.setBounds(100,100,700,100);
		welcome.setFont(new Font("����", Font.BOLD, 50));
		welcome.setForeground(Color.white);
		panel.add(welcome);
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		initialPage = new InitialPage();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == login) {
			setVisible(false);
			LoginPage.loginPage = new LoginPage();
		}
		else if (e.getSource() == register) {
			initialPage.setVisible(false);
			RegisterPage1.registerPage1 = new RegisterPage1();
		}
		else if (e.getSource() == lost) {
			initialPage.setVisible(false);
			ChooseLostPage.chooseLostPage = new ChooseLostPage();
		}
		else if (e.getSource() == exit) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					exitLabel = new JLabel("��лʹ�ñ�ATM����ϵͳ��");
					exitLabel.setFont(new Font("���Ŀ���",Font.BOLD,40));
					exitLabel.setForeground(Color.RED);
					exitLabel.setBounds(210,300,600,50);
					panel.add(exitLabel);
					paint(getGraphics());
					try {
						Thread.sleep(1500);
						System.exit(0);
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});			
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
