package com.lpz.ussd.enums;

/**
 * UssdVersion字段表示USSD会话的版本，目前USSD会话包括PHASE I、PHASE II、PHASE II+三种版本。
 * 
 * @author lpz
 *
 */
public enum UssdVersion {

	/**
	 * PHASE I版本是早期USSD会话版本 − 只支持一次交互（PSSD），不支持业务侧发起的会话 − 只有IMSI号码，不支持MSISDN −
	 * 只支持英文ASCII编码，不支持UCS2编码
	 * 
	 */
	PHASEI(0x10, "PHASE I"),
	/**
	 * PHASE II版本是当前的主流版本 − 支持多次会话交互（PSSR/USSR/USSN） − 支持业务侧发起的会话 −
	 * 支持IMSI号码和MSISDN号码 − 支持UCS2编码
	 * 
	 */
	PHASEII(0x20, "PHASE II"),
	/**
	 * PHASE II+的版本规范还在起草中，暂不使用。USSD各会话版本的具体值见表4-3。
	 */
	PHASEIII(0x25, "PHASE II+");

	private int value;
	private String msg;

	private UssdVersion(int value, String msg) {
		this.value = value;
		this.msg = msg;
	}

	public static UssdVersion fromValue(int value) {
		for (UssdVersion sc : UssdVersion.values()) {
			if (sc.getValue() == value) {
				return sc;
			}
		}
		return null;
	}

	public int getValue() {
		return value;
	}

	public String getMsg() {
		return msg;
	}

}
