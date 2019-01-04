package com.lpz.ussd.business.impl;

import com.lpz.ussd.business.ClientAuthentication;
import com.lpz.ussd.util.ConfigUtil;

/**
 * 
 * @author lpz
 *
 */
public class ClientAuthenticationImpl implements ClientAuthentication {

    private ConfigUtil configUtil;

    public void setConfigUtil(ConfigUtil configUtil) {
        this.configUtil = configUtil;
    }

    @Override
	public boolean authenticateClient(String accountName, String password) {
		if (!Boolean.valueOf(configUtil.getConfig("needValidate")))
			return true;
        String user = configUtil.getConfig("user");
        String pwd = configUtil.getConfig("password");
//        return Arrays.equals(ProtocelUtil.getAuthString(user, password, Ints.fromByteArray(timeStamp)), authenticatorSource);
		return user.equals(accountName) && pwd.equals(password);
    }
}
