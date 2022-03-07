package com.yomahub.liteflow.test.cmpRetry;

import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.core.FlowExecutorHolder;
import com.yomahub.liteflow.entity.data.DefaultSlot;
import com.yomahub.liteflow.entity.data.LiteflowResponse;
import com.yomahub.liteflow.property.LiteflowConfig;
import com.yomahub.liteflow.test.BaseTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.annotation.Resource;


/**
 * 测试springboot下的节点执行器
 * @author Bryan.Zhang
 * @since 2.5.10
 */
public class LiteflowRetryTest extends BaseTest {

    private static FlowExecutor flowExecutor;

    @BeforeClass
    public static void init(){
        LiteflowConfig config = new LiteflowConfig();
        config.setRuleSource("cmpRetry/flow.xml");
        config.setRetryCount(3);
        config.setSlotSize(512);
        flowExecutor = FlowExecutorHolder.loadInstance(config);
    }

    //全局重试配置测试
    @Test
    public void testRetry1() {
        LiteflowResponse<DefaultSlot> response = flowExecutor.execute2Resp("chain1", "arg");
        Assert.assertTrue(response.isSuccess());
        Assert.assertEquals("a==>b==>b==>b", response.getSlot().getExecuteStepStr());
    }

    //单个组件重试配置测试
    @Test
    public void testRetry2() {
        LiteflowResponse<DefaultSlot> response = flowExecutor.execute2Resp("chain2", "arg");
        Assert.assertFalse(response.isSuccess());
        Assert.assertEquals("c==>c==>c==>c==>c==>c", response.getSlot().getExecuteStepStr());
    }

    //单个组件指定异常，但抛出的并不是指定异常的场景测试
    @Test
    public void testRetry3() {
        LiteflowResponse<DefaultSlot> response = flowExecutor.execute2Resp("chain3", "arg");
        Assert.assertFalse(response.isSuccess());
    }

    //单个组件指定异常重试，抛出的是指定异常或者
    @Test
    public void testRetry4() {
        LiteflowResponse<DefaultSlot> response = flowExecutor.execute2Resp("chain4", "arg");
        Assert.assertFalse(response.isSuccess());
        Assert.assertEquals("e==>e==>e==>e==>e==>e", response.getSlot().getExecuteStepStr());
    }
}
