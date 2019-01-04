package test.ussd.mq;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.lpz.ussd.entity.MQBeginNotify;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import test.ussd.BaseJunit4Test;

/**
 * 
 * @author lpz
 *
 */
public class Producer extends BaseJunit4Test {
	
	public final static Logger logger = LoggerFactory.getLogger(Producer.class);

	public final static String QUEUE_NAME = "ussd_msg_queque";
	
	ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(5);
	ConnectionFactory factory;
	Connection connection;
	Channel channel;

	@Before
	public void beforeMethod() {
		logger.info("......before......");
		// 创建连接工厂
		factory = new ConnectionFactory();
		// 设置RabbitMQ相关信息
		factory.setHost("172.28.16.66");
		factory.setUsername("guest");
		factory.setPassword("guest");
		factory.setPort(5672);
		try {
			// 创建一个新的连接
			connection = factory.newConnection();
			logger.info("ConnectionFactory isOpen: {}", connection.isOpen());

			// 创建一个通道
			channel = connection.createChannel();
			logger.info("Channel isOpen: {}", channel.isOpen());

			// 声明一个队列
			channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Test
	public void produce() {
		//
		int count = 10;
		for (int i = 10; i < count; i++) {
			final int flag = i;
			newFixedThreadPool.submit(new Runnable() {
				@Override
				public void run() {
					MQBeginNotify begin = new MQBeginNotify();
					begin.setMessageId("aaabbbccclpz0001" + flag);
					begin.setMsIsdn("8615168426858");
					begin.setUssdString("好嗨哦！感觉人生已经达到了高潮。哈哈哈" + flag);
					// 发送消息到队列中
					String message = JSON.toJSONString(begin);
					try {
						channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
						logger.info("Producer Send: " + message);
					} catch (UnsupportedEncodingException e) {
						logger.error(e.getMessage(), e);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			});
		}

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}

	}

	@After
	public void closeMth() {
		// 关闭通道和连接
		try {
			channel.close();
			connection.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}


    public static void main(String[] args) throws IOException, TimeoutException {
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(20);
//		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 4, 0L, TimeUnit.MILLISECONDS,
//				new LinkedBlockingQueue<Runnable>());
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置RabbitMQ相关信息
		factory.setHost("172.28.16.66");
		factory.setUsername("guest");
		factory.setPassword("guest");
        factory.setPort(5672);
        //创建一个新的连接
        Connection connection = factory.newConnection();
        //创建一个通道
        Channel channel = connection.createChannel();
        // 声明一个队列
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		int count = 10;
		for (int i = 0; i < count; i++) {
			final int flag = i;
			newFixedThreadPool.submit(new Runnable() {
				@Override
				public void run() {
					MQBeginNotify begin = new MQBeginNotify();
					begin.setMessageId("aaabbbccclpz0001" + flag);
					begin.setMsIsdn("8615168426858");
					begin.setUssdString("好嗨哦！感觉人生已经达到了高潮。哈哈哈" + flag);

					// 发送消息到队列中
					String message = JSON.toJSONString(begin);
					try {
						channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
						logger.info("Producer Send: " + message);
					} catch (UnsupportedEncodingException e) {
						logger.error(e.getMessage(), e);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			});
		}
        //关闭通道和连接
        channel.close();
        connection.close();
    }
}