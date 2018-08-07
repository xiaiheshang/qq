package com.it18zhang.tcp.qq.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.it18zhang.tcp.qq.common.MessageFactory;

/**
 * �ͻ���˽�Ľ���
 */
public class QQClientChatSingleUI extends JFrame implements ActionListener{
	
	private ClientCommThread commThread ;

	//��ʷ������
	private JTextArea taHistory ;
	
	//��Ϣ������
	private JTextArea taInputMessage ;
	
	//���Ͱ�ť
	private JButton btnSend ;
	
	//��������Ϣ
	private String recvInfo ;
	
	public QQClientChatSingleUI(ClientCommThread sender,String recvInfo){
		this.commThread = sender ;
		this.recvInfo = recvInfo ;
		init();
		this.setVisible(true);
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
		
		//taInputMessage
		taInputMessage = new JTextArea();
		taInputMessage.setBounds(0, 420, 540, 160);
		this.add(taInputMessage);
		
		//btnSend
		btnSend = new JButton("����");
		btnSend.setBounds(560, 420, 100, 160);
		btnSend.addActionListener(this);
		this.add(btnSend);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(-1);
			}
		});
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		//���Ͱ�ť
		if(source == btnSend){
			String str = taInputMessage.getText();
			if(str != null && !str.equals("")){
				//�����Լ���������Ϣ
				byte[] bytes = MessageFactory.popClientSingleChatMessage(recvInfo, str);
				commThread.sendMessage(bytes);
				//�����Ϣ��
				taInputMessage.setText("");
				//��ӵ���ʷ��
				taHistory.append("�� ˵ : \r\n");
				taHistory.append("        " + str);
				taHistory.append("\r\n");
			}
		}
	}
	
	/**
	 * ������ʷ��������
	 */
	public void updateHistory(String senderInfo,String msg) {
		taHistory.append(senderInfo + " ˵:\r\n");
		taHistory.append("       " + msg);
		taHistory.append("\r\n");
	}
}