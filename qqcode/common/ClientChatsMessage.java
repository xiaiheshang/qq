package com.it18zhang.tcp.qq.common;

/**
 * �ͻ��˺ʹ��Ⱥ����Ϣ
 */
public class ClientChatsMessage extends Message {
	//��Ϣ����
	private byte[] message ;

	public byte[] getMessage() {
		return message;
	}

	public void setMessage(byte[] message) {
		this.message = message;
	}

	public int getMessageType() {
		return CLIENT_TO_SERVER_CHATS;
	}
}
