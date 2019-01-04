package com.lpz.ussd.entity;

import java.io.Serializable;

/**
 *
 * @author lpz
 * @version 2018年12月10日 下午3:02:42
 */
public class Header implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4860127412003341979L;

    /**
	 * CommandLength字段表示USSD业务应用协议消息的字节数长度。 它包括消息头和消息体两部分，
	 * CommandLength字段本身的长度也包含在内。
	 */
    private long commandLength;

    /**
     * CommandID字段表示USSD业务应用协议消息的类型。
     */
    private long commandID;

    /**
     * CommandStatus字段表示一个USSD业务应用协议消息成功或出错的状态，不使用该字段的消息应将该字段置为NULL
     */
	private int commandStatus;
    /**
	 * SenderCB字段表示发起方会话控制标识.
	 * 在USSD会话中，它用于发起方对当前会话进行标识。接收方在响应消息中应将此字段的值原样返回在ReceiverCB字段中。
	 * 若消息无需使用该字段，则将该字段置为0xFFFFFFFF。
	 */
    private long senderCB = 0xFFFFFFFF;
    /**
	 * ReceiverCB字段表示接收方会话控制标识。
	 * 在USSD会话中，它用于接收方对当前会话进行标识。若消息无需使用该字段，则将该字段置为0xFFFFFFFF。
	 */
    private long receiverCB = 0xFFFFFFFF;

    public long getCommandLength() {
        return commandLength;
    }

    public void setCommandLength(long commandLength) {
        this.commandLength = commandLength;
    }

    public long getCommandID() {
        return commandID;
    }

    public void setCommandID(long commandID) {
        this.commandID = commandID;
    }

	public int getCommandStatus() {
        return commandStatus;
    }

	public void setCommandStatus(int commandStatus) {
        this.commandStatus = commandStatus;
    }

    public long getSenderCB() {
        return senderCB;
    }

    public void setSenderCB(long senderCB) {
        this.senderCB = senderCB;
    }

    public long getReceiverCB() {
        return receiverCB;
    }

    public void setReceiverCB(long receiverCB) {
        this.receiverCB = receiverCB;
    }

    @Override
    public String toString() {
        return "Header [commandLength=" + commandLength + ", commandID=" + commandID + ", commandStatus="
                + commandStatus + ", senderCB=" + senderCB + ", receiverCB=" + receiverCB + "]";
    }

}
