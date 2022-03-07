package com.yomahub.liteflow.test.privateDelivery;

import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.core.FlowExecutorHolder;
import com.yomahub.liteflow.entity.data.DefaultSlot;
import com.yomahub.liteflow.entity.data.LiteflowResponse;
import com.yomahub.liteflow.property.LiteflowConfig;
import com.yomahub.liteflow.test.BaseTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.Set;

/**
 * 非spring环境下隐私投递的测试
 * @author Bryan.Zhang
 * @since 2.5.0
 */
public class PrivateDeliveryTest extends BaseTest {

    private static FlowExecutor flowExecutor;

    @BeforeClass
    public static void init(){
        LiteflowConfig config = new LiteflowConfig();
        config.setRuleSource("privateDelivery/flow.xml");
        flowExecutor = FlowExecutorHolder.loadInstance(config);
    }

    @Test
    public void testPrivateDelivery() throws Exception{
        LiteflowResponse<DefaultSlot> response = flowExecutor.execute2Resp("chain1", "arg");
        Set<Integer> set = response.getSlot().getData("testSet");
        Assert.assertTrue(response.isSuccess());
        Assert.assertEquals(100, set.size());
    }
}
