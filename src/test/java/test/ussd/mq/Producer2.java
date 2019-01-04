package test.ussd.mq;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.lpz.ussd.entity.MQBeginNotify;

import test.ussd.BaseJunit4Test;

public class Producer2 extends BaseJunit4Test {

	public final static Logger logger = LoggerFactory.getLogger(Producer2.class);

	@Autowired
	AmqpTemplate amqpTemplate;

	ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(5);

	@Before
	public void beforeMethod2() {
		logger.info("......before2......");
	}

	/**
	 * 单个请求
	 */
	@Test
	public void produce00() {
		sendBean(0);
	}

	/**
	 * 一次性批量
	 */
	@Test
	public void produce0() {
//		for (int flag = 0; flag < 20; flag++) {
//			sendBean(flag);
//		}
		int flag = 1;
		while (true) {
			try {
				Thread.sleep(3);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}
			//
			sendBean(flag++);
		}
	}



	@Test
	public void produce1() {
		int count = 10;
		for (int i = 10; i < count; i++) {
			new Thread(new ThreadMsg(i)).start();
		}
	}

	/**
	 * 
	 */
	@Test
	public void produce2() {
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
					try {
						// 发送消息到队列中
						amqpTemplate.convertAndSend("ussd_msg_queque", begin);
						logger.info("Producer2 Send: " + begin);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			});
		}

//		try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			logger.error(e.getMessage(), e);
//		}

	}


	public void sendBean(int flag) {
		MQBeginNotify begin = new MQBeginNotify();
		begin.setMessageId("aaabbbccclpz000" + flag);
		begin.setMsIsdn("8615168426858");
		begin.setUssdString("好嗨哦！感觉人生已经达到了高潮。哈哈哈" + flag);
		try {
			// 发送消息到队列中
			amqpTemplate.convertAndSend("ussd_msg_queque", begin);
			logger.info("Producer0 Send: " + begin);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	class ThreadMsg implements Runnable {
		private int flag;
		ThreadMsg(int flag) {
			this.flag = flag;
		}
		@Override
		public void run() {
			MQBeginNotify begin = new MQBeginNotify();
			begin.setMessageId("aaabbbccclpz0001" + flag);
			begin.setMsIsdn("8615168426858");
			begin.setUssdString("好嗨哦！感觉人生已经达到了高潮。哈哈哈" + flag);
			try {
				// 发送消息到队列中
				amqpTemplate.convertAndSend("ussd_msg_queque", begin);
				logger.info("Producer1 Send: " + begin);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

}
