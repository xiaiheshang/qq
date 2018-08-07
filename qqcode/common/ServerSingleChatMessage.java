package com.it18zhang.tcp.qq.common;

/**
 * ������˽��
 */
public class ServerSingleChatMessage extends Message {
	
	//���ͷ���Ϣ
	private byte[] recverInfoBytes ;
	
	private byte[] senderInfoBytes ;
	
	public byte[] getSenderInfoBytes() {
		return senderInfoBytes;
	}

	public void setSenderInfoBytes(byte[] senderInfoBytes) {
		this.senderInfoBytes = senderInfoBytes;
	}

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
		return Message.SERVER_TO_CLIENT_SINGLE_CHAT;
	}
}
