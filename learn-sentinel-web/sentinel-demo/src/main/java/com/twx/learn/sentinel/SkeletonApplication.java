package com.twx.learn.sentinel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** @MapperScan: mybatis-plus扫描mapper
 *  @EnableScheduling:开启定时调度
 * @author tangwx@soyuan.com.cn
 * @date 2020/4/15 15:22
 */
@SpringBootApplication
public class SkeletonApplication {
    public static void main(String[] args) {
        SpringApplication.run(SkeletonApplication.class, args);
    }
}
