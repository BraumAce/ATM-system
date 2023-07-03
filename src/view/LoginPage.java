package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import dao.CardDao;
import dao.UserDao;
import util.MyButton;
import util.RegisterPanel;

public class LoginPage extends ATMManage implements MouseListener{

	static LoginPage loginPage;
	private JLabel step,tips,PIDLabel,passLabel;
	private JTextField PID;
	private JPasswordField pass;
	private MyButton confirm,back;
	private RegisterPanel registerPanel;
	private JLabel wrongLabel3[] = new JLabel[2];
	private JLabel rightLabel3[] = new JLabel[2];
	public static String CardID;
	public LoginPage() {
		//�����һ����ť
		back = new MyButton("previous","gif");
		back.setBounds(700,500,170,60);
		panel.add(back);
		back.addMouseListener(this);
		
		//���ȷ�ϰ�ť
		confirm = new MyButton("confirm","gif");
		confirm.setBounds(700,600,170,60);
		panel.add(confirm);
		confirm.addMouseListener(this);
		
		//��ӵ�¼�������
		registerPanel = new RegisterPanel(.1f,500,610);
		registerPanel.setBounds(130,50,500,610);
		registerPanel.setOpaque(false);  
		registerPanel.setLayout(null);
		panel.add(registerPanel);
		
		tips = new JLabel("�û���¼��");
		tips.setBounds(50,100,640,50);
		tips.setFont(new Font("����",Font.BOLD,25));
		tips.setForeground(Color.orange);
		registerPanel.add(tips);
		
		PIDLabel = new JLabel("���ţ�");
		PIDLabel.setBounds(50,230,110,50);
		PIDLabel.setFont(new Font("����",Font.PLAIN,35));
		PIDLabel.setForeground(Color.white);
		registerPanel.add(PIDLabel);
		PID = new JTextField();
		PID.setBounds(160,230,290,50);
		PID.setFont(new Font("����",Font.BOLD,25));
		registerPanel.add(PID);

		passLabel = new JLabel("���룺");
		passLabel.setBounds(50,330,110,50);
		passLabel.setFont(new Font("����",Font.PLAIN,35));
		passLabel.setForeground(Color.white);
		registerPanel.add(passLabel);
		pass = new JPasswordField();
		pass.setBounds(160,330,290,50);
		pass.setFont(new Font("����",Font.BOLD,35));
		registerPanel.add(pass);
		
		setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == confirm) {
			removeLabel();
			if(isRight()) {
				removeLabel();
				boolean isloss=CardDao.executeIsLost(PID.getText());
				if (isloss){
					JOptionPane.showMessageDialog(loginPage, "�����п��ѹ�ʧ����ȡ����ʧ������","����",JOptionPane.ERROR_MESSAGE);
				}
				else{
					System.out.println("��¼");
					CardID = PID.getText();
					TradingPage.tradingPage = new TradingPage();
					loginPage.setVisible(false);
				}
			}
		}
		else if (e.getSource() == back) {
			CardID = null;
			InitialPage.initialPage.setVisible(true);
			loginPage.setVisible(false);
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
		//���Ų�����
		int state = CardDao.executeLogin(PID.getText(),pass.getText());
		System.out.println(state);
		if (state == 1) {
			wrongLabel3[0] = new JLabel("���Ų�����");
			wrongLabel3[0].setFont(new Font("����",Font.BOLD,15));
			wrongLabel3[0].setForeground(Color.RED);
			wrongLabel3[0].setBounds(200,270,100,50);
			registerPanel.add(wrongLabel3[0]);
			repaint();
			flag = 1;
		}
		else if (state == 0) {
			rightLabel3[0] = new JLabel("��");
			rightLabel3[0].setFont(new Font("����",Font.BOLD,15));
			rightLabel3[0].setForeground(Color.green);
			rightLabel3[0].setBounds(450,230,100,50);
			registerPanel.add(rightLabel3[0]);
			rightLabel3[1] = new JLabel("��");
			rightLabel3[1].setFont(new Font("����",Font.BOLD,15));
			rightLabel3[1].setForeground(Color.green);
			rightLabel3[1].setBounds(450,330,100,50);
			registerPanel.add(rightLabel3[1]);
			repaint();
			repaint();
		}
		if (state == 2) {
			wrongLabel3[1] = new JLabel("�������");
			wrongLabel3[1].setFont(new Font("����",Font.BOLD,15));
			wrongLabel3[1].setForeground(Color.RED);
			wrongLabel3[1].setBounds(200,370,100,50);
			registerPanel.add(wrongLabel3[1]);
			repaint();
			flag = 1;
		}
		if (flag == 1) return false;
		return true;
	}
	public void removeLabel() {
		for (int i = 0; i < 3; i++) {
			try{
				registerPanel.remove(wrongLabel3[i]);
				registerPanel.remove(rightLabel3[i]);
			}
			catch (Exception e) {
			}
			finally {
			}
		}
	}
}
