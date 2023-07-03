package view;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import model.CardInfo;
import model.UserInfo;
import util.BgpPanel;

public class ATMManage extends JFrame {
	BgpPanel panel;
	ImageIcon bgp;
	static ArrayList<UserInfo> userInfo = new ArrayList<UserInfo>();
	static ArrayList<CardInfo> cardInfo = new ArrayList<CardInfo>();
	public ATMManage() {
		setSize(900,800);
		setLocationRelativeTo(null);
		setResizable(false);
		bgp = new ImageIcon("source/bankBgp.jpg");
		panel = new BgpPanel(bgp.getImage());
		panel.setLayout(null);
		
		add(panel);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void init() {
		setVisible(true);
	}
}
