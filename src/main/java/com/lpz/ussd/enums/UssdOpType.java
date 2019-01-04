package com.lpz.ussd.enums;

/**
 * UssdOpType字段表示USSD会话的操作类型，主要包括四种类型：Request、Notify、Response、Release。
 * 
 * @author lpz
 *
 */
public enum UssdOpType {

	/**
	 *  UssdBegin对应Request、Notify两种操作类型，移动台发起的UssdBegin会话的操作类型只能为Request，
	 * 业务方发起的UssdBegin会话的操作类型可以为Request和Notify。
	 * 
	 */
	REQUEST(0x01),
	/**
	 * 
	 * UssdContinue对应Request、Notify、Response三种操作类型，移动台发出的UssdContinue会话的操作类型只能为Response，
	 * 业务方发出的UssdContinue会话的操作类型只能为Request和Notify。
	 * 
	 */
	NOTIFY(0x02),
	/**
	 */
	RESPONSE(0x03),
	/**
	 *  UssdEnd对应Response、Release两种操作类型，若会话的最初发起方为移动台，则UssdEnd的操作类型只能为Response，
	 * 若会话的最初发起方为业务方，则UssdEnd的操作类型只能为Release。
	 */
	RELEASE(0x04);

	private int value;

	private UssdOpType(int value) {
		this.value = value;
	}

	public static UssdOpType fromValue(int value) {
		for (UssdOpType sc : UssdOpType.values()) {
			if (sc.getValue() == value) {
				return sc;
			}
		}
		return null;
	}

	public int getValue() {
		return value;
	}

}
