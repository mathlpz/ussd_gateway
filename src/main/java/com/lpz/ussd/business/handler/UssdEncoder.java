package com.lpz.ussd.business.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpz.ussd.entity.Message;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 
 * @author lpz
 *
 */
public class UssdEncoder extends MessageToByteEncoder<Message> {

	private static final Logger logger = LoggerFactory.getLogger(UssdEncoder.class.getName());

	@Override
	protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
		try {
			if (msg != null) {
				msg.writeMsgToBuf(out);
			}
			// 重置header中的totalLength为整个CMPP报文的长度
			out.setInt(0, out.readableBytes());
		} catch (Exception e) {
			logger.error("encode msg error: " + e.getMessage(), e);
		}
		logger.debug("encode msg: {}, out: {}, readable: " + out.readableBytes(), msg, out);
	}

}
