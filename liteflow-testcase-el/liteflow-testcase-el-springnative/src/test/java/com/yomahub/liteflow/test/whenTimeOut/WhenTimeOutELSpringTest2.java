package com.yomahub.liteflow.test.whenTimeOut;

import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import com.yomahub.liteflow.test.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * springboot环境下异步线程超时日志打印测试
 *
 * @author Bryan.Zhang
 * @since 2.6.4
 */
@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:/whenTimeOut/application2.xml")
public class WhenTimeOutELSpringTest2 extends BaseTest {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource
	private FlowExecutor flowExecutor;

	// 其中d,e,f都sleep 4秒，其中def是不同的组，超时设置5秒
	@Test
	public void testWhenTimeOut() throws Exception {
		LiteflowResponse response = flowExecutor.execute2Resp("chain1", "arg");
		Assert.assertTrue(response.isSuccess());
	}

}
