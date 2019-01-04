package com.lpz.ussd.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title ：配置管理工具
 * Description :用springTask实现周期性配置重加载
 * Create Time: 14-3-28 下午2:04
 */
public class ConfigUtil {

    private final static Logger logger = LoggerFactory.getLogger(ConfigUtil.class);

    private final ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<String, String>();
    private long fileLastModifyTime = 0;

	private static String configPath;

    public String getConfig(String name) {
        return cache.get(name.trim());
    }

    public void setConfig(String name, String value) {
        cache.put(name.trim(), value.trim());
    }

	public void loadConf() {
		//
		String pathRoot = System.getProperty("USSD_CONF_ROOT");
		if (pathRoot == null) {
			// 如果jvm传参没有值，则去系统环境变量中获取
			pathRoot = System.getenv("USSD_CONF_ROOT");
			if (pathRoot == null) {
				pathRoot = "";
			}
		}
		configPath = pathRoot + "/ussd/appconfig/server.properties";

		//
//		String configPath = ConfigUtil.class.getResource("/server.properties").getPath();
		File file = new File(configPath);
		logger.info("load config file: {}", file.getPath());
        if (!file.exists()) {
            logger.error("未找到配置文件!");
            return;
        }
        if (file.lastModified() == fileLastModifyTime) {
            return;
        }
        fileLastModifyTime = file.lastModified();
		//
		Properties properties = new Properties();
        try {
            InputStream fi = new FileInputStream(file);
            properties.load(fi);
            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                this.setConfig((String) entry.getKey(), (String) entry.getValue());
            }
            fi.close();
            logger.info("【配置更新】");
            logOutConfig();
        } catch (IOException e) {
            logger.error("load properties Error:{}", e);
        }
    }

    /**
     * 输出当前配置信息
     */
    private void logOutConfig() {
		logger.info("current config info is:【{}】", cache.toString());

    }
}