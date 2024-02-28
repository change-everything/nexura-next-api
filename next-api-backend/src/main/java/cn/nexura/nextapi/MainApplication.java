package cn.nexura.nextapi;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 主类（项目启动入口）
 *
 * @author peiYP
 */
// 前端新增、修改接口时，支持修改请求参数
// 前端请求参数回显
// 前端接口页面新增响应示例模块
// 前端接口页面新增请求示例模块
// 后端返回响应结果时，使用统一结构
// 定义统一业务返回码并回显前端
@SpringBootApplication(exclude = {RedisAutoConfiguration.class})
@MapperScan("cn.nexura.nextapi.mapper")
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@EnableDubbo
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
        System.out.println("hello world");
    }

}
