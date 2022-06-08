package com.twx.learn.learnsatoken;

import cn.dev33.satoken.listener.SaTokenListener;
import cn.dev33.satoken.stp.SaLoginModel;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class MySaTokenListener implements SaTokenListener, InitializingBean, SmartInitializingSingleton, ApplicationContextAware {
    @Override
    public void doLogin(String loginType, Object loginId, SaLoginModel loginModel) {
        System.out.println("doLogin=>"+loginId);
    }

    @Override
    public void doLogout(String loginType, Object loginId, String tokenValue) {

    }

    @Override
    public void doKickout(String loginType, Object loginId, String tokenValue) {
        System.out.println("doKickout=>"+loginId+" "+tokenValue);
    }

    @Override
    public void doReplaced(String loginType, Object loginId, String tokenValue) {

    }

    @Override
    public void doDisable(String loginType, Object loginId, long disableTime) {

    }

    @Override
    public void doUntieDisable(String loginType, Object loginId) {

    }

    @Override
    public void doCreateSession(String id) {
        System.out.println("doCreateSession=>"+id);
    }

    @Override
    public void doLogoutSession(String id) {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("MySaTokenListener InitializingBean afterPropertiesSet");
    }

    @Override
    public void afterSingletonsInstantiated() {
        System.out.println("MySaTokenListener SmartInitializingSingleton afterSingletonsInstantiated");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("MySaTokenListener ApplicationContext applicationContext");
    }
}
