package test.ussd.mq;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.lpz.ussd.util.ConfigUtil;

import test.ussd.BaseJunit4Test;

/**
 * 
 * @author lpz
 *
 */
public class GatewayTest extends BaseJunit4Test {
	
	@Autowired
	private ConfigUtil configUtil;

	@Before
	public void beforeMethod() {
		System.out.println("......before......");
	}

	@Test
	@Transactional // 标明此方法需使用事务
	@Rollback(false) // 标明使用完此方法后事务不回滚,true时为回滚
	public void test() {

	}

	@Test
	public void testOut() {
		System.out.println("1234567890");
	}

	@Test
	public void configUtilConfig() {
		String config = configUtil.getConfig("user");
		System.out.println(config);
		System.out.println(configUtil.getConfig("password"));
	}
}