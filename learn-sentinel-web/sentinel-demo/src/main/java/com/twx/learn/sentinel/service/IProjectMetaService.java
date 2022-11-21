package com.twx.learn.sentinel.service;

/**
 * @author tangwx@soyuan.com.cn
 * @date 2020/4/15 22:29
 */
public interface IProjectMetaService {

    String sentinelDemo();

    String test(String key);

    void register(String key,Integer count);
}
