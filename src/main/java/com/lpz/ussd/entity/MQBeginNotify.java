package com.lpz.ussd.entity;

public class MQBeginNotify {

	/**
	 * api调用-消息标识
	 */
	private String messageId;

	/**
	 * 移动台号码。接收手机号
	 */
	private String msIsdn;
	/**
	 * USSD串。消息内容
	 */
	private String ussdString;

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getMsIsdn() {
		return msIsdn;
	}

	public void setMsIsdn(String msIsdn) {
		this.msIsdn = msIsdn;
	}

	public String getUssdString() {
		return ussdString;
	}

	public void setUssdString(String ussdString) {
		this.ussdString = ussdString;
	}

	@Override
	public String toString() {
		return "MQBeginNotify [messageId=" + messageId + ", msIsdn=" + msIsdn + ", ussdString="
				+ ussdString + "]";
	}

}
