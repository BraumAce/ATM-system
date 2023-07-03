package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JTextField;

import dao.CardDao;
import dao.UserDao;
import model.CardInfo;
import model.UserInfo;
import util.MyButton;
import util.RegisterPanel;

public class CancellationPage extends ATMManage implements MouseListener{
	static CancellationPage cancellationPage;
	private MyButton back,confirm;
	private JLabel tips,IDLabel,nameLabel,opdenDateLabel,savingTypeLabel,curTypeLabel,balanceLabel;
	private RegisterPanel registerPanel;
	CardInfo cardInfo;
	UserInfo userInfo;
	public CancellationPage() {
		//输入注销的卡号和用户名
		cardInfo = CardDao.executeGetAllCardInfo(LoginPage.CardID);
		System.out.println(cardInfo.getCardID());
		userInfo = UserDao.executeGetAllUserInfo(LoginPage.CardID);
		System.out.println(userInfo.getPID());
		
		registerPanel = new RegisterPanel(0.1f, 640,610);
		registerPanel.setBounds(30,50,640,610);
		registerPanel.setOpaque(false);  
		registerPanel.setLayout(null);
		panel.add(registerPanel);
		
		tips = new JLabel("请确认是否注销：");
		tips.setBounds(50,30,640,50);
		tips.setFont(new Font("宋体",Font.BOLD,35));
		tips.setForeground(Color.orange);
		registerPanel.add(tips);
		
		
		IDLabel = new JLabel("卡号："+cardInfo.getCardID());
		IDLabel.setBounds(50,100,500,50);
		IDLabel.setFont(new Font("宋体",Font.PLAIN,35));
		IDLabel.setForeground(Color.white);
		registerPanel.add(IDLabel);

		nameLabel = new JLabel("开户名："+userInfo.getCustomerName());
		nameLabel.setBounds(50,180,500,50);
		nameLabel.setFont(new Font("宋体",Font.PLAIN,35));
		nameLabel.setForeground(Color.white);
		registerPanel.add(nameLabel);
		
		opdenDateLabel = new JLabel("开户时间："+cardInfo.getOpenDate().toString());
		opdenDateLabel.setBounds(50,260,500,50);
		opdenDateLabel.setFont(new Font("宋体",Font.PLAIN,35));
		opdenDateLabel.setForeground(Color.white);
		registerPanel.add(opdenDateLabel);

		
		savingTypeLabel = new JLabel("存储类型："+cardInfo.getSavingType());
		savingTypeLabel.setBounds(50,340,500,50);
		savingTypeLabel.setFont(new Font("宋体",Font.PLAIN,35));
		savingTypeLabel.setForeground(Color.white);
		registerPanel.add(savingTypeLabel);

		curTypeLabel = new JLabel("货币类型："+cardInfo.getCurType());
		curTypeLabel.setBounds(50,420,500,50);
		curTypeLabel.setFont(new Font("宋体",Font.PLAIN,35));
		curTypeLabel.setForeground(Color.white);
		registerPanel.add(curTypeLabel);
		
		balanceLabel = new JLabel("余额："+cardInfo.getBalance());
		balanceLabel.setBounds(50,500,500,50);
		balanceLabel.setFont(new Font("宋体",Font.PLAIN,35));
		balanceLabel.setForeground(Color.white);
		registerPanel.add(balanceLabel);
		
		//添加上一步按钮
		back = new MyButton("back","gif");
		back.setBounds(700,500,170,60);
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
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == back) {
			cancellationPage.dispose();
			TradingPage.tradingPage.setVisible(true);
		}
		else if (e.getSource() == confirm) {
			CardDao.executeDelete(LoginPage.CardID);
			if (CardDao.isPossessAnotherCard(cardInfo.getCustomerID()) == 0) {
				UserDao.executeDelete(cardInfo.getCustomerID());
			}
			cancellationPage.dispose();
			TradingPage.tradingPage.dispose();
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
