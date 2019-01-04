package com.lpz.ussd.business;

/**
 * 客户端鉴权接口
 * 
 * @author lpz
 *
 */
public interface ClientAuthentication {

    /**
	 * 
	 * @param username
	 * @param pwd
	 * @return
	 */
	boolean authenticateClient(String accountName, String password);
}
