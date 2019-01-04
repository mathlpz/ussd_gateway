package com.lpz.ussd.entity;


import com.lpz.ussd.enums.UssdCommandID;
import com.lpz.ussd.util.GlobalConstance;
import com.lpz.ussd.util.MsgUtils;

import io.netty.buffer.ByteBuf;

/**
 * UssdBind用于USSD业务应用向USSD中心登录，进行身份认证，并建立网络连接。
 * 
 * @author lpz
 * @version 2018年12月10日 下午3:21:50
 */
public class UssdBind extends Message {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -103721409469601645L;

	/**
	 * AccountName字段表示需要登录到USSD中心的USSD业务应用的帐号名。
	 */
    private String accountName;
	/**
	 * Password字段表示USSD业务应用登录USSD中心的密码，用于USSD中心对USSD业务应用进行身份验证。
	 */
    private String password;
	/**
	 * SystemType字段表示USSD业务应用的类型，暂不使用。
	 */
    private String systemType;
	/**
	 * InterfaceVersion字段表示USSD业务应用协议的版本，目前版本定为0x10。
	 */
    private long interfaceVersion = 0x10;

	public UssdBind() {
        super(UssdCommandID.USSD_BIND);
    }

    public UssdBind(Header header) {
        super(UssdCommandID.USSD_BIND, header);
    }

    public UssdBind(UssdCommandID command) {
        super(command);
    }

	/**
	 * decode
	 */
    @Override
    public void buildBodyFromBuf(ByteBuf in) {
		this.accountName = in.readBytes(11).toString(GlobalConstance.GBK_CHARSET).trim();
		this.password = in.readBytes(9).toString(GlobalConstance.GBK_CHARSET).trim();
		this.systemType = in.readBytes(13).toString(GlobalConstance.GBK_CHARSET).trim();
        this.interfaceVersion = in.readUnsignedInt();
    }

	/**
	 * encode
	 */
    @Override
    public void writeMsgToBuf(ByteBuf out) {
        super.writeMsgToBuf(out);
		//
        out.writeBytes(MsgUtils.getBytes(accountName, 11));
        out.writeBytes(MsgUtils.getBytes(password, 9));
        out.writeBytes(MsgUtils.getBytes(systemType, 13));
        out.writeInt((int) interfaceVersion);
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public long getInterfaceVersion() {
        return interfaceVersion;
    }

    public void setInterfaceVersion(long interfaceVersion) {
        this.interfaceVersion = interfaceVersion;
    }

	@Override
	public String toString() {
		return "UssdBind [accountName=" + accountName + ", password=" + password + ", systemType=" + systemType
				+ ", interfaceVersion=" + interfaceVersion + ", header:" + getHeader().toString() + "]";
	}


}
