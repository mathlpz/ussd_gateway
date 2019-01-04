package com.lpz.ussd.entity;


import com.lpz.ussd.enums.UssdCommandID;
import com.lpz.ussd.util.MsgUtils;

import io.netty.buffer.ByteBuf;

/**
 * 在进行USSD会话前USSD业务应用必须先进行Bind操作，请求登录到USSD中心，登录时需指定USSD业务应用的帐号名和登录密码。
 * 无论USSD业务应用身份认证是否通过，USSD中心都会给USSD业务应用发送一个应答消息UssdBindResp。
 *
 * @author lpz
 */
public class UssdBindResp extends Message {

    /**
     *
     */
    private static final long serialVersionUID = -679985582386958992L;

    private String accountName;

	public UssdBindResp() {
		super(UssdCommandID.USSD_BIND_RESP);
	}

    public UssdBindResp(Header header) {
        super(UssdCommandID.USSD_BIND_RESP, header);
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    @Override
    public void writeMsgToBuf(ByteBuf out) {
        super.writeMsgToBuf(out);
        out.writeBytes(MsgUtils.getBytes(accountName, 11));
    }

    @Override
    public void buildBodyFromBuf(ByteBuf byteBuf) {
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
		this.accountName = new String(bytes).trim();
    }

    @Override
    public String toString() {
        return "UssdBindResp [accountName=" + accountName + ", " + getHeader() + "]";
    }

}
