package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import dao.TransDao;
import model.TransInfo;
import util.MyButton;

public class QueryPage extends ATMManage implements MouseListener{

	static QueryPage queryPage;
	private MyButton confirm;
	private JLabel tips;
	public QueryPage() {
		
		tips = new JLabel("交易记录");
		tips.setBounds(100,50,700,100);
		tips.setFont(new Font("黑体", Font.BOLD, 50));
		tips.setForeground(Color.orange);
		panel.add(tips);
		
        String[] columnNames = {"交易日期","卡号","收款方卡号","交易类型","交易金额","备注"};
        ArrayList<TransInfo> transInfo=TransDao.executeGetAllRecords(LoginPage.CardID);
        String[][] tableValues = new String[transInfo.size()][6];
        int i = 0;
        for (TransInfo info : transInfo) {
        	tableValues[i][0]=transInfo.get(i).getTransDate().toString();
        	tableValues[i][1]=transInfo.get(i).getCardID();
        	tableValues[i][2]=(transInfo.get(i).getOtherCardID()==null?transInfo.get(i).getCardID():transInfo.get(i).getOtherCardID());
        	tableValues[i][3]=transInfo.get(i).getTransType();
        	tableValues[i][4]=Float.toString(transInfo.get(i).getTransMoney());
        	tableValues[i][5]=(transInfo.get(i).getRemark()==null?"无":transInfo.get(i).getRemark());
        	i++;
        }

        JTable table = new JTable(tableValues,columnNames);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(130);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        table.getColumnModel().getColumn(3).setPreferredWidth(90);
        table.getColumnModel().getColumn(4).setPreferredWidth(90);
        table.getColumnModel().getColumn(5).setPreferredWidth(90);
        table.setRowHeight(25);
        table.setBounds(50,150,800,400);
        table.setFont(new Font("楷体", Font.PLAIN, 20));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50,150,800,400);

        //添加退出按钮
        confirm = new MyButton("confirm","gif");
        confirm.setBounds(700,620,170,60);
      	panel.add(confirm);
      	confirm.addMouseListener(this);
      		
        panel.add(scrollPane);
		setVisible(true);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == confirm) {
			queryPage.dispose();
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
