package com.lpz.ussd.entity;

import com.lpz.ussd.enums.UssdCommandID;

/**
 * UssdContinue用于继续一个USSD对话，它既可以由移动台发出，也可以由USSD业务应用发出。
 * 
 * @author lpz
 *
 */
public class UssdContinue extends UssdBegin {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6436580760076458225L;
//
//	/**
//	 * UUSSD会话版本号。保持与UssdBegin相同。
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
//	 * UssdContinue中为保留字段，置为NULL
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

	public UssdContinue() {
		super(UssdCommandID.USSD_CONTINUE);
	}

	public UssdContinue(Header header) {
		super(UssdCommandID.USSD_CONTINUE, header);
	}

//	/**
//	 * 这个decode怎么处理
//	 */
//	@Override
//	public void buildBodyFromBuf(ByteBuf inBuf) {
//		this.ussdVersion = inBuf.readUnsignedByte();
//		this.ussdOpType = inBuf.readUnsignedByte();
//		this.msIsdn = inBuf.readBytes(21).toString();
//		this.serviceCode = inBuf.readBytes(4).toString();
//		this.codeScheme = inBuf.readUnsignedByte();
//		this.ussdString = inBuf.readBytes(inBuf.readableBytes())
//				.toString(charset(getCodeScheme()));
//	}
//
//	@Override
//	public void writeMsgToBuf(ByteBuf out) {
//		super.writeMsgToBuf(out);
//
//		// Integer 1字节
//		out.writeByte(ussdVersion);
//		out.writeByte(ussdOpType);
//		// C-Octet String 21字节
//		out.writeBytes(MsgUtils.getBytes(msIsdn, 21));
//		// FIXME: 2018/12/18 如果长度已经达到了4位，那么实际上还要以null结尾吗？
//		out.writeBytes(MsgUtils.getBytes(serviceCode, 4));
//		out.writeByte(codeScheme);
//		// Ocet String Var. max 182 字节
//		// FIXME: 2018/12/18 用什么编码转成的bytes
//		out.writeBytes(ussdString.getBytes(charset(codeScheme)));
//	}

	public short getUssdVersion() {
		return ussdVersion;
	}

	public void setUssdVersion(short ussdVersion) {
		this.ussdVersion = ussdVersion;
	}

	public short getUssdOpType() {
		return ussdOpType;
	}

	public void setUssdOpType(short ussdOpType) {
		this.ussdOpType = ussdOpType;
	}

	public String getMsIsdn() {
		return msIsdn;
	}

	public void setMsIsdn(String msIsdn) {
		this.msIsdn = msIsdn;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public short getCodeScheme() {
		return codeScheme;
	}

	public void setCodeScheme(short codeScheme) {
		this.codeScheme = codeScheme;
	}

	public String getUssdString() {
		return ussdString;
	}

	public void setUssdString(String ussdString) {
		this.ussdString = ussdString;
	}

	@Override
	public String toString() {
		return "UssdContinue [ussdVersion=" + ussdVersion + ", ussdOpType=" + ussdOpType + ", msIsdn=" + msIsdn
				+ ", serviceCode=" + serviceCode + ", codeScheme=" + codeScheme + ", ussdString=" + ussdString
				+ ", " + header + "]";
	}

}
