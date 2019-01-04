package com.lpz.ussd.entity;


import com.lpz.ussd.enums.UssdCommandID;
import com.lpz.ussd.util.GlobalConstance;
import com.lpz.ussd.util.MsgUtils;

import io.netty.buffer.ByteBuf;

/**
 * UssdBegin用于请求建立一个新的USSD会话，它既可以由移动台发出，也可以由USSD业务应用发出。
 *
 * @author lpz
 */
public class UssdBegin extends Message {

    /**
     *
     */
    private static final long serialVersionUID = -2241352658935513721L;

    /**
     * USSD会话版本号。目前USSD会话包括PHASE I、PHASE II、PHASE II+三种版本。
     */
    protected short ussdVersion;
    /**
     * USSD会话操作类型。主要包括四种类型：Request、Notify、Response、Release。
     */
    protected short ussdOpType;
    /**
     * 移动台号码。MSISDN字段为包含国家码的号码格式。
     */
    protected String msIsdn;
    /**
     * 会话对应的业务码。该字段只在UssdBegin会话中有效，其他类型消息中该字段置为Null。!!!
     * USSD业务码是包含引导符的，引导符为1~3位“*”、“#”字符的组合，有可能为扩展业务码，比如*100和*100*1可以作为两个不同的业务码；
     * ServiceCode赋值为数字业务码部分不超过3字节的内容，并以“\0”结束，如手机拨打*101*1#、*70#、*2000#，ServiceCode字段的值为“101”、“70”、“200”。
     */
    protected String serviceCode;
    /**
     * USSD串的编码方案，具体的编码方案参见GSM03.38。 对于USSD业务方发起的会话，该字段表示USSD中心要采用何种方式对USSD串进行编码。
     * 对于USSD中心发往USSD业务应用的会话消息，该字段的值通常为明码，即8位或16位。 常用的编码方案包括：  0x0F（7位编码） 
     * 0x11（16位编码）  0x44（8位编码）  0x48（16位编码）
     */
    protected short codeScheme;
    /**
	 * USSD会话的信息串。Var. max 182
	 * 
	 * SSDString中需要携带USSD业务码信息，UssdString的格式为：@+业务码+@+具体USSD消息。其中业务码部分的填写需要包含引导符。
	 * 网络侧发起的USSDBegin消息的USSDString格式比如：@*123@Hello,World!
	 * 
	 * @author lpz
	 * 
	 */
    protected String ussdString;

    public UssdBegin() {
        this(UssdCommandID.USSD_BEGIN);
    }

    protected UssdBegin(UssdCommandID ussdCommandID) {
        super(ussdCommandID);
    }

    public UssdBegin(Header header) {
        this(UssdCommandID.USSD_BEGIN, header);
    }

    protected UssdBegin(UssdCommandID ussdCommandID, Header header) {
        super(ussdCommandID, header);
    }

    @Override
    public void buildBodyFromBuf(ByteBuf in) {
        ussdVersion = in.readUnsignedByte();
        ussdOpType = in.readUnsignedByte();
		msIsdn = in.readBytes(21).toString(GlobalConstance.GBK_CHARSET).trim();
		serviceCode = in.readBytes(4).toString(GlobalConstance.GBK_CHARSET).trim();
        codeScheme = in.readUnsignedByte();
        // FIXME: 2018/12/18 需要制定的编码方式进行解码
//        ussdString = in.readBytes(in.readableBytes())
//                .toString(charset(getCodeScheme()));

		byte[] bytes = new byte[in.readableBytes()];
		in.readBytes(bytes);
		this.ussdString = new String(bytes, charset(getCodeScheme())).trim();

    }

    /**
     * 不知道字节怎么搞？encode
     */
    @Override
    public void writeMsgToBuf(ByteBuf out) {
        super.writeMsgToBuf(out);

        // Integer 1字节
        out.writeByte(ussdVersion);
        out.writeByte(ussdOpType);
        // C-Octet String 21字节
        out.writeBytes(MsgUtils.getBytes(msIsdn, 21));
        // FIXME: 2018/12/18 如果长度已经达到了4位，那么实际上还要以null结尾吗？
        out.writeBytes(MsgUtils.getBytes(serviceCode, 4));
        out.writeByte(codeScheme);
        // Ocet String Var. max 182 字节
        // FIXME: 2018/12/18 用什么编码转成的bytes
		if (null != ussdString) {
			out.writeBytes(ussdString.getBytes(charset(codeScheme)));
		} else {
			out.writeBytes(MsgUtils.getBytes(null, 1));
		}
    }

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
        return "UssdBegin [ussdVersion=" + ussdVersion + ", ussdOpType=" + ussdOpType + ", msIsdn=" + msIsdn
                + ", serviceCode=" + serviceCode + ", codeScheme=" + codeScheme + ", ussdString=" + ussdString
                + ", " + getHeader().toString() + "]";
    }

}
