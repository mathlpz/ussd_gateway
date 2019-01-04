package com.lpz.ussd.business.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpz.ussd.business.ClientManager;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelMatcher;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;


/**
 * Title ：
 * Description :
 * Create Time: 14-7-11 上午10:46
 */
public class ClientManagerImpl implements ClientManager{

    Logger logger = LoggerFactory.getLogger(ClientManagerImpl.class);

    ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void registChannel(Channel channel) {
        channels.add(channel);
		logger.debug("groub size:{}", channels.size());
//		closeMatchChannels(channel);
    }

    @Override
    public Integer activeChannels() {
        return channels.size();
    }

    @Override
    public void sendMessageToMatchChannels(Object message, ChannelMatcher channelMatcher) {
		channels.writeAndFlush(message, channelMatcher);
    }

	@Override
	public void closeMatchChannels(ChannelMatcher channelMatcher) {
		channels.close(channelMatcher);
	}
}
