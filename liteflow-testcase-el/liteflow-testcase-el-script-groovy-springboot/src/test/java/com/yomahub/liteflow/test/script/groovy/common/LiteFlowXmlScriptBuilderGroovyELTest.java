package com.yomahub.liteflow.test.script.groovy.common;

import cn.hutool.core.io.resource.ClassPathResource;
import com.yomahub.liteflow.builder.LiteFlowNodeBuilder;
import com.yomahub.liteflow.builder.el.LiteFlowChainELBuilder;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.enums.NodeTypeEnum;
import com.yomahub.liteflow.flow.LiteflowResponse;
import com.yomahub.liteflow.slot.DefaultContext;
import com.yomahub.liteflow.test.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LiteFlowXmlScriptBuilderGroovyELTest.class)
@EnableAutoConfiguration
public class LiteFlowXmlScriptBuilderGroovyELTest extends BaseTest {

	@Resource
	private FlowExecutor flowExecutor;

	/**
	 * 测试通过builder方式运行普通script节点，以file绝对路径的方式运行
	 */
	@Test
	public void testAbsoluteScriptFilePath() {
		String absolutePath = new ClassPathResource("classpath:builder/s2.groovy").getAbsolutePath();
		LiteFlowNodeBuilder.createNode()
			.setId("d")
			.setName("组件D")
			.setType(NodeTypeEnum.COMMON)
			.setClazz("com.yomahub.liteflow.test.script.groovy.common.cmp.DCmp")
			.build();
		LiteFlowNodeBuilder.createNode()
			.setId("s2")
			.setName("条件脚本S2")
			.setType(NodeTypeEnum.SWITCH_SCRIPT)
			.setFile(absolutePath)
			.build();
		LiteFlowNodeBuilder.createNode()
			.setId("a")
			.setName("组件A")
			.setType(NodeTypeEnum.COMMON)
			.setClazz("com.yomahub.liteflow.test.script.groovy.common.cmp.ACmp")
			.build();
		LiteFlowNodeBuilder.createNode()
			.setId("b")
			.setName("组件B")
			.setType(NodeTypeEnum.COMMON)
			.setClazz("com.yomahub.liteflow.test.script.groovy.common.cmp.BCmp")
			.build();

		LiteFlowChainELBuilder.createChain().setChainName("chain2").setEL("THEN(d,SWITCH(s2).to(a,b))").build();

		LiteflowResponse response = flowExecutor.execute2Resp("chain2", "arg1");
		DefaultContext context = response.getFirstContextBean();
		Assert.assertTrue(response.isSuccess());
		Assert.assertEquals("d[组件D]==>s2[条件脚本S2]==>a[组件A]", response.getExecuteStepStr());
	}

	// 测试通过builder方式运行普通script节点，以脚本文本的方式运行
	@Test
	public void testBuilderScript1() {
		LiteFlowNodeBuilder.createNode()
			.setId("a")
			.setName("组件A")
			.setType(NodeTypeEnum.COMMON)
			.setClazz("com.yomahub.liteflow.test.script.groovy.common.cmp.ACmp")
			.build();
		LiteFlowNodeBuilder.createNode()
			.setId("b")
			.setName("组件B")
			.setType(NodeTypeEnum.COMMON)
			.setClazz("com.yomahub.liteflow.test.script.groovy.common.cmp.BCmp")
			.build();
		LiteFlowNodeBuilder.createNode()
			.setId("c")
			.setName("组件C")
			.setType(NodeTypeEnum.COMMON)
			.setClazz("com.yomahub.liteflow.test.script.groovy.common.cmp.CCmp")
			.build();
		LiteFlowNodeBuilder.createScriptNode()
			.setId("s1")
			.setName("普通脚本S1")
			.setType(NodeTypeEnum.SCRIPT)
			.setScript("import cn.hutool.core.collection.ListUtil;" + "import java.util.stream.Collectors;"
					+ "Integer a=3;Integer b=2;defaultContext.setData(\"s1\",a*b);"
					+ "List<String> list = ListUtil.toList(\"a\", \"b\", \"c\");"
					+ "List<String> resultList = list.stream().map(s -> \"hello,\" + s).collect(Collectors.toList());"
					+ "defaultContext.setData(\"resultList\", resultList)")
			.build();

		LiteFlowChainELBuilder.createChain().setChainName("chain1").setEL("THEN(a,b,c,s1)").build();

		LiteflowResponse response = flowExecutor.execute2Resp("chain1", "arg1");
		DefaultContext context = response.getFirstContextBean();
		Assert.assertTrue(response.isSuccess());
		Assert.assertEquals(Integer.valueOf(6), context.getData("s1"));
		List<String> resultList = context.getData("resultList");
		Assert.assertEquals(3, resultList.size());
	}

	// 测试通过builder方式运行普通script节点，以file的方式运行
	@Test
	public void testBuilderScript2() {
		LiteFlowNodeBuilder.createNode()
			.setId("d")
			.setName("组件D")
			.setType(NodeTypeEnum.COMMON)
			.setClazz("com.yomahub.liteflow.test.script.groovy.common.cmp.DCmp")
			.build();
		LiteFlowNodeBuilder.createNode()
			.setId("s2")
			.setName("条件脚本S2")
			.setType(NodeTypeEnum.SWITCH_SCRIPT)
			.setFile("builder/s2.groovy")
			.build();
		LiteFlowNodeBuilder.createNode()
			.setId("a")
			.setName("组件A")
			.setType(NodeTypeEnum.COMMON)
			.setClazz("com.yomahub.liteflow.test.script.groovy.common.cmp.ACmp")
			.build();
		LiteFlowNodeBuilder.createNode()
			.setId("b")
			.setName("组件B")
			.setType(NodeTypeEnum.COMMON)
			.setClazz("com.yomahub.liteflow.test.script.groovy.common.cmp.BCmp")
			.build();

		LiteFlowChainELBuilder.createChain().setChainName("chain2").setEL("THEN(d,SWITCH(s2).to(a,b))").build();

		LiteflowResponse response = flowExecutor.execute2Resp("chain2", "arg1");
		DefaultContext context = response.getFirstContextBean();
		Assert.assertTrue(response.isSuccess());
		Assert.assertEquals("d[组件D]==>s2[条件脚本S2]==>a[组件A]", response.getExecuteStepStr());
	}

}
