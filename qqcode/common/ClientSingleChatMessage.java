package com.it18zhang.tcp.qq.common;

/**
 * �ͻ��˵�����Ϣ
 */
public class ClientSingleChatMessage extends Message {
	
	//���ͷ���Ϣ
	private byte[] recverInfoBytes ;
	
	public byte[] getRecverInfoBytes() {
		return recverInfoBytes;
	}

	public void setRecverInfoBytes(byte[] recverInfoBytes) {
		this.recverInfoBytes = recverInfoBytes;
	}

	//��Ϣ����
	private byte[] message ;

	public byte[] getMessage() {
		return message;
	}

	public void setMessage(byte[] message) {
		this.message = message;
	}


	public int getMessageType() {
		return Message.CLIENT_TO_SERVER_SINGLE_CHAT;
	}
}
