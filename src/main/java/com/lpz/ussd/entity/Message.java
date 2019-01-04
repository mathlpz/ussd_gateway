package com.lpz.ussd.entity;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicInteger;

import com.lpz.ussd.enums.UssdCommandID;

import io.netty.buffer.ByteBuf;

/**
 * USSD消息的基类，预先定义USSD消息头，在其子类中再定义USSD消息体
 *
 * @author lpz
 */
public class Message implements Serializable, Cloneable {

    private static final long serialVersionUID = -2122804366529481241L;

    protected UssdCommandID ussdCommand;
    protected Header header;

    //用以记录重试次数
    private AtomicInteger requests = new AtomicInteger();

    public Message() {
        super();
    }

    public Message(Header header) {
        this(UssdCommandID.fromValue(header.getCommandID()), header);
    }

    public Message(UssdCommandID command, Header header) {
        setUssdCommand(command);
        if (header == null) {
            header = new Header();
        }
        header.setCommandID(command.getValue());
        setHeader(header);
    }

    public Message(UssdCommandID command) {
        setUssdCommand(command);
        Header header = new Header();
        //TODO:注意此时没有给header赋值长度,在encode时赋了长度
        header.setCommandID(command.getValue());
        setHeader(header);
    }

    /**
     * 将消息写入ByteBuf缓冲区内
     *
     * @param out 待写入的缓冲区
     */
    public void writeMsgToBuf(ByteBuf out) {
        out.writeInt((int) header.getCommandLength());
        out.writeInt((int) header.getCommandID());
        out.writeInt(header.getCommandStatus());
        out.writeInt((int) header.getSenderCB());
        out.writeInt((int) header.getReceiverCB());
    }

    /**
     * 从缓冲区中读取数据以建立消息体
     *
     * @param in 待读取的缓冲区
     */
    public void buildBodyFromBuf(ByteBuf in) {

    }

	/**
	 * 定义编码格式。UssdCodeScheme
	 * 
	 * @param charSet
	 * @return
	 */
    protected Charset charset(short charSet) {
        switch (charSet) {
		case 0x0F:
		case 0x44:
                return Charset.forName("US-ASCII");
		case 0x11:
		case 0x48:
			return Charset.forName("GBK");
//                return Charset.forName("ISO-10646-UCS-2");
            default:
                return Charset.forName("GBK");
        }
    }

    public UssdCommandID getUssdCommand() {
        return ussdCommand;
    }

    public void setUssdCommand(UssdCommandID ussdCommand) {
        this.ussdCommand = ussdCommand;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public AtomicInteger getRequests() {
        return requests;
    }

    public void setRequests(AtomicInteger requests) {
        this.requests = requests;
    }

    /**
     * 重置
     */
    public void resetRequests() {
        requests = new AtomicInteger();
    }

    /**
     * 自增
     *
     * @return
     */
    public int incrementAndGetRequests() {
        return requests.incrementAndGet();
    }

    @Override
    public Message clone() throws CloneNotSupportedException {
        Message msg = (Message) super.clone();

        Header newHeader = new Header();
        newHeader.setCommandID(ussdCommand.getValue());
        msg.setHeader(newHeader);

        msg.resetRequests();
        return msg;
    }
}