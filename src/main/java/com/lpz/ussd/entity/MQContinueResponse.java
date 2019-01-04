package com.lpz.ussd.entity;

/**
 * 
 * @author lpz
 *
 */
public class MQContinueResponse {


	/**
	 * api调用-消息标识
	 */
	private String messageId;
	/**
	 * 移动台号码。接收手机号
	 */
	private String msIsdn;
	/**
	 * USSDC响应字段：CommandStatus
	 */
	private int status;


	public String getMsIsdn() {
		return msIsdn;
	}

	public void setMsIsdn(String msIsdn) {
		this.msIsdn = msIsdn;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "MQContinueResponse [messageId=" + messageId + ", msIsdn=" + msIsdn + ", status=" + status + "]";
	}

}
