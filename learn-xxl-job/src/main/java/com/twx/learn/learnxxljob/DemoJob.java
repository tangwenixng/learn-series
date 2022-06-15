package com.twx.learn.learnxxljob;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.stereotype.Component;

@Component
public class DemoJob extends IJobHandler {
    /**
     * 1、简单任务示例（Bean模式）
     */
    @XxlJob("demoJobHandler")
    public ReturnT<String> execute(String s) throws Exception {
        XxlJobLogger.log("This is a demo job.args: {}", s);
        Thread.sleep(5 * 1000L);
        return SUCCESS;
    }
}
