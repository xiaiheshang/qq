package com.it18zhang.tcp.qq.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.it18zhang.tcp.qq.common.MessageFactory;

/**
 * �ͻ���UI
 */
public class QQClientUI extends JFrame{
	
	public Map<String, QQClientChatSingleUI> windows = new HashMap<String, QQClientChatSingleUI>();
	
	private ClientCommThread commThread ;
	
	//��ʷ������
	private JTextArea taHistory ;
	
	//�����б�
	private JList<String> lstFriends  ;
	
	//��Ϣ������
	private JTextArea taInputMessage ;
	
	//���Ͱ�ť
	private JButton btnSend ;
	
	//ˢ�º����б�ť
	private JButton btnRefresh ;
	
	public QQClientUI(){
		init();
		this.setVisible(true);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(-1);
			}
		});
	}

	/**
	 * ��ʼ������
	 */
	private void init() {
		this.setTitle("QQClient");
		this.setBounds(100,100, 800, 600);
		this.setLayout(null);
		
		//��ʷ��
		taHistory = new JTextArea();
		taHistory.setBounds(0, 0, 600, 400);
		
		JScrollPane sp1 = new JScrollPane(taHistory);
		sp1.setBounds(0, 0, 600, 400);
		this.add(sp1);
		
		//lstFriends
		lstFriends = new JList<String>();
		lstFriends.setBounds(620, 0, 160, 400);
		
		this.add(lstFriends);
		
		lstFriends.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2){
					String friend = lstFriends.getModel().getElementAt(lstFriends.getSelectedIndex());
					if(windows.containsKey(friend)){
						windows.get(friend).setVisible(true);
					}
					else{
						QQClientChatSingleUI ui = new QQClientChatSingleUI(commThread,friend);
						windows.put(friend, ui);
					}
				}
			}
		});
		
		//taInputMessage
		taInputMessage = new JTextArea();
		taInputMessage.setBounds(0, 420, 540, 160);
		this.add(taInputMessage);
		
		//btnSend
		btnSend = new JButton("����");
		btnSend.setBounds(560, 420, 100, 160);
		this.add(btnSend);
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//
				String txt = taInputMessage.getText();
				if(!txt.trim().equals("")){
					byte[] msgBytes = MessageFactory.popClientChatsMessage(txt);
					commThread.sendMessage(msgBytes);
				}
				taInputMessage.setText("");
			}
		});
		
		//btnRefresh
		btnRefresh = new JButton("ˢ��");
		btnRefresh.setBounds(680, 420, 100, 160);
		this.add(btnRefresh);
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				byte[] msg = MessageFactory.popClientRefreshFriendsMessage();
				commThread.sendMessage(msg);
				System.out.println("ˢ�����!!!");
				
			}
		});
	}

	/**
	 * �����Ϣ����ʷ��
	 */
	public void addMsgToHistory(String senderInfo, String msgStr) {
		taHistory.append(senderInfo + " ˵ :\r\n");
		taHistory.append("       " + msgStr);
		taHistory.append("\r\n");
		taHistory.append("\r\n");
	}

	/**
	 * ˢ�º����б�
	 */
	public void refreshFriends(List<String> friends) {
		DefaultListModel<String> model = new DefaultListModel<String>();
		for(String s : friends){
			model.addElement(s);
		}
		lstFriends.setModel(model);
	}

	public ClientCommThread getCommThread() {
		return commThread;
	}

	public void setCommThread(ClientCommThread commThread) {
		this.commThread = commThread;
	}
}