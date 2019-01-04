package com.lpz.ussd;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Title ： Description : Create Time: 12-5-24 下午12:11
 *
 * @version 1.0
 * @author: tanyang
 */
public class MainStart {

//    static {
//		String logbackCfg = "./conf/logback.xml";
//		try {
//			// 初始化log配置
//			LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
//			JoranConfigurator configurator = new JoranConfigurator();
//			configurator.setContext(lc);
//			lc.reset();
//			URL url = MainStart.class.getResource(".");
//			configurator.doConfigure(logbackCfg);
//		} catch (Exception e) {
//			e.printStackTrace();
//        }
//	}

    public static void main(String[] args) {

//        ApplicationContext context = new FileSystemXmlApplicationContext("./conf/applicationcontex.xml");
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationcontext.xml");

    }

}
