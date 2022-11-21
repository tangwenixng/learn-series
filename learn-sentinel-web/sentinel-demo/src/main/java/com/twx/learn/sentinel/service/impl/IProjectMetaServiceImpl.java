package com.twx.learn.sentinel.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.twx.learn.sentinel.common.exceptions.AccessLimitException;
import com.twx.learn.sentinel.service.IProjectMetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author tangwx@soyuan.com.cn
 * @date 2020/4/15 22:29
 */
@Service
public class IProjectMetaServiceImpl implements IProjectMetaService {

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    /**
     * sentinel入门示例,示例中的资源名是sentinel-demo,
     * 可以在sentinel-dashboard配置sentinel-demo的规则
     * 也可以在nacos中配置sentinel-demo的规则
     * 在nacos配置sentinel规则需要引入sentinel-datasource-nacos依赖
     * 流控规则示例请参考 src/main/resources/learn-sentinel.json
     * @return
     */
    @Override
    @SentinelResource(value = "sentinel-demo", blockHandler = "failHandler")
    public String sentinelDemo() {
        return "OK";
    }

    public String failHandler(BlockException ex) {
        ex.printStackTrace();
        if (ex != null) {
            FlowException fEx = (FlowException) ex;
            System.err.println("err occur "+fEx.getRule().getResource());
        }
        return "ex occur";
    }

    @Override
    public void register(String key, Integer count) {
        ValueOperations<String, String> valOps = redisTemplate.opsForValue();
        valOps.set(key, String.valueOf(count));
    }

    @SentinelResource(value = "projectMetaTest",blockHandler = "testBlockHandler")
    @Override
    public String test(String key) {
        ValueOperations<String, String> valOps = redisTemplate.opsForValue();
        if (redisTemplate.hasKey(key)) {
            Long decrement = valOps.decrement(key);
            if (decrement <= 0) {
                throw new AccessLimitException("调用次数已用完...");
            }
            return key+"您的业务逻辑处理完毕";
        }
        throw new AccessLimitException("未注册用户，不能访问...");
    }

    public String testBlockHandler(String name, BlockException be) {
        System.out.println("testBlockHandler");
        return "error";
    }
}
