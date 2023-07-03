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

	//转账
	public TransferPage() {
		welcome = new JLabel("请输入收款方卡号以及转账金额：");
		welcome.setBounds(100,100,800,100);
		welcome.setFont(new Font("黑体", Font.BOLD, 40));
		welcome.setForeground(Color.white);
		panel.add(welcome);

		IDLabel = new JLabel("收款方卡号：");
		IDLabel.setBounds(120,250,260,60);
		IDLabel.setFont(new Font("宋体", Font.PLAIN, 40));
		IDLabel.setForeground(Color.orange);
		panel.add(IDLabel);
		
		moneyLabel = new JLabel("转账金额  ：");
		moneyLabel.setBounds(120,350,260,60);
		moneyLabel.setFont(new Font("宋体", Font.PLAIN, 40));
		moneyLabel.setForeground(Color.orange);
		panel.add(moneyLabel);
		
		//添卡号输入框
		TID = new JTextField();
		TID.setBounds(350,250,230,50);
		TID.setFont(new Font("黑体", Font.BOLD, 20));
		panel.add(TID);
		TID.addKeyListener(this);
		
		//添加金额输入框
		Tmoney = new JTextField();
		Tmoney.setBounds(350,350,230,50);
		Tmoney.setFont(new Font("黑体", Font.BOLD, 20));
		panel.add(Tmoney);
		Tmoney.addKeyListener(this);
		
		
		//添加返回按钮
		back = new MyButton("back","gif");
		back.setBounds(30,600,170,60);
		panel.add(back);
		back.addMouseListener(this);
		
		//添加确认按钮
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
            e.consume(); //关键，屏蔽掉非法输入  
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
			  		 JOptionPane.showMessageDialog(transferPage,"收款方账号不能为空","错误",JOptionPane.ERROR_MESSAGE); 
				}
				else if (Tmoney.getText().isEmpty() || Float.parseFloat(Tmoney.getText())==0) {
			  		 JOptionPane.showMessageDialog(transferPage,"转账金额不能为空","错误",JOptionPane.ERROR_MESSAGE); 
				}
				else {
					float balance=CardDao.executeTrans(LoginPage.CardID, TID.getText(),
						Float.parseFloat(Tmoney.getText()));
					if (balance == -2) {
					  		 JOptionPane.showMessageDialog(transferPage,"收款方账号不存在","错误",JOptionPane.ERROR_MESSAGE); 
					}
					else if (balance == -1) {
					  		JOptionPane.showMessageDialog(transferPage,"您的余额不足","错误",JOptionPane.ERROR_MESSAGE); 
					}
					else {
					  	transferPage.dispose();
					 	BalancePage.balancePage = new BalancePage("转账成功","您的余额是：");
					}					
				}
			}
			else {
		  		JOptionPane.showMessageDialog(transferPage,"不可以给自己转账","错误",JOptionPane.ERROR_MESSAGE); 
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
