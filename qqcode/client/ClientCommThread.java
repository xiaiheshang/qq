package com.it18zhang.tcp.qq.client;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import com.it18zhang.tcp.qq.common.Message;
import com.it18zhang.tcp.qq.common.MessageFactory;
import com.it18zhang.tcp.qq.common.ServerChatsMessage;
import com.it18zhang.tcp.qq.common.ServerRefreshFriendsMessage;
import com.it18zhang.tcp.qq.common.ServerSingleChatMessage;
import com.it18zhang.tcp.qq.common.Util;

/**
 * ������Ϣ�߳�
 */
public class ClientCommThread extends Thread {

	private Socket sock;
	
	private QQClientUI ui;

	public ClientCommThread(Socket sock,QQClientUI ui) {
		this.sock = sock ;
		this.ui = ui ;
		this.setDaemon(true);
	}
	
	public void run() {
		while(true){
			try {
				Message msg = MessageFactory.parseServerMessage(sock);
				//Ⱥ��
				if(msg.getClass() == ServerChatsMessage.class){
					ServerChatsMessage m = (ServerChatsMessage)msg;
					String senderInfo = new String(m.getSenderInfoBytes());
					String msgStr = new String(m.getMsgBytes());
					//�����Ϣ����ʷ��
					ui.addMsgToHistory(senderInfo,msgStr);
				}
				//˽��
				else if(msg.getClass() == ServerSingleChatMessage.class){
					ServerSingleChatMessage m = (ServerSingleChatMessage)msg;
					String senderInfo = new String(m.getSenderInfoBytes());
					String msgStr = new String(m.getMessage());
					
					//TODO ˽��
					QQClientChatSingleUI sui = null ;
					if(!ui.windows.containsKey(senderInfo)){
						sui = new QQClientChatSingleUI(this, senderInfo);
						ui.windows.put(senderInfo, sui);
					}
					else{
						sui = ui.windows.get(senderInfo);
						sui.setVisible(true);
					}
					sui.updateHistory(senderInfo, msgStr);
				} 
				//ˢ��
				else if(msg.getClass() == ServerRefreshFriendsMessage.class){
					ServerRefreshFriendsMessage m = (ServerRefreshFriendsMessage)msg;
					byte[] bytes = m.getFriendsBytes();
					List<String> friends = (List<String>)Util.deSerializeObject(bytes);
					ui.refreshFriends(friends);
				} 
			} catch (Exception e) {
				return ;
			}
		}
	}
	
	/**
	 * ������Ϣ
	 */
	public void sendMessage(byte[] bytes){
		try {
			sock.getOutputStream().write(bytes);
			sock.getOutputStream().flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			sock.getOutputStream().close();
			sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
