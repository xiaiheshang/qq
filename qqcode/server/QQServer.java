package com.it18zhang.tcp.qq.server;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.it18zhang.tcp.qq.common.Message;
import com.it18zhang.tcp.qq.common.Util;

/**
 * Server��
 */
public class QQServer {
	
	private static QQServer server = new QQServer();
	
	public static QQServer getInstance(){
		return server ;
	}
	
	private QQServer(){
	}
	
	//ά������socket����
	//key : remoteIP + ":" + remotePort
	private Map<String, Socket> allSockets = new HashMap<String,Socket>();
	
	/**
	 * ����������
	 */
	public void start(){
		try {
			ServerSocket ss = new ServerSocket(8888);
			while(true){
				//����
				Socket sock = ss.accept();
				
				InetSocketAddress remoteAddr = (InetSocketAddress)sock.getRemoteSocketAddress();
				//Զ��ip
				String remoteIp = remoteAddr.getAddress().getHostAddress();
				//Զ�̶˿�
				int remotePort = remoteAddr.getPort();
				String key= remoteIp + ":" + remotePort;
				allSockets.put(key, sock);
				
				//�����������ͨ���߳�
				new CommThread(sock).start();
				this.broadcastFriends();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �õ������б�Ĵ�������
	 * @return
	 */
	public byte[] getFriendBytes(){
		try {
			List<String> list = new ArrayList<String>(allSockets.keySet());
			return Util.serializeObject((Serializable)list);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null ;
	}


	/**
	 * �㲥Ⱥ��
	 */
	public void broadcast(byte[] bytes) {
		Iterator<Socket> it = allSockets.values().iterator();
		while(it.hasNext()){
			try{
				OutputStream out = it.next().getOutputStream();
				//1.��Ϣ����
				out.write(bytes);
				out.flush();
			}
			catch(Exception e){
				continue ;
			}
		}
	}
	
	/**
	 * ����˽��
	 */
	public void send(byte[] msg , byte[] userInfo) {
		try{
			String key = new String(userInfo);
			if(allSockets.containsKey(key)){
				OutputStream out = allSockets.get(key).getOutputStream();
				out.write(msg);
				out.flush();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * �㲥�����б�
	 */
	public void broadcastFriends(){
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			//1.ˢ���б���Ϣ����
			baos.write(Util.int2Bytes(Message.SERVER_TO_CLIENT_REFRESH_FRIENTS));
			//2.�б�����ݳ���
			byte[] friendsBytes = QQServer.getInstance().getFriendBytes() ;
			baos.write(Util.int2Bytes(friendsBytes.length));
			//3.�б�����
			baos.write(QQServer.getInstance().getFriendBytes());
			QQServer.getInstance().broadcast(baos.toByteArray());
			
		} catch (Exception e) {
		}
	}
	
	/**
	 * ɾ��ָ���û� 
	 */
	public synchronized void removeUser(String user){
		allSockets.remove(user);
	}
}
