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
		//���ת�˰�ť
		transfer = new MyButton("transfer","gif");
		transfer.setBounds(700,300,170,60);
		panel.add(transfer);
		transfer.addMouseListener(this);
		
		//��Ӵ�ť
		deposit = new MyButton("deposit","gif");
		deposit.setBounds(700,400,170,60);
		panel.add(deposit);
		deposit.addMouseListener(this);	
		
		//���ȡ�ť
		withdraw = new MyButton("withdraw","gif");
		withdraw.setBounds(700,500,170,60);
		panel.add(withdraw);
		withdraw.addMouseListener(this);	
		
		//��ӽ��׼�¼��ť
		query = new MyButton("query","gif");
		query.setBounds(30,300,170,60);
		panel.add(query);
		query.addMouseListener(this);
		
		//�������ѯ��ť
		balance=new MyButton("balance","gif");
		balance.setBounds(30,400,170,60);
		panel.add(balance);
		balance.addMouseListener(this);
		
		//����޸����밴ť
		change = new MyButton("change","gif");
		change.setBounds(30,500,170,60);
		panel.add(change);
		change.addMouseListener(this);
		
		//���ע����ť
		cancellation=new MyButton("cancellation","gif");
		cancellation.setBounds(30,600,170,60);
		panel.add(cancellation);
		cancellation.addMouseListener(this);
		
		//����˳���ť
		exit = new MyButton("loginexit","gif");
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

	@Override
	public void mouseClicked(MouseEvent e) {
		//���
		if (e.getSource() == deposit) {
			DepositPage.depositPage = new DepositPage();
			tradingPage.setVisible(false);
		}
		//ȡ��
		else if (e.getSource() == withdraw) {
			WithDrawPage.withDrawPage = new WithDrawPage();
			tradingPage.setVisible(false);
		}
		//ת��
		else if (e.getSource() == transfer) {
			TransferPage.transferPage = new TransferPage();
			tradingPage.setVisible(false);
		}
		//����ѯ
		else if (e.getSource() == balance) {
			BalancePage.balancePage = new BalancePage(null,"��������ǣ�");
			tradingPage.setVisible(false);
		}
		//�޸�����
		else if (e.getSource() == change) {
			ResetPassPage.resetPassPage = new ResetPassPage();
			tradingPage.setVisible(false);
		}
		//���׼�¼
		else if (e.getSource() == query) {
			QueryPage.queryPage = new QueryPage();
			tradingPage.setVisible(false);
		}
		//ע��
		else if (e.getSource() == cancellation) {
			CancellationPage.cancellationPage = new CancellationPage();
			tradingPage.setVisible(false);
		}
		//�˳�
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
