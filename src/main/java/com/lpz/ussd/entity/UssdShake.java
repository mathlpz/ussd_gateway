package com.lpz.ussd.entity;

import com.lpz.ussd.enums.UssdCommandID;

/**
 * UssdShake用于USSD业务应用与USSD中心之间的握手，USSD中心收到USSD业务应用的UssdShake消息后会发送一条应答消息UssdShakeResp。
 * “USSD业务应用定期（5秒以内）向USSD中心发送UssdShake消息”，并检查是否收到UssdShakeResp消息来确定与USSD中心之间的连接是否正常。
 * 
 * @author lpz
 *
 */
public class UssdShake extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 393997852507316609L;

	public UssdShake() {
		super(UssdCommandID.USSD_SHAKE);
	}

	public UssdShake(Header header) {
		super(UssdCommandID.USSD_SHAKE, header);
	}

	@Override
	public String toString() {
		return "UssdShake: [Header = " + header + ", Body = null]";
	}

}
