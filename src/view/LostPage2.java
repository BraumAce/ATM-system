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
import dao.UserDao;
import util.MyButton;

public class LostPage2 extends ATMManage implements MouseListener,KeyListener{
	
	static LostPage2 lostPage;
	private MyButton confirm,back;
	private JLabel cardIDLabel,nameLabel,IDLabel,telephoneLabel,tips;
	private JTextField cardID,name,ID,telephone;
	
	public LostPage2() {
		tips = new JLabel("����дҪȡ����ʧ���п�����Ϣ��");
		tips.setBounds(50,30,640,50);
		tips.setFont(new Font("����",Font.BOLD,25));
		tips.setForeground(Color.orange);
		panel.add(tips);
		
		cardIDLabel = new JLabel("��ʧ���ţ�");
		cardIDLabel.setBounds(150,130,180,50);
		cardIDLabel.setFont(new Font("����",Font.PLAIN,35));
		cardIDLabel.setForeground(Color.white);
		panel.add(cardIDLabel);
		cardID = new JTextField();
		cardID.setBounds(330,130,300,50);
		cardID.setFont(new Font("����",Font.BOLD,25));
		panel.add(cardID);
		cardID.addKeyListener(this);
		
		nameLabel = new JLabel("������  ��");
		nameLabel.setBounds(150,230,180,50);
		nameLabel.setFont(new Font("����",Font.PLAIN,35));
		nameLabel.setForeground(Color.white);
		panel.add(nameLabel);
		name = new JTextField();
		name.setBounds(330,230,300,50);
		name.setFont(new Font("����",Font.BOLD,25));
		panel.add(name);

		IDLabel = new JLabel("���֤�ţ�");
		IDLabel.setBounds(150,330,180,50);
		IDLabel.setFont(new Font("����",Font.PLAIN,35));
		IDLabel.setForeground(Color.white);
		panel.add(IDLabel);
		ID = new JTextField();
		ID.setBounds(330,330,300,50);
		ID.setFont(new Font("����",Font.BOLD,25));
		panel.add(ID);
		ID.addKeyListener(this);
		
		telephoneLabel = new JLabel("��ϵ�绰��");
		telephoneLabel.setBounds(150,430,180,50);
		telephoneLabel.setFont(new Font("����",Font.PLAIN,35));
		telephoneLabel.setForeground(Color.white);
		panel.add(telephoneLabel);
		telephone = new JTextField();
		telephone.setBounds(330,430,300,50);
		telephone.setFont(new Font("����",Font.BOLD,25));
		panel.add(telephone);
		telephone.addKeyListener(this);
		
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
		if (e.getSource() == back) {
			lostPage.dispose();
			ChooseLostPage.chooseLostPage.setVisible(true);
		}
		else if (e.getSource() == confirm) {
			int customerID = CardDao.executeGetCustomerID(cardID.getText());
			if(customerID == -1) {
				JOptionPane.showMessageDialog(lostPage, "ȡ����ʧ�Ŀ��Ų����ڣ��������������","����",JOptionPane.ERROR_MESSAGE);
			}
			else {
				boolean isRight=UserDao.executeVerify(customerID,ID.getText(),name.getText(),telephone.getText());
				if (!isRight) {
					JOptionPane.showMessageDialog(lostPage, "������������Ϣ��ƥ�䣬�������������","����",JOptionPane.ERROR_MESSAGE);
				}
				else {
					if(CardDao.executeIsLost(cardID.getText())) {
						CardDao.executeSetLost(cardID.getText(), false);
						JOptionPane.showMessageDialog(lostPage, "ȡ����ʧ�ɹ�");
						lostPage.dispose();
						InitialPage.initialPage.setVisible(true);					
					}
					else {
						JOptionPane.showMessageDialog(lostPage, "�����п�δ��ʧ","����",JOptionPane.ERROR_MESSAGE);
					}
				}				
			}
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
