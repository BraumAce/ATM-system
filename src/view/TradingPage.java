package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import util.MyButton;

public class TradingPage extends ATMManage implements MouseListener{

	static TradingPage tradingPage;
	private MyButton deposit,withdraw,transfer,query,balance,change,exit,cancellation;
	private JLabel welcome,exitLabel;
	public TradingPage() {
		//添加转账按钮
		transfer = new MyButton("transfer","gif");
		transfer.setBounds(700,300,170,60);
		panel.add(transfer);
		transfer.addMouseListener(this);
		
		//添加存款按钮
		deposit = new MyButton("deposit","gif");
		deposit.setBounds(700,400,170,60);
		panel.add(deposit);
		deposit.addMouseListener(this);	
		
		//添加取款按钮
		withdraw = new MyButton("withdraw","gif");
		withdraw.setBounds(700,500,170,60);
		panel.add(withdraw);
		withdraw.addMouseListener(this);	
		
		//添加交易记录按钮
		query = new MyButton("query","gif");
		query.setBounds(30,300,170,60);
		panel.add(query);
		query.addMouseListener(this);
		
		//添加余额查询按钮
		balance=new MyButton("balance","gif");
		balance.setBounds(30,400,170,60);
		panel.add(balance);
		balance.addMouseListener(this);
		
		//添加修改密码按钮
		change = new MyButton("change","gif");
		change.setBounds(30,500,170,60);
		panel.add(change);
		change.addMouseListener(this);
		
		//添加注销按钮
		cancellation=new MyButton("cancellation","gif");
		cancellation.setBounds(30,600,170,60);
		panel.add(cancellation);
		cancellation.addMouseListener(this);
		
		//添加退出按钮
		exit = new MyButton("loginexit","gif");
		exit.setBounds(700,600,170,60);
		panel.add(exit);
		exit.addMouseListener(this);
		
		welcome = new JLabel("您好，请选择您需要的服务！");
		welcome.setBounds(100,100,700,100);
		welcome.setFont(new Font("黑体", Font.BOLD, 50));
		welcome.setForeground(Color.white);
		panel.add(welcome);
		
		setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//存款
		if (e.getSource() == deposit) {
			DepositPage.depositPage = new DepositPage();
			tradingPage.setVisible(false);
		}
		//取款
		else if (e.getSource() == withdraw) {
			WithDrawPage.withDrawPage = new WithDrawPage();
			tradingPage.setVisible(false);
		}
		//转账
		else if (e.getSource() == transfer) {
			TransferPage.transferPage = new TransferPage();
			tradingPage.setVisible(false);
		}
		//余额查询
		else if (e.getSource() == balance) {
			BalancePage.balancePage = new BalancePage(null,"您的余额是：");
			tradingPage.setVisible(false);
		}
		//修改密码
		else if (e.getSource() == change) {
			ResetPassPage.resetPassPage = new ResetPassPage();
			tradingPage.setVisible(false);
		}
		//交易记录
		else if (e.getSource() == query) {
			QueryPage.queryPage = new QueryPage();
			tradingPage.setVisible(false);
		}
		//注销
		else if (e.getSource() == cancellation) {
			CancellationPage.cancellationPage = new CancellationPage();
			tradingPage.setVisible(false);
		}
		//退出
		else if (e.getSource() == exit) {
			LoginPage.CardID = null;
			tradingPage.dispose();
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
