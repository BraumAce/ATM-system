package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import dao.CardDao;
import util.MyButton;

public class WithDrawPage extends ATMManage implements MouseListener,KeyListener{

	static WithDrawPage withDrawPage;
	private JLabel welcome,moneyLabel;
	private JTextField input;
	private MyButton y100,y500,y1000,y2000,y5000,y10000,confirm,back;
	public WithDrawPage() {
		//���100Ԫ��ť
		y100 = new MyButton("100","gif");
		y100.setBounds(700,300,170,60);
		panel.add(y100);
		y100.addMouseListener(this);
		
		//���500Ԫ��ť
		y500 = new MyButton("500","gif");
		y500.setBounds(700,400,170,60);
		panel.add(y500);
		y500.addMouseListener(this);	
		
		//���1000Ԫ��ť
		y1000 = new MyButton("1000","gif");
		y1000.setBounds(700,500,170,60);
		panel.add(y1000);
		y1000.addMouseListener(this);	
		
		//���200Ԫ��ť
		y2000 = new MyButton("2000","gif");
		y2000.setBounds(30,300,170,60);
		panel.add(y2000);
		y2000.addMouseListener(this);
		
		//���5000Ԫ��ť
		y5000 = new MyButton("5000","gif");
		y5000.setBounds(30,400,170,60);
		panel.add(y5000);
		y5000.addMouseListener(this);
		
		//���10000Ԫ��ť
		y10000 = new MyButton("10000","gif");
		y10000.setBounds(30,500,170,60);
		panel.add(y10000);
		y10000.addMouseListener(this);
		
		//���ȡ�ť
		moneyLabel = new JLabel("ȡ���");
		moneyLabel.setBounds(300,330,250,60);
		moneyLabel.setFont(new Font("����",Font.BOLD,40));
		moneyLabel.setForeground(Color.orange);
		panel.add(moneyLabel);
		
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
		
		//��������
		input = new JTextField();
		input.setBounds(350,400,170,60);
		input.setFont(new Font("����", Font.BOLD, 20));
		panel.add(input);
		input.addKeyListener(this);
		
		welcome = new JLabel("������ȡ���");
		welcome.setBounds(100,100,700,100);
		welcome.setFont(new Font("����", Font.BOLD, 50));
		welcome.setForeground(Color.white);
		panel.add(welcome);
		
		setVisible(true);		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == confirm) {
			if(input.getText().isEmpty() || Float.parseFloat(input.getText())==0) {
				JOptionPane.showMessageDialog(withDrawPage,"ȡ�����Ϊ��","����",JOptionPane.ERROR_MESSAGE); 
			}
			else {
				float balance = CardDao.executeOperate(LoginPage.CardID,
						Float.parseFloat(input.getText()),false);
				if (balance == -1) {
					JOptionPane.showMessageDialog(withDrawPage,"��������","����",JOptionPane.ERROR_MESSAGE); 
				}
				else{
					BalancePage.balancePage = new BalancePage("ȡ��ɹ�","��������ǣ�");
					withDrawPage.dispose();
				}				
			}
		}
		else if (e.getSource() == y100) {
			input.setText("100");
		}
		else if (e.getSource() == y500) {
			input.setText("500");
		}
		else if (e.getSource() == y1000) {
			input.setText("1000");
		}
		else if (e.getSource() == y2000) {
			input.setText("2000");
		}
		else if (e.getSource() == y5000) {
			input.setText("5000");
		}
		else if (e.getSource() == y10000) {
			input.setText("10000");
		}
		else if (e.getSource() == back) {
			withDrawPage.dispose();
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
