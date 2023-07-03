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
		//修改密码
		welcome = new JLabel("修改密码");
		welcome.setBounds(100,100,700,100);
		welcome.setFont(new Font("黑体", Font.BOLD, 50));
		welcome.setForeground(Color.orange);
		panel.add(welcome);
		
		oldPassLabel = new JLabel("原密码：");
		oldPassLabel.setBounds(150,230,180,50);
		oldPassLabel.setFont(new Font("宋体",Font.PLAIN,35));
		oldPassLabel.setForeground(Color.white);
		panel.add(oldPassLabel);
		oldPass = new JPasswordField();
		oldPass.setBounds(330,230,300,50);
		oldPass.setFont(new Font("宋体",Font.BOLD,35));
		oldPass.addKeyListener(this);
		panel.add(oldPass);
		
		newPassLabel = new JLabel("新密码：");
		newPassLabel.setBounds(150,330,180,50);
		newPassLabel.setFont(new Font("宋体",Font.PLAIN,35));
		newPassLabel.setForeground(Color.white);
		panel.add(newPassLabel);
		newPass = new JPasswordField();
		newPass.setBounds(330,330,300,50);
		newPass.setFont(new Font("宋体",Font.BOLD,35));
		newPass.addKeyListener(this);
		panel.add(newPass);
		
		newPassLabel2 = new JLabel("确认密码：");
		newPassLabel2.setBounds(150,430,180,50);
		newPassLabel2.setFont(new Font("宋体",Font.PLAIN,35));
		newPassLabel2.setForeground(Color.white);
		panel.add(newPassLabel2);
		newPass2 = new JPasswordField();
		newPass2.setBounds(330,430,300,50);
		newPass2.setFont(new Font("宋体",Font.BOLD,35));
		newPass2.addKeyListener(this);
		panel.add(newPass2);

		//添加确认按钮
		confirm = new MyButton("confirm","gif");
		confirm.setBounds(700,600,170,60);
		panel.add(confirm);
		confirm.addMouseListener(this);
		
		//添加返回按钮
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
				JOptionPane.showMessageDialog(resetPassPage, "修改成功！");
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

		//原密码错误
		if (!pass.equals(oldPass.getText())) {
			wrongLabel4[0] = new JLabel("原密码错误");
			wrongLabel4[0].setFont(new Font("宋体",Font.BOLD,15));
			wrongLabel4[0].setForeground(Color.RED);
			wrongLabel4[0].setBounds(400,270,100,50);
			panel.add(wrongLabel4[0]);
			repaint();
			flag = 1;
		}
		else {
			rightLabel4[0] = new JLabel("√");
			rightLabel4[0].setFont(new Font("宋体",Font.BOLD,15));
			rightLabel4[0].setForeground(Color.green);
			rightLabel4[0].setBounds(650,230,100,50);
			panel.add(rightLabel4[0]);
			repaint();
		}

		if (flag == 1) return false;
		
		if (newPass.getText().length()!=6) {
			wrongLabel4[1] = new JLabel("请输入六位纯数字密码");
			wrongLabel4[1].setFont(new Font("宋体",Font.BOLD,15));
			wrongLabel4[1].setForeground(Color.RED);
			wrongLabel4[1].setBounds(400,370,200,50);
			panel.add(wrongLabel4[1]);
			repaint();
			flag = 1;
		}
		else if (newPass.getText().equals(oldPass.getText())) {
			wrongLabel4[1]=new JLabel("不能和原密码相同");
			wrongLabel4[1].setFont(new Font("宋体",Font.BOLD,15));
			wrongLabel4[1].setForeground(Color.RED);
			wrongLabel4[1].setBounds(400,370,200,50);
			panel.add(wrongLabel4[1]);
			repaint();
			flag = 1;
		}
		else {
			rightLabel4[1] = new JLabel("√");
			rightLabel4[1].setFont(new Font("宋体",Font.BOLD,15));
			rightLabel4[1].setForeground(Color.green);
			rightLabel4[1].setBounds(650,330,100,50);
			panel.add(rightLabel4[1]);
			repaint();
		}

		//检查密码是否一致
		if (!newPass.getText().equals(newPass2.getText())
				&& !newPass.getText().equals(oldPass.getText())) {
			wrongLabel4[2] = new JLabel("两次密码输入不一致");
			wrongLabel4[2].setFont(new Font("宋体",Font.BOLD,15));
			wrongLabel4[2].setForeground(Color.RED);
			wrongLabel4[2].setBounds(400,470,200,50);
			panel.add(wrongLabel4[2]);
			repaint();
			flag = 1;
		}
		else if (!newPass.getText().isEmpty()
				&& !newPass.getText().equals(oldPass.getText())){
			rightLabel4[2] = new JLabel("√");
			rightLabel4[2].setFont(new Font("宋体",Font.BOLD,15));
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
