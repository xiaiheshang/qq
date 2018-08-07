package com.it18zhang.tcp.qq.client;

import java.net.Socket;

public class QQClientMain {
	public static void main(String[] args) throws Exception {
		QQClientUI ui = new QQClientUI();
		Socket sock = new Socket("192.168.12.1", 8888) ;
		String localip = sock.getLocalAddress().getHostAddress();
		int port = sock.getLocalPort();
		ui.setTitle(localip + ":" + port);
		ClientCommThread thread = new ClientCommThread(sock,ui);
		thread.start();
		ui.setCommThread(thread);
	}

}
