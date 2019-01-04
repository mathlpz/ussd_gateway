package com.lpz.ussd.entity;


import com.lpz.ussd.enums.UssdCommandID;

import io.netty.buffer.ByteBuf;

/**
 * UssdAbort用于中止当前的USSD会话，它既可以由移动台发出，也可以由USSD业务应用发出。UssdAbort表示USSD会话是异常结束的。
 * 
 * @author lpz
 *
 */
public class UssdAbort extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5675962230906231399L;

	public UssdAbort() {
		super(UssdCommandID.USSD_ABORT);
	}

	public UssdAbort(Header header) {
		super(UssdCommandID.USSD_ABORT, header);
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
		return "UssdAbort: " + header + ", Body = null]";
	}

}
