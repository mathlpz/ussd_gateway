package com.lpz.ussd.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpz.ussd.business.handler.UssdDecoder;
import com.lpz.ussd.business.handler.UssdEncoder;
import com.lpz.ussd.business.handler.UssdServerHandler;
import com.lpz.ussd.util.ConfigUtil;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Title ：服务端口监听器
 * Description :
 * Create Time: 14-4-17 下午2:46
 */
public class ServerListener {

    Logger logger = LoggerFactory.getLogger(ServerListener.class);

    private ConfigUtil configUtil;
	// ChannelDuplexHandler
	private UssdServerHandler ussdServerHandler;

    public void startListener(){
        ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(new NioEventLoopGroup(), new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .option(ChannelOption.TCP_NODELAY,true)
                .childOption(ChannelOption.TCP_NODELAY,true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
						ChannelPipeline pipeline = ch.pipeline();
						Integer idleSeconds = Integer.valueOf(configUtil.getConfig("idleSeconds"));
						pipeline.addLast(new IdleStateHandler(0, 0, idleSeconds));
						pipeline.addLast("frameDecoder",
								new LengthFieldBasedFrameDecoder(4 * 1024, 0, 4, -4, 0, true));
						pipeline.addLast("ussdDecoder", new UssdDecoder());
						pipeline.addLast("ussdEncoder", new UssdEncoder());
						pipeline.addLast("ussdServerHandler", ussdServerHandler);
                    }
                });
        try {
            serverBootstrap.bind(Integer.parseInt(configUtil.getConfig("listenPort"))).sync();
			logger.info("Server Start Success, Listning on Port:【{}】", configUtil.getConfig("listenPort"));
        } catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
        }
    }

    public void setConfigUtil(ConfigUtil configUtil) {
        this.configUtil = configUtil;
    }

	public void setUssdServerHandler(UssdServerHandler ussdServerHandler) {
		this.ussdServerHandler = ussdServerHandler;
    }
}
