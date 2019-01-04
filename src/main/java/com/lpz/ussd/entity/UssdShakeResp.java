package com.lpz.ussd.entity;


import com.lpz.ussd.enums.UssdCommandID;

import io.netty.buffer.ByteBuf;

/**
 * USSD中心可以通过检查是否收到UssdShake消息来确定与USSD业务应用之间的连接是否正常。
 * 
 * @author lpz
 *
 */
public class UssdShakeResp extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4814959390302696840L;

	public UssdShakeResp() {
		super(UssdCommandID.USSD_SHAKE_RESP);
	}

	public UssdShakeResp(Header header) {
		super(UssdCommandID.USSD_SHAKE_RESP, header);
	}

	@Override
	public void writeMsgToBuf(ByteBuf out) {
		super.writeMsgToBuf(out);
	}

	@Override
	public void buildBodyFromBuf(ByteBuf in) {

	}

	@Override
	public String toString() {
		return "UssdShakeResp: " + header + ", Body = null]";
	}


}
