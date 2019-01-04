package com.lpz.ussd.entity;

import com.lpz.ussd.enums.UssdCommandID;

/**
 * UssdEnd用于指示某次USSD会话的结束，它只能由USSD业务应用发出。UssdEnd表示USSD会话是正常结束的。
 *
 * @author lpz
 */
public class UssdEnd extends UssdBegin {

    /**
     *
     */
    private static final long serialVersionUID = -5425428760704027207L;

    //	/**
//	 * USSD会话版本号。 保持与UssdBegin相同。
//	 *
//	 */
//	private short ussdVersion;
//	/**
//	 * USSD会话操作类型
//	 */
//	private short ussdOpType;
//	/**
//	 * 移动台号码。保持与UssdBegin相同
//	 */
//	private String msIsdn;
//	/**
//	 * UssdEnd中为保留字段，置为NULL。
//	 */
//	private String serviceCode;
//	/**
//	 * USSD串的编码方案
//	 */
//	private short codeScheme;
//	/**
//	 * USSD串。Var. max 182
//	 */
//	private String ussdString;

    public UssdEnd() {
		super(UssdCommandID.USSD_END);
    }

    public UssdEnd(Header header) {
		super(UssdCommandID.USSD_END, header);
    }

//	public short getUssdVersion() {
//		return ussdVersion;
//	}
//
//	public void setUssdVersion(short ussdVersion) {
//		this.ussdVersion = ussdVersion;
//	}
//
//	public short getUssdOpType() {
//		return ussdOpType;
//	}
//
//	public void setUssdOpType(short ussdOpType) {
//		this.ussdOpType = ussdOpType;
//	}
//
//	public String getMsIsdn() {
//		return msIsdn;
//	}
//
//	public void setMsIsdn(String msIsdn) {
//		this.msIsdn = msIsdn;
//	}
//
//	public String getServiceCode() {
//		return serviceCode;
//	}
//
//	public void setServiceCode(String serviceCode) {
//		this.serviceCode = serviceCode;
//	}
//
//	public short getCodeScheme() {
//		return codeScheme;
//	}
//
//	public void setCodeScheme(short codeScheme) {
//		this.codeScheme = codeScheme;
//	}
//
//	public String getUssdString() {
//		return ussdString;
//	}
//
//	public void setUssdString(String ussdString) {
//		this.ussdString = ussdString;
//	}

	@Override
	public String toString() {
		return "UssdEnd [ussdVersion=" + ussdVersion + ", ussdOpType=" + ussdOpType + ", msIsdn=" + msIsdn
				+ ", serviceCode=" + serviceCode + ", codeScheme=" + codeScheme + ", ussdString=" + ussdString
				+ ", " + header + "]";
	}

}
