package com.lpz.ussd.enums;

/**
 * CodeScheme字段表示USSD串的编码方案，具体的编码方案参见GSM03.38。
 * 对于USSD业务方发起的会话，该字段表示USSD中心要采用何种方式对USSD串进行编码。
 * 对于USSD中心发往USSD业务应用的会话消息，该字段的值通常为明码，即8位或16位。
 * 
 * 其中0x11仅适用于中国境内。
 * 
 * @author lpz
 *
 */
public enum UssdCodeScheme {

	CODE_SCHEME_7((short) 0x0F, "7位编码"),
	//
	CODE_SCHEME_16((short) 0x11, "16位编码"),
	//
	CODE_SCHEME_8((short) 0x44, "8位编码"),
	//
	CODE_SCHEME_16_2((short) 0x48, "16位编码");

	private short value;
	private String msg;

	private UssdCodeScheme(short value, String msg) {
		this.value = value;
		this.msg = msg;
	}

	public static UssdCodeScheme fromValue(short value) {
		for (UssdCodeScheme sc : UssdCodeScheme.values()) {
			if (sc.getValue() == value) {
				return sc;
			}
		}
		return null;
	}

	public short getValue() {
		return value;
	}

	public String getMsg() {
		return msg;
	}
}
