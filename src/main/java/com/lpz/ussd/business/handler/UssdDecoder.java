package com.lpz.ussd.business.handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpz.ussd.entity.Header;
import com.lpz.ussd.entity.Message;
import com.lpz.ussd.entity.UssdAbort;
import com.lpz.ussd.entity.UssdBegin;
import com.lpz.ussd.entity.UssdBind;
import com.lpz.ussd.entity.UssdEnd;
import com.lpz.ussd.entity.UssdShake;
import com.lpz.ussd.enums.UssdCommandID;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class UssdDecoder extends ByteToMessageDecoder {

	private static final Logger logger = LoggerFactory.getLogger(UssdDecoder.class.getName());

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
//      logger.debug("UssdDecoder:in {}. readables: {}", in, in.readableBytes());
		if (in.readableBytes() < 4) {
			return;
		}
		Header header = new Header();
		header.setCommandLength(in.readUnsignedInt());
		header.setCommandID(in.readUnsignedInt());
		header.setCommandStatus(in.readInt());
		header.setSenderCB(in.readUnsignedInt());
		header.setReceiverCB(in.readUnsignedInt());
		//
		UssdCommandID commandId = UssdCommandID.fromValue(header.getCommandID());
		Message msg = null;
		if (commandId != null) {
			switch (commandId) {
			case USSD_BIND:
				msg = new UssdBind(header);
				break;
			case USSD_SHAKE:
				msg = new UssdShake(header);
				break;
			case USSD_BEGIN:
				msg = new UssdBegin(header);
				break;
			case USSD_ABORT:
				msg = new UssdAbort(header);
				break;
			case USSD_END:
				msg = new UssdEnd(header);
				break;
//			case USSD_BIND_RESP:
//				msg = new UssdBindResp(header);
//				break;
//			case USSD_SHAKE_RESP:
//				msg = new UssdShakeResp(header);
//				break;
//			case USSD_CONTINUE:
//				msg = new UssdContinue(header);
//				break;
			// TODO:有很多消息定义的解码有待添加
			default:
				logger.error("Can't Decode this Message: {}, the ComandId is: {}--{}", in, header.getCommandID());
				break;
			}
		} else {
			logger.error("Can't Decode this Message: {}, the ComandId is: {}, the CommandStatus is: "
					+ header.getCommandStatus(), in, header.getCommandID());
		}
		// 如果还有消息体，则decode消息体body
		if (in.readableBytes() > 0 && msg != null) {
			msg.buildBodyFromBuf(in);
		}
		logger.debug("decode msg: {}.", msg);
		out.add(msg);

	}

}
