/*
 *  项目: openplatform
 *  模块: open-cmpp-client
 *  文件: MsgUtils.java
 *
 *  文件创建时间: 2018年11月26日 17:47:21
 *  作者: 张虎威
 *
 *  Copyright (c) 2018 CMCC Corporation Limited.
 *  All rights reserved.
 *
 *  This software is the confidential and proprietary information of
 *  ZYHY Company. ("Confidential Information").  You shall not
 *  disclose such Confidential Information and shall use it only in
 *  accordance with the terms of the license.
 *
 */

package com.lpz.ussd.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 辅助工具类
 *
 * @author sunyiwei
 */
public class MsgUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(MsgUtils.class);

    /**
	 * 将字符串转化成bytes，长度不够时，右补0x00
	 * <p/>
	 * C-Octet String 的类型不足其指定位数如何补位 ==》 末尾填0x00（njkf\0\0\0\0\0\0\0 不够就填0x00）
	 * 
	 * VAR没有补0要求
	 *
	 * @param originStr    原始的字符串
	 * @param targetLength 目标的字节数
	 * @return
	 * @throws UnsupportedEncodingException
	 */
    public static byte[] getBytes(String originStr, int targetLength) {
    	if (originStr == null) {
    		return StringUtils.repeat('\0', targetLength).getBytes();
		}
        if (originStr.getBytes().length > targetLength) {
			LOGGER.error("原始字符串超过目标字符串...");
			return StringUtils.repeat('\0', targetLength).getBytes();
        }

		String appendStr = StringUtils.repeat('\0', targetLength - originStr.getBytes().length);
        return (originStr + appendStr).getBytes();
    }
}
