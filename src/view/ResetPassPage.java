package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import dao.CardDao;
import util.MyButton;

public class ResetPassPage extends ATMManage implements MouseListener,KeyListener{

	static ResetPassPage resetPassPage;
	private MyButton back,confirm;
	private JLabel oldPassLabel,newPassLabel,newPassLabel2,welcome;
	private JPasswordField oldPass,newPass,newPass2;
	private JLabel[] wrongLabel4=new JLabel[3];
	private JLabel[] rightLabel4=new JLabel[3];
	
	public ResetPassPage() {
		//�޸�����
		welcome = new JLabel("�޸�����");
		welcome.setBounds(100,100,700,100);
		welcome.setFont(new Font("����", Font.BOLD, 50));
		welcome.setForeground(Color.orange);
		panel.add(welcome);
		
		oldPassLabel = new JLabel("ԭ���룺");
		oldPassLabel.setBounds(150,230,180,50);
		oldPassLabel.setFont(new Font("����",Font.PLAIN,35));
		oldPassLabel.setForeground(Color.white);
		panel.add(oldPassLabel);
		oldPass = new JPasswordField();
		oldPass.setBounds(330,230,300,50);
		oldPass.setFont(new Font("����",Font.BOLD,35));
		oldPass.addKeyListener(this);
		panel.add(oldPass);
		
		newPassLabel = new JLabel("�����룺");
		newPassLabel.setBounds(150,330,180,50);
		newPassLabel.setFont(new Font("����",Font.PLAIN,35));
		newPassLabel.setForeground(Color.white);
		panel.add(newPassLabel);
		newPass = new JPasswordField();
		newPass.setBounds(330,330,300,50);
		newPass.setFont(new Font("����",Font.BOLD,35));
		newPass.addKeyListener(this);
		panel.add(newPass);
		
		newPassLabel2 = new JLabel("ȷ�����룺");
		newPassLabel2.setBounds(150,430,180,50);
		newPassLabel2.setFont(new Font("����",Font.PLAIN,35));
		newPassLabel2.setForeground(Color.white);
		panel.add(newPassLabel2);
		newPass2 = new JPasswordField();
		newPass2.setBounds(330,430,300,50);
		newPass2.setFont(new Font("����",Font.BOLD,35));
		newPass2.addKeyListener(this);
		panel.add(newPass2);

		//���ȷ�ϰ�ť
		confirm = new MyButton("confirm","gif");
		confirm.setBounds(700,600,170,60);
		panel.add(confirm);
		confirm.addMouseListener(this);
		
		//��ӷ��ذ�ť
		back = new MyButton("back","gif");
		back.setBounds(30,600,170,60);
		panel.add(back);
		back.addMouseListener(this);
		
		setVisible(true);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == confirm) {
			removeLabel();
			if (isRight()) {
				removeLabel();
				JOptionPane.showMessageDialog(resetPassPage, "�޸ĳɹ���");
				resetPassPage.dispose();
				TradingPage.tradingPage.setVisible(true);
			}
		}
		else if (e.getSource() == back) {
			resetPassPage.dispose();
			TradingPage.tradingPage.setVisible(true);
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
	
	public boolean isRight() {
		int flag = 0;
		String pass = CardDao.executeGetPass(LoginPage.CardID);

		//ԭ�������
		if (!pass.equals(oldPass.getText())) {
			wrongLabel4[0] = new JLabel("ԭ�������");
			wrongLabel4[0].setFont(new Font("����",Font.BOLD,15));
			wrongLabel4[0].setForeground(Color.RED);
			wrongLabel4[0].setBounds(400,270,100,50);
			panel.add(wrongLabel4[0]);
			repaint();
			flag = 1;
		}
		else {
			rightLabel4[0] = new JLabel("��");
			rightLabel4[0].setFont(new Font("����",Font.BOLD,15));
			rightLabel4[0].setForeground(Color.green);
			rightLabel4[0].setBounds(650,230,100,50);
			panel.add(rightLabel4[0]);
			repaint();
		}

		if (flag == 1) return false;
		
		if (newPass.getText().length()!=6) {
			wrongLabel4[1] = new JLabel("��������λ����������");
			wrongLabel4[1].setFont(new Font("����",Font.BOLD,15));
			wrongLabel4[1].setForeground(Color.RED);
			wrongLabel4[1].setBounds(400,370,200,50);
			panel.add(wrongLabel4[1]);
			repaint();
			flag = 1;
		}
		else if (newPass.getText().equals(oldPass.getText())) {
			wrongLabel4[1]=new JLabel("���ܺ�ԭ������ͬ");
			wrongLabel4[1].setFont(new Font("����",Font.BOLD,15));
			wrongLabel4[1].setForeground(Color.RED);
			wrongLabel4[1].setBounds(400,370,200,50);
			panel.add(wrongLabel4[1]);
			repaint();
			flag = 1;
		}
		else {
			rightLabel4[1] = new JLabel("��");
			rightLabel4[1].setFont(new Font("����",Font.BOLD,15));
			rightLabel4[1].setForeground(Color.green);
			rightLabel4[1].setBounds(650,330,100,50);
			panel.add(rightLabel4[1]);
			repaint();
		}

		//��������Ƿ�һ��
		if (!newPass.getText().equals(newPass2.getText())
				&& !newPass.getText().equals(oldPass.getText())) {
			wrongLabel4[2] = new JLabel("�����������벻һ��");
			wrongLabel4[2].setFont(new Font("����",Font.BOLD,15));
			wrongLabel4[2].setForeground(Color.RED);
			wrongLabel4[2].setBounds(400,470,200,50);
			panel.add(wrongLabel4[2]);
			repaint();
			flag = 1;
		}
		else if (!newPass.getText().isEmpty()
				&& !newPass.getText().equals(oldPass.getText())){
			rightLabel4[2] = new JLabel("��");
			rightLabel4[2].setFont(new Font("����",Font.BOLD,15));
			rightLabel4[2].setForeground(Color.green);
			rightLabel4[2].setBounds(650,430,100,50);
			panel.add(rightLabel4[2]);
			repaint();
			CardDao.executeResetPass(LoginPage.CardID,newPass.getText());
		}
		if (flag == 1) return false;
		return true;
	}

	public void removeLabel() {
		for (int i = 0; i < 3; i++) {
			try{
				panel.remove(wrongLabel4[i]);
				panel.remove(rightLabel4[i]);
			}
			catch (Exception e) {
			}
			finally {
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		int keyChar = e.getKeyChar();                 
        if (keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9){
        }
		else{
            e.consume(); //�ؼ������ε��Ƿ�����  
        } 
	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}
