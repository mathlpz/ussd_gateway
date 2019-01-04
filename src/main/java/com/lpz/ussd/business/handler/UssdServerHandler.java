package com.lpz.ussd.business.handler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpz.ussd.business.ClientAuthentication;
import com.lpz.ussd.business.ClientManager;
import com.lpz.ussd.business.impl.ClientManagerImpl;
import com.lpz.ussd.entity.Header;
import com.lpz.ussd.entity.Message;
import com.lpz.ussd.entity.UssdBegin;
import com.lpz.ussd.entity.UssdBind;
import com.lpz.ussd.entity.UssdBindResp;
import com.lpz.ussd.entity.UssdContinue;
import com.lpz.ussd.entity.UssdShake;
import com.lpz.ussd.entity.UssdShakeResp;
import com.lpz.ussd.enums.UssdCommandID;
import com.lpz.ussd.enums.UssdCommandStatus;
import com.lpz.ussd.enums.UssdOpType;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

/**
 * 
 * @author lpz
 *
 */
@ChannelHandler.Sharable
public class UssdServerHandler extends ChannelDuplexHandler {

    Logger logger = LoggerFactory.getLogger(UssdServerHandler.class);

//    private FlowControl flowControl;
    private ExecutorService executorService = Executors.newFixedThreadPool(4);
    private ClientAuthentication clientAuthentication;
    private ClientManager clientManager;

	public void setClientManager(ClientManagerImpl clientManager) {
        this.clientManager = clientManager;
    }

    public void setClientAuthentication(ClientAuthentication clientAuthentication) {
        this.clientAuthentication = clientAuthentication;
    }

//    public void setFlowControl(FlowControl flowControl) {
//        this.flowControl = flowControl;
//    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		logger.info("Cinet:【{}】 closed Connection!", ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error("Handler异常! 异常信息:{}, 连接关闭!", cause.getMessage(), cause);
        ctx.close();
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("Received Connection From:【{}】", ctx.channel().remoteAddress());
    }

    @Override
	public void channelRead(final ChannelHandlerContext ctx, final Object obj) throws Exception {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
				Message msg = (Message) obj;
				Header header = msg.getHeader();
				UssdCommandID commandId = UssdCommandID.fromValue(header.getCommandID());
				switch (commandId) {
				case USSD_BIND:
					processBind(ctx, (UssdBind) msg);
					break;
				case USSD_SHAKE:
					processShake(ctx, (UssdShake) msg);
					break;
				case USSD_BEGIN:
					processSubmit(ctx, (UssdBegin) msg);
					break;
				case USSD_END:
					logger.info("=============USSD_END release: {}", msg);
					break;
				default:
					logger.error("未识别的请求消息：{}, UssdCommand: {}", msg, msg.getUssdCommand());
					break;
                }
            }
        });
    }

	/**
	 * 
	 */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		logger.info("Client idle time too long, close clinet:【{}】, evt: {}", ctx.channel().remoteAddress(), evt);
        ctx.close();
    }

	/**
	 * 登录请求
	 * 
	 * @param ctx
	 * @param connect
	 */
	private void processBind(ChannelHandlerContext ctx, UssdBind connect) {
		boolean loginSuccess = clientAuthentication.authenticateClient(connect.getAccountName(), connect.getPassword());
		//
        Header header = new Header();
		header.setCommandStatus(
				loginSuccess ? UssdCommandStatus.CODE_0.getCode() : UssdCommandStatus.CODE_1001.getCode());
		header.setReceiverCB(connect.getHeader().getSenderCB());
		UssdBindResp connectResp = new UssdBindResp(header);
		connectResp.setAccountName(connect.getAccountName());
        ctx.writeAndFlush(connectResp);
		//
        if (!loginSuccess) {
			logger.info("disabled Connection Form:【{}】, Closed! ", ctx.channel().remoteAddress().toString());
            ctx.close();
        }else {
            clientManager.registChannel(ctx.channel());
            logger.info("Connection From:【{}】login Success!", ctx.channel().remoteAddress().toString());
        }
    }

	/**
	 * 心跳握手请求
	 * 
	 * @param ctx
	 * @param activeTest
	 */
	private void processShake(ChannelHandlerContext ctx, UssdShake activeTest) {
		logger.info("Received Heartbeat From:【{}】......", ctx.channel().remoteAddress());
		UssdShakeResp resp = new UssdShakeResp();
        ctx.writeAndFlush(resp);
    }

	/**
	 * 处理提交请求，UssdBegin
	 * 
	 * @param ctx
	 * @param submit
	 */
	private void processSubmit(ChannelHandlerContext ctx, UssdBegin submit) {
		logger.info("-----------response to ussd client success------------SenderCB:{}, Received Submit: {}",
				submit.getHeader().getSenderCB(), submit);
		// 响应Continue(Response)
		Header header = new Header();
		header.setCommandStatus(UssdCommandStatus.CODE_0.getCode());
		header.setReceiverCB(submit.getHeader().getSenderCB());
		UssdContinue resp = new UssdContinue(header);
//		resp.setUssdVersion((short) UssdVersion.PHASEII.getValue());
		resp.setUssdVersion(submit.getUssdVersion());
		resp.setUssdOpType((short) UssdOpType.RESPONSE.getValue());
		resp.setMsIsdn(submit.getMsIsdn());
		resp.setServiceCode(null);
//		resp.setCodeScheme(UssdCodeScheme.CODE_SCHEME_16_2.getValue());
		resp.setCodeScheme(submit.getCodeScheme());
		resp.setUssdString(null);
        ctx.writeAndFlush(resp);
    }

}
