package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.print.DocFlavor.INPUT_STREAM;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import dao.CardDao;
import util.MyButton;

public class TransferPage extends ATMManage implements MouseListener,KeyListener{

	static TransferPage transferPage;
	private MyButton back,confirm;
	private JLabel moneyLabel,IDLabel,welcome;
	private JTextField Tmoney,TID;

	//ת��
	public TransferPage() {
		welcome = new JLabel("�������տ�����Լ�ת�˽�");
		welcome.setBounds(100,100,800,100);
		welcome.setFont(new Font("����", Font.BOLD, 40));
		welcome.setForeground(Color.white);
		panel.add(welcome);

		IDLabel = new JLabel("�տ���ţ�");
		IDLabel.setBounds(120,250,260,60);
		IDLabel.setFont(new Font("����", Font.PLAIN, 40));
		IDLabel.setForeground(Color.orange);
		panel.add(IDLabel);
		
		moneyLabel = new JLabel("ת�˽��  ��");
		moneyLabel.setBounds(120,350,260,60);
		moneyLabel.setFont(new Font("����", Font.PLAIN, 40));
		moneyLabel.setForeground(Color.orange);
		panel.add(moneyLabel);
		
		//���������
		TID = new JTextField();
		TID.setBounds(350,250,230,50);
		TID.setFont(new Font("����", Font.BOLD, 20));
		panel.add(TID);
		TID.addKeyListener(this);
		
		//��ӽ�������
		Tmoney = new JTextField();
		Tmoney.setBounds(350,350,230,50);
		Tmoney.setFont(new Font("����", Font.BOLD, 20));
		panel.add(Tmoney);
		Tmoney.addKeyListener(this);
		
		
		//��ӷ��ذ�ť
		back = new MyButton("back","gif");
		back.setBounds(30,600,170,60);
		panel.add(back);
		back.addMouseListener(this);
		
		//���ȷ�ϰ�ť
		confirm = new MyButton("confirm","gif");
		confirm.setBounds(700,600,170,60);
		panel.add(confirm);
		confirm.addMouseListener(this);
		
		setVisible(true);
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

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == confirm) {
			if (!LoginPage.CardID.equals(TID.getText())) {
				if (TID.getText().isEmpty()) {
			  		 JOptionPane.showMessageDialog(transferPage,"�տ�˺Ų���Ϊ��","����",JOptionPane.ERROR_MESSAGE); 
				}
				else if (Tmoney.getText().isEmpty() || Float.parseFloat(Tmoney.getText())==0) {
			  		 JOptionPane.showMessageDialog(transferPage,"ת�˽���Ϊ��","����",JOptionPane.ERROR_MESSAGE); 
				}
				else {
					float balance=CardDao.executeTrans(LoginPage.CardID, TID.getText(),
						Float.parseFloat(Tmoney.getText()));
					if (balance == -2) {
					  		 JOptionPane.showMessageDialog(transferPage,"�տ�˺Ų�����","����",JOptionPane.ERROR_MESSAGE); 
					}
					else if (balance == -1) {
					  		JOptionPane.showMessageDialog(transferPage,"��������","����",JOptionPane.ERROR_MESSAGE); 
					}
					else {
					  	transferPage.dispose();
					 	BalancePage.balancePage = new BalancePage("ת�˳ɹ�","��������ǣ�");
					}					
				}
			}
			else {
		  		JOptionPane.showMessageDialog(transferPage,"�����Ը��Լ�ת��","����",JOptionPane.ERROR_MESSAGE); 
			}
		}
		else if (e.getSource() == back) {
			transferPage.dispose();
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
}
