package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import dao.CardDao;
import dao.UserDao;
import model.CardInfo;
import model.UserInfo;
import util.MyButton;
import util.RandomID;
import util.RegisterPanel;

public class RegisterPage2 extends ATMManage implements MouseListener,KeyListener{

	static RegisterPage2 registerPage2;
	private MyButton back,confirm;
	private JLabel cardIDLabel,curTypeLabel,savingTypeLabel,openDateLabel,openMoneyLabel,passLabel,passAgainLabel;
	private JLabel[] wrongLabel2=new JLabel[2];
	private JLabel[] rightLabel2=new JLabel[2];
	private RegisterPanel registerPanel;
	private Date openDate;
	private JComboBox curType,savingType;
	private JPasswordField pass,passAgain;
	private String cardID;
	private JTextField openMoney;
	
	public RegisterPage2() {
		
		//添加上一步按钮
		back = new MyButton("previous","gif");
		back.setBounds(700,500,170,60);
		panel.add(back);
		back.addMouseListener(this);
		
		//添加确认按钮
		confirm = new MyButton("confirm","gif");
		confirm.setBounds(700,600,170,60);
		panel.add(confirm);
		confirm.addMouseListener(this);
		
		//添加注册面板内容
		registerPanel = new RegisterPanel(.1f,640,610);
		registerPanel.setBounds(30,50,640,610);
		registerPanel.setOpaque(false);  
		registerPanel.setLayout(null);
		panel.add(registerPanel);
		
		cardID = RandomID.getID();
		System.out.println(cardID);
		cardIDLabel=new JLabel("当前卡号为："+cardID);
		cardIDLabel.setFont(new Font("宋体",Font.BOLD,25));
		cardIDLabel.setForeground(Color.orange);
		cardIDLabel.setBounds(100,50,500,50);
		registerPanel.add(cardIDLabel);
		
		curTypeLabel = new JLabel("货币种类：");
		curTypeLabel.setFont(new Font("宋体",Font.BOLD,25));
		curTypeLabel.setForeground(Color.orange);
		curTypeLabel.setBounds(100,120,300,50);
		registerPanel.add(curTypeLabel);
		curType = new JComboBox();
		curType.addItem("人民币");  
		curType.addItem("英镑");  
		curType.addItem("日元"); 
		curType.addItem("美元");
		curType.addItem("欧元");
		curType.setFont(new Font("黑体",Font.BOLD,15));
		curType.setBounds(230,130,100,30);
        registerPanel.add(curType);  
		
        savingTypeLabel = new JLabel("存款类型：");
        savingTypeLabel.setFont(new Font("宋体",Font.BOLD,25));
        savingTypeLabel.setForeground(Color.orange);
        savingTypeLabel.setBounds(100,200,300,50);
		registerPanel.add(savingTypeLabel);
		savingType = new JComboBox();
		savingType.addItem("活期存储");  
		savingType.addItem("定期存储");  
		savingType.setFont(new Font("黑体",Font.BOLD,15));
		savingType.setBounds(230,210,100,30);
        registerPanel.add(savingType); 
        
        openDate = new Date();  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        String dateNowStr = sdf.format(openDate);  
        
		openDateLabel = new JLabel("开户日期："+dateNowStr);
		openDateLabel.setFont(new Font("宋体",Font.BOLD,25));
		openDateLabel.setForeground(Color.orange);
		openDateLabel.setBounds(100,280,500,50);
		registerPanel.add(openDateLabel);
        
		openMoneyLabel = new JLabel("开户金额：");
		openMoneyLabel.setFont(new Font("宋体",Font.BOLD,25));
		openMoneyLabel.setForeground(Color.orange);
		openMoneyLabel.setBounds(100,360,500,50);
		registerPanel.add(openMoneyLabel);
		openMoney = new JTextField("10");
		openMoney.setBounds(230,360,200,40);
		openMoney.setFont(new Font("宋体",Font.BOLD,25));
		openMoney.addKeyListener(this);
		registerPanel.add(openMoney);
		
		passLabel = new JLabel("密    码：");
		passLabel.setFont(new Font("宋体",Font.BOLD,25));
		passLabel.setForeground(Color.orange);
		passLabel.setBounds(100,440,300,50);
		registerPanel.add(passLabel);
		pass = new JPasswordField();
		pass.setBounds(230,445,200,40);
		pass.setFont(new Font("宋体",Font.BOLD,35));
		pass.addKeyListener(this);
		registerPanel.add(pass);
		
		passAgainLabel = new JLabel("确认密码：");
		passAgainLabel.setFont(new Font("宋体",Font.BOLD,25));
		passAgainLabel.setForeground(Color.orange);
		passAgainLabel.setBounds(100,520,300,50);
		registerPanel.add(passAgainLabel);
		passAgain = new JPasswordField();
		passAgain.setBounds(230,525,200,40);
		passAgain.setFont(new Font("宋体",Font.BOLD,35));
		passAgain.addKeyListener(this);
		registerPanel.add(passAgain);
		
		setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == confirm) {
			removeLabel();
			if (isRight() == true) {
				removeLabel();
				int customerID = UserDao.executeQueryMaxID();
				java.sql.Date date= new java.sql.Date(openDate.getTime());
				float money = Float.parseFloat(openMoney.getText());
				CardInfo card = new CardInfo(cardID, curType.getSelectedItem().toString(),
						savingType.getSelectedItem().toString(), date, money, money,
						pass.getText(), false, customerID);
				CardDao.executeInsert(card);
				
				RegisterPage1.registerPage1.dispose();
				JOptionPane.showMessageDialog(registerPage2,"开户成功");
				registerPage2.dispose();
				InitialPage.initialPage.setVisible(true);
			}
		}
		else if (e.getSource() == back) {
			UserDao.executeDelete(UserDao.executeQueryMaxID());
			RegisterPage1.registerPage1.setVisible(true);
			registerPage2.dispose();
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
		Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(pass.getText());
        if(pass.getText().isEmpty()) {
        	wrongLabel2[0] = new JLabel("密码不能为空");
			wrongLabel2[0].setFont(new Font("宋体",Font.BOLD,15));
			wrongLabel2[0].setForeground(Color.RED);
			wrongLabel2[0].setBounds(440,440,200,50);
			registerPanel.add(wrongLabel2[0]);
			repaint();
        	flag = 1;
        }
        else if (pass.getText().length() != 6 || !isNum.matches()) {
        	wrongLabel2[0] = new JLabel("请输入六位数字密码");
			wrongLabel2[0].setFont(new Font("宋体",Font.BOLD,15));
			wrongLabel2[0].setForeground(Color.RED);
			wrongLabel2[0].setBounds(440,440,200,50);
			registerPanel.add(wrongLabel2[0]);
			repaint();
        	flag = 1;
        }
        else {
        	rightLabel2[0] = new JLabel("√");
			rightLabel2[0].setFont(new Font("宋体",Font.BOLD,15));
			rightLabel2[0].setForeground(Color.green);
			rightLabel2[0].setBounds(440,440,100,50);
			registerPanel.add(rightLabel2[0]);
			repaint();
        }
        if (pass.getText().equals("")) {
        	wrongLabel2[1] = new JLabel("密码不能为空");
			wrongLabel2[1].setFont(new Font("宋体",Font.BOLD,15));
			wrongLabel2[1].setForeground(Color.RED);
			wrongLabel2[1].setBounds(440,520,200,50);
			registerPanel.add(wrongLabel2[1]);
			repaint();
        	flag = 1;
        }
        else if (!pass.getText().equals(passAgain.getText())) {
        	wrongLabel2[1] = new JLabel("两次输入密码不一致");
			wrongLabel2[1].setFont(new Font("宋体",Font.BOLD,15));
			wrongLabel2[1].setForeground(Color.RED);
			wrongLabel2[1].setBounds(440,520,200,50);
			registerPanel.add(wrongLabel2[1]);
			repaint();
        	flag = 1;
        }
        else {
        	rightLabel2[1] = new JLabel("√");
			rightLabel2[1].setFont(new Font("宋体",Font.BOLD,15));
			rightLabel2[1].setForeground(Color.green);
			rightLabel2[1].setBounds(440,520,100,50);
			registerPanel.add(rightLabel2[1]);
			repaint();
        }
        if (flag == 1) return false;
        return true;
	}
	public void removeLabel() {
		for (int i = 0; i < 2; i++) {
			try{
				registerPanel.remove(wrongLabel2[i]);
				registerPanel.remove(rightLabel2[i]);
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
            e.consume(); //关键，屏蔽掉非法输入  
        } 
	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}
