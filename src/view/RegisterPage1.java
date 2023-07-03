package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JTextField;

import dao.UserDao;
import model.UserInfo;
import util.MyButton;
import util.RegisterPanel;

public class RegisterPage1 extends ATMManage implements MouseListener,KeyListener{

	static RegisterPage1 registerPage1;
	private MyButton next,previous,exit,back;
	private JLabel tips,nameLabel,PIDLabel,telephoneLabel,addressLabel;
	private JLabel wrongLabel1[]=new JLabel[4];
	private JLabel rightLabel1[]=new JLabel[4];
	private JTextField name,PID,telephone,address;
	private RegisterPanel registerPanel;
	public RegisterPage1() {
		//�����һ����ť
		back = new MyButton("back","gif");
		back.setBounds(700,500,170,60);
		panel.add(back);
		back.addMouseListener(this);
		
		//�����һ����ť
		next = new MyButton("next","gif");
		next.setBounds(700,600,170,60);
		panel.add(next);
		next.addMouseListener(this);
		
		//���ע���������
		registerPanel = new RegisterPanel(.1f,640,610);
		registerPanel.setBounds(30,50,640,610);
		registerPanel.setOpaque(false);  
		registerPanel.setLayout(null);
		panel.add(registerPanel);
		
		tips = new JLabel("����д������Ϣ��");
		tips.setBounds(50,30,640,50);
		tips.setFont(new Font("����",Font.BOLD,25));
		tips.setForeground(Color.orange);
		registerPanel.add(tips);
		
		
		nameLabel = new JLabel("������  ��");
		nameLabel.setBounds(50,130,180,50);
		nameLabel.setFont(new Font("����",Font.PLAIN,35));
		nameLabel.setForeground(Color.white);
		registerPanel.add(nameLabel);
		name = new JTextField();
		name.setBounds(230,130,300,50);
		name.setFont(new Font("����",Font.BOLD,25));
		registerPanel.add(name);

		PIDLabel = new JLabel("���֤�ţ�");
		PIDLabel.setBounds(50,230,180,50);
		PIDLabel.setFont(new Font("����",Font.PLAIN,35));
		PIDLabel.setForeground(Color.white);
		registerPanel.add(PIDLabel);
		PID = new JTextField();
		PID.setBounds(230,230,300,50);
		PID.setFont(new Font("����",Font.BOLD,25));
		registerPanel.add(PID);
		
		telephoneLabel = new JLabel("��ϵ�绰��");
		telephoneLabel.setBounds(50,330,180,50);
		telephoneLabel.setFont(new Font("����",Font.PLAIN,35));
		telephoneLabel.setForeground(Color.white);
		registerPanel.add(telephoneLabel);
		telephone = new JTextField();
		telephone.setBounds(230,330,300,50);
		telephone.setFont(new Font("����",Font.BOLD,25));
		registerPanel.add(telephone);
		telephone.addKeyListener(this);
		
		addressLabel = new JLabel("��ͥסַ��");
		addressLabel.setBounds(50,430,180,50);
		addressLabel.setFont(new Font("����",Font.PLAIN,35));
		addressLabel.setForeground(Color.white);
		registerPanel.add(addressLabel);
		address = new JTextField();
		address.setBounds(230,430,300,50);
		address.setFont(new Font("����",Font.BOLD,25));
		registerPanel.add(address);
		
		setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == next) {
			removeLabel();
			if (isRight() == true) {
				removeLabel();
				UserInfo user=new UserInfo(UserDao.executeQueryMaxID()+1,
						name.getText(),PID.getText(),telephone.getText(),address.getText());
				UserDao.executeInsert(user);

				//������¼���������
				registerPage1.setVisible(false);
				RegisterPage2.registerPage2=new RegisterPage2();
			}
		}
		else if (e.getSource()==back) {
			registerPage1.setVisible(false);
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
	
	//����û���Ϣ�Ƿ���д��ȷ����ȷ�������һ��
	public boolean isRight() {
		int flag = 0;
		Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum1 = pattern.matcher(PID.getText());
        Matcher isNum2 = pattern.matcher(telephone.getText());

		//����û����Ƿ�Ϸ�
		if (name.getText().isEmpty() || name.getText().indexOf(" ") != -1) {
			wrongLabel1[0] = new JLabel("�û����Ƿ�");
			wrongLabel1[0].setFont(new Font("����",Font.BOLD,15));
			wrongLabel1[0].setForeground(Color.RED);
			wrongLabel1[0].setBounds(540,130,100,50);
			registerPanel.add(wrongLabel1[0]);
			repaint();
			flag = 1;
		}
		else {
			rightLabel1[0] = new JLabel("��");
			rightLabel1[0].setFont(new Font("����",Font.BOLD,15));
			rightLabel1[0].setForeground(Color.green);
			rightLabel1[0].setBounds(540,130,100,50);
			registerPanel.add(rightLabel1[0]);
			repaint();
		}
		if (PID.getText().isEmpty() || PID.getText().indexOf(" ")!=-1 || PID.getText().length()!=18 || !isNum1.matches()) {
			wrongLabel1[1] = new JLabel("���֤�ŷǷ�");
			wrongLabel1[1].setFont(new Font("����",Font.BOLD,15));
			wrongLabel1[1].setForeground(Color.RED);
			wrongLabel1[1].setBounds(540,230,100,50);
			registerPanel.add(wrongLabel1[1]);
			repaint();
			flag = 1;
		}
		else {
			rightLabel1[1] = new JLabel("��");
			rightLabel1[1].setFont(new Font("����",Font.BOLD,15));
			rightLabel1[1].setForeground(Color.green);
			rightLabel1[1].setBounds(540,230,100,50);
			registerPanel.add(rightLabel1[1]);
			repaint();
		}
		if (telephone.getText().isEmpty() ||telephone.getText().length()!=11 || !isNum2.matches()) {
			wrongLabel1[2] = new JLabel("��ϵ�绰�Ƿ�");
			wrongLabel1[2].setFont(new Font("����",Font.BOLD,15));
			wrongLabel1[2].setForeground(Color.RED);
			wrongLabel1[2].setBounds(540,330,100,50);
			registerPanel.add(wrongLabel1[2]);
			repaint();
			flag = 1;
		}
		else {
			rightLabel1[2] = new JLabel("��");
			rightLabel1[2].setFont(new Font("����",Font.BOLD,15));
			rightLabel1[2].setForeground(Color.green);
			rightLabel1[2].setBounds(540,330,100,50);
			registerPanel.add(rightLabel1[2]);
			repaint();
		}
		if (address.getText().isEmpty() || address.getText().indexOf(" ") != -1) {
			wrongLabel1[3]=new JLabel("��ͥסַ�Ƿ�");
			wrongLabel1[3].setFont(new Font("����",Font.BOLD,15));
			wrongLabel1[3].setForeground(Color.RED);
			wrongLabel1[3].setBounds(540,430,100,50);
			registerPanel.add(wrongLabel1[3]);
			repaint();
			flag = 1;
		}
		else {
			rightLabel1[3]=new JLabel("��");
			rightLabel1[3].setFont(new Font("����",Font.BOLD,15));
			rightLabel1[3].setForeground(Color.green);
			rightLabel1[3].setBounds(540,430,100,50);
			registerPanel.add(rightLabel1[3]);
			repaint();
		}
		if (flag == 1) return false;
		return true;
	}
	public void removeLabel() {
		for (int i = 0; i < 4; i++) {
			try{
				registerPanel.remove(wrongLabel1[i]);
				registerPanel.remove(rightLabel1[i]);
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
        if(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9){
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
