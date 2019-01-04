package com.lpz.ussd.util;


import java.nio.charset.Charset;

/**
 * Created by zhanghuwei on 15/11/10.
 */
public interface GlobalConstance {

	/**
	 * Integer 无符号整数，大小可为1字节、4字节、8字节，由具体 场合指定
	 * C-Octet String 以NULL（0x00）字符为结尾的字符串
	 * Octet String 任意由八位字符组成的字符串（不需要以NULL为结尾）
	 * 所有消息结构中各参数没有Var说明的，均为固定长度。
	 */
	static final byte END_FLAG = 0x00;

    byte[] EMPTY_BYTES = new byte[0];

    String[] EMPTY_STRING_ARRAY = new String[0];

    Charset GBK_CHARSET = Charset.forName("GBK");

//	AttributeKey<SessionState> SESSION_STATE_ATTRIBUTE_KEY = AttributeKey.newInstance(SessionState.CONNECT.name());

	String LOGGER_NAME_PREFIX = "entity.%s";

}
