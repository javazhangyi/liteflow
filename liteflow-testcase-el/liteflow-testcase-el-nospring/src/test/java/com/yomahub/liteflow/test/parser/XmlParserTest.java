package com.yomahub.liteflow.test.parser;

import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.core.FlowExecutorHolder;
import com.yomahub.liteflow.flow.LiteflowResponse;
import com.yomahub.liteflow.property.LiteflowConfig;
import com.yomahub.liteflow.test.BaseTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 非spring环境的xml parser单元测试
 *
 * @author Bryan.Zhang
 * @since 2.5.0
 */
public class XmlParserTest extends BaseTest {

	private static FlowExecutor flowExecutor;

	@BeforeClass
	public static void init() {
		LiteflowConfig config = new LiteflowConfig();
		config.setRuleSource("parser/flow.el.xml");
		flowExecutor = FlowExecutorHolder.loadInstance(config);
	}

	// 测试无spring场景的xml parser
	@Test
	public void testXmlParser() {
		LiteflowResponse response = flowExecutor.execute2Resp("chain1", "arg");
		Assert.assertTrue(response.isSuccess());
	}

}
