package com.twx.learn.asspringbootautoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value = AsProperties.AS_PREFIX)
public class AsProperties {
    public static final String AS_PREFIX = "soyuan.as";
    private boolean enable;
    private String env;
    private boolean devMode;
    private String proxyUrl;
    private int maxWeight;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public boolean isDevMode() {
        return devMode;
    }

    public void setDevMode(boolean devMode) {
        this.devMode = devMode;
    }

    public String getProxyUrl() {
        return proxyUrl;
    }

    public void setProxyUrl(String proxyUrl) {
        this.proxyUrl = proxyUrl;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }
}
